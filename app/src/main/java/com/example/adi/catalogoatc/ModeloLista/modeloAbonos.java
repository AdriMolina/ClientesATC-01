package com.example.adi.catalogoatc.ModeloLista;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Adi on 17/01/2018.
 */

public class modeloAbonos {
    private String folio;
    private String fecha;
    private String abono;
    private String totral;

    public modeloAbonos(String folio, String fecha, String abono, String totral) {
        this.folio = folio;
        this.fecha = fecha;
        this.abono = abono;
        this.totral = totral;
    }

    public String getFolio() {
        return folio;
    }

    public void setFolio(String folio) {
        this.folio = folio;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getAbono() {
        return abono;
    }

    public void setAbono(String abono) {
        this.abono = abono;
    }

    public String getTotral() {
        return totral;
    }

    public void setTotral(String totral) {
        this.totral = totral;
    }


    public static List<modeloAbonos> sacarListaAbonos(JSONArray array)
    {
        List<modeloAbonos> lista = new ArrayList<>();
        try
        {
            for (int i = 0; i < array.length(); i++)
            {
                JSONObject jsonObject = array.getJSONObject(i);
                modeloAbonos catalogo = new modeloAbonos(jsonObject.getString("0"),
                        jsonObject.getString("1"),
                        jsonObject.getString("2"),
                        jsonObject.getString("3"));

                lista.add(catalogo);
            }
        }
        catch (JSONException e)
        {
            lista = null;
        }

        return lista;
    }
}
