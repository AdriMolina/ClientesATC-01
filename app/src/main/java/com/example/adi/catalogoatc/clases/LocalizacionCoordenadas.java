package com.example.adi.catalogoatc.clases;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.adi.catalogoatc.Recursos.Basic;

import org.json.JSONArray;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import static android.content.Context.LOCATION_SERVICE;

/**
 * Created by Adi on 20/02/2018.
 */

public class LocalizacionCoordenadas implements Basic{

    static  Context context;
    static private Configuracion configuracion = new Configuracion(context);
    static String latitud, longitud, url;

    public LocalizacionCoordenadas(Context context) {
        this.context = context;
    }


    static public String  direccion;

   public void locationStart() {
        LocationManager mlocManager = (LocationManager) context.getSystemService(LOCATION_SERVICE);
        Localizacion Local = new Localizacion();
        Local.setMainActivity(this);
       Toast.makeText(context," ejecuta localicacion" +
               "", Toast.LENGTH_SHORT).show();

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        mlocManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, (LocationListener) Local);
        mlocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, (LocationListener) Local);

    }

    /* Aqui empieza la Clase Localizacion */
    public class Localizacion implements LocationListener {

       LocalizacionCoordenadas mainActivity;

        public LocalizacionCoordenadas getMainActivity() {
            return mainActivity;
        }
        public void setMainActivity(LocalizacionCoordenadas mainActivity) {
            this.mainActivity = mainActivity;
        }
        @Override
        public void onLocationChanged(Location loc) {
        // Este metodo se ejecuta cada vez que el GPS recibe nuevas coordenadas
        // debido a la deteccion de un cambio de ubicacion
           latitud= String.valueOf(loc.getLatitude());
           longitud = String.valueOf(loc.getLongitude());

            //Guarda datos
            configuracion.guardarDatos(latitud,longitud);

         }
        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            switch (status) {
                case LocationProvider.AVAILABLE:
                    Log.d("debug", "LocationProvider.AVAILABLE");
                    break;
                case LocationProvider.OUT_OF_SERVICE:
                    Log.d("debug", "LocationProvider.OUT_OF_SERVICE");
                    break;
                case LocationProvider.TEMPORARILY_UNAVAILABLE:
                    Log.d("debug", "LocationProvider.TEMPORARILY_UNAVAILABLE");
                    break;
            }
        }

        @Override
        public void onProviderEnabled(String s) {

        }

        @Override
        public void onProviderDisabled(String s) {

        }


    }
    public void setLocation(Location loc) {
/*Obtener la direccion de la calle a partir de la latitud y la longitud
        if (loc.getLatitude() != 0.0 && loc.getLongitude() != 0.0) {
            try {
            //    Geocoder geocoder = new Geocoder(this, Locale.getDefault());
              //  List<Address> list = geocoder.getFromLocation(
                        loc.getLatitude(), loc.getLongitude(), 1);
                if (!list.isEmpty()) {
                    Address DirCalle = list.get(0);
                     direccion = DirCalle.getAddressLine(0);
                    //mensaje2.setText("Mi direccion es: \n"
                      //      + DirCalle.getAddressLine(0));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }*/
    }

    //Verifica si el cliente ya tiene registrada la latitud y longitud
    public void vericarLocalizacion(){

        //Inicia la peticion
        RequestQueue queue = Volley.newRequestQueue(context);
        String consulta = "Select id" +
                " from localizacion_cliente" +
                " where claveCliente_id ="+IDClaveCliente;
        consulta = consulta.replace(" ", "%20");
        String cadena = "?host=" + HOST + "&db=" + DB + "&usuario=" + USER + "&pass=" + PASS + "&consulta=" + consulta;
        url= SERVER + RUTA + "consultaGeneral.php" + cadena;
        Log.i("info", url);

        //Hace la petición String
        final JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {


                //Si ya la tiene agregada no realiza ningun método
                if(response.length()>0){

                    Toast.makeText(context," ya tiene datos", Toast.LENGTH_SHORT).show();
                }else {
                    //ejecuta el cambio de la localización
                    locationStart();
                    Toast.makeText(context, " No tiene datos", Toast.LENGTH_SHORT).show();

                    //verifica si las variables son diferentes de nulas
                    if (latitud == "null" && longitud == "null") {

                    }else{
                    //Si no tiene agregados los valores los inserta
                    //Inicia la peticion
                    RequestQueue queue = Volley.newRequestQueue(context);
                    String consulta = "Insert into localizacion_cliente(" +
                            " latitud, longitud, claveCliente_id)" +
                            " values('" + latitud + "','" + longitud + "','" + IDClaveCliente + "')";

                    consulta = consulta.replace(" ", "%20");
                    String cadena = "?host=" + HOST + "&db=" + DB + "&usuario=" + USER + "&pass=" + PASS + "&consulta=" + consulta;
                    url = SERVER + RUTA + "consultaGeneral.php" + cadena;
                    Log.i("info", url);

                    //Hace la petición String
                    JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    });

                    //Agrega y ejecuta la cola
                    queue.add(request);
                }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context," error", Toast.LENGTH_SHORT).show();
            }

        });

        //Agrega y ejecuta la cola
        queue.add(request);
    }

}
