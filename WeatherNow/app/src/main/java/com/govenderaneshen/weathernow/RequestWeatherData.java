package com.govenderaneshen.weathernow;

import android.content.Context;
import android.os.AsyncTask;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import static com.govenderaneshen.weathernow.MainActivity.condition;

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
    public String areaName;


    public RequestWeatherData(Context context)
    {
        thisContext = context;
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
            JSONObject jsonWeather = jsresponse.getJSONObject("main");
            JSONArray jsonConditionArray = jsresponse.optJSONArray("weather");
            JSONObject jsonCondition = jsonConditionArray.getJSONObject(0);


             /*
            Retrieving Data from JSON object
            */
            TemperatureConversions tempCelsius = new TemperatureConversions();
            areaName = jsresponse.getString("name");
            String weatherDescription = jsonCondition.getString("description");
            String conditionsCode = jsonCondition.getString("icon");
            int tempMin = tempCelsius.kelvinToCelsius(Double.parseDouble(jsonWeather.getString("temp_min")));
            int tempMax = tempCelsius.kelvinToCelsius(Double.parseDouble(jsonWeather.getString("temp_max")));


            /*
                Setting text in textViews
             */

                MainActivity.minTempView.setText("Min:  "+tempMin+ " °C");
                MainActivity.maxTempView.setText("Max: "+tempMax+ " °C");
                MainActivity.ConditionView.setText(weatherDescription);
                MainActivity.AreaView.setText(areaName);
        /*
         * Switch statement to allocate the correct icon based on the weather condition
         */

            switch(conditionsCode)
            {
                case "01d":
                    condition.setImageResource(R.drawable.sun);
                    break;
                case "01n":
                    condition.setImageResource(R.drawable.moon);
                    break;
                case "02d":
                    condition.setImageResource(R.drawable.daycloud);
                    break;
                case "02n":
                    condition.setImageResource(R.drawable.nightcloud);
                    break;
                case "03d":
                    condition.setImageResource(R.drawable.cloud);
                    break;
                case "03n":
                    condition.setImageResource(R.drawable.cloud);
                    break;
                case "04d":
                    condition.setImageResource(R.drawable.brokencloud);
                    break;
                case "04n":
                    condition.setImageResource(R.drawable.brokencloud);
                    break;
                case "09d":
                    condition.setImageResource(R.drawable.showers);
                    break;
                case "09n":
                    condition.setImageResource(R.drawable.showers);
                    break;
                case "10d":
                    condition.setImageResource(R.drawable.rainday);
                    break;
                case "10n":
                    condition.setImageResource(R.drawable.rainnight);
                    break;
                case "11d":
                    condition.setImageResource(R.drawable.thunder);
                    break;
                case "11n":
                    condition.setImageResource(R.drawable.thunder);
                    break;
                case "13d":
                    condition.setImageResource(R.drawable.snow);
                    break;
                case "13n":
                    condition.setImageResource(R.drawable.snow);
                    break;
                case "50d":
                    condition.setImageResource(R.drawable.mist);
                    break;
                case "50n":
                    condition.setImageResource(R.drawable.mist);
                    break;
            }

            MainActivity.load.setVisibility(View.GONE);



        }

        /*
            Error with JSON result
         */
        catch (JSONException e) {
            e.printStackTrace();
        }


    }
}
