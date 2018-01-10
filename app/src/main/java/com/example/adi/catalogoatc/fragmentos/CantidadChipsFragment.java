package com.example.adi.catalogoatc.fragmentos;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.adi.catalogoatc.R;


public class CantidadChipsFragment extends DialogFragment {


     public static CantidadChipsFragment newInstance(boolean correcto, String texto ){
         CantidadChipsFragment alertaFragment = new CantidadChipsFragment();
         Bundle bundle = new Bundle();
         bundle.putBoolean("CORRECTO", correcto);
         bundle.putString("MENSAJE", texto);
         alertaFragment.setArguments(bundle);
         return alertaFragment;
     }

   public Dialog onCreateDoalog(Bundle savedInstanceState){

       //Infla la vista y el builder necesario
       AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
       LayoutInflater inflater = getActivity().getLayoutInflater();
       View vista = inflater.inflate(R.layout.fragment_cantidad_chips, null);

       ImageView imgAlerta = (ImageView) vista.findViewById(R.id.imgAlerta);
       TextView txtMensaje = (TextView) vista.findViewById(R.id.txtMensaje);

       //Argumentos que se rescatan del fragmento
       Bundle bundle = getArguments();
       int icono;
       String mensaje = bundle.getString("MENSAJE");
       if (bundle.getBoolean("CORRECTO")) {
           //icono = R.drawable.ic_correcto;
       }else {
           //icono = R.drawable.ic_incorrecto;
       }
       //imgAlerta.setImageResource(icono);
       txtMensaje.setText(mensaje);

       //Arma el dialogo y sus contenidos
       builder.setView(vista).setTitle("Mensaje").setPositiveButton("Aceptar", new DialogInterface.OnClickListener()
       {
           @Override
           public void onClick(DialogInterface dialog, int which)
           {
           }
       });

       return builder.create();
   }

    public static CantidadChipsFragment newInstance(String param1, String param2) {
        CantidadChipsFragment fragment = new CantidadChipsFragment();
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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cantidad_chips, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
