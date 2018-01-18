package com.govenderaneshen.weathernow;

import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Toast;

import org.json.JSONArray;
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
    private Context thisContext;
    private weather[] forecast;
    private AccessData dataOut;
    public String areaName;


    public RequestWeatherData(Context context)
    {
        thisContext = context;
        dataOut = (AccessData)context;
    }



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
            JSONObject jsresponse = new JSONObject(result.substring(result.indexOf("{"),result.lastIndexOf("}")+1));

            /*
                JSON objects of separate weather information
            */
            JSONArray jsonList = jsresponse.optJSONArray("list");
            forecast = new weather[jsonList.length()];
            JSONObject jsonCity = jsresponse.getJSONObject("city");
            areaName = jsonCity.getString("name");
            TemperatureConversions tempCelsius = new TemperatureConversions();

            for(int i =0;i<jsonList.length();i++)
            {
                   /*
            Retrieving Data from JSON object
            */
                JSONObject jsonWeatherObject =  jsonList.getJSONObject(i);
                JSONArray jsonConditionArray = jsonWeatherObject.optJSONArray("weather");
                JSONObject jsonCondition = jsonConditionArray.getJSONObject(0);
                JSONObject jsonTemp = jsonWeatherObject.getJSONObject("temp");

                String date = jsonWeatherObject.getString("dt");
                String weatherDescription = jsonCondition.getString("description");
                String conditionsCode = jsonCondition.getString("icon");
                int tempMin = tempCelsius.kelvinToCelsius(Double.parseDouble(jsonTemp.getString("min")));
                int tempMax = tempCelsius.kelvinToCelsius(Double.parseDouble(jsonTemp.getString("max")));

                forecast[i] = new weather(Long.valueOf(date),weatherDescription,conditionsCode,tempMin,tempMax);

            }

            dataOut.returnData(forecast,areaName);




        }

        /*
            Error with JSON result
         */
        catch (Exception e)
        {
            MainActivity.load.setVisibility(View.GONE);
            Toast.makeText(thisContext,"Please Refresh",Toast.LENGTH_LONG).show();
        }


    }
}
