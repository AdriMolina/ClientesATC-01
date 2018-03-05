package com.example.adi.catalogoatc.fragmentos;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.adi.catalogoatc.ModeloLista.modeloCatalogo;
import com.example.adi.catalogoatc.R;
import com.example.adi.catalogoatc.Recursos.Basic;
import com.example.adi.catalogoatc.adapters.CatalogoAdapter;
import com.example.adi.catalogoatc.adapters.HistorialAdapter;
import com.example.adi.catalogoatc.ModeloLista.modeloHistorial;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

//Probado

public class PedidosFragment extends Fragment implements Basic, Response.Listener<JSONArray>, Response.ErrorListener {
    private ProgressDialog progressDialog;
    String url;
    private ListView listView;
    View view;
    HistorialAdapter adapter;

    public static PedidosFragment newInstance(String param1, String param2) {
        PedidosFragment fragment = new PedidosFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
         view = inflater.inflate(R.layout.fragment_pedidos, container, false);


        // Inflate the layout for this fragment

        listView = (ListView)view.findViewById(R.id.listaPedidos);

        //Coloca el dialogo de carga
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("En Proceso");
        progressDialog.setMessage("Un momento...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();

        //Inicia la peticion
        RequestQueue queue = Volley.newRequestQueue(getContext());
        String consulta = "SELECT o.id, o.id, o.folio,  DATE(o.fecha)," +
                "                             (SELECT SUM( od.precio_final * od.cantidad)" +
                "                             FROM orden_descripcion od, orden ord" +
                "                             where od.orden_id = ord.id" +
                "                             AND ord.id = o.id) AS total" +
                "                             from orden o, cliente cl, clave_cliente cc" +
                "                             where o.id not in(Select orden_id from orden_completa)" +
                "                             and cc.cliente_id = cl.id" +
                "                             and o.cliente_id = cl.id" +
                "                             and cc.id =" +IDClaveCliente+
                "                             order by o.fecha desc;";

        consulta = consulta.replace(" ", "%20");
        String cadena = "?host=" + HOST + "&db=" + DB + "&usuario=" + USER + "&pass=" + PASS + "&consulta=" + consulta;
        url= SERVER + RUTA + "consultaGeneral.php" + cadena;
        Log.i("info", url);

        //Hace la petición String
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, this, this);

        //Agrega y ejecuta la cola
        queue.add(request);

    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            TextView txtTotal = (TextView)view.findViewById(R.id.txtTotal);
           String totalContado = txtTotal.getText().toString();
            int idOrden =  (int)adapter.getItemId(i);

            Toast.makeText(getContext(), "preciono......", Toast.LENGTH_SHORT).show();
            Fragment nuevofragmento = DetallesComprasFragment.newInstance(idOrden, totalContado, "Pedidos");
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.content_main, nuevofragmento);
            transaction.addToBackStack(null);
            transaction.commit();
        }
    });

        return view;
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
        adapter = new HistorialAdapter(getContext(), modeloHistorial.sacarListaClientes(response));
        listView.setAdapter(adapter);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        MenuItem menuItem =menu.findItem(R.id.itembucar);
        MenuItem menuItem1 =menu.findItem(R.id.itemCarrito);
        menuItem.setVisible(true);
        menuItem1.setVisible(false);
    }
}
