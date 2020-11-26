package com.example.daniel.biiotrutas;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class info_3_Activity extends AppCompatActivity {
    //Variables botones
    private ImageView adelante;
    private ImageView atras;
    private ImageView cerrar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_3);
        //Funciones de los botones
        adelante = (ImageView) findViewById(R.id.imageView9);
        adelante.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(info_3_Activity.this, info_1_Activity.class);
                startActivity(intent);
                finish();
            }
        });
        atras = (ImageView) findViewById(R.id.imageView8);
        atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(info_3_Activity.this, info_2_Activity.class);
                startActivity(intent);
                finish();
            }
        });
        cerrar = (ImageView) findViewById(R.id.imageView2);
        cerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}
