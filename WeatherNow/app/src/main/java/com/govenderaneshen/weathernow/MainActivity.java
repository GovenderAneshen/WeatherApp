
package com.govenderaneshen.weathernow;

import android.Manifest;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author Govender Aneshen
 * Main Activity class which excecutes code to output the current Weather to the user
 */
public class MainActivity extends AppCompatActivity {

    /*
     * Global Variables
     */
    static ImageView condition;
    static TextView AreaView;
    static TextView minTempView;
    static TextView maxTempView;
    static TextView ConditionView;


    private double currentLongitude;
    private double currentLatitude;


    /*
     * code value assigned to the different weather condition icons
     */
    private String conditionCode = " ";


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


        /* Create an instance of the GPSCoordinateFinder class */
        GPSCoordinatesFinder GPS = new GPSCoordinatesFinder(getApplicationContext());

        /* Getting the Location */
        Location location = GPS.getCurrentLocation();

        /* If the Location if found*/
        if(location != null)
        {
            currentLongitude = location.getLongitude();
            currentLatitude = location.getLatitude();

            RequestWeatherData weatherData = new RequestWeatherData(getApplicationContext());
            String url = "http://api.openweathermap.org/data/2.5/weather?lat="+String.valueOf(currentLatitude)+"&lon="+String.valueOf(currentLongitude)+"&appid=83f02b94f16881850e678ca1b96731a5";
            weatherData.execute(url);

        }
        else
        {
            Toast.makeText(getApplicationContext(),"Please Refresh",Toast.LENGTH_LONG).show();
        }







    }

}
