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

import com.example.adi.catalogoatc.R;
import com.example.adi.catalogoatc.fragmentos.DetallesComprasFragment;
import com.example.adi.catalogoatc.fragmentos.HistorialContadoFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Adi on 04/01/2018.
 */

public class HistorialAdapter extends BaseAdapter {

    private Context context;
    private JSONArray array;

    public HistorialAdapter(Context context, JSONArray array)
    {
        this.context = context;
        this.array = array;
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
            view = inflater.inflate(R.layout.item_historial, null);


        TextView txtFolio = (TextView)view.findViewById(R.id.txtFolio);
        TextView txtFecha = (TextView)view.findViewById(R.id.txtFecha);
        TextView txtTotal = (TextView)view.findViewById(R.id.txtTotal);

        String folio, fecha, total;
        try
        {
            folio = getItem(position).getString("0");
            fecha = getItem(position).getString("1");
            total = "$"+ getItem(position).getString("2");

        }
        catch (JSONException e)
        {
            folio = null;
            fecha = null;
            total = null;

        }

        if (folio != null)
        {
            txtFolio.setText(folio);
            txtFecha.setText(fecha);
            txtTotal.setText(total);

        }




        return view;
    }


}
