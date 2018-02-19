package com.example.adi.catalogoatc.ModeloLista;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Adi on 19/02/2018.
 */

public class modeloLocalizacion {
    private String id;
    private String clave;
    private String direccion;
    private String latitud;
    private String longitud;

    public modeloLocalizacion(String id, String clave, String direccion, String latitud, String longitud) {
        this.id = id;
        this.clave = clave;
        this.direccion = direccion;
        this.latitud = latitud;
        this.longitud = longitud;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }


    public static List<modeloLocalizacion> sacarListaUbicaciones(JSONArray array)
    {
        List<modeloLocalizacion> lista = new ArrayList<>();
        try
        {
            for (int i = 0; i < array.length(); i++)
            {
                JSONObject jsonObject = array.getJSONObject(i);
                modeloLocalizacion catalogo = new modeloLocalizacion(jsonObject.getString("0"),
                        jsonObject.getString("1"),
                        jsonObject.getString("2"),
                        jsonObject.getString("3"),
                        jsonObject.getString("4"));

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
