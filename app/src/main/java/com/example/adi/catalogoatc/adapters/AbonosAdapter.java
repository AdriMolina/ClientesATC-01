package com.example.adi.catalogoatc.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.adi.catalogoatc.ModeloLista.modeloAbonos;
import com.example.adi.catalogoatc.R;

import java.util.List;

/**
 * Created by Adi on 17/01/2018.
 */

public class AbonosAdapter extends BaseAdapter {
    Context context;
    List<modeloAbonos> list;

    public AbonosAdapter(Context context,List<modeloAbonos> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public modeloAbonos getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = convertView;
        if (convertView == null)
            view = inflater.inflate(R.layout.item_abonos, null);

        //Referencia a los view
        TextView txtFecha = (TextView)view.findViewById(R.id.txtFechaAbono);
        TextView txtFolio = (TextView)view.findViewById(R.id.txtFolioAbono);
        TextView txtAbono = (TextView)view.findViewById(R.id.txtCantidadAbono);
        TextView txtTotal = (TextView)view.findViewById(R.id.txtTotalMomento);


        //Asigna los valores
        txtFecha.setText(getItem(i).getFecha());
        txtFolio.setText(getItem(i).getFolio());
        txtAbono.setText("$"+getItem(i).getAbono());
        txtTotal.setText("$"+getItem(i).getTotral());



        return view;
    }
}
