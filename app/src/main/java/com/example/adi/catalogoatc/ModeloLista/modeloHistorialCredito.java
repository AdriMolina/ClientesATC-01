package com.example.adi.catalogoatc.ModeloLista;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Adi on 13/01/2018.
 */

public class modeloHistorialCredito {

    private int idOrden;
    private int idOtro;
    private String folio;
    private String fecha;
    private String estado;
    private String total;

    public modeloHistorialCredito(int id, int id_otro, String folio, String fecha, String total, String estado) {
        this.idOrden = id;
        this.idOtro = id_otro;
        this.folio = folio;
        this.fecha = fecha;
        this.total = total;
        this.estado = estado;
    }

    public int getId() {
        return idOrden;
    }

    public void setId(int id) {
        this.idOrden = id;
    }

    public int getIdOtro() {
        return idOtro;
    }

    public void setIdOtro(int id) {
        this.idOtro = id;
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

    public String getTotal() {
        return total;
    }
    public String getEstado(){return estado;}

    public void setTotal(String total) {
        this.total = total;
    }

    public static List<modeloHistorialCredito> sacarCreditos(JSONArray array)
    {
        List<modeloHistorialCredito> lista = new ArrayList<>();
        try
        {
            for (int i = 0; i < array.length(); i++)
            {
                JSONObject jsonObject = array.getJSONObject(i);
                modeloHistorialCredito historial = new modeloHistorialCredito(Integer.parseInt(jsonObject.getString("0")),
                        Integer.parseInt(jsonObject.getString("1")),
                        jsonObject.getString("2"),
                        jsonObject.getString("3"),
                        jsonObject.getString("4"),
                        jsonObject.getString("5"));

                lista.add(historial);
            }
        }
        catch (JSONException e)
        {
            lista = null;
        }

        return lista;
    }
}
