package com.cayn.caynapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class NotificationActivity extends AppCompatActivity {

    //DECLARACION VARIABLES
    private ImageButton imGoBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        //union front y back
        imGoBack = (ImageButton) findViewById(R.id.btn_go_back);

        //logica botones
        imGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(NotificationActivity.this, HomeActivity.class);
                startActivity(i);
                finish();
            }
        });

    }
}