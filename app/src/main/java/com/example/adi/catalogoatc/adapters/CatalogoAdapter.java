package com.example.adi.catalogoatc.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
    int resultado;
    private String nombre;

    public CatalogoAdapter(Context context, List<modeloCatalogo> list, String nombre )
    {
        this.context = context;
        this.nombre= nombre;
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

                if(nombre.equals("Chip")){
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);

                    final EditText textoCantidad = new EditText(context);
                    builder.setTitle("Cantidad");
                    textoCantidad.setInputType(InputType.TYPE_CLASS_NUMBER);
                    builder.setView(textoCantidad);
                    builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            String res =textoCantidad.getText().toString();
                            resultado = Integer.parseInt(res);
                            Toast.makeText(context, String.valueOf(resultado), Toast.LENGTH_SHORT).show();
                        }
                    });
                    builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    builder.show();

                }else{

                    final NumberPicker numberPicker = new NumberPicker(context);
                    numberPicker.setMinValue(1);
                    numberPicker.setMaxValue(100);
                    NumberPicker.OnValueChangeListener myValChangedListener = new NumberPicker.OnValueChangeListener() {
                        @Override
                        public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                            resultado = (i1);
                        }
                    };
                    numberPicker.setOnValueChangedListener(myValChangedListener);
                    AlertDialog.Builder dialogo1 = new AlertDialog.Builder(context).setView(numberPicker);
                    dialogo1.setTitle("Cantidad");
                    dialogo1.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Toast.makeText(context, String.valueOf(resultado), Toast.LENGTH_SHORT).show();
                        }
                    });

                    dialogo1.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    dialogo1.show();


                }



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
