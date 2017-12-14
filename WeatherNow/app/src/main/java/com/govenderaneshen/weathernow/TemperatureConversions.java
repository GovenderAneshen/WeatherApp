package com.govenderaneshen.weathernow;

/**
 * @author Govender Aneshen
 * Class containing functions for converting temperatures from Kelvin to Celsius or Fahrenheit and vise versa
 */

public class TemperatureConversions
{
    public int kelvinToCelsius(Double tempKel)
    {
        return (int)(tempKel - 273.15);
    }
}
