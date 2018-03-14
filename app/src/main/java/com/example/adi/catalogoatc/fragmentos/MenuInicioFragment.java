package com.example.adi.catalogoatc.fragmentos;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import com.example.adi.catalogoatc.R;


public class MenuInicioFragment extends Fragment {

    int IDRuta=2;
    int IDClaveCliente=20;

    public MenuInicioFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static MenuInicioFragment newInstance(int ruta_id, int clavecliente_id) {
        MenuInicioFragment fragment = new MenuInicioFragment();
        Bundle args = new Bundle();
        args.putInt("RUTA", ruta_id);
        args.putInt("CLAVECLIENTE", clavecliente_id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
           IDRuta = getArguments().getInt("RUTA");
           IDClaveCliente = getArguments().getInt("CLAVECLIENTE");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_menu_inicio, container, false);

        final Animation animScale = AnimationUtils.loadAnimation(getContext(),R.anim.scaele_animacion);
        Button btnCatalogo = view.findViewById(R.id.idBtnCatalogo);
        Button btnHistorial = view.findViewById(R.id.idBtnHistorial);
        Button btnClientes= view.findViewById(R.id.idBtnClientes);

        btnCatalogo.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View arg0) {
                arg0.startAnimation(animScale);

                Fragment fragment = CatalogoFragment.newInstance(IDRuta, IDClaveCliente);
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.content_main,fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

            }});
        btnHistorial.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View arg0) {
                arg0.startAnimation(animScale);

                Fragment fragment = HistorialFragment.newInstance("","");
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.content_main,fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

            }});

        btnClientes.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View arg0) {
                arg0.startAnimation(animScale);

                Fragment fragment = PedidosFragment.newInstance("","");
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.content_main,fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

            }});
        return view;
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
