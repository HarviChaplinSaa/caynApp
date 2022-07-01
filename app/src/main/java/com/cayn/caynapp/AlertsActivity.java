package com.cayn.caynapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

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
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cayn.caynapp.modelos.Alertas;
import com.cayn.caynapp.modelos.Ruta;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class AlertsActivity extends AppCompatActivity
        implements OnMapReadyCallback,
        GoogleMap.OnMarkerClickListener{

    //declaracion de variables
    private ImageButton btnGoBack, btnCerrarModal, btnAddAlert;
    private LinearLayout llModal;
    private TextView tvCausa, tvLugar, tvFecha, tvHora;

    private List<Alertas> alertasMapa = new ArrayList<>();
    private GoogleMap mMap;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alerts);

        //union front y back
        btnGoBack = (ImageButton) findViewById(R.id.btn_go_back);
        btnCerrarModal = (ImageButton) findViewById(R.id.ib_cerrar_modal);
        btnAddAlert = (ImageButton) findViewById(R.id.btn_add_alert);
        llModal = (LinearLayout) findViewById(R.id.ll_modal);
        tvCausa = (TextView) findViewById(R.id.tv_causa);
        tvLugar = (TextView) findViewById(R.id.tv_lugar);
        tvFecha = (TextView) findViewById(R.id.tv_fecha);
        tvHora = (TextView) findViewById(R.id.tv_hora);

        //configuracion inicial
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //metodo inicial
        setUp();

        //logica botones
        btnGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AlertsActivity.this, HomeActivity.class);
                startActivity(i);
                finish();
            }
        });
        //cerrar modal
        btnCerrarModal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llModal.setVisibility(View.GONE);
                btnCerrarModal.setVisibility(View.GONE);
                btnGoBack.setEnabled(true);
                btnAddAlert.setEnabled(true);
            }
        });
        //crear nueva alerta
        btnAddAlert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AlertsActivity.this, CreateAlertActivit.class);
                startActivity(i);
            }
        });
    }

    private void setUp() {
        db.collection("alertas")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Alertas a = new Alertas();
                                a.setCausa(document.getData().get("causa").toString());
                                a.setFecha(document.getData().get("fecha").toString());
                                a.setLon(document.getData().get("lon").toString());
                                a.setLat(document.getData().get("lat").toString());
                                a.setHora(document.getData().get("hora").toString());
                                LatLng latLng = new LatLng(Double.parseDouble(a.getLat()), Double.parseDouble(a.getLon()));
                                mMap.addMarker(new MarkerOptions()
                                        .icon(BitmapFromVector(getApplicationContext(), R.drawable.ic_warning2))
                                        .position(latLng)
                                        .title(a.getCausa()));
                                alertasMapa.add(a);
                            }
                        }
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
        mMap.setOnMarkerClickListener(this);

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }

    @Override
    public boolean onMarkerClick(@NonNull Marker marker) {
        Geocoder geo = new Geocoder(getApplicationContext(), Locale.getDefault());
        Address address;
        for(Alertas a : alertasMapa){
            if(a.getCausa().equals(marker.getTitle())){
                tvFecha.setText(a.getFecha());
                tvHora.setText(a.getHora());
                try {
                    address = geo.getFromLocation(Double.parseDouble(a.getLat()), Double.parseDouble(a.getLon()), 1).get(0);
                    tvLugar.setText(address.getAddressLine(0).split(",")[0]);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        tvCausa.setText(marker.getTitle());
        llModal.setVisibility(View.VISIBLE);
        btnCerrarModal.setVisibility(View.VISIBLE);
        btnGoBack.setEnabled(false);
        btnAddAlert.setEnabled(false);
        return false;
    }

    private BitmapDescriptor BitmapFromVector(Context context, int vectorResId) {
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorResId);
        vectorDrawable.setBounds(0, 0, vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight());
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }

}