package com.example.daniel.biiotrutas;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.net.URI;

public class contactanos_Activity extends AppCompatActivity {
    private TextView llamar;
    private TextView correo;
    private ImageView facebook;
    private ImageView instagram;
    private ImageView google;
    private ImageView cerrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contactanos);

        llamar = (TextView) findViewById(R.id.textView8);
        correo = (TextView) findViewById(R.id.textView9);
        cerrar = (ImageView) findViewById(R.id.imageView2);
        facebook = (ImageView) findViewById(R.id.imageView4);
        google = (ImageView) findViewById(R.id.imageView5);
        instagram = (ImageView) findViewById(R.id.imageView6);

        cerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        llamar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent contactIntent = new Intent(ContactsContract.Intents.Insert.ACTION);
                contactIntent.setType(ContactsContract.RawContacts.CONTENT_TYPE);

                contactIntent
                        .putExtra(ContactsContract.Intents.Insert.NAME, "Botes Interactivos Cali")
                        .putExtra(ContactsContract.Intents.Insert.PHONE, "0323044621");

                startActivityForResult(contactIntent, 1);
            }
        });
        correo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:botes.interactivos@gmail.com"));
                startActivity(intent);
            }
        });

        facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/daniel.delgadorodriguez.7"));
                startActivity(intent);
            }
        });
        instagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/?hl=es-la"));
                startActivity(intent);
            }
        });
        google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/daniel.delgadorodriguez.7"));
                startActivity(intent);
            }
        });
    }
}
