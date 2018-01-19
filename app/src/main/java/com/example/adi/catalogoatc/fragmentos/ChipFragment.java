package com.example.adi.catalogoatc.fragmentos;

import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
import com.example.adi.catalogoatc.adapters.CatalogoAdapter;
import com.example.adi.catalogoatc.ModeloLista.modeloCatalogo;

import org.json.JSONArray;


public class ChipFragment extends Fragment implements Basic, Response.Listener<JSONArray>, Response.ErrorListener{
    private ListView listView;
    private ProgressDialog progressDialog;
    String url;
    CatalogoAdapter adapter;
    private TelefonoFragment.OnFragmentInteractionListener mListener;



    // TODO: Rename and change types and number of parameters
    public static ChipFragment newInstance(String param1, String param2) {
        ChipFragment fragment = new ChipFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    //Cuando se crea el fragmento
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //compara si hay algun elemento guardado
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_telefono, container, false);
        listView = (ListView)view.findViewById(R.id.ListaTelfono);

        //Coloca el dialogo de carga
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("En Proceso");
        progressDialog.setMessage("Un momento...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();

        //Inicia la peticion
        RequestQueue queue = Volley.newRequestQueue(getContext());
        String consulta = "select distinct ca.id, ma.nombre as marca, mo.nombre as  modelo,a.precio" +
                "                               from marca ma, modelo mo, articulo a, punto_venta pv, cantidad ca, tipo_articulo ta" +
                "                                where a.modelo_id = mo.id" +
                "                                and mo.marca_id = ma.id" +
                "                                and ca.puntoVenta_id = pv.id" +
                "                                and ca.articulo_id = a.id" +
                "                                and a.tipoArticulo_id = ta.id" +
                "                                and ta.nombre = 'Chip'" +
                "                                and pv.tipo <> 'Local Zaragoza'" +
                "                                and pv.tipo <> 'Local Juarez'" +
                "                                and pv.tipo <> 'Local Atc'" +
                "                                and ta.nombre = 'Chip'" +
                "                                and ca.valor > 0" +
                "                                order by ma.nombre asc;";
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
                Object listItem = listView.getItemAtPosition(position);

                //SACA EL ID DEL ARTICULO
                int idArticulo =  (int)adapter.getItemId(position);
                Fragment nuevofragmento = DetallesCatalogoFragment.newInstance(idArticulo);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.content_main, nuevofragmento);
                transaction.addToBackStack(null);
                transaction.commit();

                Toast.makeText(getContext(), "preciono......", Toast.LENGTH_SHORT).show();

            }
        });


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



        adapter = new CatalogoAdapter(getContext(), modeloCatalogo.sacarListaClientes(response));
        listView.setAdapter(adapter);
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
