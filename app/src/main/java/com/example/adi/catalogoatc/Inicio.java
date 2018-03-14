package com.example.adi.catalogoatc;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageButton;


import com.example.adi.catalogoatc.clases.MapsLocalizacionActivity;
import com.example.adi.catalogoatc.fragmentos.AccesoriosFragment;
import com.example.adi.catalogoatc.fragmentos.BuzonFragment;
import com.example.adi.catalogoatc.fragmentos.CarritoFragment;
import com.example.adi.catalogoatc.fragmentos.CatalogoFragment;
import com.example.adi.catalogoatc.fragmentos.ChipFragment;
import com.example.adi.catalogoatc.fragmentos.DetallesComprasFragment;
import com.example.adi.catalogoatc.fragmentos.HistorialContadoFragment;
import com.example.adi.catalogoatc.fragmentos.HistorialCreditoFragment;
import com.example.adi.catalogoatc.fragmentos.HistorialFragment;
import com.example.adi.catalogoatc.fragmentos.MenuInicioFragment;
import com.example.adi.catalogoatc.fragmentos.PedidosFragment;
import com.example.adi.catalogoatc.fragmentos.PerfilFragment;
import com.example.adi.catalogoatc.fragmentos.TelefonoFragment;

public class Inicio extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener

{

    ImageButton floatButton;
    MenuItem itemBuscar, itemCarrito;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //ADI te falto documentar esta parte XD att: Oscar

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


        Fragment miFragment = new MenuInicioFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.content_main,miFragment).addToBackStack(null).commit();
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
        itemCarrito = menu.findItem(R.id.itemCarrito);
        getMenuInflater().inflate(R.menu.menu_catalogo, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

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
            miFragment = new MenuInicioFragment();
            fragmentSeleccionado = true;

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

        } else if (id == R.id.nav_pedidos) {
            miFragment = new PedidosFragment();
            fragmentSeleccionado = true;

        } else if (id == R.id.nav_localizacion) {
            Intent intent = new Intent(this, MapsLocalizacionActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_buzon) {

            miFragment = new BuzonFragment();
            fragmentSeleccionado = true;
        } else if (id == R.id.nav_ayuda) {

            miFragment = new CarritoFragment();
            fragmentSeleccionado = true;

        }

        if(fragmentSeleccionado==true){
            getSupportFragmentManager().beginTransaction().replace(R.id.content_main,miFragment).addToBackStack(null).commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
