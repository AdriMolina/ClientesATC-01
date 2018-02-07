package com.example.adi.catalogoatc.fragmentos;

import android.content.Context;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.adi.catalogoatc.R;
import com.example.adi.catalogoatc.Recursos.Basic;
import com.google.android.gms.location.LocationServices;

import org.json.JSONArray;


public class LocalizacionFragment extends Fragment implements Basic, Response.Listener<JSONArray>, Response.ErrorListener {

    private OnFragmentInteractionListener mListener;


    public LocalizacionFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static LocalizacionFragment newInstance(String param1, String param2) {
        LocalizacionFragment fragment = new LocalizacionFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

            private FusedLocationProviderClient mFusedLocationClient ;
            @Override

                    Bundle savedInstanceState )
                mFusedLocationClient = LocationServices. getFusedLocationProviderClient ( this );


        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_localizacion, container, false);


        Button btnActualizar = (Button)view.findViewById(R.id.btnActualizar);



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


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }



}
