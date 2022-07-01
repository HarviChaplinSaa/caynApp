package com.cayn.caynapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.cayn.caynapp.HomeFragments.gallery.GalleryFragment;
import com.cayn.caynapp.HomeFragments.home.HomeFragment;
import com.cayn.caynapp.databinding.ActivityHomeBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class HomeActivity extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    private ActivityHomeBinding binding;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    //declaracion variables
    private TextView tvEditUser, tvNameDrawer, tvHeaderText, txtVerTodas;
    private ImageButton btnGoNotification, btnAlertas, btnRutas, btnInicio, btnEventos, btnInfo, btnDrawer;
    private Button btnCloseDrawer, btnDrHome, btnDrSos, btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarHome.toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        //union de variables
        btnDrHome = (Button) findViewById(R.id.btn_home);
        btnDrSos = (Button) findViewById(R.id.btn_sos);
        btnLogout = (Button) findViewById(R.id.btn_logout);
        tvEditUser = (TextView) findViewById(R.id.tv_editar_usuario);
        tvNameDrawer = (TextView) findViewById(R.id.tv_name_drawer);
        btnDrawer = (ImageButton) findViewById(R.id.btn_drawer);
        btnCloseDrawer = (Button) findViewById(R.id.btn_close_drawer);
        btnGoNotification = (ImageButton) findViewById(R.id.btn_notification);
        tvHeaderText = (TextView) findViewById(R.id.tv_headerText);
        btnAlertas = (ImageButton) findViewById(R.id.btn_alertas);
        btnRutas = (ImageButton) findViewById(R.id.btn_rutas);
        btnInicio = (ImageButton) findViewById(R.id.btn_inicio);
        btnEventos = (ImageButton) findViewById(R.id.btn_eventos);
        btnInfo = (ImageButton) findViewById(R.id.btn_info);
        txtVerTodas = (TextView) findViewById(R.id.txt_ver_todas);

        //configuraciones iniciales
        preferences = this.getSharedPreferences("sesion",Context.MODE_PRIVATE);
        editor = preferences.edit();
        tvEditUser.setText(Html.fromHtml("<u>"+getString(R.string.txt_link_editar_perfil)+"</u>"));
//        txtVerTodas.setText(Html.fromHtml("<u>"+"Ver todas"+"</u>"));

        //metodo inicial
        setUp(this.preferences.getString("nameUser", ""));

        //LOGICA BOTONES

        //ir hacia notificaciones
        btnGoNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(HomeActivity.this, NotificationActivity.class);
                startActivity(i);
                finish();
            }
        });
        //abrir menu lateral
        btnDrawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FrameLayout flDrawer = (FrameLayout) findViewById(R.id.app_drawer);
                flDrawer.setVisibility(View.VISIBLE);
            }
        });
        //cerrar barra lateral
        btnCloseDrawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeDrawer();
            }
        });
        //boton cerrar sesion
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            FirebaseAuth.getInstance().signOut();
            editor.putString("idUser", "");
            editor.putString("nameUser", "");
            editor.apply();
            Intent i = new Intent(HomeActivity.this, LoginActivity.class);
            startActivity(i);
            finish();
            }
        });
        //logica editar usuario
        tvEditUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(HomeActivity.this, ProfileActivity.class);
                startActivity(i);
            }
        });

        //MAINTABS y DRAWER
        btnDrHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            HomeFragment home = new HomeFragment();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.nav_host_fragment_content_home, home);
            ft.commit();
            closeDrawer();
            }
        });
        btnDrSos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            GalleryFragment sos = new GalleryFragment();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.nav_host_fragment_content_home, sos);
            ft.commit();
            closeDrawer();
            }
        });
        btnAlertas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(HomeActivity.this, AlertsActivity.class);
                startActivity(i);
            }
        });
        btnRutas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(HomeActivity.this, RoutesActivity.class);
                startActivity(i);
            }
        });
        btnInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HomeFragment home = new HomeFragment();
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.nav_host_fragment_content_home, home);
                ft.commit();
            }
        });
        btnEventos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(HomeActivity.this, EventActivity.class);
                startActivity(i);
            }
        });
        btnInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(HomeActivity.this, InfoActivity.class);
                startActivity(i);
            }
        });
    }

    private void setUp(String title) {
        tvNameDrawer.setText(title);
        tvHeaderText.setText(title);
        HomeFragment home = new HomeFragment();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.nav_host_fragment_content_home, home);
        ft.commit();
    }

    private void closeDrawer(){
        FrameLayout flDrawer = (FrameLayout) findViewById(R.id.app_drawer);
        flDrawer.setVisibility(View.GONE);
    }
}