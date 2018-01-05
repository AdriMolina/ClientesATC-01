package com.example.adi.catalogoatc.adapters;

import android.media.Image;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Adi on 29/12/2017.
 */

public class SeccionesAdapter extends FragmentStatePagerAdapter {
    private final List<Fragment> ListaFragments = new ArrayList<>();
    private final List<String> ListaTitulos= new ArrayList<>();
    private final List<Image> ListaImagen= new ArrayList<>();

    public void addFragment(Fragment fr, String titulo){
        ListaFragments.add(fr);
        ListaTitulos.add(titulo);


    }

    public SeccionesAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    //re realisa el swtch para los casos de los titulos de las pesatañas
    public CharSequence getPageTitle(int position) {
        return ListaTitulos.get(position);
    }

    @Override
    //instancia de los fragmentos que estaran en cada pestaña
    public Fragment getItem(int position) {
        return ListaFragments.get(position);
    }

    @Override
    public int getCount() {
        return ListaFragments.size();
    }
}