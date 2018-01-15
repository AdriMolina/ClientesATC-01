package com.example.adi.catalogoatc.fragmentos;

import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.adi.catalogoatc.R;
import com.example.adi.catalogoatc.Recursos.Basic;
import com.example.adi.catalogoatc.adapters.DetallesComprasAdapter;
import com.example.adi.catalogoatc.ModeloLista.modeloDetallosCatalogo;

import org.json.JSONArray;


public class DetallesComprasFragment extends Fragment implements Basic, Response.Listener<JSONArray>, Response.ErrorListener {
    private ListView listView;
    private ProgressDialog progressDialog;
    String url;
    private OnFragmentInteractionListener mListener;
    int articulo_id;


    public static DetallesComprasFragment newInstance(int id) {
        DetallesComprasFragment fragment = new DetallesComprasFragment();
        Bundle args = new Bundle();
        args.putInt("Articulo_ID", id);
        fragment.setArguments(args);
        return fragment;
    }


    //Cuando se crea el fragmento
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //compara si hay algun elemento guardado
        if (getArguments() != null) {
            articulo_id = getArguments().getInt("Articulo_ID");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detalles_compras, container, false);
        listView = (ListView)view.findViewById(R.id.listaDetalles);

        //Coloca el dialogo de carga
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("En Proceso");
        progressDialog.setMessage("Un momento...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();

        //Inicia la peticion
        RequestQueue queue = Volley.newRequestQueue(getContext());
        String consulta = "SELECT od.tipoVentaId, ta.nombre, ma.nombre, mo.nombre , od.cantidad, oc.total, (od.cantidad * od.precio_final)" +
                " FROM tipo_articulo ta,marca ma, modelo mo, orden_descripcion od,orden_completa oc, orden ord, cantidad ca, articulo ar" +
                " where od.tipoVentaId = ca.id" +
                " and ord.id = od.orden_id" +
                " and ord.id = oc.orden_id" +
                " and mo.id = ar.modelo_id" +
                " and ma.id = mo.marca_id" +
                " and ca.articulo_id = ar.id" +
                " and ar.tipoArticulo_id = ta.id" +
                " and od.orden_id = "+articulo_id;

        consulta = consulta.replace(" ", "%20");
        String cadena = "?host=" + HOST + "&db=" + DB + "&usuario=" + USER + "&pass=" + PASS + "&consulta=" + consulta;
        url= SERVER + RUTA + "consultaGeneral.php" + cadena;
        Log.i("info", url);

        //Hace la petición String
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, this, this);

        //Agrega y ejecuta la cola
        queue.add(request);


        return view;
    }



    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }


    @Override
    public void onErrorResponse(VolleyError error) {
        progressDialog.hide();
        Log.i("mensaje", error.toString());
        Toast.makeText(getContext(), "Error en el WebService", Toast.LENGTH_SHORT).show();
        Toast.makeText(getContext(), url, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onResponse(JSONArray response) {
        progressDialog.hide();

        DetallesComprasAdapter adapter = new DetallesComprasAdapter(getContext(),modeloDetallosCatalogo.sacarListaClientes(response));
        listView.setAdapter(adapter);
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
