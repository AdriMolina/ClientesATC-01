package com.example.adi.catalogoatc.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.adi.catalogoatc.ModeloLista.modeloCatalogo;
import com.example.adi.catalogoatc.ModeloLista.modeloHistorial;
import com.example.adi.catalogoatc.ModeloLista.modeloHistorialCredito;
import com.example.adi.catalogoatc.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Adi on 04/01/2018.
 */

public class HistorialCreditoAdapter extends BaseAdapter {

    private Context context;
    private List<modeloHistorialCredito> list;

    public HistorialCreditoAdapter(Context context, List<modeloHistorialCredito> list)
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
    public modeloHistorialCredito getItem(int position)
    {
        return list.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return list.get(position).getId();
    }
    public long getOtroId(int position){
        return list.get(position).getIdOtro();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = convertView;
        if (convertView == null)
            view = inflater.inflate(R.layout.item_historialcredito, null);

        //Referencia los view
        TextView txtFolio = (TextView)view.findViewById(R.id.txtFolio);
        TextView txtFecha = (TextView)view.findViewById(R.id.txtFecha);
        TextView txtTotal = (TextView)view.findViewById(R.id.txtTotal);
        TextView txtEstado = (TextView)view.findViewById(R.id.estado);


            txtFolio.setText(getItem(position).getFolio());
            txtFecha.setText(getItem(position).getFecha());
            txtTotal.setText("$"+getItem(position).getTotal());


        String estado = getItem(position).getEstado();


        if(estado.equals("0")) {
            txtEstado.setText("Pagado");
            txtEstado.setTextColor(Color.parseColor("#0D0DF1"));
        }else{
            txtEstado.setText("Pendiente");
            txtEstado.setTextColor(Color.parseColor("#FF0F0F"));
        }


        return view;
    }
    public void setFilter(List<modeloCatalogo>lista){
        this.list = new ArrayList<>();
        this.list.addAll(list);
        notifyDataSetChanged();
    }

}
