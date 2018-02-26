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
    private SharedPreferences getSettings(){
        return context.getSharedPreferences(SHARED_FILE, 0);
    }
    public String getLatitud()
    {
        return getSettings().getString(LATITUD, null);
    }

    public String getLongitud()
    {
        return getSettings().getString(LONGITUD, null);
    }

    public void setValues(String latitud, String longitud)
    {
        SharedPreferences.Editor editor = getSettings().edit();
        editor.putString(LATITUD, latitud);
        editor.putString(LONGITUD, longitud);
        editor.commit();
    }

    //https://www.nosinmiubuntu.com/como-guardar-datos-en-android/
}
