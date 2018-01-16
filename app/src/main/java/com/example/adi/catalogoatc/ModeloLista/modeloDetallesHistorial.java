package com.example.adi.catalogoatc.ModeloLista;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Adi on 29/12/2017.
 */

public class modeloDetallesHistorial {

    private int id;
    private String articulo;
    private String marca;
    private String modelo;
    private String cantidad;
    private String Precio;
    private String Total;



    public modeloDetallesHistorial(int id, String articulo, String marca, String modelo, String cantidad, String precio, String Total) {
        this.id = id;
        this.articulo = articulo;
        this.marca = marca;
        this.modelo = modelo;
        this.cantidad = cantidad;
        this.Precio = precio;
        this.Total = Total;
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

    public String getPrecio() {
        return Precio;
    }

    public void setPrecio(String precio) {
        Precio = precio;
    }

    public String getTotal() {
        return Total;
    }

    public void setTotal(String total) {
        Total = total;
    }

    public static List<modeloDetallesHistorial> sacarDetallesCompra(JSONArray array)
    {
        List<modeloDetallesHistorial> lista = new ArrayList<>();
        try
        {
            for (int i = 0; i < array.length(); i++)
            {
                JSONObject jsonObject = array.getJSONObject(i);
                modeloDetallesHistorial catalogo = new modeloDetallesHistorial(Integer.parseInt(jsonObject.getString("0")),
                                                                jsonObject.getString("1"),
                                                                jsonObject.getString("2"),
                                                                jsonObject.getString("3"),
                                                                jsonObject.getString("4"),
                                                                jsonObject.getString("5"),

                        jsonObject.getString("6"));

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
