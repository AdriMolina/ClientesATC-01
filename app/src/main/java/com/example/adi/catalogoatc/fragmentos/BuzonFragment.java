package com.example.adi.catalogoatc.fragmentos;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.adi.catalogoatc.R;
import com.example.adi.catalogoatc.Recursos.Basic;

import org.json.JSONArray;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link BuzonFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link BuzonFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BuzonFragment extends Fragment implements Basic {
    private ProgressDialog progressDialog;
    private OnFragmentInteractionListener mListener;
    String url;
    ImageButton aceptar;

    public BuzonFragment() {
        // Required empty public constructor
    }


    public static BuzonFragment newInstance(String param1, String param2) {
        BuzonFragment fragment = new BuzonFragment();
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
        View view =inflater.inflate(R.layout.fragment_buzon, container, false);

        //
        aceptar = (ImageButton)view.findViewById(R.id.btnAceptar);



        //insertar comentario
        aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*//Coloca el dialogo de carga
                progressDialog = new ProgressDialog(getContext());
                progressDialog.setTitle("En Proceso");
                progressDialog.setMessage("Un momento...");
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressDialog.show();

                //Inicia la peticion
                RequestQueue queue = Volley.newRequestQueue(getContext());
                String consulta = "select id, nombre, direccion, telefono, email" +
                        " from cliente" +
                        " where id="+IDUsusario+";";
                consulta = consulta.replace(" ", "%20");
                String cadena = "?host=" + HOST + "&db=" + DB + "&usuario=" + USER + "&pass=" + PASS + "&consulta=" + consulta;
                url= SERVER + RUTA + "consultaGeneral.php" + cadena;
                Log.i("info", url);
*
                //Hace la petición String
                JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        Toast.makeText(getContext(), "Su mensaje se ha enviado", Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), "Error en el WebService", Toast.LENGTH_SHORT).show();
                    }
                });

                //Agrega y ejecuta la cola
                queue.add(request);*/

                Toast.makeText(getContext(), "Su mensaje se ha enviado", Toast.LENGTH_SHORT).show();
            }
        });


        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
