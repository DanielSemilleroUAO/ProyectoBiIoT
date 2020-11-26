package com.example.daniel.biiotrutas;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    //Variables de botones
    private Button boton_1;
    private Button boton_2;
    private Button boton_3;
    private Button salir;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        boton_1 = (Button) findViewById(R.id.button); //Leer boton de inicion de sesión
        boton_2 = (Button) findViewById(R.id.button2); //Leer boton de ¿quien somos?
        boton_3 = (Button) findViewById(R.id.button3); //Leer boton de contactanos
        salir = (Button) findViewById(R.id.button5);
        //Acciones de los botones
        boton_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Ir a la actividad de iniciar sesión
                Intent intent = new Intent(MainActivity.this, iniciar_sesion_Activity.class);
                startActivity(intent);
            }
        });
        boton_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Ir a la actividad de información 1
                Intent intent = new Intent(MainActivity.this, info_1_Activity.class);
                startActivity(intent);
            }
        });
        boton_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Ir a la actividad de contactanos
                Intent intent = new Intent(MainActivity.this, contactanos_Activity.class);
                startActivity(intent);
            }
        });
        salir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Cerrar app
                finish();
            }
        });
    }
}