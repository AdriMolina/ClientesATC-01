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
import android.widget.ImageButton;
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
import com.example.adi.catalogoatc.clases.HiloGeolocalizador;
import com.example.adi.catalogoatc.clases.LocalizacionCoordenadas;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

//Probado
public class PerfilFragment extends Fragment implements Basic, Response.Listener<JSONArray>, Response.ErrorListener {


    String nombre, telefono, correo, id;
    private ProgressDialog progressDialog;
    View view;
    Context mContext;
    String url;

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

        mContext = this.getActivity();

        //compara si hay algun elemento guardado
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
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
        String consulta = "select cc.id, c.nombre, c.direccion, c.telefono, c.email" +
                            " from cliente c, clave_cliente cc" +
                            " where cc.cliente_id = c.id" +
                            " and cc.id ="+IDClaveCliente;
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

                Fragment nuevofragmento = EditarPerfilFragment.newInstance(id);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.content_main, nuevofragmento);
                transaction.addToBackStack(null);
                transaction.commit();
               // HiloGeolocalizador hiloGeolocalizador = new HiloGeolocalizador(getContext());
                //hiloGeolocalizador.ejecutar();
                LocalizacionCoordenadas lc = new LocalizacionCoordenadas(getContext());
               // lc.vericarLocalizacion();
                //lc.locationStart();

            }
        });

        return view;

    }


    //Método que se ejecuta al encontrar error al conectarse al web service
    @Override
    public void onErrorResponse(VolleyError error) {
        progressDialog.hide();
        Log.i("mensaje", error.toString());
        Toast.makeText(getContext(), "Error en el WebService", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onResponse(JSONArray response) {
        progressDialog.hide();
        TextView txtNombre = (TextView) view.findViewById(R.id.txtNombre);
        TextView txtdireccion = (TextView)view.findViewById(R.id.txtDireccion);
        TextView txtTelefono = (TextView)view.findViewById(R.id.txtTelefono);

        JSONObject jsonObject;

        try
        {
            jsonObject = response.getJSONObject(0);
        }
        catch (JSONException e)
        {
            jsonObject = new JSONObject();
        }




        try
        {
            //Guarda los valores de la consulta en sus respectivas variables
            id = jsonObject.getString("0");
            nombre = jsonObject.getString("1");
            correo = jsonObject.getString("2");
            telefono = jsonObject.getString("3");

        }
        catch (JSONException e)
        {
            nombre = null;
            telefono = null;
            correo= null;

        }

        if (nombre != null)
        {
            //Asigna los valores a su txt
            txtNombre.setText(nombre);
            txtTelefono.setText(telefono);
            txtdireccion.setText(correo);



        }
    }

    //Infla el menu que contiene los iconos de carrito y busqueda y los oculta
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        MenuItem menuItem =menu.findItem(R.id.itembucar);
        MenuItem menuItem1 =menu.findItem(R.id.itemCarrito);
        menuItem.setVisible(false);
        menuItem1.setVisible(false);
    }
}
