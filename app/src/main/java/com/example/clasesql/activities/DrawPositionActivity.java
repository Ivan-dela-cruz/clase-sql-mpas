package com.example.clasesql.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.example.clasesql.MainActivity;
import com.example.clasesql.R;
import com.example.clasesql.entidades.Ruta;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;

import java.util.ArrayList;

public class DrawPositionActivity extends AppCompatActivity implements OnMapReadyCallback {

    GoogleMap map;
    private Button btn_getPsition;
    MarkerOptions place, place2, place3;
    Polyline currentPolyline;

    private ArrayList<Ruta> lista_rutas;

    private ArrayList<MarkerOptions> rutas;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw_position);

        // btn_getPsition = (Button) findViewById(R.id.btn_map);

        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map_fragment);
        mapFragment.getMapAsync(this);
        llenarRutas();


        place = new MarkerOptions().position(new LatLng(-0.404952, -78.545483)).title("Location 1");
        place2 = new MarkerOptions().position(new LatLng(-0.402603, -78.544529)).title("Location 2");
        place3 = new MarkerOptions().position(new LatLng(-0.402174, -78.546674)).title("Location 3");

        //String url = getUrl(place.getPosition(),place2.getPosition(),"driving");
        //new FetchUrl(MainActivity.this).execute(url,"driving");


    }

    public void llenarRutas() {
        lista_rutas = new ArrayList<>();
        rutas = new ArrayList<>();
        Cursor cursor = MainActivity.sqLiteHelper.getDataTable("SELECT codigo,ciudad,latitud,longitud FROM rutas");

        while (cursor.moveToNext()) {
            String cod = cursor.getString(0);
            String ciudad = cursor.getString(1);
            String lati = cursor.getString(2);
            String longitud = cursor.getString(3);
            lista_rutas.add(new Ruta(cod, ciudad, lati, longitud));


            double latii = Double.parseDouble(lati);
            double lonii = Double.parseDouble(longitud);
            LatLng coordenadas = new LatLng(latii, lonii);
            MarkerOptions markerOptions = new MarkerOptions().position(coordenadas).title(ciudad);
            rutas.add(markerOptions);


            System.out.println("================================================" + cod + " " + ciudad + "  " + lati + " " + longitud + "=======================================");
        }
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        map = googleMap;

        map.clear();

       /* if (lista_rutas.size() > 0) {

            LatLng ultpos = null;
            for (int i = 0; i < lista_rutas.size(); i++) {

                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(new LatLng((Double.parseDouble(lista_rutas.get(i).getLatitud()))
                        , (Double.parseDouble(lista_rutas.get(i).getLongitud()))))
                        .title(lista_rutas.get(i).getCiudad());
                //.icon(BitmapDescriptorFactory.fromResource(R.drawable.marker));

                Marker marcador_ = map.addMarker(new MarkerOptions()
                        .position(new LatLng((Double.parseDouble(lista_rutas.get(i).getLatitud()))
                                , (Double.parseDouble(lista_rutas.get(i).getLongitud()))))
                        //   .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker))
                        .title(lista_rutas.get(i).getCiudad())

                );
                marcador_.setTag(lista_rutas.get(i));
                //  marcador.setTag(i);

                ultpos = new LatLng((Double.parseDouble(lista_rutas.get(i).getLatitud()))
                        , (Double.parseDouble(lista_rutas.get(i).getLongitud())));
            }



        } else {
            Toast.makeText(this, "Lo sentimos, no tenemos agentes en la Unidad Seleccionada", Toast.LENGTH_SHORT).show();
        }
*/


        // map.getUiSettings().setMyLocationButtonEnabled(true);
        //  map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(-0.402174, -78.546674), 16));



        for (MarkerOptions ruta : rutas) {

            /*double lat = Double.parseDouble(ruta.getLatitud());
            double lon = Double.parseDouble(ruta.getLongitud());
            System.out.println("================================================" + lat + " <<<<<<<<< >>>>>>>><>>" + lon + "=======================================");

            LatLng coordenadas = new LatLng(lat, lon);
            MarkerOptions markerOptions = new MarkerOptions().position(coordenadas).title(ruta.getCiudad());
            */

            map.addMarker(ruta);

        }

        // map.addMarker(place);
        // map.addMarker(place2);
        // map.addMarker(place3);


    }

    private String getUrl(LatLng origin, LatLng dest, String directionMode) {
        String str_origin = "origin=" + origin.latitude + ", " + origin.longitude;
        String str_dest = "destination=" + dest.latitude + ", " + dest.longitude;
        String mode = "mode=" + directionMode;
        String parameters = str_origin + "&" + str_dest + "&" + mode;
        String output = "json";

        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters + "&key=" + getString(R.string.google_maps_key);
        return url;

    }
}
