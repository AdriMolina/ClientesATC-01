package com.example.adi.catalogoatc.fragmentos;

import android.content.ClipData;
import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.adi.catalogoatc.R;
import com.example.adi.catalogoatc.adapters.SeccionesAdapter;
import com.example.adi.catalogoatc.clases.utilidades;
//Probado


public class CatalogoFragment extends Fragment {


    private OnFragmentInteractionListener mListener;
    View vista;
    private AppBarLayout appBar;
    private TabLayout pestanas;
    private ViewPager viewPager;
    ClipData.Item item;
    public CatalogoFragment() {

    }

    public static CatalogoFragment newInstance(String param1, String param2) {
        CatalogoFragment fragment = new CatalogoFragment();
        Bundle args = new Bundle();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        vista = inflater.inflate(R.layout.fragment_catalogo, container, false);

        if(utilidades.rotacion==0) {
            View parent = (View) container.getParent();

            if (appBar == null) {
                appBar = (AppBarLayout) parent.findViewById(R.id.appBar);
                pestanas = new TabLayout(getActivity());

                pestanas.setTabTextColors(Color.parseColor("#FFFFFF"), Color.parseColor("#FFFFFF"));
                appBar.addView(pestanas);



                viewPager = (ViewPager) vista.findViewById(R.id.idViewPagerInformacion);
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

    private void llenarViewPager(ViewPager viewPager){
        SeccionesAdapter adapter = new SeccionesAdapter(getFragmentManager());
        adapter.addFragment(new ChipFragment(), "Chips" );
        adapter.addFragment(new TelefonoFragment(), "Teléfonos");
        adapter.addFragment(new AccesoriosFragment(), "Accesorios");

        viewPager.setAdapter(adapter);

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
        if(utilidades.rotacion==0) {
            appBar.removeView(pestanas);
        }
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);


    }


}


