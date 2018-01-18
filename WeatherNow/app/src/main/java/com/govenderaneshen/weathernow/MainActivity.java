
package com.govenderaneshen.weathernow;

import android.Manifest;
import android.app.AlertDialog;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * @author Govender Aneshen
 * Main Activity class which excecutes code to output the current Weather to the user
 */
public class MainActivity extends AppCompatActivity implements AccessData{

    /*
     * Global Variables
     */
    static ImageView condition;
    static TextView AreaView;
    static TextView minTempView;
    static TextView maxTempView;
    static TextView ConditionView;
    static TextView date;
    static TableLayout tblForecast;
    static ProgressBar load;

 

    /*
        Location variables
     */
    private double currentLongitude;
    private double currentLatitude;

    /**
     * This Method sets the imageResource for the given Imageview
     * @param imgCondition Is the Imageview for which the resource must be set
     * @param conditionsCode is the weather conditions code
     */
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
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {

            }
            return false;
        }

    };

    /**
     * function to refresh details
     */
    public void refresh()
    {
         /* Create an instance of the GPSCoordinateFinder class */
        GPSCoordinatesFinder GPS = new GPSCoordinatesFinder(getApplicationContext());

        /* Getting the Location */
        Location location = GPS.getCurrentLocation();

        /* If the Location if found*/
        if(location != null)
        {
            currentLongitude = location.getLongitude();
            currentLatitude = location.getLatitude();

            RequestWeatherData weatherData = new RequestWeatherData(MainActivity.this);
            String url = "http://api.openweathermap.org/data/2.5/forecast/daily?lat="+String.valueOf(currentLatitude)+"&lon="+String.valueOf(currentLongitude)+"&cnt=6&appid=927d09bc49dbee6aac7f5cb1df707542";
            weatherData.execute(url);
           

        }
        else
        {
            Toast.makeText(getApplicationContext(),"Please Refresh",Toast.LENGTH_LONG).show();
        }

    }

    /**
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*
         * Hiding of the Action bar of the activity
         */
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);

        /*
            Request for GPS permission
         */

        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},123);

        /*
         * Initialization of the condition ImageView and Textviews
         */
        condition = (ImageView)findViewById(R.id.imgCondition);
        AreaView = (TextView)findViewById(R.id.txtArea);
        minTempView = (TextView)findViewById(R.id.txtMinTemp);
        maxTempView = (TextView)findViewById(R.id.txtMax);
        ConditionView = (TextView)findViewById(R.id.txtCondition);
        date = (TextView)findViewById(R.id.txtDate);
        tblForecast = (TableLayout)findViewById(R.id.tblForecast);
        load = (ProgressBar)findViewById(R.id.progressBar);

        load.getIndeterminateDrawable().setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.MULTIPLY);


       refresh();

        /*
            Refresh Button
         */
        FloatingActionButton refresh = (FloatingActionButton)findViewById(R.id.btnRefresh);
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                load.setVisibility(View.GONE);
                recreate();
            }
        });




    }

    /**
     * Implementation of the function in the AccessData interface in order to access the JSON data from RequestWeatherData
     * @param forecast is the array containing the weather data
     * @param areaName is the name of the city location
     */
    @Override
    public void returnData(weather[] forecast, String areaName)
    {

        try
        {

            /*
                Setting text in textViews
             */
            minTempView.setText("Min:  " + forecast[0].getTempMin() + " °C");
            maxTempView.setText("Max: " + forecast[0].getTempMax() + " °C");
            ConditionView.setText(forecast[0].getWeatherDescription());
            AreaView.setText(areaName);
            setImage(MainActivity.condition, forecast[0].getConditionsCode());

            /*
                Setting date formats
             */
            SimpleDateFormat dateFormatday = new SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.getDefault());
            dateFormatday.applyPattern("EEEE, dd MMM yyyy");
            date.setText(dateFormatday.format(forecast[0].getDate()));


            /*
                Dealing with the forecast for 5 days
             */
            tblForecast.removeAllViewsInLayout();
            tblForecast.setStretchAllColumns(true);
            tblForecast.setGravity(Gravity.CENTER);

            TableRow tr = new TableRow(MainActivity.this);
            TableRow tr2 = new TableRow(MainActivity.this);
            for (int i = 1; i < forecast.length; i++)
            {


                /*
                    Storing data from the forecast array into local variables
                 */
                final String Day = forecast[i].getDay();
                SimpleDateFormat dateFormatday2 = new SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.getDefault());
                dateFormatday2.applyPattern("dd MMM");
                final String Date = dateFormatday2.format(forecast[i].getDate());
                final String icon = forecast[i].getConditionsCode();
                final String Description = forecast[i].getWeatherDescription();
                final String MinTemp = "Min:  " + forecast[i].getTempMin() + " °C";
                final String MaxTemp = "Max: " + forecast[0].getTempMax() + " °C";

                /*
                    Calculating average temperature for forecast
                 */
                int AverageTemp = (forecast[i].getTempMin() + forecast[i].getTempMax()) / 2;

                /*
                    Setting texts of the textviews
                 */
                TextView day = new TextView(MainActivity.this);
                day.setText(Day);
                day.setPadding(10, 5, 5, 5);
                day.setTextColor(Color.WHITE);
                day.setBackgroundColor(Color.parseColor("#80002afe"));
                day.setGravity(Gravity.CENTER);
                day.setTextSize(14);

                TextView date2 = new TextView(MainActivity.this);
                date2.setText(Date);
                date2.setPadding(10, 5, 5, 5);
                date2.setTextColor(Color.WHITE);
                date2.setBackgroundColor(Color.parseColor("#80002afe"));
                date2.setGravity(Gravity.CENTER);
                date2.setTextSize(16);


                TextView temp = new TextView(MainActivity.this);
                temp.setText(AverageTemp + " °C");
                temp.setPadding(10, 5, 5, 5);
                temp.setTextColor(Color.BLACK);
                temp.setGravity(Gravity.CENTER);
                temp.setTextSize(18);

                TextView description = new TextView(MainActivity.this);
                description.setText(Description);
                description.setPadding(10, 5, 5, 5);
                description.setTextColor(Color.BLACK);
                description.setGravity(Gravity.CENTER);
                description.setTextSize(16);


                /*
                    Setting Image view for weather condition icons
                 */
                ImageView iconCondition = new ImageView(MainActivity.this);

                iconCondition.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
                setImage(iconCondition, icon);

                ViewGroup.LayoutParams layoutParams = iconCondition.getLayoutParams();
                layoutParams.width = 120;
                layoutParams.height = 120;
                iconCondition.setLayoutParams(layoutParams);

                /*
                    Adding all the views into LinearLayouts
                 */
                final LinearLayout linearLayout = new LinearLayout(MainActivity.this);
                linearLayout.setOrientation(LinearLayout.VERTICAL);
                linearLayout.addView(day);
                linearLayout.addView(date2);

                final View vline1 = new View(MainActivity.this);
                vline1.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, 1));
                vline1.setBackgroundColor(Color.WHITE);
                linearLayout.addView(vline1);

                linearLayout.addView(temp);
                linearLayout.addView(iconCondition);

                final View vline2 = new View(MainActivity.this);
                vline2.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, 1));
                vline2.setBackgroundColor(Color.WHITE);

                linearLayout.addView(vline2);

                linearLayout.setGravity(Gravity.CENTER);
                /*
                    Handling specific day clicks to display full weather details
                 */
                linearLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        /*
                            Setting texts of textViews and setting image resources for the weather condition icons
                         */
                        final LinearLayout linearLayout = new LinearLayout(MainActivity.this);
                        linearLayout.setOrientation(LinearLayout.VERTICAL);

                        TextView min = new TextView(MainActivity.this);
                        min.setText(MinTemp);
                        min.setPadding(10, 5, 5, 5);
                        min.setTextColor(Color.WHITE);
                        min.setGravity(Gravity.CENTER);
                        min.setTextSize(18);

                        TextView max = new TextView(MainActivity.this);
                        max.setText(MaxTemp);
                        max.setPadding(10, 5, 5, 5);
                        max.setTextColor(Color.WHITE);
                        max.setGravity(Gravity.CENTER);
                        max.setTextSize(18);

                        final LinearLayout linearLayout2 = new LinearLayout(MainActivity.this);
                        linearLayout2.setOrientation(LinearLayout.VERTICAL);

                        final LinearLayout verticalBorder = new LinearLayout(MainActivity.this);
                        verticalBorder.setBackgroundColor(Color.WHITE);

                        ImageView iconCondition2 = new ImageView(MainActivity.this);

                        iconCondition2.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
                        setImage(iconCondition2, icon);

                        ViewGroup.LayoutParams layoutParams = iconCondition2.getLayoutParams();
                        layoutParams.width = 200;
                        layoutParams.height = 200;
                        iconCondition2.setLayoutParams(layoutParams);

                        final LinearLayout linearLayout3 = new LinearLayout(MainActivity.this);
                        linearLayout3.setOrientation(LinearLayout.VERTICAL);

                        TextView description = new TextView(MainActivity.this);
                        description.setText(Description);
                        description.setPadding(10, 5, 5, 5);
                        description.setTextColor(Color.WHITE);
                        description.setGravity(Gravity.CENTER);
                        description.setTextSize(16);

                        /*
                            Addition of views to LinearLayout
                         */
                        linearLayout.addView(min);
                        linearLayout.addView(max);
                        linearLayout2.addView(iconCondition2);
                        linearLayout3.addView(description);

                        /*
                            Building of the alert Dialog
                         */
                        ContextThemeWrapper ctw = new ContextThemeWrapper(MainActivity.this, R.style.Theme_AppCompat_Dialog);
                        AlertDialog.Builder builder = new AlertDialog.Builder(ctw);


                        TextView TitleView = new TextView(MainActivity.this);
                        String out = "Weather for " + Day + ", " + Date;
                        TitleView.setText(out);
                        TitleView.setTextColor(Color.WHITE);
                        TitleView.setTextSize(20);
                        TitleView.setPadding(0, 15, 0, 15);
                        TitleView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                        TitleView.setBackgroundColor(Color.parseColor("#80002afe"));
                        builder.setCustomTitle(TitleView);

                        final LinearLayout linearLayout4 = new LinearLayout(MainActivity.this);
                        linearLayout4.setOrientation(LinearLayout.HORIZONTAL);
                        linearLayout4.setGravity(Gravity.CENTER);
                        linearLayout4.addView(linearLayout);
                        linearLayout4.addView(linearLayout2);
                        linearLayout4.addView(linearLayout3);

                        builder.setCancelable(true);
                        builder.setView(linearLayout4);

                        final AlertDialog alert = builder.create();
                        alert.show();


                    }
                });

                /*
                    Addition of seperate LinearLayout to produce Vertical Divider
                 */
                final LinearLayout verticalBorder = new LinearLayout(MainActivity.this);
                verticalBorder .setOrientation(LinearLayout.VERTICAL);
                verticalBorder.setBackgroundColor(Color.WHITE);
                verticalBorder.setLayoutParams(new TableRow.LayoutParams(1,TableRow.LayoutParams.MATCH_PARENT));

                TextView border = new TextView(MainActivity.this);
                description.setText("");
                description.setTextColor(Color.WHITE);
                description.setGravity(Gravity.CENTER);
                description.setTextSize(16);

                verticalBorder.addView(border);
                tr.addView(linearLayout);

                /*
                    For no border at the end
                 */
                if(i !=5)
                {
                    tr.addView(verticalBorder);
                }




            }


            /*
                Addition of row to table
             */
            tblForecast.addView(tr);


        }
        catch (Exception e)
        {
            Toast.makeText(MainActivity.this,"Please Refresh",Toast.LENGTH_LONG).show();
        }

        /*
            Stopping the progress bar
         */
        MainActivity.load.setVisibility(View.GONE);

    }
}
