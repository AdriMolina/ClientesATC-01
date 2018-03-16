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
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
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
    static   Context context;

    static private Configuracion configuracion = new Configuracion(context);
    Gps gps = new Gps(context);
    static String latitud, longitud, url;
    int IDClaveCliente;

    public LocalizacionCoordenadas(Context context, int clavecliente)
    {
        this.context = context;
        this.IDClaveCliente = clavecliente;
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

                    Toast.makeText(context," ya tiene datos "+ context.toString(), Toast.LENGTH_SHORT).show();
                }else {
                    //ejecuta el cambio de la localización
                  //  gps.locationStart();
                    Toast.makeText(context, " No tiene datos ", Toast.LENGTH_SHORT).show();

                    //verifica si las variables son diferentes de nulas
                    if (latitud == null && longitud == null) {

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



