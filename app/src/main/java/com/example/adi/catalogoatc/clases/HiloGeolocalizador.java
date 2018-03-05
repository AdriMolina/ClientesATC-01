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
    //Funcion que hara que se el hilo se duerma cada segundo
    public void hilo() {

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
    public void ejecutar(){
        time time = new time();
        time.execute();

    }
    //clase que crea el hilo con que se trabajara en segundo plano
    public class time extends AsyncTask<Void, Integer,Boolean> {

        //metodo que trabaja en segundo plano
        @Override
        protected Boolean doInBackground(Void... voids) {
            for (int i=1; i<=3; i++){
                hilo();
            }
            return true;
        }

        //Método que ejecuta cuando termina el tiempo

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            ejecutar();
           lc.locationStart();

            Toast.makeText(context," cada dos segundos", Toast.LENGTH_SHORT).show();
        }
    }
}
