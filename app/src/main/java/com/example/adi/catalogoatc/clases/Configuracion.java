package com.example.adi.catalogoatc.clases;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Adi on 21/02/2018.
 */

public class Configuracion {
    private final String SHARED_FILE = "HMPrefs";
    private final String LATITUD = "latitud";
    private final String LONGITUD = "longitud";
    private Context context;

    public Configuracion(Context context) {
        this.context = context;
    }
    //Devuelve un objeto tipo SharedPreferences que solo sera accesible por nuestra aplicacion
    private SharedPreferences getSettings(){
        return context.getSharedPreferences(SHARED_FILE, 0);
    }
    //Método para sacar la latitud
    public String getLatitud()
    {
        return getSettings().getString(LATITUD, null);
    }
    //Método para sacar la longitud
    public String getLongitud()
    {
        return getSettings().getString(LONGITUD, null);
    }

    //Metodo para guardar las variables
    public void setValues(String latitud, String longitud)
    {
        SharedPreferences.Editor editor = getSettings().edit();
        editor.putString(LATITUD, latitud);
        editor.putString(LONGITUD, longitud);
        editor.commit();
    }

    //https://www.nosinmiubuntu.com/como-guardar-datos-en-android/
    //Método que guarda los datos de latitud y longitud para recordarlos
    public void guardarDatos(String latitudGuardar, String longintudGuardar)
    {

        setValues(latitudGuardar, longintudGuardar);

    }
}
