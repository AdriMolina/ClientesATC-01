package com.example.adi.catalogoatc.clases;

import android.content.Context;
import android.os.AsyncTask;

/**
 * Created by Adi on 05/03/2018.
 */

public class HiloGeolocalizador {


    Context context;

    public HiloGeolocalizador(Context context)
    {
        this.context = context;
    }

    //Funcion que hara que se el hilo se duerma cada segundo
    public void hilo() {

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();

        }

    }
    public void ejecutar(){
        CreacionHilo creacionHilo =new CreacionHilo(context);
        creacionHilo.execute();

    }

}
