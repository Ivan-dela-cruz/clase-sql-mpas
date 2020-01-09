package com.example.clasesql;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.clasesql.activities.DrawPositionActivity;
import com.example.clasesql.activities.MapsActivity;
import com.example.clasesql.entidades.BaseInicial;

public class MainActivity extends AppCompatActivity implements ActivityCompat.OnRequestPermissionsResultCallback {

    private EditText txt_cdigo, txt_nombre, txt_lati, txt_lon;
    private TextView txt_mensaje;
    private Button btn_save, btn_consultar,btn_query;
    LocationManager localizar;
    Location llocaliza;

    public static  BaseInicial sqLiteHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        sqLiteHelper = new BaseInicial(this, "bd_rutas.sqlite", null, 1);
        SQLiteDatabase database = sqLiteHelper.getWritableDatabase();
        database.close();


        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Registro(v);
            }
        });
        btn_consultar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Consulta(v);
                Intent intent = new Intent(MainActivity.this, DrawPositionActivity.class);
                     startActivity(intent);
            }
        });
        btn_query.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String dni = txt_cdigo.getText().toString();
                String sql = "SELECT codigo,ciudad,latitud,longitud FROM rutas WHERE codigo= '" + dni+"'";


                Cursor cursor = MainActivity.sqLiteHelper.getDataTable(sql);
                if (cursor.moveToFirst()) {
                    txt_cdigo.setText(cursor.getString(0));
                    txt_nombre.setText(cursor.getString(1));
                    txt_lati.setText(cursor.getString(2));
                    txt_lon.setText(cursor.getString(3));
                }


//                Intent intent = new Intent(MainActivity.this, DrawPositionActivity.class);
  //              startActivity(intent);
            }
        });

        Button btn = (Button) findViewById(R.id.btn_posicion);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Consulta(v);
            }
        });
    }

    public void init() {
        txt_cdigo = (EditText) findViewById(R.id.txt_dni);
        txt_nombre = (EditText) findViewById(R.id.txt_nombre);
        txt_lati = (EditText) findViewById(R.id.txt_ape);
        txt_lon = (EditText) findViewById(R.id.txt_genero);


        txt_mensaje = (TextView) findViewById(R.id.txt_val_mesaje);

        btn_save = (Button) findViewById(R.id.btn_save);
        btn_consultar = (Button) findViewById(R.id.btn_buscar);
        btn_query = (Button) findViewById(R.id.btn_consultar);


    }

    public void Registro(View view) {

        String dni = txt_cdigo.getText().toString();
        String nombre = txt_nombre.getText().toString();
        String latitud = txt_lati.getText().toString();
        String longitud = txt_lon.getText().toString();


        if (!dni.isEmpty() && !nombre.isEmpty() && !latitud.isEmpty() && !longitud.isEmpty()) {


            txt_mensaje.setVisibility(View.GONE);

            try {
                MainActivity.sqLiteHelper.insertDataRutas(dni,nombre,latitud,longitud);
                limpiar();
                Toast.makeText(getApplicationContext(), "Agregado exitosamente!!", Toast.LENGTH_SHORT).show();

            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "Error al ingresar ", Toast.LENGTH_SHORT).show();

            }


        } else {
            Toast.makeText(getApplicationContext(), "Los campos son obligatorios ", Toast.LENGTH_SHORT).show();

        }

    }

    public void Consulta(View view) {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            Toast.makeText(this, "no hay permiso", Toast.LENGTH_LONG);
        else {
            localizar = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            llocaliza = localizar.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            if (llocaliza != null) {
                txt_lon.setText(String.valueOf(llocaliza.getLongitude()));
                txt_lati.setText(String.valueOf(llocaliza.getLatitude()));
            } else {
                Toast.makeText(this, "no hay ubicacion", Toast.LENGTH_LONG);
            }
        }

    }


    public void BuscarId(String dni) {

        String sql = "SELECT codigo,ciudad,latitud,longitud FROM alumnos WHERE dni= '" + dni+"'";


        Cursor cursor = MainActivity.sqLiteHelper.getDataTable(sql);
        if (cursor.moveToFirst()) {
            txt_cdigo.setText(cursor.getString(0));
            txt_nombre.setText(cursor.getString(0));
            txt_lati.setText(cursor.getString(0));
            txt_lon.setText(cursor.getString(0));
        }



    }

    public void limpiar() {
        txt_cdigo.setText("");
        txt_nombre.setText("");
        txt_lati.setText("");
        txt_lon.setText("");

    }
}
