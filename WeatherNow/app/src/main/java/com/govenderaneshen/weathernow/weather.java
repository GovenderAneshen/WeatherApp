package com.govenderaneshen.weathernow;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * @author Govender Aneshen
 * Class used to form the basic structure of the weather content for a weather update
 */

public class weather

{
    /*
        Variables
     */
    private Date date;
    private String weatherDescription;
    private String conditionsCode;
    private int tempMin;
    private int tempMax;
    private String day;

    /**
     *
     * @param dateStamp of the weather conditions
     * @param weatherDescription
     * @param conditionsCode
     * @param tempMin
     * @param tempMax
     */
    public weather(long dateStamp, String weatherDescription, String conditionsCode, int tempMin, int tempMax)
    {

        this.weatherDescription = weatherDescription;
        this.conditionsCode = conditionsCode;
        this.tempMin = tempMin;
        this.tempMax = tempMax;
        date = new Date();


        /*
            Getting the date and day of the week
         */
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.getDefault());
        SimpleDateFormat dateFormatday = new SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.getDefault());
        dateFormatday.applyPattern("EEEE");
        Date newdate = new Date(dateStamp*1000);
        setDate(newdate);
        setDay(dateFormatday.format(getDate()));


    }

    public Date getDate()
    {
        return date;
    }

    public void setDate(Date date)
    {
        this.date = date;
    }

    public String getWeatherDescription()
    {
        return weatherDescription;
    }

    public void setWeatherDescription(String weatherDescription)
    {
        this.weatherDescription = weatherDescription;
    }

    public String getConditionsCode()
    {
        return conditionsCode;
    }

    public void setConditionsCode(String conditionsCode)
    {
        this.conditionsCode = conditionsCode;
    }

    public int getTempMin()
    {
        return tempMin;
    }

    public void setTempMin(int tempMin)
    {
        this.tempMin = tempMin;
    }

    public int getTempMax()
    {
        return tempMax;
    }

    public void setTempMax(int tempMax)
    {
        this.tempMax = tempMax;
    }

    public String getDay()
    {
        return day;
    }

    public void setDay(String day)
    {
        this.day = day;
    }


}
