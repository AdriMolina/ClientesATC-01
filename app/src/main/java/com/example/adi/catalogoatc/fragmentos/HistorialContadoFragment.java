package com.example.adi.catalogoatc.fragmentos;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
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
import com.example.adi.catalogoatc.adapters.HistorialAdapter;
import com.example.adi.catalogoatc.ModeloLista.modeloHistorial;

import org.json.JSONArray;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HistorialContadoFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HistorialContadoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HistorialContadoFragment extends Fragment implements Basic, Response.Listener<JSONArray>, Response.ErrorListener {
    private ListView listView;
    private ProgressDialog progressDialog;
    String url;
    int idOrden;
    HistorialAdapter adapter;
    ImageButton imageButton;
    private HistorialFragment.OnFragmentInteractionListener mListener;


    public static HistorialContadoFragment newInstance(String param1, String param2) {
       HistorialContadoFragment fragment = new HistorialContadoFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
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
        View view = inflater.inflate(R.layout.fragment_historial_contado, container, false);
        listView = (ListView)view.findViewById(R.id.ListaHistorial);

        //Coloca el dialogo de carga
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("En Proceso");
        progressDialog.setMessage("Un momento...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();

        //Inicia la peticion
        RequestQueue queue = Volley.newRequestQueue(getContext());
        String consulta = "SELECT o.id, oc.id, o.folio,DATE(o.fecha),oc.total" +
                " from orden_completa oc, orden o" +
                " where o.id not in(Select orden_id from credito)" +
                " and oc.orden_id = o.id" +
                " and o.cliente_id="+IDUsusario+"" +
                " order by o.fecha desc;";

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
                idOrden =  (int)adapter.getItemId(position);

                Toast.makeText(getContext(), "preciono......", Toast.LENGTH_SHORT).show();
                Fragment nuevofragmento = DetallesComprasFragment.newInstance(idOrden);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.content_main, nuevofragmento);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        /*Button btn = (Button)view.findViewById(R.id.Detalles);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Fragment nuevofragmento = new DetallesComprasFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.content_main, nuevofragmento);
                transaction.addToBackStack(null);
                transaction.commit();

            }
        });*/





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

        adapter = new HistorialAdapter(getContext(), modeloHistorial.sacarListaClientes(response));
        listView.setAdapter(adapter);
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
