package com.example.adi.catalogoatc.fragmentos;

import android.app.AlertDialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NotificationCompat;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.adi.catalogoatc.Inicio;
import com.example.adi.catalogoatc.R;
import com.example.adi.catalogoatc.Recursos.Basic;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

//Probado

public class DetallesCatalogoFragment extends Fragment implements Basic, Response.Listener<JSONArray>, Response.ErrorListener {
   int id_cantidad, resultado;
    private ProgressDialog progressDialog;
    String url, Titulo;
    View view;
    public DetallesCatalogoFragment() {

    }


    public static DetallesCatalogoFragment newInstance(int cantidad_id, String titulo) {
        DetallesCatalogoFragment fragment = new DetallesCatalogoFragment();
        Bundle args = new Bundle();
        args.putInt("ID_CANTIDAD", cantidad_id);
        args.putString("TITULO", titulo);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            id_cantidad = getArguments().getInt("ID_CANTIDAD");
            Titulo = getArguments().getString("TITULO");

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_detalles_catalogo, container, false);
        Button agregar = (Button)view.findViewById(R.id.AgrgarCarrito);

        //Coloca el dialogo de carga
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("En Proceso");
        progressDialog.setMessage("Un momento...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();

        //Inicia la peticion
        RequestQueue queue = Volley.newRequestQueue(getContext());
        String consulta = "select ar.id, ma.nombre, mo.nombre,ar.precio,("+
                           " SELECT SUM(ca.valor)"+
                           " FROM cantidad ca, articulo ar, punto_venta pv"+
                           " WHERE ca.articulo_id = ar.id"+
                           " AND ca.puntoVenta_id = pv.id"+
                           " AND ar.id = "+id_cantidad+
                           " AND ar.id = ar.id) as CantidadTotal"+
                           " from marca ma, modelo mo, articulo ar, punto_venta pv, cantidad ca, tipo_articulo ta"+
                           " WHERE ca.articulo_id = ar.id"+
                           " and ar.modelo_id = mo.id"+
                           " and ca.puntoVenta_id = pv.id"+
                           " and mo.marca_id = ma.id"+
                           " and ar.tipoArticulo_id = ta.id"+
                           " and ar.id = "+id_cantidad;

        consulta = consulta.replace(" ", "%20");
        String cadena = "?host=" + HOST + "&db=" + DB + "&usuario=" + USER + "&pass=" + PASS + "&consulta=" + consulta;
        url= SERVER + RUTA + "consultaGeneral.php" + cadena;
        Log.i("info", url);

        //Hace la petición String
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, this, this);

        //Agrega y ejecuta la cola
        queue.add(request);

        //notificacion


        agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(Titulo.equals("Chip")){
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                    final EditText textoCantidad = new EditText(getContext());
                    builder.setTitle("Cantidad");
                    textoCantidad.setInputType(InputType.TYPE_CLASS_NUMBER);
                    builder.setView(textoCantidad);
                    builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            String res =textoCantidad.getText().toString();
                            resultado = Integer.parseInt(res);
                            Toast.makeText(getContext(), String.valueOf(resultado), Toast.LENGTH_SHORT).show();
                        }
                    });
                    builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    builder.show();

                }else{

                    final NumberPicker numberPicker = new NumberPicker(getContext());
                    numberPicker.setMinValue(1);
                    numberPicker.setMaxValue(100);
                    NumberPicker.OnValueChangeListener myValChangedListener = new NumberPicker.OnValueChangeListener() {
                        @Override
                        public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                            resultado = (i1);
                        }
                    };
                    numberPicker.setOnValueChangedListener(myValChangedListener);
                    AlertDialog.Builder dialogo1 = new AlertDialog.Builder(getContext()).setView(numberPicker);
                    dialogo1.setTitle("Cantidad");
                    dialogo1.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Toast.makeText(getContext(), String.valueOf(resultado), Toast.LENGTH_SHORT).show();
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
        return view;
    }


    @Override
    public void onErrorResponse(VolleyError error) {

    }

    @Override
    public void onResponse(JSONArray response) {
        progressDialog.hide();
        TextView txtMarca = (TextView) view.findViewById(R.id.MarcaDetalles);
        TextView txtModelo = (TextView)view.findViewById(R.id.ModeloDetalles);
        TextView txtPrecio = (TextView)view.findViewById(R.id.PrecioDetalles);
        TextView txtCantidad = (TextView)view.findViewById(R.id.CantidadDetalles);

        JSONObject jsonObject;

        try
        {
            jsonObject = response.getJSONObject(0);
        }
        catch (JSONException e)
        {
            jsonObject = new JSONObject();
        }

            String modelo, marca, precio, cantidad, id;
        try
        {
            id = jsonObject.getString("0");
            marca = jsonObject.getString("1");
            modelo = jsonObject.getString("2");
            precio = jsonObject.getString("3");
            cantidad = jsonObject.getString("4");

        }
        catch (JSONException e)
        {
            marca = null;
            modelo = null;
            precio = null;
            cantidad = null;

        }

        if (marca != null)
        {
            txtMarca.setText(marca);
            txtModelo.setText(modelo);
            txtPrecio.setText("$"+precio);
            txtCantidad.setText(cantidad);



        }
    }

}
