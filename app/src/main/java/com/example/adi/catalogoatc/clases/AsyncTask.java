package com.example.adi.catalogoatc.clases;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Morpheus on 08/03/2018.
 */

public class AsyncTask extends android.os.AsyncTask<Void, Integer,Boolean>
{
    Context context;
    LocalizacionCoordenadas lc = new LocalizacionCoordenadas(context);
    HiloGeolocalizador hg = new HiloGeolocalizador();

    //metodo que trabaja en segundo plano
    @Override
    protected Boolean doInBackground(Void... voids)
    {
        for (int i=1; i<=3; i++){
            hg.hilo();
        }
        return true;
    }

    //Método que ejecuta cuando termina el tiempo

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        hg.ejecutar();
        lc.locationStart();

        Toast.makeText(context," cada dos segundos", Toast.LENGTH_SHORT).show();
    }


}
