package com.example.adi.catalogoatc.fragmentos;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.icu.text.DateFormat;
import android.icu.util.Calendar;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
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
import com.example.adi.catalogoatc.ModeloLista.modeloAbonos;
import com.example.adi.catalogoatc.R;
import com.example.adi.catalogoatc.Recursos.Basic;
import com.example.adi.catalogoatc.adapters.AbonosAdapter;

import org.json.JSONArray;

public class AbonosFragment extends Fragment implements Basic, Response.Listener<JSONArray>, Response.ErrorListener{
    ImageButton btnBucar;
    EditText edtFecha, edtfechaFinal;
    int anio, mes, dia;
    private ProgressDialog progressDialog;
    String url;
    int anioF, mesF, diaF;
    int anioi, mesi, diai;
    TextView txtfecha;
    ListView list;
    int orden_id, cliente_id, credito_id;
    String fechaActual, fechaInicial, FechaFinal;

    private OnFragmentInteractionListener mListener;

    public AbonosFragment() {

    }


    public static AbonosFragment newInstance(int credito_id, int orden_id, int cliente_id) {

        AbonosFragment fragment = new AbonosFragment();
        Bundle args = new Bundle();
        args.putInt("CREDITO_ID", credito_id);
        args.putInt("ORDEN_ID", orden_id);
        args.putInt("CLIENTE_ID", cliente_id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            orden_id = getArguments().getInt("ORDEN_ID");
            credito_id = getArguments().getInt("CREDITO_ID");
            cliente_id = getArguments().getInt("CLIENTE_ID");
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =inflater.inflate(R.layout.fragment_abonos, container, false);

        //rEFERENCIA A LOS VIEW
       /* edtFecha = (EditText)view.findViewById(R.id.edtFecha);
        edtfechaFinal = (EditText)view.findViewById(R.id.edtFechaFinal);
        btnBucar = (ImageButton)view.findViewById(R.id.BuscarAbono)*/;
        list = (ListView)view.findViewById(R.id.lbDetallesAbonos);


    /*//cOMIENZA EL PROCESO DEL DATEpICKERdIALOG

        //fecha actual
        final Calendar c = Calendar.getInstance();
        anio = c.get(Calendar.YEAR);
        mes = c.get(Calendar.MONTH);
        dia = c.get(Calendar.DAY_OF_MONTH);
        fechaActual = anio+"/"+mes+"/"+dia;

        //Evento del edit text de la fecha inicial
        edtFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final  DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener(){


                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        mesi = datePicker.getDayOfMonth();
                        anioi = datePicker.getYear();
                        diai = datePicker.getDayOfMonth();

                        fechaInicial = String.valueOf(anioi)+"/"+String.valueOf(mesi)+"/"+String.valueOf(diai);
                        edtFecha.setText(fechaInicial);
                    }
                },anio, mes, dia);
                datePickerDialog.show();

            }


        });

        //Evento fecha final
        edtfechaFinal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final  DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener(){


                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        mesF = datePicker.getDayOfMonth();
                        anioF = datePicker.getYear();
                        diaF = datePicker.getDayOfMonth();

                        FechaFinal = String.valueOf(anioF)+"/"+String.valueOf(mesF)+"/"+String.valueOf(diaF);
                        edtfechaFinal.setText(FechaFinal);
                    }
                },anio, mes, dia);
                datePickerDialog.show();

            }
        });*/
//Evento del boton buscar
      /*  btnBucar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "preciono......", Toast.LENGTH_SHORT).show();
                Fragment nuevofragmento = new AbonosFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.content_main, nuevofragmento);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });*/

        //COMIENZA EL PROCESO DE LA CONSULTA

        //Coloca el dialogo de carga
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("En Proceso");
        progressDialog.setMessage("Un momento...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();

        //Inicia la peticion
        RequestQueue queue = Volley.newRequestQueue(getContext());
        String consulta = "SELECT Distinct c.id, o.folio, DATE(bc.fecha) AS fecha,bc.cantidad, (c.total -" +
                        " (SELECT Sum(bc.cantidad) as total_abonos"+
                        " FROM credito c, orden o, bono_credito bc"+
                        " WHERE c.orden_id = o.id"+
                        " AND bc.credito_id = c.id"+
                        " AND o.cliente_id ="+cliente_id+
                        " AND bc.credito_id ="+credito_id+
                        " AND c.id = bc.credito_id)) as total_momento"+
                        " from credito c, orden o, bono_credito bc"+
                        " WHERE c.orden_id = o.id"+
                        " AND bc.credito_id = c.id"+
                        " And bc.cantidad > 0"+
                        " AND o.cliente_id ="+cliente_id+
                        " AND c.orden_id ="+orden_id+";";

        consulta = consulta.replace(" ", "%20");
        String cadena = "?host=" + HOST + "&db=" + DB + "&usuario=" + USER + "&pass=" + PASS + "&consulta=" + consulta;
        url= SERVER + RUTA + "consultaGeneral.php" + cadena;
        Log.i("info", url);

        //Hace la petición String
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, this, this);

        //Agrega y ejecuta la cola
        queue.add(request);
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
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

        AbonosAdapter adapter = new AbonosAdapter(getContext(), modeloAbonos.sacarListaAbonos(response));
        list.setAdapter(adapter);
    }


    /*@Override
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
