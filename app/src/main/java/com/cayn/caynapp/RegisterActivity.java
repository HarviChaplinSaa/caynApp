package com.cayn.caynapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

public class RegisterActivity extends AppCompatActivity {

    private int genero = 0;

    Map<String, Object> user = new HashMap<>();
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    SharedPreferences preferences;
    Calendar calendar = Calendar.getInstance();
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //Relacion front y back
        //Declaracion varaibles
        TextView tvLogin = (TextView) findViewById(R.id.tv_inicia_sesion);
        Button btnRegister = (Button) findViewById(R.id.btn_register);
        EditText etName = (EditText) findViewById(R.id.te_name);
        TextView tvEdad = (TextView) findViewById(R.id.tv_edad_edit);
        SeekBar sbEdad = (SeekBar) findViewById(R.id.db_edad);
        EditText etEmail = (EditText) findViewById(R.id.te_email);
        EditText etPass = (EditText) findViewById(R.id.te_password);
        EditText etPassAux = (EditText) findViewById(R.id.te_passwordAux);
        EditText etNit = (EditText) findViewById(R.id.te_nit);
        Spinner etGen = (Spinner) findViewById(R.id.sp_genero);

        //configuracion inicial
        tvLogin.setText(Html.fromHtml("<u>"+getString(R.string.txt_link_iniciar_sesion)+"</u>"));
        preferences = this.getSharedPreferences("sesion", Context.MODE_PRIVATE);
        editor = preferences.edit();
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.gen_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        etGen.setAdapter(adapter);

        //cambio de genero
        etGen.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
                tvEdad.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        //cambios de pantalla
        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(i);
                finish();
            }
        });
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(etName.getText().toString().equals("") || etEmail.getText().toString().equals("") || etPass.getText().toString().equals("") || etPassAux.getText().toString().equals("") || etNit.getText().toString().equals("") || genero == 0 ){
                    Toast.makeText(RegisterActivity.this, "Todos los campos tienen que estar llenos para continuar", Toast.LENGTH_LONG).show();
                }else{
                    if(etPass.getText().toString().length()<6){
                        Toast.makeText(RegisterActivity.this, "La contraseña debe tener mínimo 6 caracteres", Toast.LENGTH_LONG).show();
                    }else{
                        if(!etPass.getText().toString().equals(etPassAux.getText().toString())){
                            Toast.makeText(RegisterActivity.this, "Las contraseñas no coinciden", Toast.LENGTH_LONG).show();
                        }else{
                            FirebaseAuth.getInstance().createUserWithEmailAndPassword(etEmail.getText().toString(), etPass.getText().toString())
                                    .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                        @Override
                                        public void onSuccess(AuthResult authResult) {
                                            user.put("correo", etEmail.getText().toString());
                                            user.put("edad", String.valueOf(sbEdad.getProgress()));
                                            user.put("nombre", etName.getText().toString());
                                            if(genero == 1){
                                                user.put("sexo", "1");
                                            }else if(genero == 2){
                                                user.put("sexo", "0");
                                            }else{
                                                user.put("sexo", "2");
                                            }
                                            user.put("fecha", obtenerFechaActual("GMT-5"));
                                            DocumentReference doc = db.collection("users").document(etNit.getText().toString());
                                            doc.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                @Override
                                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                    if(task.isSuccessful()){
                                                        DocumentSnapshot userAux = task.getResult();
                                                        if(userAux.exists()){
                                                            Toast.makeText(RegisterActivity.this, "Ya existe una cuenta con este documento asociado: "+etNit.getText().toString(), Toast.LENGTH_LONG).show();
                                                        }else{
                                                            db.collection("users").document(etNit.getText()
                                                                    .toString())
                                                                    .set(user)
                                                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                        @Override
                                                                        public void onSuccess(Void unused) {
                                                                            Toast.makeText(RegisterActivity.this, "Usuario registrado con exito", Toast.LENGTH_SHORT).show();
                                                                            Intent i = new Intent(RegisterActivity.this, TutorialActivity.class);
                                                                            editor.putString("idUser", etNit.getText().toString());
                                                                            editor.putString("nameUser", etName.getText().toString());
                                                                            editor.apply();
                                                                            i.putExtra("title", etName.getText().toString());
                                                                            i.putExtra("idUser", etNit.getText().toString());
                                                                            startActivity(i);
                                                                            finish();
                                                                        }})
                                                                    .addOnFailureListener(new OnFailureListener() {
                                                                        @Override
                                                                        public void onFailure(@NonNull Exception e) {
                                                                            Toast.makeText(RegisterActivity.this, e.toString(), Toast.LENGTH_LONG).show();
                                                                        }
                                                                    });
                                                        }
                                                    }else{
                                                        Toast.makeText(RegisterActivity.this, "Ha ocurrido un error, intentalo más tarde", Toast.LENGTH_LONG).show();
                                                    }
                                                }
                                            });

                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(RegisterActivity.this, "Ya existe una cuenta con este correo asociado: "+etEmail.getText().toString(), Toast.LENGTH_LONG).show();
                                        }
                                    });
                        }
                    }


                }
            }
        });

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
}