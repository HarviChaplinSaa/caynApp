package com.cayn.caynapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.cayn.caynapp.adapters.PuntoRutaAdapter;
import com.cayn.caynapp.directionhelpers.FetchURL;
import com.cayn.caynapp.directionhelpers.TaskLoadedCallback;
import com.cayn.caynapp.modelos.Evento;
import com.cayn.caynapp.modelos.Ruta;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.security.KeyPairGenerator;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class DetailEventActivity extends AppCompatActivity
        implements
        OnMapReadyCallback,
        TaskLoadedCallback {

    //variables
    private TextView tvNombreCreador, tvDescripcion, tvFechaInicio, tvFechaFin, tvLugarSalida, tvLugarLlegada;
    private ListView lvRutas;
    private ImageButton ibGoBack, btnBackModal, btnCerrarModal;
    private Button btnAsitir, btnAccept, btnCancel;
    private LinearLayout llModal;

    private boolean asistir=false;
    private Evento evento;
    private String idUsuario = "";
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    SharedPreferences preferences;

    private LatLng origen = null;
    private Polyline currentPolyline;
    private LatLng destino = null;
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_event);

        //uniont front y back
        tvNombreCreador = (TextView) findViewById(R.id.tv_nombre_creador);
        tvDescripcion = (TextView) findViewById(R.id.tv_descripcion);
        tvFechaInicio = (TextView) findViewById(R.id.tv_fecha_inicio);
        tvFechaFin = (TextView) findViewById(R.id.tv_fecha_fin);
        tvLugarSalida = (TextView) findViewById(R.id.tv_lugar_salida);
        tvLugarLlegada = (TextView) findViewById(R.id.tv_lugar_llegada);
        lvRutas = (ListView) findViewById(R.id.lv_rutas);
        ibGoBack = (ImageButton) findViewById(R.id.btn_go_back);
        btnBackModal = (ImageButton) findViewById(R.id.btn_back_modal);
        btnCerrarModal = (ImageButton) findViewById(R.id.ib_cerrar_modal);
        btnAsitir = (Button) findViewById(R.id.btn_principal_evento);
        btnAccept = (Button) findViewById(R.id.btn_aceptar);
        btnCancel = (Button) findViewById(R.id.btn_cancelar);
        llModal = (LinearLayout) findViewById(R.id.linearLayout7);

        //configuracion inicial
        preferences = this.getSharedPreferences("sesion",Context.MODE_PRIVATE);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //metodo inicial
        setUp();

        //logica botones
        //volver pantalla anterior
        ibGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnBackModal.setVisibility(View.GONE);
                btnCerrarModal.setVisibility(View.GONE);
                llModal.setVisibility(View.GONE);
                btnAsitir.setEnabled(true);
                Intent i = new Intent(DetailEventActivity.this, EventActivity.class);
                startActivity(i);
                finish();
            }
        });
        //asistir o salir del evento
        btnAsitir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnBackModal.setVisibility(View.VISIBLE);
                btnCerrarModal.setVisibility(View.VISIBLE);
                llModal.setVisibility(View.VISIBLE);
                btnAsitir.setEnabled(false);
            }
        });
        //cerrar modal
        btnBackModal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnBackModal.setVisibility(View.GONE);
                btnCerrarModal.setVisibility(View.GONE);
                llModal.setVisibility(View.GONE);
                btnAsitir.setEnabled(true);
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnBackModal.setVisibility(View.GONE);
                btnCerrarModal.setVisibility(View.GONE);
                llModal.setVisibility(View.GONE);
                btnAsitir.setEnabled(true);
            }
        });
        btnCerrarModal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnBackModal.setVisibility(View.GONE);
                btnCerrarModal.setVisibility(View.GONE);
                llModal.setVisibility(View.GONE);
                btnAsitir.setEnabled(true);
            }
        });
        //validar evento
        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnBackModal.setVisibility(View.GONE);
                btnCerrarModal.setVisibility(View.GONE);
                llModal.setVisibility(View.GONE);
                btnAsitir.setEnabled(true);
                asistir();
            }
        });

    }

    public void asistir(){
        List<String> asistentesAux = new ArrayList<>();
        int cantidad = Integer.parseInt(evento.getCantidad_asistentes());
        asistentesAux = evento.getId_asistentes();
        if(asistir){
            asistentesAux.remove(idUsuario);
            cantidad--;
            evento.setId_asistentes(asistentesAux);
            evento.setCantidad_asistentes(String.valueOf(cantidad));
            db.collection("eventos").document(evento.getId_evento())
                    .set(evento)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(DetailEventActivity.this, "Gracias por participar, esperamos verte pronto", Toast.LENGTH_SHORT).show();
                            btnAsitir.setText("ASISTIR AL EVENTO");
                            asistir= !asistir;
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(DetailEventActivity.this, "No se pudo realizar la acción, por favor intenta de nuevo", Toast.LENGTH_SHORT).show();
                        }
                    });
        }else{
            asistentesAux.add(idUsuario);
            cantidad++;
            evento.setId_asistentes(asistentesAux);
            evento.setCantidad_asistentes(String.valueOf(cantidad));
            db.collection("eventos").document(evento.getId_evento())
                    .set(evento)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(DetailEventActivity.this, "Bienvenido al evento", Toast.LENGTH_SHORT).show();
                            btnAsitir.setText("SALIR DEL EVENTO");
                            asistir= !asistir;
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(DetailEventActivity.this, "No se pudo realizar la acción, por favor intenta de nuevo", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
                Intent i = new Intent(DetailEventActivity.this, EventActivity.class);
                startActivity(i);
                finish();
    }

    public void setUp(){
        btnAsitir.setText("ASISTIR AL EVENTO");
        asistir=false;
        idUsuario = this.preferences.getString("idUser", "");
        evento = new Evento();
        evento = (Evento) getIntent().getExtras().getSerializable("evento");

        db.collection("users").document(evento.getId_creador())
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        tvNombreCreador.setText(task.getResult().get("nombre").toString());
                    }
                });

        for(String s: evento.getId_asistentes()){
            if(idUsuario.equals(s)){
                asistir=true;
                btnAsitir.setText("SALIR DEL EVENTO");
                break;
            }
        }
        tvDescripcion.setText(evento.getDescripcion());
        tvFechaInicio.setText(evento.getFecha_inicio()+" "+evento.getHora_inicio());
        tvFechaFin.setText(evento.getFecha_fin()+" "+evento.getHora_fin());

        JSONArray jsonArray = null;
        ArrayList<String> listDir = new ArrayList<String>();
        ArrayList<Address> listdata = new ArrayList<>();
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
        tvLugarSalida.setText(listdata.get(0).getAddressLine(0).split(",")[0]);
        tvLugarLlegada.setText(listdata.get(listdata.size()-1).getAddressLine(0).split(",")[0]);
        int aux = 0;
        for(Address dir: listdata){
                listDir.add(dir.getAddressLine(0).split(",")[0]);
        }
        PuntoRutaAdapter adapter = new PuntoRutaAdapter(DetailEventActivity.this, R.layout.item_list_ruta, listDir);
        lvRutas.setAdapter(adapter);

        db.collection("puntosInteres")
                .limit(30)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        Ruta r = new Ruta();
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                r.setLat(document.getData().get("latitud").toString());
                                r.setLon(document.getData().get("longitud").toString());
                                r.setNombre(document.getData().get("nombre").toString());
                                LatLng latLng = new LatLng(Double.parseDouble(r.getLat()), Double.parseDouble(r.getLon()));
                                mMap.addMarker(new MarkerOptions()
                                        .icon(BitmapFromVector(getApplicationContext(), R.drawable.ic_interes2))
                                        .position(latLng)
                                        .title(r.getNombre()));
                            }
                        }
                    }
                });

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        JSONArray jsonArray = null;
        float maxZoom = 1f;
        try {
            jsonArray = new JSONArray(evento.getRuta());
            for (int i=0;i<jsonArray.length();i++){
                JSONObject el = new JSONObject(jsonArray.getString(i));
                if(i==0){
                    destino = new LatLng(Double.parseDouble(el.getString("lat")), Double.parseDouble(el.getString("lon")));
                    CameraUpdate point = CameraUpdateFactory.newLatLng(new LatLng(Double.parseDouble(el.getString("lat")), Double.parseDouble(el.getString("lon"))));
                    mMap.moveCamera(point);
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(Double.parseDouble(el.getString("lat")), Double.parseDouble(el.getString("lon"))), 12), 1000, null);
                    mMap.addMarker(new MarkerOptions()
                            .icon(BitmapFromVector(getApplicationContext(), R.drawable.ic_bike2))
                            .position(new LatLng(Double.parseDouble(el.getString("lat")), Double.parseDouble(el.getString("lon"))))
                            .title(i==0? "Salida" : i==jsonArray.length()-1? "Llegada" : "Parada: "+i));

                }else if(i==jsonArray.length()-1){
                    origen = destino;
                    destino = new LatLng(Double.parseDouble(el.getString("lat")), Double.parseDouble(el.getString("lon")));
                    mMap.addMarker(new MarkerOptions()
                            .icon(BitmapFromVector(getApplicationContext(), R.drawable.ic_bike2))
                            .position(new LatLng(Double.parseDouble(el.getString("lat")), Double.parseDouble(el.getString("lon"))))
                            .title(i==0? "Salida" : i==jsonArray.length()-1? "Llegada" : "Parada: "+i));
                    String url = getUrl(origen, destino, "driving");
                    new FetchURL(DetailEventActivity.this).execute(url, "driving");
                }else{
                    origen = destino;
                    destino = new LatLng(Double.parseDouble(el.getString("lat")), Double.parseDouble(el.getString("lon")));
                    mMap.addMarker(new MarkerOptions()
                            .icon(BitmapFromVector(getApplicationContext(), R.drawable.ic_baseline_directions_bike_24))
                            .position(new LatLng(Double.parseDouble(el.getString("lat")), Double.parseDouble(el.getString("lon"))))
                            .title(i==0? "Salida" : i==jsonArray.length()-1? "Llegada" : "Parada: "+i));
                    String url = getUrl(origen, destino, "driving");
                    new FetchURL(DetailEventActivity.this).execute(url, "driving");
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private String getUrl(LatLng origin, LatLng dest, String directionMode) {
        String str_origin = "origin=" + origin.latitude + ","+ origin.longitude;
        String str_des = "destination=" + dest.latitude + "," + dest.longitude;
        String mode = "mode=" + directionMode;
        String parameters = str_origin + "&" + str_des + "&" + mode;
        String output = "json";
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters + "&key=" + getString(R.string.api);
        return url;
    };

    private BitmapDescriptor BitmapFromVector(Context context, int vectorResId) {
        // below line is use to generate a drawable.
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorResId);

        // below line is use to set bounds to our vector drawable.
        vectorDrawable.setBounds(0, 0, vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight());

        // below line is use to create a bitmap for our
        // drawable which we have added.
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);

        // below line is use to add bitmap in our canvas.
        Canvas canvas = new Canvas(bitmap);

        // below line is use to draw our
        // vector drawable in canvas.
        vectorDrawable.draw(canvas);

        // after generating our bitmap we are returning our bitmap.
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }


    @Override
    public void onTaskDone(Object... values) {
        currentPolyline = mMap.addPolyline((PolylineOptions) values[0]);
    }
}