package com.example.adi.catalogoatc.ModeloLista;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Adi on 29/12/2017.
 */

public class modeloCatalogo {

    private int id;
    private String marca;
    private String modelo;
    private String precio;
    private String cantidadID;
    //agregar el id de la cantidad



    public modeloCatalogo(int id, String marca, String modelo, String precio) {
        this.id = id;
        this.marca = marca;
        this.modelo = modelo;
        this.precio = precio;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public static List<modeloCatalogo> sacarListaClientes(JSONArray array)
    {
        List<modeloCatalogo> lista = new ArrayList<>();
        try
        {
            for (int i = 0; i < array.length(); i++)
            {
                JSONObject jsonObject = array.getJSONObject(i);
                modeloCatalogo catalogo = new modeloCatalogo(Integer.parseInt(jsonObject.getString("0")),
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
