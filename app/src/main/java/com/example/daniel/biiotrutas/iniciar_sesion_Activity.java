package com.example.daniel.biiotrutas;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.app.AlertDialog;
import android.widget.ImageView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

public class iniciar_sesion_Activity extends AppCompatActivity {
    private Button boton;
    private EditText contrasena;
    private ImageView cerrar;
    private EditText codigo;
    public String respuesta = "";
    String id = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inicio_sesion);
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        boton = (Button) findViewById(R.id.button4);
        contrasena = (EditText) findViewById(R.id.editText);

        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Consultar().execute("https://botesinteractivoscali.000webhostapp.com/enviar_datos_app.php?id="+contrasena.getText().toString());
                if(respuesta.equals("maestro")){
                    Intent intent = new Intent(iniciar_sesion_Activity.this, menu_usuario_maestro_Activity.class);
                    intent.putExtra("id", id);
                    startActivity(intent);
                }
                if(respuesta.equals("esclavo")){
                    Intent intent2 = new Intent(iniciar_sesion_Activity.this, panel_usuario_Activity.class);
                    intent2.putExtra("mensaje", contrasena.getText().toString());
                    intent2.putExtra("id", id);
                    startActivity(intent2);
                }
                if(respuesta.equals("none")){
                    builder.setTitle("Importante");
                    builder.setMessage("El usuario no existe");
                    builder.setPositiveButton("OK",null);
                    builder.create();
                    builder.show();
                }
                respuesta = "";
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

    private class Insertar extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... param) {

            // params comes from the execute() call: params[0] is the url.
            try {
                return consultarUrl(param[0]);
            } catch (IOException e) {
                Toast.makeText(getApplicationContext(), "Error de conexion", Toast.LENGTH_LONG).show();
                return "Unable to retrieve web page. URL may be invalid.";
            }
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {

            Toast.makeText(getApplicationContext(), "Se almacenaron los datos correctamente", Toast.LENGTH_LONG).show();

        }
    }

    private class Consultar extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... param) {

            // params comes from the execute() call: params[0] is the url.
            try {
                return consultarUrl(param[0]);
            } catch (IOException e) {
                Toast.makeText(getApplicationContext(), "Error de conexion", Toast.LENGTH_LONG).show();
                return "Error al conectar";
            }
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {

            JSONObject ja = null;
            try {
                ja = new JSONObject(result);
                //codigo.setText(ja.getString("codigo"));
                respuesta = ja.getString("respuesta");
                id = ja.getString("ID");
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }


    private String consultarUrl(String myurl) throws IOException {

        InputStream is = null;

        int len = 500;

        try {
            URL url = new URL(myurl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("GET");
            conn.setDoInput(true);

            conn.connect();
            int response = conn.getResponseCode();

            is = conn.getInputStream();

            String res = readIt(is, len);
            return res;

        } finally {
            if (is != null) {
                is.close();
            }
        }
    }

    public String readIt(InputStream stream, int len) throws IOException, UnsupportedEncodingException {
        Reader reader = null;
        reader = new InputStreamReader(stream, "UTF-8");
        char[] buffer = new char[len];
        reader.read(buffer);
        return new String(buffer);
    }


}