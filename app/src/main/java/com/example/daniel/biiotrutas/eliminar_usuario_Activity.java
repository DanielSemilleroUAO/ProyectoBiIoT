package com.example.daniel.biiotrutas;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.os.AsyncTask;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;


public class eliminar_usuario_Activity extends AppCompatActivity {
    private Button borrar;
    private EditText codigo;
    private ImageView cerrar;
    String respuesta = "";
    String id_usuario = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.eliminar_usuario);

        //Adquirir dato de la actividad menu_usuario_maestro
        Intent intent = getIntent();
        id_usuario = intent.getStringExtra("id");

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        borrar = (Button) findViewById(R.id.button4);
        codigo = (EditText) findViewById(R.id.editText);
        cerrar = (ImageView) findViewById(R.id.imageView2);

        cerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        borrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Consulta para ver si el usuario ya existe
                new Consultar().execute("https://botesinteractivoscali.000webhostapp.com/crear_usuario_app.php?id="+id_usuario+"&cod_trabajador="+codigo.getText().toString());
                //Compara la respuesta de la bd
                if(respuesta.equals("no")){
                    //Manda petición get para almacenar en la bd (trabajadores)
                    new Insertar().execute("https://botesinteractivoscali.000webhostapp.com/eliminar_usuario_app.php?id="+id_usuario+"&cod_trabajador="+codigo.getText().toString());
                    //Muestra mensaje de exitoso
                    builder.setTitle("Importante");
                    builder.setMessage("Usuario eliminado exitosamente");
                    //builder.setPositiveButton("OK",null);
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            //Termina crear usuario y va a menu usuario maestro
                            finish();
                        }
                    });
                    builder.create();
                    builder.show();


                }
                if(respuesta.equals("si")){
                    //Muestra mensaje de error de usuario ya existe
                    builder.setTitle("Importante");
                    builder.setMessage("El código de usuario no existe");
                    builder.setPositiveButton("OK",null);
                    builder.create();
                    builder.show();
                }
                respuesta = "";
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
                return "Unable to retrieve web page. URL may be invalid.";
            }
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {

            JSONObject ja = null;
            try {
                ja = new JSONObject(result);
                //codigo.setText(ja.getString("codigo"));
                respuesta = ja.getString("crear");
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
