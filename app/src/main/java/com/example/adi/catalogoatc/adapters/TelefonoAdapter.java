package com.example.adi.catalogoatc.adapters;

import android.content.Context;
import android.media.Image;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.adi.catalogoatc.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Adi on 29/12/2017.
 */

public class TelefonoAdapter extends BaseAdapter{
    private Context context;
    private JSONArray array;

    public TelefonoAdapter(Context context, JSONArray array)
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
            view = inflater.inflate(R.layout.item, null);


        TextView txtTitulo = (TextView)view.findViewById(R.id.txtTitulo);
        TextView txtPrecio = (TextView)view.findViewById(R.id.txtPrecio);
        TextView txtDesc = (TextView)view.findViewById(R.id.txtDesc);


        String titulo, precio, desc;
        try
        {
            titulo = getItem(position).getString("0");
            precio = getItem(position).getString("2");
            desc = getItem(position).getString("1");

        }
        catch (JSONException e)
        {
            titulo = null;
            precio = null;
            desc= null;

        }

        if (titulo != null)
        {
            txtTitulo.setText(titulo);
            txtPrecio.setText(precio);
            txtDesc.setText(desc);

        }

        return view;
    }

}
