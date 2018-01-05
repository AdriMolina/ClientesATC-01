package com.example.adi.catalogoatc.fragmentos;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.adi.catalogoatc.R;
import com.example.adi.catalogoatc.adapters.HistorialAdapter;
import com.example.adi.catalogoatc.adapters.SeccionesAdapter;
import com.example.adi.catalogoatc.clases.utilidades;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HistorialFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HistorialFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HistorialFragment extends Fragment {
    View vista;
    private AppBarLayout appBar;
    private TabLayout pestanas;
    private ViewPager viewPager;

    private OnFragmentInteractionListener mListener;

    public HistorialFragment() {

    }


    public static HistorialFragment newInstance(String param1, String param2) {
        HistorialFragment fragment = new HistorialFragment();
        Bundle args = new Bundle();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        vista = inflater.inflate(R.layout.fragment_historial, container, false);

        if(utilidades.rotacion==0) {
            View parent = (View) container.getParent();

            if (appBar == null) {
                appBar = (AppBarLayout) parent.findViewById(R.id.appBar);
                pestanas = new TabLayout(getActivity());

                pestanas.setTabTextColors(Color.parseColor("#FFFFFF"), Color.parseColor("#FFFFFF"));
                appBar.addView(pestanas);


                viewPager = (ViewPager) vista.findViewById(R.id.idViewPagerHistorial);
                llenarViewPager(viewPager);
                viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {

                    @Override
                    public void onPageScrollStateChanged(int state) {
                        super.onPageScrollStateChanged(state);
                    }
                });

                pestanas.setupWithViewPager(viewPager);

            }
            pestanas.setTabGravity(TabLayout.GRAVITY_FILL);

        }else {
            utilidades.rotacion=1;
        }

        return vista;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(utilidades.rotacion==0){
            appBar.removeView(pestanas);
        }
    }

    private void llenarViewPager(ViewPager viewPager){

        SeccionesAdapter adapter1 = new SeccionesAdapter(getFragmentManager());
        adapter1.addFragment(new HistorialContadoFragment(), "Contado" );
        adapter1.addFragment(new HistorialCreditoFragment(), "Credito");


        viewPager.setAdapter(adapter1);

    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
