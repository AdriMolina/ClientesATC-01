package com.example.adi.catalogoatc.fragmentos;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
        setHasOptionsMenu(true);
        View view =inflater.inflate(R.layout.fragment_buzon, container, false);

        //
        aceptar = (ImageButton)view.findViewById(R.id.btnAceptar);



        //insertar comentario
        aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


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
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        MenuItem menuItem =menu.findItem(R.id.itembucar);
        MenuItem menuItem1 =menu.findItem(R.id.itemCarrito);
        menuItem.setVisible(false);
        menuItem1.setVisible(false);
    }
}
