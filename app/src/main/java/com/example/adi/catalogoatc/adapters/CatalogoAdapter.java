package com.example.adi.catalogoatc.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.adi.catalogoatc.ModeloLista.modeloCatalogo;
import com.example.adi.catalogoatc.ModeloLista.modeloDetallosCatalogo;
import com.example.adi.catalogoatc.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Adi on 29/12/2017.
 */

public class CatalogoAdapter extends BaseAdapter {
    private Context context;
    private List<modeloCatalogo> list;

    public CatalogoAdapter(Context context, List<modeloCatalogo> list)
    {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount()

    {

        return list.size();
    }

    @Override
    public modeloCatalogo getItem(int position)
    {

        return list.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return list.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = convertView;
        if (convertView == null)
            view = inflater.inflate(R.layout.item, null);

        //Referencia a los view
        TextView txtTitulo = (TextView)view.findViewById(R.id.txtTitulo);
        TextView txtPrecio = (TextView)view.findViewById(R.id.txtPrecio);
        TextView txtDesc = (TextView)view.findViewById(R.id.txtDesc);


            //Asigna los valores
            txtTitulo.setText(getItem(position).getMarca());
            txtPrecio.setText("$"+getItem(position).getPrecio());
            txtDesc.setText(getItem(position).getModelo());

        return view;
    }

    public void setFilter(List<modeloCatalogo>lista){
        this.list = new ArrayList<>();
        this.list.addAll(lista);
        notifyDataSetChanged();
    }

}
