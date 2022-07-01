package com.cayn.caynapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.cayn.caynapp.modelos.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

public class ProfileActivity extends AppCompatActivity {

    //Declaracion variables
    private ImageButton ibGoBack, ivEditUser, ibCloseModal, btnBackModal, btnCerrarModal;
    private TextView tvNombre, tvSexo, tvEdad, tvCorreo, tvEdadEdit;
    private EditText etName, etCorreo;
    private Spinner spGenero;
    private SeekBar sbEdad;
    private Button btnEditar, btnAccept, btnCancel;
    private ScrollView svModal;
    private LinearLayout llModal;

    SharedPreferences preferences;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    private Usuario user = new Usuario();
    private int genero = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //union front y back
        ibGoBack = (ImageButton) findViewById(R.id.btn_go_back);
        ivEditUser = (ImageButton) findViewById(R.id.btn_edit_user);
        btnBackModal = (ImageButton) findViewById(R.id.btn_back_modal);
        btnCerrarModal = (ImageButton) findViewById(R.id.btn_cerrar_modal);
        tvSexo = (TextView) findViewById(R.id.tv_sexo);
        tvNombre = (TextView) findViewById(R.id.tv_nombre);
        tvCorreo = (TextView) findViewById(R.id.tv_correo);
        tvEdad = (TextView) findViewById(R.id.tv_edad);
        etName= (EditText) findViewById(R.id.et_nombre);
        etCorreo = (EditText) findViewById(R.id.et_correo);
        spGenero = (Spinner) findViewById(R.id.sp_genero);
        sbEdad = (SeekBar) findViewById(R.id.db_edad);
        tvEdadEdit = (TextView) findViewById(R.id.tv_edad_edit);
        ibCloseModal = (ImageButton) findViewById(R.id.ib_cerrar_modal);
        btnEditar = (Button) findViewById(R.id.btn_editar);
        btnAccept = (Button) findViewById(R.id.btn_aceptar);
        btnCancel = (Button) findViewById(R.id.btn_cancelar);
        svModal = (ScrollView) findViewById(R.id.sv_modal);
        llModal = (LinearLayout) findViewById(R.id.linearLayout7);

        //configuracion inicial
        preferences = ProfileActivity.this.getSharedPreferences("sesion", Context.MODE_PRIVATE);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.gen_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spGenero.setAdapter(adapter);


        //metodo inicial
        setUp(this.preferences.getString("idUser", ""));

        //cambio de genero
        spGenero.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                genero = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        //cambio de edad
        sbEdad.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                tvEdadEdit.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        //logica botones
        ibGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ProfileActivity.this, HomeActivity.class);
                startActivity(i);
                finish();
            }
        });
        //abrir boton editar botones
        ivEditUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etName.setText(user.getNombre());
                etCorreo.setText(user.getCorreo());
                sbEdad.setProgress(Integer.parseInt(user.getEdad()));
                svModal.setVisibility(View.VISIBLE);
            }
        });
        //boton cerrar modal
        ibCloseModal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                svModal.setVisibility(View.GONE);
            }
        });
        //editar informacion
        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnBackModal.setVisibility(View.VISIBLE);
                btnCerrarModal.setVisibility(View.VISIBLE);
                llModal.setVisibility(View.VISIBLE);
                btnEditar.setEnabled(false);
            }
        });
        //cerrar modal confirmacion
        btnBackModal.setOnClickListener(new View.OnClickListener() {
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
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnBackModal.setVisibility(View.GONE);
                btnCerrarModal.setVisibility(View.GONE);
                llModal.setVisibility(View.GONE);
                btnEditar.setEnabled(true);
            }
        });
        //validad informacion
        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editUser();
            }
        });
    }

    public void editUser(){
        user.setNombre(etName.getText().toString());
        user.setCorreo(etCorreo.getText().toString());
        user.setEdad(String.valueOf(sbEdad.getProgress()));
        user.setSexo(genero==1? 1 : genero==2? 0 : 2);
        db.collection("users").document(preferences.getString("idUser", ""))
                .set(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        svModal.setVisibility(View.GONE);
                        setUp(preferences.getString("idUser", ""));
                        Toast.makeText(ProfileActivity.this, "Informacion editada con exito", Toast.LENGTH_LONG).show();
                        btnBackModal.setVisibility(View.GONE);
                        btnCerrarModal.setVisibility(View.GONE);
                        llModal.setVisibility(View.GONE);
                        btnEditar.setEnabled(true);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ProfileActivity.this, e.toString(), Toast.LENGTH_LONG).show();
                    }
                });
    }

    public void setUp(String idUser){
        db.collection("users").document(idUser)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful()){
                            DocumentSnapshot document = task.getResult();
                            user.setNombre(document.getData().get("nombre").toString());
                            tvNombre.setText(user.getNombre());
                            user.setEdad(document.getData().get("edad").toString());
                            tvEdad.setText(user.getEdad());
                            sbEdad.setProgress(Integer.parseInt(user.getEdad()));
                            tvEdadEdit.setText(user.getEdad());
                            user.setCorreo(document.getData().get("correo").toString());
                            tvCorreo.setText(user.getCorreo());
                            user.setSexo(Integer.parseInt(document.getData().get("sexo").toString()));
                            tvSexo.setText(user.getSexo()==1? "Masculino" : user.getSexo()==0? "Femenino" : "Otro");
                            user.setFecha(document.getData().get("fecha").toString());
                        }
                    }
                });


    }
}