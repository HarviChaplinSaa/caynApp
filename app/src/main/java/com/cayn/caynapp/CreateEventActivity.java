package com.cayn.caynapp;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.cayn.caynapp.directionhelpers.FetchURL;
import com.cayn.caynapp.directionhelpers.TaskLoadedCallback;
import com.cayn.caynapp.modelos.Evento;
import com.cayn.caynapp.modelos.Ruta;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.TypeFilter;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class CreateEventActivity extends AppCompatActivity
        implements OnMapReadyCallback,
        GoogleMap.OnMarkerClickListener,
        GoogleMap.OnMapClickListener,
        GoogleMap.OnMarkerDragListener,
        TaskLoadedCallback {

    //declaracion variable
    private ImageButton ibGoBack, btnBackModal, btnCerrarModal;
    private EditText etNombreEvento, etDescripcion;
    private DatePicker dpFechaInicial, dpFechaFinal;
    private TimePicker tpHoraInicial, tpHoraFinal;
    private TextView tvFechaInicial, tvFechaFinal, btnHoraInicial, btnHoraFinal, tvModalConfirmacion;
    private Switch swTipoEvento;
    private Button btnCreateEvent, btnCancel, btnAccept;
    private LinearLayout llFechaInicial, llFechaFinal, llHoraInicial, llHoraFinal, llModal;


    private String horaInicial, horaFinal, fechaInicial, fechaFinal, idUser;
    private Evento evento;
    private Polyline currentPolyline;
    private List<Ruta> ruta = new ArrayList<>();
    private List<Ruta> puntosInteres = new ArrayList<>();
    private List<String> asistentes = new ArrayList<>();

    private LatLng origen = null;
    private LatLng destino = null;

    private GoogleMap mMap;
    private int contadorPunto=0;
    Calendar calendar = Calendar.getInstance();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    Location currentLocation;
    FusedLocationProviderClient fusedLocationProviderClient;
    SharedPreferences preferences;
    private static final int REQUEST_CODE=101;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);

        //union front y back
        ibGoBack = (ImageButton) findViewById(R.id.btn_go_back);
        etNombreEvento = (EditText) findViewById(R.id.et_nombre_evento);
        etDescripcion = (EditText) findViewById(R.id.et_descripcion);
        tvFechaInicial = (TextView) findViewById(R.id.tv_fecha_evento_inicial);
        tvFechaFinal = (TextView) findViewById(R.id.tv_fecha_evento_final);
        tvModalConfirmacion = (TextView) findViewById(R.id.tv_texto_modal_confirmacion);
        dpFechaFinal = (DatePicker) findViewById(R.id.dp_fecha_final);
        dpFechaInicial = (DatePicker) findViewById(R.id.dp_fecha_inicial);
        swTipoEvento = (Switch) findViewById(R.id.sw_tipo_evento);
        btnCreateEvent = (Button) findViewById(R.id.btn_principal_evento);
        btnCancel = (Button) findViewById(R.id.btn_cancelar);
        btnAccept = (Button) findViewById(R.id.btn_aceptar);
        llFechaInicial = (LinearLayout) findViewById(R.id.ll_fecha_inicial);
        llFechaFinal = (LinearLayout) findViewById(R.id.ll_fecha_final);
        llModal = (LinearLayout) findViewById(R.id.linearLayout7);
        llHoraFinal = (LinearLayout) findViewById(R.id.ll_hora_final);
        llHoraInicial = (LinearLayout) findViewById(R.id.ll_hora_inicial);
        tpHoraInicial = (TimePicker) findViewById(R.id.tp_hora_inicial);
        tpHoraFinal = (TimePicker) findViewById(R.id.tp_hora_final);
        btnHoraInicial = (TextView) findViewById(R.id.btn_hora_inicial);
        btnHoraFinal = (TextView) findViewById(R.id.btn_hora_final);
        btnBackModal = (ImageButton) findViewById(R.id.btn_back_modal);
        btnCerrarModal = (ImageButton) findViewById(R.id.ib_cerrar_modal);

        //configuracion inicial
        preferences = CreateEventActivity.this.getSharedPreferences("sesion", Context.MODE_PRIVATE);
        fusedLocationProviderClient= LocationServices.getFusedLocationProviderClient(this);
        getCurrentLocation();
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        Places.initialize(getApplicationContext(), getString(R.string.api));

        //metodo inicial
        setUp();

        //logica botones
        //volver atras
        ibGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CreateEventActivity.this, RoutesActivity.class);
                startActivity(i);
                finish();
            }
        });
        //crear evento
        btnCreateEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etNombreEvento.getText().toString().equals("") || etDescripcion.getText().toString().equals("") || contadorPunto == 0){
                    Toast.makeText(CreateEventActivity.this, "Todos los campos tienen que estar llenos para crear el evento", Toast.LENGTH_SHORT).show();
                }else if(contadorPunto == 1){
                    Toast.makeText(CreateEventActivity.this, "Deben haber mínimo dos puntos para poder crear la ruta", Toast.LENGTH_SHORT).show();
                }else{
                    btnBackModal.setVisibility(View.VISIBLE);
                    llModal.setVisibility(View.VISIBLE);
                    btnCerrarModal.setVisibility(View.VISIBLE);
                    btnCreateEvent.setEnabled(false);
                }
            }
        });
        //cerral modal confirmacion
        btnCerrarModal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnBackModal.setVisibility(View.GONE);
                llModal.setVisibility(View.GONE);
                btnCerrarModal.setVisibility(View.GONE);
                btnCreateEvent.setEnabled(true);
            }
        });
        btnBackModal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnBackModal.setVisibility(View.GONE);
                llModal.setVisibility(View.GONE);
                btnCerrarModal.setVisibility(View.GONE);
                btnCreateEvent.setEnabled(true);
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnBackModal.setVisibility(View.GONE);
                llModal.setVisibility(View.GONE);
                btnCerrarModal.setVisibility(View.GONE);
                btnCreateEvent.setEnabled(true);
            }
        });
        //validad evento
        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnBackModal.setVisibility(View.GONE);
                llModal.setVisibility(View.GONE);
                btnCerrarModal.setVisibility(View.GONE);
                btnCreateEvent.setEnabled(true);
                validateEvent();
            }
        });
        //abrir calendario seleccion fechas
        tvFechaInicial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etNombreEvento.setEnabled(false);
                etDescripcion.setEnabled(false);
                llFechaInicial.setVisibility(View.VISIBLE);
            }
        });
        tvFechaFinal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etNombreEvento.setEnabled(false);
                etDescripcion.setEnabled(false);
                llFechaFinal.setVisibility(View.VISIBLE);
            }
        });
        //cambiar fecha
        dpFechaInicial.setOnDateChangedListener(new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                llFechaInicial.setVisibility(View.GONE);
                llHoraInicial.setVisibility(View.VISIBLE);
                fechaInicial = dayOfMonth+"/"+(monthOfYear+1)+"/"+year;
            }
        });
        dpFechaFinal.setOnDateChangedListener(new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                llFechaFinal.setVisibility(View.GONE);
                llHoraFinal.setVisibility(View.VISIBLE);
                fechaFinal = dayOfMonth+"/"+(monthOfYear+1)+"/"+year;
            }
        });
        //cambiar hora
        tpHoraInicial.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                horaInicial=hourOfDay+":"+minute;
            }
        });
        tpHoraFinal.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                horaFinal=hourOfDay+":"+minute;
            }
        });
        //cerrar selector de hora
        btnHoraInicial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvFechaInicial.setText(fechaInicial+" "+horaInicial);
                etNombreEvento.setEnabled(true);
                etDescripcion.setEnabled(true);
                llHoraInicial.setVisibility(View.GONE);
            }
        });
        btnHoraFinal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvFechaFinal.setText(fechaFinal+" "+horaFinal);
                etNombreEvento.setEnabled(true);
                etDescripcion.setEnabled(true);
                llHoraFinal.setVisibility(View.GONE);
            }
        });
    }

    public void setUp(){
        idUser = preferences.getString("idUser", "");
        tvFechaInicial.setText(obtenerFechaActual("GMT-5")+" "+obtenerHoraActual("GMT-5"));
        fechaInicial = obtenerFechaActual("GMT-5");
        horaInicial = obtenerHoraActual("GMT-5");
        dpFechaInicial.setMinDate(calendar.getTimeInMillis());
        tvFechaFinal.setText(obtenerFechaActual("GMT-5")+" "+obtenerHoraActual("GMT-5"));
        fechaFinal = obtenerFechaActual("GMT-5");
        horaFinal = obtenerHoraActual("GMT-5");
        dpFechaFinal.setMinDate(calendar.getTimeInMillis());

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

    public void validateEvent(){
        evento = new Evento();
        evento.setNombre(etNombreEvento.getText().toString());
        evento.setDescripcion(etDescripcion.getText().toString());
        evento.setFecha_inicio(fechaInicial);
        evento.setFecha_fin(fechaFinal);
        evento.setHora_inicio(horaInicial);
        evento.setHora_fin(horaFinal);
        evento.setTipo(swTipoEvento.isChecked());
        evento.setId_asistentes(asistentes);
        evento.setCantidad_asistentes("0");
        evento.setImagen("https://upload.wikimedia.org/wikipedia/commons/thumb/a/a5/Google_Calendar_icon_%282020%29.svg/1200px-Google_Calendar_icon_%282020%29.svg.png");
        evento.setId_creador(idUser);
        evento.setRuta(convertirRutas());

        db.collection("eventos")
                .add(evento)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        btnBackModal.setVisibility(View.GONE);
                        llModal.setVisibility(View.GONE);
                        btnCerrarModal.setVisibility(View.GONE);
                        btnCreateEvent.setEnabled(true);
                        Toast.makeText(CreateEventActivity.this, "Evento creado con exito", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(CreateEventActivity.this, RoutesActivity.class);
                        startActivity(i);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("TAG", "onFailure: "+e);
                        Toast.makeText(CreateEventActivity.this, "No se pudo crear el evento, vuelve a intentarlo más tarde", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public String convertirRutas(){
        String rutaString = "[";
        int aux = 1;
        for(Ruta r: ruta){
            rutaString+=(r.toString().substring(4, r.toString().length()).replace("=", ":").replace("'", "").replace("lat", "\"lat\"").replace("lon", "\"lon\""));
            if(aux != ruta.size()){
                rutaString+=",";
            }
            aux++;
        }
        rutaString+="]";
        return rutaString;
    }

    @SuppressLint("SimpleDateFormat")
    public String obtenerFechaConFormato(String formato, String zonaHoraria) {
        Date date = this.calendar.getTime();
        SimpleDateFormat sdf;
        sdf = new SimpleDateFormat(formato);
        sdf.setTimeZone(TimeZone.getTimeZone(zonaHoraria));
        return sdf.format(date);
    }

    public String obtenerFechaActual(String zonaHoraria) {
        String formato = "yyyy-MM-dd";
        return obtenerFechaConFormato(formato, zonaHoraria);
    }

    public String obtenerHoraActual(String zonaHoraria) {
        String formato = "HH:mm:ss";
        return obtenerFechaConFormato(formato, zonaHoraria);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;

        LatLng unab = new LatLng(7.117325628520283, -73.10513605545792);
        LatLng casa = new LatLng(7.124674422833709, -73.11479461019363);

        mMap.moveCamera(CameraUpdateFactory.newLatLng(casa));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(casa, 15), 1000, null);
        mMap.setOnMarkerClickListener(this);
        mMap.setOnMapClickListener(this);
        mMap.setOnMarkerDragListener(this);
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


    private void getCurrentLocation(){
        if(ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
            return;
        }

        @SuppressLint("MissingPermission") Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if(location!=null){
                    currentLocation = location;
                    SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager()
                            .findFragmentById(R.id.map);
                    assert supportMapFragment != null;
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (REQUEST_CODE) {
            case REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getCurrentLocation();
                }
                break;
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }

    @Override
    public boolean onMarkerClick(@NonNull Marker marker) {

        return false;
    }

    @Override
    public void onMapClick(@NonNull LatLng latLng) {
        if(contadorPunto==0){
            destino = latLng;
            mMap.addMarker(new MarkerOptions().draggable(true)
                .icon(BitmapFromVector(getApplicationContext(), R.drawable.ic_bike2))
                .title("Salida")
                .position(latLng));
        }else{
            origen = destino;
            destino = latLng;
            mMap.addMarker(new MarkerOptions().draggable(true)
                    .icon(BitmapFromVector(getApplicationContext(), R.drawable.ic_baseline_directions_bike_24))
                    .title("parada: "+contadorPunto)
                    .position(latLng));
            String url = getUrl(origen, destino, "driving");
            new FetchURL(CreateEventActivity.this).execute(url, "driving");
        }
        Ruta r = new Ruta();
        r.setLat(String.valueOf(latLng.latitude));
        r.setLon(String.valueOf(latLng.longitude));
        ruta.add(r);
        contadorPunto++;
    }

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
    public void onTaskDone(Object... values) {
        currentPolyline = mMap.addPolyline((PolylineOptions) values[0]);
    }

    @Override
    public void onMarkerDrag(@NonNull Marker marker) {
    }

    @Override
    public void onMarkerDragEnd(@NonNull Marker marker) {
        currentPolyline.remove();
        destino = marker.getPosition();
        String url = getUrl(origen, destino, "driving");
        new FetchURL(CreateEventActivity.this).execute(url, "driving");
    }

    @Override
    public void onMarkerDragStart(@NonNull Marker marker) {
    }
}