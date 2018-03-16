package com.example.adi.catalogoatc.ModeloLista;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Adi on 29/12/2017.
 */

public class modeloCarrito {

    private int id;
    private String tipo;
    private String marca;
    private String modelo;
    private String precio;

    //Agregar id cantidad


    public modeloCarrito(int id, String tipo, String marca, String modelo, String precio) {
        this.id = id;
        this.tipo = tipo;
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

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
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

    public static List<modeloCarrito> agregadoCarrito(JSONArray array)
    {
        List<modeloCarrito> lista = new ArrayList<>();
        try
        {
            for (int i = 0; i < array.length(); i++)
            {
                JSONObject jsonObject = array.getJSONObject(i);
                modeloCarrito catalogo = new modeloCarrito(Integer.parseInt(jsonObject.getString("0")),
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
