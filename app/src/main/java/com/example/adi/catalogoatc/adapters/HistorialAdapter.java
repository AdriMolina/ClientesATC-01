package com.example.adi.catalogoatc.adapters;

import android.content.Context;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v4.app.Fragment;

import com.example.adi.catalogoatc.ModeloLista.modeloHistorial;
import com.example.adi.catalogoatc.R;
import com.example.adi.catalogoatc.fragmentos.DetallesComprasFragment;
import com.example.adi.catalogoatc.fragmentos.HistorialContadoFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by Adi on 04/01/2018.
 */

public class HistorialAdapter extends BaseAdapter {

    private Context context;
    private List<modeloHistorial> list;

    public HistorialAdapter(Context context, List<modeloHistorial> list)
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
    public modeloHistorial getItem(int position)
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
            view = inflater.inflate(R.layout.item_historial, null);

        //Referencia los view
        TextView txtFolio = (TextView)view.findViewById(R.id.txtFolio);
        TextView txtFecha = (TextView)view.findViewById(R.id.txtFecha);
        TextView txtTotal = (TextView)view.findViewById(R.id.txtTotal);


            txtFolio.setText(getItem(position).getFolio());
            txtFecha.setText(getItem(position).getFecha());
            txtTotal.setText("$"+getItem(position).getTotal());


        return view;
    }


}
