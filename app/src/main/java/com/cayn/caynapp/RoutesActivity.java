package com.cayn.caynapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.cayn.caynapp.adapters.ListEventoPropioAdapter2;
import com.cayn.caynapp.adapters.PuntoRutaAdapter;
import com.cayn.caynapp.modelos.Evento;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class RoutesActivity extends AppCompatActivity {

    //declaracion variables
    private ImageButton btnGoBack, btnCrearEvento, btnCerrarModal;
    private Button btnEditEvento;
    private RecyclerView rvEventos;
    private ScrollView svModalEditEventos;
    private TextView tvEventName, tvEventDate, tvEventType, tvPuntoPartida, tvPuntoLlegada, tvSinEventos, tvDescripcion;
    private ListView lvPuntosRuta, lvComentarios;

    private List<Evento> eventosUsuario; //eventos usuario
    ListEventoPropioAdapter2 listAdapter; //adaptador
    private Evento eventoAux= null; //vento seleccionado

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routes);

        //union front y back
        btnGoBack = (ImageButton) findViewById(R.id.btn_go_back);
        rvEventos = (RecyclerView) findViewById(R.id.rv_eventos);
        btnCrearEvento = (ImageButton) findViewById(R.id.btn_add_event);
        btnCerrarModal = (ImageButton) findViewById(R.id.ib_cerrar_modal);
        btnEditEvento = (Button) findViewById(R.id.btn_editar);
        svModalEditEventos = (ScrollView) findViewById(R.id.sv_modal);
        tvEventName = (TextView) findViewById(R.id.tv_nombre_evento);
        tvEventDate = (TextView) findViewById(R.id.tv_fecha_evento);
        tvEventType = (TextView) findViewById(R.id.tv_tipo_evento);
        tvPuntoPartida = (TextView) findViewById(R.id.tv_lugar_inicial);
        tvPuntoLlegada = (TextView) findViewById(R.id.tv_punto_llegada);
        lvPuntosRuta = (ListView) findViewById(R.id.lv_ruta);
        tvSinEventos = (TextView) findViewById(R.id.tv_sin_eventos);
        tvDescripcion = (TextView) findViewById(R.id.tv_descripcion);

        //CONFIGURACION INICIAL
        preferences = RoutesActivity.this.getSharedPreferences("sesion", Context.MODE_PRIVATE);
        editor = preferences.edit();

        //metodo inicial
        setup(this.preferences.getString("idUser", ""));

        //LOGICA BOTONES

        //boton cerrar modal
        btnCerrarModal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                svModalEditEventos.setVisibility(View.GONE);
            }
        });
        //boton editar evento
        btnEditEvento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(RoutesActivity.this, EditRoute.class);
                try {
                    Evento eventoDefault = (Evento) getIntent().getExtras().getSerializable("evento");
                    i.putExtra("evento", eventoDefault);
                }catch (Exception e){
                    i.putExtra("evento", eventoAux);
                }
                startActivity(i);
                svModalEditEventos.setVisibility(View.GONE);
            }
        });
        //boton volver atras
        btnGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(RoutesActivity.this, HomeActivity.class);
                startActivity(i);
                finish();
            }
        });
        //boton crear nuevo evento
        btnCrearEvento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(RoutesActivity.this, CreateEventActivity.class);
                startActivity(i);
            }
        });
    }

    public void openDetail(Evento evento){
        JSONArray jsonArray = null;
        ArrayList<Address> listdata = new ArrayList<>();
        ArrayList<String> listDir = new ArrayList<String>();
        Geocoder geo = new Geocoder(getApplicationContext(), Locale.getDefault());
        try {
            jsonArray = new JSONArray(evento.getRuta());
            for (int i=0;i<jsonArray.length();i++){
                JSONObject el = new JSONObject(jsonArray.getString(i));
                listdata.add(geo.getFromLocation(Double.parseDouble(el.getString("lat")), Double.parseDouble(el.getString("lon")), 1).get(0));
            }
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
        tvEventName.setText(evento.getNombre().toUpperCase(Locale.ROOT));
        tvDescripcion.setText(evento.getDescripcion());
        tvEventDate.setText(evento.getFecha_inicio()+"    "+evento.getHora_inicio());
        tvPuntoPartida.setText(listdata.get(0).getAddressLine(0).split(",")[0]);
        tvPuntoLlegada.setText(listdata.get(listdata.size()-1).getAddressLine(0).split(",")[0]);
        tvEventType.setText(evento.getTipo()? "Privado: Obtener link" : "Público");
        listdata.remove(0);
        listdata.remove(listdata.size()-1);
        for(Address dir: listdata){ listDir.add(dir.getAddressLine(0).split(",")[0]); }
        PuntoRutaAdapter adapter = new PuntoRutaAdapter(RoutesActivity.this, R.layout.item_list_ruta, listDir);
        lvPuntosRuta.setAdapter(adapter);
        svModalEditEventos.setVisibility(View.VISIBLE);
    }

    public void setup(String idUser){

        //obtener objeto del intent
        try{
            Evento eventoDefault = (Evento) getIntent().getExtras().getSerializable("evento");
            openDetail(eventoDefault);
        }catch (Exception e){
            Log.e("TAG", "setup: "+e);
        }

        eventosUsuario = new ArrayList<>();
        db.collection("eventos")
                .whereEqualTo("id_creador", idUser)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for (QueryDocumentSnapshot user : task.getResult()) {
                                Evento evento = new Evento();
                                evento.setId_evento(user.getId());
                                evento.setDescripcion(user.getData().get("descripcion").toString());
                                evento.setFecha_fin(user.getData().get("fecha_fin").toString());
                                evento.setFecha_inicio(user.getData().get("fecha_inicio").toString());
                                evento.setHora_fin(user.getData().get("hora_fin").toString());
                                evento.setHora_inicio(user.getData().get("hora_inicio").toString());
                                List<String> idAsistentes = (ArrayList<String>) user.getData().get("id_asistentes");
                                evento.setId_asistentes(idAsistentes);
                                evento.setId_creador(user.getData().get("id_creador").toString());
                                evento.setImagen(user.getData().get("imagen").toString());
                                evento.setNombre(user.getData().get("nombre").toString());
                                evento.setRuta(user.getData().get("ruta").toString());
                                evento.setTipo((Boolean) user.getData().get("tipo"));
                                evento.setCantidad_asistentes(user.getData().get("cantidad_asistentes").toString());
                                eventosUsuario.add(evento);
                            }
                        }
                        if(eventosUsuario.size()==0){
                            tvSinEventos.setVisibility(View.VISIBLE);
                            rvEventos.setVisibility(View.GONE);
                        }
                        listAdapter = new ListEventoPropioAdapter2(eventosUsuario);
                        rvEventos.setAdapter(listAdapter);
                        rvEventos.setLayoutManager(new GridLayoutManager( RoutesActivity.this, 2));
                        rvEventos.setHasFixedSize(true);

                        //abrir modal detalle evento
                        listAdapter.setOnItemClickListener(new ListEventoPropioAdapter2.OnItemClickListener() {
                            @Override
                            public void OnItemClick(Evento miEvento) {
                                eventoAux = miEvento;
                                openDetail(miEvento);
                            }
                        });
                    }
                });
    }
}