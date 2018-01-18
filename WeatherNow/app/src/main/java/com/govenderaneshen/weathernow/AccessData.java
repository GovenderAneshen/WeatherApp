package com.govenderaneshen.weathernow;

/**
 * Created by Govender on 1/18/2018.
 * interface to allow access to data from RequestWeatherData Task
 */

public interface AccessData
{
    public void returnData(weather[] forecast,String areaName);
}
