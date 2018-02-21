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

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import static android.content.Context.LOCATION_SERVICE;

/**
 * Created by Adi on 20/02/2018.
 */

public class LocalizacionCoordenadas {

    Context context;
    private Configuracion configuracion;

    public LocalizacionCoordenadas(Context context) {
        this.context = context;
    }

    // Cronómetro de la aplicación.
    private CountDownTimer timer;
    static String latitud, longitud, direccion;

    private void locationStart() {
        LocationManager mlocManager = (LocationManager) context.getSystemService(LOCATION_SERVICE);
        Localizacion Local = new Localizacion();
        Local.setMainActivity(this);

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
    public void cambio() {
        // Iniciamos el timer, como parámetros pasaremos el número de
        // minutos que hemos establecido en la aplicación, multiplicado
        // por 60 y por 1000 para obtener el valor en milisegundos, el
        // segúndo parámetro es el que nos dirá cada cuánto se produce el
        // "tick".
        timer = new CountDownTimer(4000, 1000) {
            @Override
            public void onTick(long l) {
                // Este método se lanza por cada lapso de tiempo
                // transcurrido,
               //contgador.setText(String
                 //       .valueOf(l / 1000) + "s");
            }

            @Override
            public void onFinish() {
                new Localizacion();
                //Guarda datos
                guardarDatos(latitud,longitud);
                // Mostramos el aviso de que ha finalizado el tiempo.
                Toast.makeText(context, "yaaaa", Toast.LENGTH_LONG).show();
            }
        };
        timer.start();
    }
    //Método que guarda los datos de usuario y password para recordarlos
    private void guardarDatos(String user, String pass)
    {
        configuracion = new Configuracion(context);
        configuracion.setValues(user, pass);
    }
}
