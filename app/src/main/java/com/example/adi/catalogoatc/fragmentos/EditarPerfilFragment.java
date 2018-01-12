package com.example.adi.catalogoatc.fragmentos;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.adi.catalogoatc.R;


public class EditarPerfilFragment extends Fragment {
    //Variables

    String cliente_id;
    private OnFragmentInteractionListener mListener;

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

        return inflater.inflate(R.layout.fragment_editar_perfil, container, false);
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
