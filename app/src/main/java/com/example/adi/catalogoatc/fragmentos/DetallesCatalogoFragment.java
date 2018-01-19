package com.example.adi.catalogoatc.fragmentos;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.adi.catalogoatc.R;


public class DetallesCatalogoFragment extends Fragment {
   int id_cantidad;
    private OnFragmentInteractionListener mListener;

    public DetallesCatalogoFragment() {
        // Required empty public constructor
    }


    public static DetallesCatalogoFragment newInstance(int cantidad_id) {
        DetallesCatalogoFragment fragment = new DetallesCatalogoFragment();
        Bundle args = new Bundle();
        args.putInt("ID_CANTIDAD", cantidad_id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            id_cantidad = getArguments().getInt("ID_CANTIDAD");

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detalles_catalogo, container, false);
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
