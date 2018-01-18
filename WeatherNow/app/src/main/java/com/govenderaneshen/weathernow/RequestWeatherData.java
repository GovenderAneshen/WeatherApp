package com.govenderaneshen.weathernow;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Locale;

import static com.govenderaneshen.weathernow.MainActivity.date;

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
    private weather[] forecast;
    public AlertDialog alert;
    public AlertDialog.Builder builder;


    public RequestWeatherData(Context context)
    {
        thisContext = context;
    }

    public void setImage(ImageView imgCondition, String conditionsCode)
    {
        /*
         * Switch statement to allocate the correct icon based on the weather condition
         */

        switch (conditionsCode) {
            case "01d":
                imgCondition.setImageResource(R.drawable.sun);
                break;
            case "01n":
                imgCondition.setImageResource(R.drawable.moon);
                break;
            case "02d":
                imgCondition.setImageResource(R.drawable.daycloud);
                break;
            case "02n":
                imgCondition.setImageResource(R.drawable.nightcloud);
                break;
            case "03d":
                imgCondition.setImageResource(R.drawable.cloud);
                break;
            case "03n":
                imgCondition.setImageResource(R.drawable.cloud);
                break;
            case "04d":
                imgCondition.setImageResource(R.drawable.brokencloud);
                break;
            case "04n":
                imgCondition.setImageResource(R.drawable.brokencloud);
                break;
            case "09d":
                imgCondition.setImageResource(R.drawable.showers);
                break;
            case "09n":
                imgCondition.setImageResource(R.drawable.showers);
                break;
            case "10d":
                imgCondition.setImageResource(R.drawable.rainday);
                break;
            case "10n":
                imgCondition.setImageResource(R.drawable.rainnight);
                break;
            case "11d":
                imgCondition.setImageResource(R.drawable.thunder);
                break;
            case "11n":
                imgCondition.setImageResource(R.drawable.thunder);
                break;
            case "13d":
                imgCondition.setImageResource(R.drawable.snow);
                break;
            case "13n":
                imgCondition.setImageResource(R.drawable.snow);
                break;
            case "50d":
                imgCondition.setImageResource(R.drawable.mist);
                break;
            case "50n":
                imgCondition.setImageResource(R.drawable.mist);
                break;
        }
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

            ContextThemeWrapper ctw = new ContextThemeWrapper(thisContext, R.style.Theme_AppCompat_Dialog);
            builder = new AlertDialog.Builder(ctw);

            MainActivity.stringWeatherData =weatherResults;

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


            /*
                Setting text in textViews
             */
            areaName = jsonCity.getString("name");
            MainActivity.minTempView.setText("Min:  "+forecast[0].getTempMin()+ " °C");
            MainActivity.maxTempView.setText("Max: "+forecast[0].getTempMax()+ " °C");
            MainActivity.ConditionView.setText(forecast[0].getWeatherDescription());
            MainActivity.AreaView.setText(areaName);
            setImage(MainActivity.condition,forecast[0].getConditionsCode());

            SimpleDateFormat dateFormatday = new SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.getDefault());
            dateFormatday.applyPattern("EEEE, dd MMM yyyy");
            date.setText(dateFormatday.format(forecast[0].getDate()));


            /*
                Dealing with the forecast for 5 days
             */
            MainActivity.tblForecast.removeAllViewsInLayout();

            for(int i =1;i<jsonList.length();i++)
            {
                TableRow tr = new TableRow(thisContext);

                /*
                    Storing data from the forecast array into local variables
                 */
                String Day = forecast[i].getDay();
                SimpleDateFormat dateFormatday2 = new SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.getDefault());
                dateFormatday2.applyPattern("dd MMM");
                String Date= dateFormatday2.format(forecast[i].getDate());
                String icon = forecast[i].getConditionsCode();
                String Description = forecast[i].getWeatherDescription();
                int AverageTemp = (forecast[i].getTempMin() +forecast[i].getTempMax())/2;

                TextView day = new TextView(thisContext);
                day.setText(Day);
                day.setPadding(10, 5, 5, 5);
                day.setTextColor(Color.WHITE);
                day.setBackgroundColor(Color.parseColor("#80002afe"));
                day.setGravity(Gravity.CENTER);
                day.setTextSize(16);

                TextView date2 = new TextView(thisContext);
                date2.setText(Date);
                date2.setPadding(10, 5, 5, 5);
                date2.setTextColor(Color.WHITE);
                date2.setBackgroundColor(Color.parseColor("#80002afe"));
                date2.setGravity(Gravity.CENTER);
                date2.setTextSize(16);

                final int index = i;
                TextView temp = new TextView(thisContext);
                temp.setText(AverageTemp + " °C" );
                temp.setPadding(10, 5, 5, 5);
                temp.setTextColor(Color.BLACK);
                temp.setGravity(Gravity.CENTER);
                temp.setTextSize(16);

                TextView description = new TextView(thisContext);
                description.setText(Description);
                description.setPadding(10, 5, 5, 5);
                description.setTextColor(Color.BLACK);
                description.setGravity(Gravity.CENTER);
                description.setTextSize(16);



                ImageView iconCondition = new ImageView(thisContext);

                iconCondition.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,TableRow.LayoutParams.WRAP_CONTENT));
                setImage(iconCondition,icon);

                ViewGroup.LayoutParams layoutParams = iconCondition.getLayoutParams();
                layoutParams.width = 80;
                layoutParams.height = 80;
                iconCondition.setLayoutParams(layoutParams);

                final LinearLayout linearLayout = new LinearLayout(thisContext);
                TextView hello;
                hello = new TextView(thisContext);
                hello.setText("hello");
                linearLayout.addView(hello);

//                linearLayout.addView(temp);
//                linearLayout.addView(description);
//                linearLayout.addView(iconCondition);

                temp.setOnClickListener(new  View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {






                        TextView TitleView = new TextView(thisContext);
                        TitleView.setText("Weather for " + forecast[index].getDate().toString());
                        TitleView.setTextColor(Color.WHITE);
                        TitleView.setTextSize(20);
                        TitleView.setPadding(0, 15, 0, 15);
                        TitleView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                        builder.setCustomTitle(TitleView);


                        builder.setCancelable(true);
                        builder.setView(linearLayout);

                        alert = builder.create();
                        alert.show();






                    }
                });





                tr.addView(day);
                tr.addView(date2);
                tr.addView(temp);
                tr.addView(description);
                tr.addView(iconCondition);


                MainActivity.tblForecast.addView(tr);
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
