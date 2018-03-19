package com.example.adi.catalogoatc.fragmentos;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.EditText;
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

import org.json.JSONArray;


public class BuzonFragment extends Fragment implements Basic {
    private ProgressDialog progressDialog;
    String url;
    String IDUsuario="20";
    ImageButton aceptar;
    String mensaje;
        EditText edt;

    public static BuzonFragment newInstance(String idUsuario) {
        BuzonFragment fragment = new BuzonFragment();
        Bundle args = new Bundle();
        args.putString("USUARIO_ID", idUsuario);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            IDUsuario = getArguments().getString("RUTA_ID");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View view =inflater.inflate(R.layout.fragment_buzon, container, false);

        //
        aceptar = (ImageButton)view.findViewById(R.id.btnAceptar);
        edt = view.findViewById(R.id.editText);


              //insertar comentario
        aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mensaje = edt.getText().toString();

                //verifica que el campo no este vacio
                if(mensaje.equals(" ")) {

                    //Coloca el dialogo de carga
                    progressDialog = new ProgressDialog(getContext());
                    progressDialog.setTitle("En Proceso");
                    progressDialog.setMessage("Un momento...");
                    progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    progressDialog.show();

                    Toast.makeText(getContext(), mensaje,Toast.LENGTH_LONG).show();
                    //Inicia la peticion
                    RequestQueue queue = Volley.newRequestQueue(getContext());
                    String consulta = "insert into buzon_sugerencia(mensaje, fecha, activo, clavecliente_id)" +
                            " values('" + mensaje + "',now(),'1', '" + IDUsuario + "');";
                    consulta = consulta.replace(" ", "%20");
                    String cadena = "?host=" + HOST + "&db=" + DB + "&usuario=" + USER + "&pass=" + PASS + "&consulta=" + consulta;
                    url = SERVER + RUTA + "consultaGeneral.php" + cadena;
                    Log.i("info", url);

                    //Hace la petición String
                    JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            progressDialog.hide();
                            Toast.makeText(getContext(), "Su mensaje se ha enviado", Toast.LENGTH_SHORT).show();
                            //enviar notificación

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            progressDialog.hide();
                            Toast.makeText(getContext(), "El mensaje no se ha podido enviar, intente nuevamente", Toast.LENGTH_SHORT).show();

                        }
                    });

                    //Agrega y ejecuta la cola
                    queue.add(request);
                }else{
                    Toast.makeText(getContext(), "El campo del mensaje esta vacio", Toast.LENGTH_SHORT).show();
                }


            }
        });


        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        MenuItem menuItem =menu.findItem(R.id.itembucar);
        MenuItem menuItem1 =menu.findItem(R.id.itemCarrito);
        menuItem.setVisible(false);
        menuItem1.setVisible(false);
    }


}
