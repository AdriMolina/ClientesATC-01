package com.example.adi.catalogoatc.fragmentos;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
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
import com.example.adi.catalogoatc.adapters.DatosUsuarioAdapter;
import com.example.adi.catalogoatc.adapters.TelefonoAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class PerfilFragment extends Fragment implements Basic, Response.Listener<JSONArray>, Response.ErrorListener {


    private ProgressDialog progressDialog;
    String url;
    private TelefonoFragment.OnFragmentInteractionListener mListener;
    View view;


    // TODO: Rename and change types and number of parameters
    public static PerfilFragment newInstance(String param1, String param2) {
        PerfilFragment fragment = new PerfilFragment();
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
         view = inflater.inflate(R.layout.fragment_perfil, container, false);

        ImageButton btnEditar = (ImageButton) view.findViewById(R.id.btnEditar);

        //Coloca el dialogo de carga
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("En Proceso");
        progressDialog.setMessage("Un momento...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();

        //Inicia la peticion
        RequestQueue queue = Volley.newRequestQueue(getContext());
        String consulta = "select nombre, direccion, telefono, email" +
                            " from cliente" +
                            " where id="+IDUsusario+";";
        consulta = consulta.replace(" ", "%20");
        String cadena = "?host=" + HOST + "&db=" + DB + "&usuario=" + USER + "&pass=" + PASS + "&consulta=" + consulta;
        url= SERVER + RUTA + "consultaGeneral.php" + cadena;
        Log.i("info", url);

        //Hace la petición String
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, this, this);

        //Agrega y ejecuta la cola
        queue.add(request);

        //ejecuta la accion de boton editar


        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Fragment nuevofragmento = new EditarPerfilFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.content_main, nuevofragmento);
                transaction.addToBackStack(null);
                transaction.commit();

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
        TextView txtNombre = (TextView) view.findViewById(R.id.txtNombre);
        TextView txtTelefono = (TextView)view.findViewById(R.id.txtTelefonoPerfil);
        TextView txtCorreo = (TextView)view.findViewById(R.id.txtCorreo);

        JSONObject jsonObject;

        try
        {
            jsonObject = response.getJSONObject(0);
        }
        catch (JSONException e)
        {
            jsonObject = new JSONObject();
        }



        String nombre, telefono, correo;
        try
        {
            nombre = jsonObject.getString("0");
            telefono = jsonObject.getString("1");
            correo = jsonObject.getString("2");

        }
        catch (JSONException e)
        {
            nombre = null;
            telefono = null;
            correo= null;

        }

        if (nombre != null)
        {
            txtNombre.setText(nombre);
            txtTelefono.setText(telefono);
            txtCorreo.setText(correo);

            /*Bundle bundle = new Bundle();
            bundle.putString("nombre", );
            bundle.putString("TIPO", TIPO_ADMIN);
            Intent intent = new Intent(Login.this, Recargas.class);
            intent.putExtras(bundle);
            startActivity(intent);
            */

        }
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


}
