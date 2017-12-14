package com.govenderaneshen.weathernow;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

/**
 * @author Govender Aneshen
 * Class is used to generate the longitude and latitude coordinates of the users current position
 *
 */

public class GPSCoordinatesFinder implements LocationListener
{

    /*
        Global Context Variable
     */
    private Context context;

    /**
     * @param AppContext
     * Constructor to set the current Application context
     */
    public GPSCoordinatesFinder(Context AppContext)
    {
        context = AppContext;
    }

    /**
     *
     * @return current location of the user
     */
    public Location getCurrentLocation()
    {
        /*
         *Permissions check
         */
        if(ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            Toast.makeText(context,"Permission not granted",Toast.LENGTH_SHORT).show();
            return null;
        }

        LocationManager locManager = (LocationManager)context.getSystemService(Context.LOCATION_SERVICE);

        /*
            Check to see whether the users device has the GPS enabled
         */
        boolean isGPSenabled = locManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

        if(isGPSenabled == true)
        {
            locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,6000,10,this);
            Location loc = locManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            return loc;
        }
        else
        {
            Toast.makeText(context,"Please Enable the GPS in order to proceed",Toast.LENGTH_LONG).show();
        }

        return null;


    }



    /**
     * Called when the location has changed.
     * @param location The new location, as a Location object.
     */
    @Override
    public void onLocationChanged(Location location) {

    }

    /**
     * Called when the provider status changes. This method is called when
     * a provider is unable to fetch a location or if the provider has recently
     * become available after a period of unavailability.
     *
     * @param provider the name of the location provider associated with this
     *                 update.
     * @param status
     * @param extras   an optional Bundle which will contain provider specific
     */
    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    /**
     * Called when the provider is enabled by the user.
     *
     * @param provider the name of the location provider associated with this
     *                 update.
     */
    @Override
    public void onProviderEnabled(String provider) {

    }

    /**
     * Called when the provider is disabled by the user. If requestLocationUpdates
     * is called on an already disabled provider, this method is called
     * immediately.
     * @param provider the name of the location provider associated with this
     *                 update.
     */
    @Override
    public void onProviderDisabled(String provider) {

    }
}
