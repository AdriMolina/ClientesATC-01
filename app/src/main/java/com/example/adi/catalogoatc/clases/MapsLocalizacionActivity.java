package com.example.adi.catalogoatc.clases;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.audiofx.BassBoost;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.adi.catalogoatc.R;
import com.example.adi.catalogoatc.Recursos.Basic;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;

public class MapsLocalizacionActivity extends FragmentActivity implements OnMapReadyCallback, Basic {

    private GoogleMap mMap;
    private LocationManager locationManager;
    private LocationListener locationListener;
    private double latitud;
    private double longitud;
    int usuario;
    String url;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_localizacion);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        //Verificar que tipo de usuario es
        //Coloca el dialogo de carga
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("En Proceso");
        progressDialog.setMessage("Un momento...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();

        //Inicia la peticion
        RequestQueue queue = Volley.newRequestQueue(this);
        String consulta = "Select id" +
                            " from punto_venta" +
                            " where id =2;";
        consulta = consulta.replace(" ", "%20");
        String cadena = "?host=" + HOST + "&db=" + DB + "&usuario=" + USER + "&pass=" + PASS + "&consulta=" + consulta;
        url= SERVER + RUTA + "consultaGeneral.php" + cadena;
        Log.i("info", url);

        //Hace la petición String
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                //dependiendo del tipo cliente

                if (response.length()>0){
                    //rutas
                    Toast.makeText(MapsLocalizacionActivity.this, "Es de rutas", Toast.LENGTH_LONG).show();

                }else{
                    //cliente
                    Toast.makeText(MapsLocalizacionActivity.this, "Es cliente", Toast.LENGTH_LONG).show();
                }






            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        //Agrega y ejecuta la cola
        queue.add(request);


    }



    //Metodo que verifica si tenemos permiso de gps en el telefono celular
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch ((requestCode)) {
            case 10:
                geolocalizar();
                break;
            default:
                break;
        }
    }

    //vrifica si tiene permisos
    public void geolocalizar() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.INTERNET}, 10);
            }
            return;
        }

        locationManager.requestLocationUpdates("gps", 0, 0, locationListener);
    }


    public void setLatLong (double dlatitud, double dlongitud){
        this.latitud = dlatitud;
        this.longitud = dlongitud;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        //dependiendo del tipo cliente

        //rutas

        //cliente
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                latitud = location.getLatitude();
                longitud = location.getLongitude();

                //setLatLong(location.getLatitude(), location.getLatitude());

                LatLng actual = new LatLng(latitud, longitud);
                mMap.clear();
                mMap.addMarker(new MarkerOptions().position(actual).title("Actual").snippet("Latitud: "+ latitud+ "Longitud: "+longitud));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(actual, 15));
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

                // cuando se bloquea el telefono
                Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(i);
            }
        };
        geolocalizar();
        //Poner los con troles de zoom en el mapa
        mMap.getUiSettings().setZoomControlsEnabled(true);
       /* // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        */

    }


}
