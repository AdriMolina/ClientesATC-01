package com.example.adi.catalogoatc.clases;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

/**
 * Created by Adi on 05/03/2018.
 */

public class HiloGeolocalizador {

    Context context;
    LocalizacionCoordenadas lc = new LocalizacionCoordenadas(context);
    com.example.adi.catalogoatc.clases.AsyncTask hilo =new com.example.adi.catalogoatc.clases.AsyncTask();
    //Funcion que hara que se el hilo se duerma cada segundo
    public void hilo() {

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
    public void ejecutar(){
        hilo.execute();

    }

}
