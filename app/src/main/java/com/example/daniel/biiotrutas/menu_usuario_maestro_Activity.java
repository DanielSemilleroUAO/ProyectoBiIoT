package com.example.daniel.biiotrutas;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class menu_usuario_maestro_Activity extends AppCompatActivity {
    private Button agregar;
    private Button eliminar;
    private Button cerrar_sesion;
    String id_usuario= "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.panel_usuario_maestro);

        //Adquirir dato de la actividad menu_usuario_maestro
        Intent intent = getIntent();
        id_usuario = intent.getStringExtra("id");

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Importante");
        builder.setMessage("ID:"+id_usuario);
        builder.setPositiveButton("OK",null);
        builder.create();
        builder.show();

        agregar = (Button) findViewById(R.id.button);
        eliminar = (Button) findViewById(R.id.button2);
        cerrar_sesion = (Button) findViewById(R.id.button3);

        agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(menu_usuario_maestro_Activity.this, crear_usuario_Activity.class);
                intent.putExtra("id", id_usuario);
                startActivity(intent);
            }
        });
        eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(menu_usuario_maestro_Activity.this, eliminar_usuario_Activity.class);
                intent.putExtra("id", id_usuario);
                startActivity(intent);
            }
        });
        cerrar_sesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
