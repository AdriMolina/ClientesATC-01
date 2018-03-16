package com.example.adi.catalogoatc.clases;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

/**
 * Created by Morpheus on 08/03/2018.
 */

public class CreacionHilo extends android.os.AsyncTask<Void, Integer,Boolean>
{
 Context context;
    Activity activity;
    int idClavecliente;

    public CreacionHilo(Context context, int idCliente)
    {
        this.context = context;
        this.idClavecliente = idCliente;
    }

    LocalizacionCoordenadas lc = new LocalizacionCoordenadas(context, idClavecliente);
    HiloGeolocalizador hg = new HiloGeolocalizador(context);

    //metodo que trabaja en segundo plano
    @Override
    protected Boolean doInBackground(Void... voids)
    {
        for (int i=1; i<=30; i++){
            hg.hilo();
        }
        return true;
    }

    //Método que ejecuta cuando termina el tiempo

    @Override
    protected void onPostExecute(Boolean aBoolean) {

        lc.vericarLocalizacion();

        hg.ejecutar();
       // Toast.makeText(context," cada dos segundos", Toast.LENGTH_SHORT).show();
    }


}
