package com.example.adi.catalogoatc.fragmentos;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
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
import org.json.JSONException;
import org.json.JSONObject;


public class EditarPerfilFragment extends Fragment implements Basic, Response.Listener<JSONArray>, Response.ErrorListener{
    //Variables
    private ProgressDialog progressDialog;
    View view;
    String url;
    String cliente_id;
    private OnFragmentInteractionListener mListener;
    EditText edtnombre, edtdireccion, edttelefono;
    String nombre, direccion, telefono;
    String nombreFinal, direccionFinal, telefonoFinal;

    public EditarPerfilFragment() {

    }

    public static EditarPerfilFragment newInstance(String id){
        EditarPerfilFragment fragment = new EditarPerfilFragment();
        Bundle bundle = new Bundle();
        bundle.putString("CLIENTE_ID", id);
        fragment.setArguments(bundle);
        return fragment;
    }

    //Cuando se crea el fragmento
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

            cliente_id = getArguments().getString("CLIENTE_ID");


        }
    }

    //Cuando se crea la vista
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Toast.makeText(getContext(), cliente_id, Toast.LENGTH_SHORT).show();

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_editar_perfil, container, false);


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
                            " where id="+cliente_id+";";
        consulta = consulta.replace(" ", "%20");
        String cadena = "?host=" + HOST + "&db=" + DB + "&usuario=" + USER + "&pass=" + PASS + "&consulta=" + consulta;
        url= SERVER + RUTA + "consultaGeneral.php" + cadena;
        Log.i("info", url);

        //Hace la petición String
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, this, this);

        //Agrega y ejecuta la cola
        queue.add(request);


        ImageButton aceptar = (ImageButton)view.findViewById(R.id.btnAceptar);

        aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nombreFinal = edtnombre.getText().toString();
                direccionFinal = edtdireccion.getText().toString();
                telefonoFinal = edttelefono.getText().toString();
                //verifica que hubo un cambio en el nombre
                if(nombre.equals(nombreFinal)  && direccion.equals(direccionFinal) && telefono.equals(telefonoFinal) ){
                    Toast.makeText(getContext(), "No hay cambios", Toast.LENGTH_SHORT).show();

                }else{
                    //Inicia la actualizacion
                    RequestQueue queue = Volley.newRequestQueue(getContext());
                    String consulta = "UPDATE cliente" +
                            " SET nombre='"+nombreFinal+"'"+",direccion="+"'"+direccionFinal+"'"+",telefono="+"'"+telefono+"'"+
                            " WHERE id = "+cliente_id+";";
                    consulta = consulta.replace(" ", "%20");
                    String cadena = "?host=" + HOST + "&db=" + DB + "&usuario=" + USER + "&pass=" + PASS + "&consulta=" + consulta;
                    url= SERVER + RUTA + "consultaGeneral.php" + cadena;
                    Log.i("info", url);

                    //Hace la petición String
                    JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    });

                    //Agrega y ejecuta la cola
                    queue.add(request);
                    Toast.makeText(getContext(), "Se actualizó correctamente"+url, Toast.LENGTH_SHORT).show();

                }


            }
        });


        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


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

        edtnombre = (EditText)view.findViewById(R.id.editNombre);
        edtdireccion = (EditText)view.findViewById(R.id.editDireccion);
        edttelefono = (EditText)view.findViewById(R.id.editTelefono);
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

            nombre = jsonObject.getString("0");
            direccion = jsonObject.getString("1");
            telefono = jsonObject.getString("2");

        }
        catch (JSONException e)
        {
            nombre = null;
            telefono = null;
            direccion= null;

        }

        if (nombre != null)
        {
            edtnombre.setText(nombre);
            edttelefono.setText(telefono);
            edtdireccion.setText(direccion);


        }
    }


    /* @Override
     public void onAttach(Context context) {
         super.onAttach(context);
         if (context instanceof OnFragmentInteractionListener) {
             mListener = (OnFragmentInteractionListener) context;
         } else {
             throw new RuntimeException(context.toString()
                     + " must implement OnFragmentInteractionListener");
         }
     }

     @Override
     public void onDetach() {
         super.onDetach();
         mListener = null;
     }

     /**
      * This interface must be implemented by activities that contain this
      * fragment to allow an interaction in this fragment to be communicated
      * to the activity and potentially other fragments contained in that
      * activity.
      * <p>
      * See the Android Training lesson <a href=
      * "http://developer.android.com/training/basics/fragments/communicating.html"
      * >Communicating with Other Fragments</a> for more information.
      */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
