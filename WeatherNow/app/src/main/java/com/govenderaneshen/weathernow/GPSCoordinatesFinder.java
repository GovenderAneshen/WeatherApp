package com.govenderaneshen.weathernow;

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
