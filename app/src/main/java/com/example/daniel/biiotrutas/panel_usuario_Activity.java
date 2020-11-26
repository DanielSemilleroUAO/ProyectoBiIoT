package com.example.daniel.biiotrutas;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

public class panel_usuario_Activity extends AppCompatActivity implements OnMapReadyCallback {
    //Variables del mapa
    private GoogleMap mMap;
    //Variables del layout
    private ImageView cerrar;
    private FloatingActionButton refrescar;
    private TextView nombre_usuario;
    String codigo = "";
    String id_usuario = "";
    String[] id_botes = new String[100];
    String[] latitudes = new String[100];
    String[] longitudes = new String[100];
    String dato = "";
    String dato_2 = "";
    String dato_3 = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.panel_usuriao);
        // tomar dato que pasa la actividad iniciar sesión
        Intent intent = getIntent();
        codigo = intent.getStringExtra("mensaje");
        id_usuario = intent.getStringExtra("id");
        //Colocar el nombre del usuario

        nombre_usuario = (TextView) findViewById(R.id.textView);
        refrescar = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        nombre_usuario.setText(codigo+" ID:"+id_usuario);

        new Consultar().execute("https://botesinteractivoscali.000webhostapp.com/graficar_botes_app.php?cod_trabajador="+codigo+"&id="+id_usuario);

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // Fragmento del mapa para visualización del mapa
        final SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        cerrar = (ImageView) findViewById(R.id.imageView2);
        cerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Terminar actividad y volver a iniciar sesión
                finish();
            }
        });

        refrescar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Consultar().execute("https://botesinteractivoscali.000webhostapp.com/graficar_botes_app.php?cod_trabajador="+codigo+"&id="+id_usuario);
                builder.setTitle("Importante");
                builder.setMessage("Recibido:"+id_botes[0]+latitudes[0]+longitudes[0]);
                builder.setPositiveButton("OK",null);
                builder.create();
                builder.show();
                // Fragmento del mapa para visualización del mapa
                //SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
                mapFragment.getMapAsync(panel_usuario_Activity.this);
            }
        });
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng[] botes = new LatLng[100];
        // Añadir marcador de acuerdo a latitud y longitud
        for(int i = 0; i < id_botes.length;i++){
            if(id_botes[i] != null && id_botes[i] != ""){
                botes[i] = new LatLng(Double.parseDouble(latitudes[i]), Double.parseDouble(longitudes[i]));
                mMap.addMarker(new MarkerOptions().position(botes[i]).title("Hola "+codigo+", soy "+id_botes[i]).icon(BitmapDescriptorFactory.fromResource(R.drawable.bote_lleno)));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(botes[i],16));
            }
        }
        //LatLng sydney = new LatLng(3.3538139, -76.522904);
        //mMap.addMarker(new MarkerOptions().position(sydney).title("Estoy lleno").icon(BitmapDescriptorFactory.fromResource(R.drawable.bote_lleno)));
        //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney,16));
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
            JSONArray ja_1 = null;
            JSONArray ja_2 = null;
            try {
                ja_1 = new JSONArray(result);
                ja = ja_1.getJSONObject(0);
                dato = ja.getString("ids");
                ja_2 = new JSONArray(dato);
                for( int i = 0; i < ja_2.length(); i++){
                    id_botes[i] = ja_2.getString(i);
                }

                ja = ja_1.getJSONObject(1);
                dato = ja.getString("lats");
                ja_2 = new JSONArray(dato);
                for( int i = 0; i < ja_2.length(); i++){
                    latitudes[i] = ja_2.getString(i);
                }

                ja = ja_1.getJSONObject(2);
                dato = ja.getString("longs");
                ja_2 = new JSONArray(dato);
                for( int i = 0; i < ja_2.length(); i++){
                    longitudes[i] = ja_2.getString(i);
                }

                dato= ja_2.getString(0);
                //ja = new JSONObject(result);
                //codigo.setText(ja.getString("codigo"));
                //ja_1 = ja.getJSONArray("ids");
                //dato = ja_1.getString(0);

                //ja_1.getString("respuesta");
                //id = ja.getString("ID");
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
