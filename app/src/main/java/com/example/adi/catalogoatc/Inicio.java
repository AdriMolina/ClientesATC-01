package com.example.adi.catalogoatc;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageButton;

import com.example.adi.catalogoatc.adapters.HistorialAdapter;
import com.example.adi.catalogoatc.fragmentos.AccesoriosFragment;
import com.example.adi.catalogoatc.fragmentos.BuzonFragment;
import com.example.adi.catalogoatc.fragmentos.CatalogoFragment;
import com.example.adi.catalogoatc.fragmentos.ChipFragment;
import com.example.adi.catalogoatc.fragmentos.DetallesComprasFragment;
import com.example.adi.catalogoatc.fragmentos.HistorialContadoFragment;
import com.example.adi.catalogoatc.fragmentos.HistorialCreditoFragment;
import com.example.adi.catalogoatc.fragmentos.HistorialFragment;
import com.example.adi.catalogoatc.fragmentos.LocalizacionFragment;
import com.example.adi.catalogoatc.fragmentos.PerfilFragment;
import com.example.adi.catalogoatc.fragmentos.TelefonoFragment;

public class Inicio extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        CatalogoFragment.OnFragmentInteractionListener,
        ChipFragment.OnFragmentInteractionListener,
        TelefonoFragment.OnFragmentInteractionListener,
        AccesoriosFragment.OnFragmentInteractionListener,
        PerfilFragment.OnFragmentInteractionListener,
        HistorialFragment.OnFragmentInteractionListener,
        HistorialCreditoFragment.OnFragmentInteractionListener,
        HistorialContadoFragment.OnFragmentInteractionListener,
        DetallesComprasFragment.OnFragmentInteractionListener,
        BuzonFragment.OnFragmentInteractionListener,
        LocalizacionFragment.OnFragmentInteractionListener
{

    ImageButton floatButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);






        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();





        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.inicio, menu);






        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        Fragment miFragment=null;
        boolean fragmentSeleccionado=false;

        if (id == R.id.nav_inicio) {
            // Handle the camera action

        } else if (id == R.id.nav_pefil) {
            miFragment = new PerfilFragment();
            fragmentSeleccionado = true;

        } else if (id == R.id.nav_catalogo) {

            miFragment = new CatalogoFragment();
            Log.i("Entrada", "Contenedor");
            fragmentSeleccionado = true;


        } else if (id == R.id.nav_busquedas) {
            miFragment = new HistorialFragment();
            fragmentSeleccionado = true;

        } else if (id == R.id.nav_localizacion) {
            miFragment = new LocalizacionFragment();
            fragmentSeleccionado = true;

        } else if (id == R.id.nav_buzon) {

            miFragment = new BuzonFragment();
            fragmentSeleccionado = true;
        } else if (id == R.id.nav_ayuda) {

        }

        if(fragmentSeleccionado==true){
            getSupportFragmentManager().beginTransaction().replace(R.id.content_main,miFragment).commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void onFragmentInteraction(Uri uri) {
        
    }



}
