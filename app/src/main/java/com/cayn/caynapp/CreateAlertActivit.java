package com.cayn.caynapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.cayn.caynapp.modelos.Alertas;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class CreateAlertActivit extends AppCompatActivity
        implements
        OnMapReadyCallback,
        GoogleMap.OnMapClickListener,
        GoogleMap.OnMarkerDragListener{

    //declaracion variables
    private ImageButton btnGoBack, btnBackModal, btnCerrarModal;
    private Button btnCrearAlerta, btnAccept, btnCancel;
    private LinearLayout llModal;
    private EditText etCausa;

    private boolean hayAlerta = false;
    Alertas a = new Alertas();
    Calendar calendar = Calendar.getInstance();
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_alert);

        //union front y back
        btnGoBack = (ImageButton) findViewById(R.id.btn_go_back);
        btnBackModal = (ImageButton) findViewById(R.id.btn_back_modal);
        btnCerrarModal = (ImageButton) findViewById(R.id.ib_cerrar_modal);
        btnCrearAlerta = (Button) findViewById(R.id.btn_crear_alerta);
        btnAccept = (Button) findViewById(R.id.btn_aceptar);
        btnCancel = (Button) findViewById(R.id.btn_cancelar);
        etCausa = (EditText) findViewById(R.id.et_nombre_evento);
        llModal = (LinearLayout) findViewById(R.id.linearLayout7);

        //configuracion inicial
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //metodo inicial

        //logica botones
        btnGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CreateAlertActivit.this, AlertsActivity.class);
                startActivity(i);
                finish();
            }
        });
        //crear alerta
        btnCrearAlerta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etCausa.getText().toString().equals("")){
                    Toast.makeText(CreateAlertActivit.this, "Debes definir una causa para la alerta", Toast.LENGTH_SHORT).show();
                }else if(!hayAlerta){
                    Toast.makeText(CreateAlertActivit.this, "Debes definir un punto en el mapa para generar la alerta", Toast.LENGTH_SHORT).show();
                }else{
                    btnBackModal.setVisibility(View.VISIBLE);
                    btnCerrarModal.setVisibility(View.VISIBLE);
                    llModal.setVisibility(View.VISIBLE);
                    btnCrearAlerta.setEnabled(false);
                }
            }
        });
        //cerrar modal
        btnBackModal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnBackModal.setVisibility(View.GONE);
                btnCerrarModal.setVisibility(View.GONE);
                llModal.setVisibility(View.GONE);
                btnCrearAlerta.setEnabled(true);
            }
        });
        btnCerrarModal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnBackModal.setVisibility(View.GONE);
                btnCerrarModal.setVisibility(View.GONE);
                llModal.setVisibility(View.GONE);
                btnCrearAlerta.setEnabled(true);
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnBackModal.setVisibility(View.GONE);
                btnCerrarModal.setVisibility(View.GONE);
                llModal.setVisibility(View.GONE);
                btnCrearAlerta.setEnabled(true);
            }
        });
        //validar alerta
        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnBackModal.setVisibility(View.GONE);
                btnCerrarModal.setVisibility(View.GONE);
                llModal.setVisibility(View.GONE);
                btnCrearAlerta.setEnabled(true);
                validarAlerta();
            }
        });
    }

    private void validarAlerta() {
        a.setCausa(etCausa.getText().toString());
        a.setHora(obtenerHoraActual("GMT-5"));
        a.setFecha(obtenerFechaActual("GMT-5"));
        db.collection("alertas")
                .add(a)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(CreateAlertActivit.this, "Alerta agregada con exito", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(CreateAlertActivit.this, AlertsActivity.class);
                        startActivity(i);
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("TAG", "onFailure: "+e);
                    }
                });

    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;

        LatLng unab = new LatLng(7.117325628520283, -73.10513605545792);
        LatLng casa = new LatLng(7.124674422833709, -73.11479461019363);

        mMap.moveCamera(CameraUpdateFactory.newLatLng(casa));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(casa, 15), 1000, null);
        mMap.setOnMarkerDragListener(this);
        mMap.setOnMapClickListener(this);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }

    @Override
    public void onMapClick(@NonNull LatLng latLng) {
        mMap.clear();
        Geocoder geo = new Geocoder(getApplicationContext(), Locale.getDefault());
        Address address;
        try {
            address = geo.getFromLocation(latLng.latitude, latLng.longitude, 1).get(0);
            mMap.addMarker(new MarkerOptions().draggable(true)
                    .icon(BitmapFromVector(getApplicationContext(), R.drawable.ic_warning2))
                    .title(address.getAddressLine(0).split(",")[0])
                    .position(latLng));
            a.setLat(String.valueOf(latLng.latitude));
            a.setLon(String.valueOf(latLng.longitude));
            hayAlerta = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onMarkerDrag(@NonNull Marker marker) {

    }

    @Override
    public void onMarkerDragEnd(@NonNull Marker marker) {

    }

    @Override
    public void onMarkerDragStart(@NonNull Marker marker) {

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

}