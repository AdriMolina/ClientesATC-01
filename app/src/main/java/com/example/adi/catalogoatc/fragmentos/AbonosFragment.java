package com.example.adi.catalogoatc.fragmentos;

import android.app.DatePickerDialog;
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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.adi.catalogoatc.R;
import com.example.adi.catalogoatc.Recursos.Basic;

import org.json.JSONArray;

public class AbonosFragment extends Fragment implements Basic, Response.Listener<JSONArray>, Response.ErrorListener{
    ImageButton btnBucar;
    EditText edtFecha, edtfechaFinal;
    int anio, mes, dia;
    int anioF, mesF, diaF;
    int anioi, mesi, diai;
    TextView txtfecha;
    String fechaActual, fechaInicial, FechaFinal;

    private OnFragmentInteractionListener mListener;

    public AbonosFragment() {

    }


    public static AbonosFragment newInstance(String param1, String param2) {
        AbonosFragment fragment = new AbonosFragment();
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


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =inflater.inflate(R.layout.fragment_abonos, container, false);

        edtFecha = (EditText)view.findViewById(R.id.edtFecha);
        edtfechaFinal = (EditText)view.findViewById(R.id.edtFechaFinal);
        btnBucar = (ImageButton)view.findViewById(R.id.BuscarAbono);



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
        });
//Evento del boton buscar
        btnBucar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "preciono......", Toast.LENGTH_SHORT).show();
                Fragment nuevofragmento = new AbonosFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.content_main, nuevofragmento);
                transaction.addToBackStack(null);
                transaction.commit();
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

    @Override
    public void onErrorResponse(VolleyError error) {

    }

    @Override
    public void onResponse(JSONArray response) {

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
