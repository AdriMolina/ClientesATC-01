package com.example.adi.catalogoatc.adapters;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.adi.catalogoatc.ModeloLista.modeloCatalogo;
import com.example.adi.catalogoatc.ModeloLista.modeloHistorialCredito;
import com.example.adi.catalogoatc.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Adi on 05/01/2018.
 */

public class CreditoAdapter extends BaseAdapter {
    private Context context;
    private JSONArray array;

    public CreditoAdapter(Context context, JSONArray array)
    {
        this.context = context;
        this.array = array;
    }

    public CreditoAdapter(FragmentManager fragmentManager) {
    }

    @Override
    public int getCount()

    {
        return array.length();
    }

    @Override
    public JSONObject getItem(int position)
    {
        JSONObject jsonObject;

        try
        {
            jsonObject = array.getJSONObject(position);
        }
        catch (JSONException e)
        {
            jsonObject = null;
        }

        return jsonObject;
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = convertView;
        if (convertView == null)
            view = inflater.inflate(R.layout.item_detalles_compras, null);


        TextView txtArticulo = (TextView)view.findViewById(R.id.txtArticulo);
        TextView txtMarca = (TextView)view.findViewById(R.id.txtMarca);
        TextView txtModelo = (TextView)view.findViewById(R.id.txtModelo);
        TextView txtCantidad = (TextView)view.findViewById(R.id.txtCantidad);
        TextView txtPrecio = (TextView)view.findViewById(R.id.txtPrecio);
        TextView txtPrecioPagar = (TextView)view.findViewById(R.id.txtPrecioTotal);


        String id, tipo, marca, modelo, cantidad, preciof, totalPagar;
        try
        {
            id = getItem(position).getString("0");
            tipo = getItem(position).getString("1");
            marca = getItem(position).getString("2");
            modelo = getItem(position).getString("3");
            cantidad = getItem(position).getString("4");
            preciof = "$"+ getItem(position).getString("5");
            totalPagar = "$"+ getItem(position).getString("6");

        }
        catch (JSONException e)
        {
            tipo = null;
            marca = null;
            modelo = null;
            cantidad = null;
            preciof = null;
            totalPagar = null;

        }

        if (tipo != null)
        {
            txtArticulo.setText(tipo);
            txtMarca.setText(marca);
            txtModelo.setText(modelo);
            txtCantidad.setText(cantidad);
            txtPrecio.setText(preciof);
            txtPrecioPagar.setText(totalPagar);
        }

        return view;
    }

}
