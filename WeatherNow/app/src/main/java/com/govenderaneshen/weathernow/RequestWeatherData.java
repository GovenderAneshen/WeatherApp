package com.govenderaneshen.weathernow;

import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author Govender Aneshen
 * Class is used to send the current location to the OpenWeatherApp API to obtain the current weather conditions
 */

public class RequestWeatherData extends AsyncTask <String,Void,String>
{

    /*
        Global Variables
     */
    private String weatherResults;
    private URL weatherURL;
    private HttpURLConnection urlConnection = null;

    /**
     * Used to retrieve the weather data
     * @param url The url parameter
     * @return A result, weather data
     */
    @Override
    protected String doInBackground(String... url)
    {
        try
        {
            /*
                Retrieving of the weather data using URL Connection
             */
            weatherURL = new URL(url[0]);
            urlConnection = (HttpURLConnection)weatherURL.openConnection();

            InputStream retrieveData = urlConnection.getInputStream();
            InputStreamReader readData = new InputStreamReader(retrieveData);

            /*
                Local variable to store the value of the data
             */
            int nextData = readData.read();

            while(nextData != -1)
            {
                char currentData = (char)nextData;
                weatherResults += currentData;

                nextData = readData.read();

            }

            return weatherResults;


        }

        /*
            Error with URL
         */
        catch (MalformedURLException e)
        {
            e.printStackTrace();
        }

        /*
            Unable to open connection
         */
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Method that extracts the required data
     * @param result The weather results
     */
    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);


        try
        {
            /*
                Main JSON object from the OpenWeatherApp API
             */
            JSONObject jsonObject = new JSONObject(result);

            JSONObject weatherData = new JSONObject(jsonObject.getString("main"));
            JSONObject conditionsData = new JSONObject(jsonObject.getString("weather"));

            String areaName = jsonObject.getString("name");
            Double tempMinKal = Double.parseDouble(weatherData.getString("temp_min"));
            Double tempMaxKal = Double.parseDouble(weatherData.getString("temp_max"));


        }


        /*
            Error with JSON result
         */
        catch (JSONException e) {
            e.printStackTrace();
        }


    }
}
