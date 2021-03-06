package com.example.adi.catalogoatc.clases;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;

import java.util.List;


/**
 * Created by Morpheus on 12/03/2018.
 */

public class LocationReader implements LocationListener
{

    private LocationManager locManager;
    private Context context;
    private boolean gpsProviderActive = false;
    private boolean networkProviderActive = false;
    private OnLocationObtainedListener onLocationObtainedListener;
    private OnNoProvidersListener onNoProvidersListener;

    /**
     * Class constructor, it needs the current context in order to work with the
     * services
     *
     * @param context
     *            The current context
     */
    public LocationReader(Context context)
    {
        this.context = context;
    }

    /***
     * Class constructor that allows stablish if the server should start or not
     *
     * @param context
     *            The current context
     * @param start
     *            A value indicating if the service should start automatically
     *            or not
     */
    public LocationReader(Context context, boolean start)
    {
        this.context = context;
        if (start)
        {
            start();
        }

    }

    /***
     * This method starts the subscription to the location providers (both GPS
     * and Network)
     */
    public void start()
    {

        gpsProviderActive = true;
        networkProviderActive = true;
        loadLocationManager();
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 5,
                this);

        locManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000,
                5, this);

    }

    /**
     * Loads the LocationManager service into locManager if not loaded previously
     */
    private void loadLocationManager()
    {
        if (locManager == null)
        {
            locManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        }
    }

    /**
     * Removes all subscriptions
     */
    public void stop()
    {
        locManager.removeUpdates(this);
    }

    /**
     * Gets the current status for the GpsProvider flag
     *
     * @return A boolean value indicating if GPS is active
     */
    public boolean isGpsProviderActive()
    {
        return gpsProviderActive;
    }

    /**
     * Sets the flag of the current GPS status
     *
     * @param gpsProviderActive
     *            A boolean value indicating if GPS is active
     */
    private void setGpsProviderActive(boolean gpsProviderActive)
    {
        this.gpsProviderActive = gpsProviderActive;
    }

    /**
     * Gets the current status for the NetworkProvider flag
     *
     * @return A boolean value indicating if NEtwork is active
     */
    public boolean isNetworkProviderActive()
    {
        return networkProviderActive;
    }

    /**
     * Sets the flag of the current Netowork status
     *
     * @param networkProviderActive
     *            A boolean value indicating if Network is active
     */
    private void setNetworkProviderActive(boolean networkProviderActive)
    {
        this.networkProviderActive = networkProviderActive;
    }

    /***
     *
     * @return The current onNoProviders Listener
     */
    public OnNoProvidersListener getOnNoProvidersListener()
    {
        return onNoProvidersListener;
    }

    /**
     * Sets a listener to throw when no providers are working
     *
     * @param onNoProvidersListener
     */
    public void setOnNoProvidersListener(
            OnNoProvidersListener onNoProvidersListener)
    {
        this.onNoProvidersListener = onNoProvidersListener;
    }

    /**
     *
     * @return the current onLocationObtainedLister
     */
    public OnLocationObtainedListener getOnLocationObtainedListener()
    {
        return onLocationObtainedListener;
    }

    /**
     * Sets a listener to warn when location is obtained
     *
     */
    public void setOnLocationObtainedListener(
            OnLocationObtainedListener onLocationObtainedListener)
    {
        this.onLocationObtainedListener = onLocationObtainedListener;
    }

    @Override
    public void onLocationChanged(Location loc)
    {
        if (onLocationObtainedListener != null)
        {
            onLocationObtainedListener.onLocationObtained(loc);
        }

    }

    /**
     * Gets the las location received by the phone, whatever the provider is
     * @return
     *      The last known location
     */
    public Location getLastKnownLocation()
    {
        loadLocationManager();
        List<String> providers = locManager.getProviders(true);

        /*
         * Loop over the array backwards, and if you get an accurate location,
         * then break out the loop
         */
        Location lastLocation = null;

        for (int i = providers.size() - 1; i >= 0 && lastLocation == null; i--)
        {
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            {
                // TODO: Consider calling


            }
            lastLocation = locManager.getLastKnownLocation(providers.get(i));
        }

        return lastLocation;
    }

    @Override
    public void onProviderDisabled(String provider) {
        if (LocationManager.GPS_PROVIDER.equals(provider)) {
            setGpsProviderActive(false);
        }
        if (LocationManager.NETWORK_PROVIDER.equals(provider)) {
            setNetworkProviderActive(false);
        }
        if (!isNetworkProviderActive() && !isGpsProviderActive()
                && onNoProvidersListener != null) {
            onNoProvidersListener.onNoProviders();
        }
    }

    @Override
    public void onProviderEnabled(String provider) {
        if (LocationManager.GPS_PROVIDER.equals(provider)) {
            setGpsProviderActive(true);
        }
        if (LocationManager.NETWORK_PROVIDER.equals(provider)) {
            setNetworkProviderActive(true);
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    /**
     * This interface should implemented in order to receive new locations
     *
     * @author Sergi Mart�nez
     *
     */
    public interface OnLocationObtainedListener {
        /**
         * This listener is called when we receive a location from any provider
         *
         * @param loc
         *            The location received
         */
        public void onLocationObtained(Location loc);

    }

    /**
     * This interface should be implemented in order to receive a warning when
     * no providers are working
     *
     * @author Sergi Mart�nez
     *
     */
    public interface OnNoProvidersListener {
        /**
         * This listener is called when no providers are working
         */
        public void onNoProviders();
    }
}
