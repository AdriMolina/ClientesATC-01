package com.example.adi.catalogoatc.fragmentos;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
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
import com.example.adi.catalogoatc.R;
import com.example.adi.catalogoatc.Recursos.Basic;
import com.example.adi.catalogoatc.adapters.DetallesComprasAdapter;
import com.example.adi.catalogoatc.adapters.HistorialAdapter;
import com.example.adi.catalogoatc.ModeloLista.modeloHistorial;
import com.example.adi.catalogoatc.adapters.HistorialCreditoAdapter;
import com.example.adi.catalogoatc.ModeloLista.modeloHistorialCredito;
import org.json.JSONArray;

//Probado



public class HistorialCreditoFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener,
        Basic, Response.Listener<JSONArray>, Response.ErrorListener {
    private ListView listView;
    private ProgressDialog progressDialog;
    String url, total;
    private SwipeRefreshLayout contenedor;
    int idOrden, idCredito;
    HistorialCreditoAdapter adapter;

    int IDclavecliente;
    public static HistorialCreditoFragment newInstance(int clavecliente_id) {
        HistorialCreditoFragment fragment = new HistorialCreditoFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        args.putInt("CLIENTE_ID", clavecliente_id);
        return fragment;
    }


    //Cuando se crea el fragmento
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //compara si hay algun elemento guardado
        if (getArguments() != null) {
            IDclavecliente = getArguments().getInt("CLIENTE_ID");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_historial_credito, container, false);
        listView = (ListView)view.findViewById(R.id.ListaHistorialCredito);
        contenedor = (SwipeRefreshLayout)view.findViewById(R.id.contenedorCredito);
        contenedor.setOnRefreshListener(this);

        //Coloca el dialogo de carga
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("En Proceso");
        progressDialog.setMessage("Un momento...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();

        //Inicia la peticion
        RequestQueue queue = Volley.newRequestQueue(getContext());
        String consulta = "SELECT o.id, c.id, o.folio,DATE(o.fecha),c.total, c.estado" +
                "                 from credito c, orden o, cliente cl, clave_cliente cc" +
                "                 where c.orden_id = o.id" +
                "                 and cc.cliente_id = cl.id" +
                "                 and o.cliente_id = cl.id" +
                "                 and cc.id=" +IDclavecliente+
                "                 order by o.fecha desc;";

        consulta = consulta.replace(" ", "%20");
        String cadena = "?host=" + HOST + "&db=" + DB + "&usuario=" + USER + "&pass=" + PASS + "&consulta=" + consulta;
        url= SERVER + RUTA + "consultaGeneral.php" + cadena;
        Log.i("info", url);

        //Hace la petición String
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, this, this);

        //Agrega y ejecuta la cola
        queue.add(request);





        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                TextView txtTotal = (TextView)view.findViewById(R.id.txtTotal);
                Object listItem = listView.getItemAtPosition(position);
                idOrden =  (int)adapter.getItemId(position);
                idCredito = (int)adapter.getOtroId(position);
                total = txtTotal.getText().toString();

                int idUsusario = IDclavecliente;



                Fragment nuevofragmento = DetallesCreditosFragment.newInstance(idOrden,idCredito, idUsusario, total);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.content_main, nuevofragmento);
                transaction.addToBackStack(null);
                transaction.commit();


            }
        });


        //Parte que recarga el listview solamente si llega al tope
        listView.setOnScrollListener(new AbsListView.OnScrollListener()
        {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState)
            {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount)
            {
                int top = (listView == null || listView.getChildCount() == 0) ? 0 : listView.getChildAt(0).getTop();
                contenedor.setEnabled(firstVisibleItem == 0 && top >= 0);
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

         adapter = new HistorialCreditoAdapter(getContext(), modeloHistorialCredito.sacarCreditos(response));
        listView.setAdapter(adapter);

    }

    @Override
    public void onRefresh() {
        //Inicia la peticion
        RequestQueue queue = Volley.newRequestQueue(getContext());
        String consulta = "SELECT o.id, c.id, o.folio,DATE(o.fecha),c.total, c.estado" +
                "                 from credito c, orden o, cliente cl, clave_cliente cc" +
                "                 where c.orden_id = o.id" +
                "                 and cc.cliente_id = cl.id" +
                "                 and o.cliente_id = cl.id" +
                "                 and cc.id=" +IDclavecliente+
                "                 order by o.fecha desc;";

        consulta = consulta.replace(" ", "%20");
        String cadena = "?host=" + HOST + "&db=" + DB + "&usuario=" + USER + "&pass=" + PASS + "&consulta=" + consulta;
        url= SERVER + RUTA + "consultaGeneral.php" + cadena;
        Log.i("info", url);

        //Hace la petición String
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                adapter = new HistorialCreditoAdapter(getContext(), modeloHistorialCredito.sacarCreditos(response));
                listView.setAdapter(adapter);
                contenedor.setRefreshing(false);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.hide();
                Log.i("mensaje", error.toString());
                Toast.makeText(getContext(), "Error en el WebService", Toast.LENGTH_SHORT).show();
                Toast.makeText(getContext(), url, Toast.LENGTH_SHORT).show();
            }
        });

        //Agrega y ejecuta la cola
        queue.add(request);
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
