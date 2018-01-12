package com.example.adi.catalogoatc.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.adi.catalogoatc.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Adi on 03/01/2018.
 */

public class DatosUsuarioAdapter extends BaseAdapter {

    private Context context;
    private JSONArray array;

    public DatosUsuarioAdapter(Context context, JSONArray array) {
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
            view = inflater.inflate(R.layout.item, null);


        TextView txtNombre = (TextView) view.findViewById(R.id.txtNombre);
        TextView txtdireccion = (TextView)view.findViewById(R.id.txtDireccion);
        TextView txtTelefono = (TextView)view.findViewById(R.id.txtTelefono);

        String nombre, telefono, correo;
        try
        {
            nombre = getItem(position).getString("0");
            telefono = getItem(position).getString("3");
            correo = getItem(position).getString("4");

        }
        catch (JSONException e)
        {
            nombre = null;
            telefono = null;
            correo= null;

        }

        if (nombre != null)
        {
            txtNombre.setText(nombre);
            txtTelefono.setText(telefono);
            txtdireccion.setText(correo);

        }

        return view;
    }
}
