package com.example.adi.catalogoatc.adapters;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.adi.catalogoatc.ModeloLista.modeloDetallosCatalogo;
import com.example.adi.catalogoatc.ModeloLista.modeloHistorial;
import com.example.adi.catalogoatc.R;

import java.util.List;

/**
 * Created by Adi on 05/01/2018.
 */

public class DetallesComprasAdapter extends BaseAdapter {
    private Context context;
    private List<modeloDetallosCatalogo> lista;

    public DetallesComprasAdapter(Context context, List<modeloDetallosCatalogo> lista)
    {
        this.context = context;
        this.lista = lista;
    }

    public DetallesComprasAdapter(FragmentManager fragmentManager) {
    }

    @Override
    public int getCount()

    {
        return lista.size();
    }

    @Override
    public modeloDetallosCatalogo getItem(int i)
    {
        return lista.get(i);
    }

    @Override
    public long getItemId(int i)
    {
        return lista.get(i).getId();
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent)
    {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = convertView;
        if (convertView == null)
            view = inflater.inflate(R.layout.item_detalles_compras, null);

        //Referencia a los view
        TextView txtArticulo = (TextView)view.findViewById(R.id.txtArticulo);
        TextView txtMarca = (TextView)view.findViewById(R.id.txtMarca);
        TextView txtModelo = (TextView)view.findViewById(R.id.txtModelo);
        TextView txtCantidad = (TextView)view.findViewById(R.id.txtCantidad);
        TextView txtTotal = (TextView)view.findViewById(R.id.txtPrecioTotal);

        //Asigna los valores

            txtArticulo.setText(getItem(i).getArticulo());
            txtMarca.setText(getItem(i).getMarca());
            txtModelo.setText(getItem(i).getModelo());
            txtCantidad.setText(getItem(i).getCantidad());



        return view;
    }
}
