package com.example.adi.catalogoatc.ModeloLista;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Adi on 29/12/2017.
 */

public class modeloDetallosCatalogo {

    private int id;
    private String articulo;
    private String marca;
    private String modelo;
    private String cantidad;
    private String telefono;

    public modeloDetallosCatalogo(int id, String string, String articulo, String marca, String modelo, String cantidad) {
        this.id = id;
        this.articulo = articulo;
        this.marca = marca;
        this.modelo = modelo;
        this.cantidad = cantidad;
        this.telefono = telefono;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getArticulo() {
        return articulo;
    }

    public void setArticulo(String articulo) {
        this.articulo = articulo;
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

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public static List<modeloDetallosCatalogo> sacarListaClientes(JSONArray array)
    {
        List<modeloDetallosCatalogo> lista = new ArrayList<>();
        try
        {
            for (int i = 0; i < array.length(); i++)
            {
                JSONObject jsonObject = array.getJSONObject(i);
                modeloDetallosCatalogo catalogo = new modeloDetallosCatalogo(Integer.parseInt(jsonObject.getString("0")),
                                                                jsonObject.getString("1"),
                                                                jsonObject.getString("2"),
                                                                jsonObject.getString("3"),
                                                                jsonObject.getString("4"),
                        jsonObject.getString("5"));

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
