package com.example.adi.catalogoatc.fragmentos;

import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
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
import android.widget.SearchView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.adi.catalogoatc.R;
import com.example.adi.catalogoatc.Recursos.Basic;
import com.example.adi.catalogoatc.adapters.CatalogoAdapter;
import com.example.adi.catalogoatc.ModeloLista.modeloCatalogo;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;


public class AccesoriosFragment extends Fragment implements Basic, Response.Listener<JSONArray>,
        Response.ErrorListener,
        SearchView.OnQueryTextListener,
        SwipeRefreshLayout.OnRefreshListener{
    private ListView listView;
    private ProgressDialog progressDialog;
    int id_cantidad;
    private SwipeRefreshLayout contenedor;
    CatalogoAdapter adapter;
    List<modeloCatalogo> listaAdapter;
    String url;
    int IDRuta, IDClaveCliente;
    FragmentManager fm;




    // TODO: Rename and change types and number of parameters
    public static TelefonoFragment newInstance(int ruta_id, int clavecliente_id) {
        TelefonoFragment fragment = new TelefonoFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        args.putInt("RUTA_ID", ruta_id);
        args.putInt("CLIENTE__ID", clavecliente_id);
        return fragment;
    }


    //Cuando se crea el fragmento
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //compara si hay algun elemento guardado
        if (getArguments() != null) {
            IDRuta = getArguments().getInt("RUTA_ID");
            IDClaveCliente = getArguments().getInt("CLIENTE_ID");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_accesorios, container, false);
        setHasOptionsMenu(true);
        listView = (ListView)view.findViewById(R.id.ListaAccesorios);
        contenedor = (SwipeRefreshLayout)view.findViewById(R.id.contenedorAccesorios);
        contenedor.setOnRefreshListener(this);

        //Coloca el dialogo de carga
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("En Proceso");
        progressDialog.setMessage("Un momento...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();

        //Inicia la peticion
        RequestQueue queue = Volley.newRequestQueue(getContext());
        String consulta = "select distinct a.id, ta.nombre, ma.nombre,a.precio, ca.id" +
                                " from marca ma, modelo mo, articulo a, punto_venta pv, cantidad ca, tipo_articulo ta" +
                                " where a.modelo_id = mo.id" +
                                " and mo.marca_id = ma.id" +
                                " and ca.puntoVenta_id = pv.id" +
                                " and ca.articulo_id = a.id" +
                                " and a.tipoArticulo_id = ta.id" +
                                " and pv.id =" +IDRuta+
                                " and ta.nombre !='Teléfono'" +
                                " and ta.nombre !='Chip'" +
                                " and ca.valor > 0" +
                                " order by ta.nombre asc;";
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
                //SACA EL ID DEL ARTICULO
                int idArticulo =  (int)adapter.getItemId(i);
                Toast.makeText(getContext(), String.valueOf(idArticulo), Toast.LENGTH_SHORT).show();
                Fragment nuevofragmento = DetallesCatalogoFragment.newInstance(idArticulo, "Accesorios");
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.content_main, nuevofragmento);
                transaction.addToBackStack(null);
                transaction.commit();

            }
        });

        /*listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                //SACA EL ID DEL ARTICULO
                int idArticulo =  (int)adapter.getItemId(i);
                Toast.makeText(getContext(), String.valueOf(idArticulo), Toast.LENGTH_SHORT).show();
                Fragment nuevofragmento = DetallesCatalogoFragment.newInstance(idArticulo, "Accesorios");
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.content_main, nuevofragmento);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });*/

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
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        MenuItem menuItem =menu.findItem(R.id.itembucar);
        MenuItem menuItem1 =menu.findItem(R.id.itemCarrito);
        menuItem.setVisible(true);
        menuItem1.setVisible(true);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
        searchView.setOnQueryTextListener(this);

        MenuItemCompat.setOnActionExpandListener(menuItem, new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                adapter.setFilter(listaAdapter);
                return true;

            }
        });


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
        listaAdapter= modeloCatalogo.sacarListaClientes(response);

        adapter = new CatalogoAdapter(getContext(), listaAdapter, "Accesorios", getActivity().getSupportFragmentManager());

        listView.setAdapter(adapter);
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        try {
            List<modeloCatalogo> listafiltrada = filter(listaAdapter,s);
            adapter.setFilter(listafiltrada);
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
    private List<modeloCatalogo>filter(List<modeloCatalogo>notas,String texto){
        List<modeloCatalogo>listaFiltrada= new ArrayList<>();
        try {
            texto=texto.toLowerCase();
            for (modeloCatalogo nota:notas){
                String nota2 = nota.getMarca().toLowerCase();
                //Para saber si el texto se encuentra dentro de la nota
                if (nota2.contains(texto)){
                    listaFiltrada.add(nota);
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return listaFiltrada;
    }

    @Override
    public void onRefresh() {
        //Inicia la peticion
        RequestQueue queue = Volley.newRequestQueue(getContext());
        String consulta = "select distinct a.id, ta.nombre, ma.nombre,a.precio, ca.id" +
                                " rom marca ma, modelo mo, articulo a, punto_venta pv, cantidad ca, tipo_articulo ta" +
                                " where a.modelo_id = mo.id" +
                                " and mo.marca_id = ma.id" +
                                " and ca.puntoVenta_id = pv.id" +
                                " and ca.articulo_id = a.id" +
                                " and a.tipoArticulo_id = ta.id" +
                                " and pv.id =" +IDRuta+
                                " and ta.nombre !='Teléfono'" +
                                " and ta.nombre !='Chip'" +
                                " and ca.valor > 0" +
                                " order by ta.nombre asc;";
        consulta = consulta.replace(" ", "%20");
        String cadena = "?host=" + HOST + "&db=" + DB + "&usuario=" + USER + "&pass=" + PASS + "&consulta=" + consulta;
        url= SERVER + RUTA + "consultaGeneral.php" + cadena;
        Log.i("info", url);

        //Hace la petición String
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                listaAdapter= modeloCatalogo.sacarListaClientes(response);
                adapter = new CatalogoAdapter(getContext(), listaAdapter, "Accesorios", getActivity().getSupportFragmentManager());

                listView.setAdapter(adapter);
                contenedor.setRefreshing(false);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("mensaje", error.toString());
                Toast.makeText(getContext(), "Error en el WebService", Toast.LENGTH_SHORT).show();
            }
        });

        //Agrega y ejecuta la cola
        queue.add(request);
    }


}
