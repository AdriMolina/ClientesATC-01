package com.example.adi.catalogoatc.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.adi.catalogoatc.ModeloLista.modeloAbonos;
import com.example.adi.catalogoatc.ModeloLista.modeloLocalizacion;

import java.util.List;

/**
 * Created by Adi on 19/02/2018.
 */

public class LocalizacionAdapter extends BaseAdapter {
    Context context;
    List<modeloLocalizacion> list;
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public modeloLocalizacion getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        return null;
    }
}
