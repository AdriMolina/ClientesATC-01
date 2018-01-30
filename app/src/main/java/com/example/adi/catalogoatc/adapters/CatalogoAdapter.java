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
        Button carrito = (Button)view.findViewById(R.id.addCarrito);


        carrito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final NumberPicker numberPicker = new NumberPicker(context);
                numberPicker.setMinValue(1);
                numberPicker.setMaxValue(1000);
                AlertDialog.Builder dialogo1 = new AlertDialog.Builder(context);
                final EditText cantidad = new EditText(context);
                cantidad.setInputType(InputType.TYPE_CLASS_NUMBER);
                dialogo1.setTitle("Cantidad");
                dialogo1.setMessage("¿Escribe la cantidad de articulos que desea comprar?");
                dialogo1.setView(cantidad);
                dialogo1.setCancelable(false);
                dialogo1.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {
                        String resultado = cantidad.getText().toString();
                        Toast.makeText(context, "Se agrego al carrito"+resultado, Toast.LENGTH_SHORT).show();

                    }
                });
                dialogo1.show();
            }
        });



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
