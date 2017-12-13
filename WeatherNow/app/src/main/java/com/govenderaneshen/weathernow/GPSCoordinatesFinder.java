package com.govenderaneshen.weathernow;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

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
//    public Location getCurrentLocation()
//    {
//        LocationManager locationManager = (LocationManager)context.getSystemService(Context.LOCATION_SERVICE);
//
//        /*
//            Check to see whether the users device has the GPS enabled
//         */
//        boolean isGPSenabled = locationManager.isProviderEnabled(locationManager.GPS_PROVIDER);
//
//        if(isGPSenabled == true)
//        {
//
//        }
//        else
//        {
//            Toast.makeText(context,"Please Enable the GPS in order to proceed",Toast.LENGTH_LONG).show();
//        }
//
//
//    }



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
