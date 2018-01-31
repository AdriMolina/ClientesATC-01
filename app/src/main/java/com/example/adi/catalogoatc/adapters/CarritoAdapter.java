package com.example.adi.catalogoatc.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.example.adi.catalogoatc.ModeloLista.modeloCarrito;
import com.example.adi.catalogoatc.ModeloLista.modeloCatalogo;
import com.example.adi.catalogoatc.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Adi on 29/12/2017.
 */

public class CarritoAdapter extends BaseAdapter {
    private Context context;
    private List<modeloCarrito> list;
    int resultado;
    private String nombre;

    public CarritoAdapter(Context context, List<modeloCarrito> list, String nombre )
    {
        this.context = context;
        this.nombre= nombre;
        this.list = list;
    }


    @Override
    public int getCount()

    {

        return list.size();
    }

    @Override
    public modeloCarrito getItem(int position)
    {

        return list.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return list.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = convertView;
        if (convertView == null)
            view = inflater.inflate(R.layout.item_carrito, null);

        //Referencia a los view
        TextView txtTipo = (TextView) view.findViewById(R.id.txtTipo);
        TextView txtMarca = (TextView) view.findViewById(R.id.txtMarca);
        TextView txtModelo = (TextView) view.findViewById(R.id.txtModelo);
        TextView txtPrecio = (TextView) view.findViewById(R.id.tvPrice);



        //Asigna los valores
            txtTipo.setText(getItem(position).getTipo());
            txtPrecio.setText("$"+getItem(position).getPrecio());
            txtMarca.setText(getItem(position).getMarca());
            txtModelo.setText(getItem(position).getModelo());


        return view;
    }

}
