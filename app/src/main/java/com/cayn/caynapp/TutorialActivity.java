package com.cayn.caynapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class TutorialActivity extends AppCompatActivity {

    //declaracion de variable
    private TextView tvOmitir;
    private Button btContinuar;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);

        //Relacion front y back
        tvOmitir = (TextView) findViewById(R.id.tv_omitir);
        btContinuar = (Button) findViewById(R.id.btn_continuar);

        //configuraciones iniciales
        tvOmitir.setText(Html.fromHtml("<u>"+getString(R.string.txt_link_omitir_tutorial)+"</u>"));
        preferences = this.getSharedPreferences("sesion", Context.MODE_PRIVATE);
        editor = preferences.edit();

        //saltos de pantalla
        tvOmitir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor.putBoolean("tutorial", true);
                editor.apply();
                Intent i = new Intent(TutorialActivity.this, HomeActivity.class);
                startActivity(i);
                finish();
            }
        });
        btContinuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor.putBoolean("tutorial", true);
                editor.apply();
                Intent i = new Intent(TutorialActivity.this, HomeActivity.class);
                startActivity(i);
                finish();
            }
        });

    }

}