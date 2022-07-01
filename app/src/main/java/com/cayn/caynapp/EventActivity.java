package com.cayn.caynapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.cayn.caynapp.adapters.ListEventoAdapter;
import com.cayn.caynapp.adapters.ListEventoPropioAdapter2;
import com.cayn.caynapp.modelos.Evento;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EventActivity extends AppCompatActivity {

    //declaracion variables
    private ImageButton ibGoBack, ibInvertFilter;
    private RecyclerView rvEventosl;
    private Spinner spFiltro;

    ListEventoAdapter listAdapter; //adaptador
    private List<Evento> eventos; //eventos usuario
    private boolean ordenFiltro = true; //orden de a busqueda true(ascendente) false(descendente)
    private String tipoFiltro = "fecha_inicio";
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        //union front y back
        ibGoBack = (ImageButton) findViewById(R.id.btn_go_back);
        rvEventosl = (RecyclerView) findViewById(R.id.rv_eventos);
        spFiltro = (Spinner) findViewById(R.id.sp_filtro);
        ibInvertFilter = (ImageButton) findViewById(R.id.im_invertir_filtro);

        //configuracion inicial
        preferences = EventActivity.this.getSharedPreferences("sesion", Context.MODE_PRIVATE);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.filter_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spFiltro.setAdapter(adapter);

        //metodo inicial
        setUp();

        //cambio de tipo de filtro
        spFiltro.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                tipoFiltro = position==0? "fecha_inicio" : position==1? "nombre" : "cantidad_asistentes";
                if(ordenFiltro){
                    consultaAscendente(tipoFiltro);
                }else{
                    consultaDescendente(tipoFiltro);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        //logica botones
        ibGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(EventActivity.this, HomeActivity.class);
                startActivity(i);
                finish();
            }
        });
        //cambiar filtro
        ibInvertFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ordenFiltro = !ordenFiltro;
                if(ordenFiltro){
                    consultaAscendente(tipoFiltro);
                }else{
                    consultaDescendente(tipoFiltro);
                }
            }
        });

    }

    public void setUp(){
        eventos = new ArrayList<>();
    }

    public void consultaAscendente(String filtro){
        eventos = new ArrayList<>();
        db.collection("eventos")
                .orderBy(filtro, Query.Direction.ASCENDING)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){

                            for (QueryDocumentSnapshot user : task.getResult()) {
                                if(!user.getData().get("id_creador").equals(preferences.getString("idUser", ""))){
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
                                    eventos.add(evento);
                                }
                            }
                        }
                        listAdapter = new ListEventoAdapter(eventos);
                        rvEventosl.setAdapter(listAdapter);
                        rvEventosl.setLayoutManager(new LinearLayoutManager(EventActivity.this));
                        rvEventosl.setHasFixedSize(true);

                        //entrar al detalle del evento
                        listAdapter.setOnItemClickListener(new ListEventoAdapter.OnItemClickListener() {
                            @Override
                            public void OnItemClick(Evento miEvento) {
                                Intent i = new Intent(EventActivity.this, DetailEventActivity.class);
                                i.putExtra("evento", miEvento);
                                startActivity(i);
                            }
                        });
                    }
                });
    }

    public void consultaDescendente(String filtro){
        eventos = new ArrayList<>();
        db.collection("eventos")
                .orderBy(filtro, Query.Direction.DESCENDING)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for (QueryDocumentSnapshot user : task.getResult()) {
                                if(!user.getData().get("id_creador").equals(preferences.getString("idUser", ""))){
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
                                    eventos.add(evento);
                                }
                            }
                        }
                        listAdapter = new ListEventoAdapter(eventos);
                        rvEventosl.setAdapter(listAdapter);
                        rvEventosl.setLayoutManager(new LinearLayoutManager(EventActivity.this));
                        rvEventosl.setHasFixedSize(true);
                    }
                });
    }
}