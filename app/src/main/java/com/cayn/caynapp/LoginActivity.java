package com.cayn.caynapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class LoginActivity extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String title = "";
    private String idUser = "";

    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Relacion front y back
        //declaracion de variable
        TextView tvReg = (TextView) findViewById(R.id.tv_registrar);
        TextView tvRecCon = (TextView) findViewById(R.id.tv_recuperar_contrasenia);
        Button btnLogin = (Button) findViewById(R.id.btn_login);
        EditText etEmail = (EditText) findViewById(R.id.et_email);
        EditText etPass = (EditText) findViewById(R.id.et_password);

        //configuraciones iniciales
        preferences = this.getSharedPreferences("sesion", Context.MODE_PRIVATE);
        editor = preferences.edit();
        tvReg.setText(Html.fromHtml("<u>"+getString(R.string.txt_link_registrarse)+"</u>"));
        tvRecCon.setText(Html.fromHtml("<u>"+getString(R.string.txt_link_recuperar_contrasenia)+"</u>"));

        setUp();


        //cambios de pantalla
        //ir a registrarse
        tvReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(i);
                finish();
            }
        });
        //iniciar sesion
        btnLogin.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (etEmail.getText().toString().equals("") || etPass.getText().toString().equals("")){
                    Toast.makeText(LoginActivity.this, "Todos los campos tienen que estar llenos para continuar", Toast.LENGTH_LONG).show();
                }else if(etPass.getText().toString().length()<6){
                    Toast.makeText(LoginActivity.this, "La contraseña debe tener minimo 6 caracteres", Toast.LENGTH_LONG).show();
                }else{
                    FirebaseAuth.getInstance().signInWithEmailAndPassword(etEmail.getText().toString(), etPass.getText().toString())
                            .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                @Override
                                public void onSuccess(AuthResult authResult) {
                                    db.collection("users")
                                            .whereEqualTo("correo", etEmail.getText().toString())
                                            .get()
                                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                @Override
                                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                    if (task.isSuccessful()) {
                                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                                            title = document.getData().get("nombre").toString();
                                                            idUser = document.getId();
                                                        }
                                                        Intent i = new Intent(LoginActivity.this, TutorialActivity.class);
                                                        editor.putString("idUser", idUser);
                                                        editor.putString("nameUser", title);
                                                        editor.apply();
                                                        startActivity(i);
                                                        finish();
                                                    }
                                                }
                                            });
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(LoginActivity.this, "Usuario o contraseña incorrecto", Toast.LENGTH_LONG).show();
                                }
                            });
                }

            }
        }));


    }

    public void setUp(){
        if(this.preferences.getString("idUser", "").length()!=0 && this.preferences.getBoolean("tutorial", false)==true){
            Intent i = new Intent(LoginActivity.this, HomeActivity.class);
            startActivity(i);
        }else if (this.preferences.getString("idUser", "").length()!=0){
            Intent i = new Intent(LoginActivity.this, TutorialActivity.class);
            startActivity(i);
        }
    }
}

