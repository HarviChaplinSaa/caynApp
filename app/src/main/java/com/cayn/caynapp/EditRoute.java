package com.cayn.caynapp;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.cayn.caynapp.modelos.Evento;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.FirebaseFirestore;

public class EditRoute extends AppCompatActivity {

    //declaracion de variables
    private TextInputEditText tvNombreEvento;
    private TextView tvEditarFecha, tvEditarRuta;
    private ImageButton ibGoBack, btnBackModal, btnCerrarModal;
    private DatePicker dpFecha;
    private LinearLayout ll1, ll2, ll3, ll4, llModal;
    private Switch swTipoEvento; //true es privado
    private Button btnEditar, btnAccept, btnCancel;

    Evento evento = null;//evento a editar
    private String fecha = "";

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_route);

        //UNION FRONT Y BACK
        tvNombreEvento = (TextInputEditText) findViewById(R.id.et_nombre_evento);
        tvEditarFecha = (TextView) findViewById(R.id.tv_fecha_evento);
        tvEditarRuta = (TextView) findViewById(R.id.tv_editar_rutas);
        ibGoBack = (ImageButton) findViewById(R.id.btn_go_back);
        btnBackModal = (ImageButton) findViewById(R.id.btn_back_modal);
        btnCerrarModal = (ImageButton) findViewById(R.id.ib_cerrar_modal);
        dpFecha = (DatePicker) findViewById(R.id.dp_fecha);
        ll1 = (LinearLayout) findViewById(R.id.linearLayout2);
        ll2 = (LinearLayout) findViewById(R.id.linearLayout3);
        ll3 = (LinearLayout) findViewById(R.id.linearLayout4);
        ll4 = (LinearLayout) findViewById(R.id.ll_tipo_evento);
        llModal = (LinearLayout) findViewById(R.id.linearLayout7);
        swTipoEvento = (Switch) findViewById(R.id.sw_tipo_evento);
        btnEditar = (Button) findViewById(R.id.btn_editar);
        btnAccept = (Button) findViewById(R.id.btn_aceptar);
        btnCancel = (Button) findViewById(R.id.btn_cancelar);

        //configuraciones iniciales
        tvEditarRuta.setText(Html.fromHtml("<u>"+"Editar"+"</u>"));

        //metodo inicial
        setUp();

        //logica botones
        //volver atras
        ibGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(EditRoute.this, RoutesActivity.class);
                startActivity(i);
                finish();
            }
        });
        //abrir calendario seleccionar fecha
        tvEditarFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvNombreEvento.setEnabled(false);
                dpFecha.setVisibility(View.VISIBLE);
                ll1.setVisibility(View.GONE);
                ll2.setVisibility(View.GONE);
                ll3.setVisibility(View.GONE);
                ll4.setVisibility(View.GONE);
            }
        });
        //editar evento
        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnBackModal.setVisibility(View.VISIBLE);
                btnCerrarModal.setVisibility(View.VISIBLE);
                llModal.setVisibility(View.VISIBLE);
                btnEditar.setEnabled(false);
            }
        });
        //cerrar modal confirmar
        btnBackModal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnBackModal.setVisibility(View.GONE);
                btnCerrarModal.setVisibility(View.GONE);
                llModal.setVisibility(View.GONE);
                btnEditar.setEnabled(true);
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnBackModal.setVisibility(View.GONE);
                btnCerrarModal.setVisibility(View.GONE);
                llModal.setVisibility(View.GONE);
                btnEditar.setEnabled(true);
            }
        });
        btnCerrarModal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnBackModal.setVisibility(View.GONE);
                btnCerrarModal.setVisibility(View.GONE);
                llModal.setVisibility(View.GONE);
                btnEditar.setEnabled(true);
            }
        });
        //validar ruta
        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editar();
            }
        });

        //LOGICA CAMBIO DE FECHA
        dpFecha.setOnDateChangedListener(new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker datePicker, int year, int month, int dayOfMont) {
                tvNombreEvento.setEnabled(true);
                dpFecha.setVisibility(View.GONE);
                ll1.setVisibility(View.VISIBLE);
                ll2.setVisibility(View.VISIBLE);
                ll3.setVisibility(View.VISIBLE);
                ll4.setVisibility(View.VISIBLE);
                fecha = dayOfMont+"/"+(month+1)+"/"+year;
                tvEditarFecha.setText(Html.fromHtml("<u>"+dayOfMont+"/"+(month+1)+"/"+year+"     "+evento.getHora_inicio()+"     Editar"+"</u>"));
            }
        });
    }

    public void editar(){
        evento.setNombre(tvNombreEvento.getText().toString());
        evento.setFecha_inicio(fecha);
        evento.setTipo(swTipoEvento.isChecked());
        db.collection("eventos").document(evento.getId_evento())
                .set(evento)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        btnBackModal.setVisibility(View.GONE);
                        btnCerrarModal.setVisibility(View.GONE);
                        llModal.setVisibility(View.GONE);
                        btnEditar.setEnabled(true);
                        Intent i = new Intent(EditRoute.this, RoutesActivity.class);
                        Toast.makeText(EditRoute.this, "Ruta editada con exito", Toast.LENGTH_LONG).show();
                        startActivity(i);
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(EditRoute.this, e.toString(), Toast.LENGTH_LONG).show();
                    }
                });
    }

    public void setUp(){
        try {
            evento = (Evento) getIntent().getExtras().getSerializable("evento");
        }catch (Exception e){
            Log.e("ERROR: ", "setUp: "+e);
        }
        tvNombreEvento.setText(evento.getNombre());
        fecha = evento.getFecha_inicio();
        tvEditarFecha.setText(Html.fromHtml("<u>"+evento.getFecha_inicio()+"     "+evento.getHora_inicio()+"     Editar"+"</u>"));

    }
}