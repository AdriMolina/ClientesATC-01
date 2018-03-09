package com.example.adi.catalogoatc.clases;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

/**
 * Created by Adi on 05/03/2018.
 */

public class HiloGeolocalizador {


    Activity activity;
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
     time ti = new time();
        ti.execute();
        Toast.makeText(context," primera clase de hilos", Toast.LENGTH_SHORT).show();

    }

    public class time extends AsyncTask<Void, Integer,Boolean>
    {

        //metodo que trabaja en segundo plano
        @Override
        protected Boolean doInBackground(Void... voids)
        {
            for (int i = 1; i <= 30; i++)
            {
                hilo();
            }
            return true;
        }

        //Método que ejecuta cuando termina el tiempo

        @Override
        protected void onPostExecute(Boolean aBoolean)
        {
           ejecutar();
            Toast.makeText(context," cada dos segundos", Toast.LENGTH_SHORT).show();
        }
    }
    }
