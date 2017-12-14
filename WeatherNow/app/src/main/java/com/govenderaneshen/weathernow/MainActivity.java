
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
    private TextView mTextMessage;
    private ImageView condition;

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
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_notifications);
                    return true;
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
         * Initialization of the condition ImageView
         */
        condition = (ImageView)findViewById(R.id.imgCondition);


        /*
         * Switch statement to allocate the correct icon based on the weather condition
         */

        switch(conditionCode)
        {
            case "111":
                condition.setImageResource(R.drawable.d3);
                break;
            case "3d0":
                condition.setImageResource(R.drawable.n);
                break;
        }


        /* Create an instance of the GPSCoordinateFinder class */
        GPSCoordinatesFinder GPS = new GPSCoordinatesFinder(getApplicationContext());

        /* Getting the Location */
        Location location = GPS.getCurrentLocation();

        /* If the Location if found*/
        if(location != null)
        {
            currentLongitude = location.getLongitude();
            currentLatitude = location.getLatitude();
            Toast.makeText(getApplicationContext(),"Longitude: " + String.valueOf(currentLongitude) + "/n Latitude: "+ String.valueOf(currentLatitude),Toast.LENGTH_LONG).show();
        }
        else
        {
            Toast.makeText(getApplicationContext(),"Please Refresh",Toast.LENGTH_LONG).show();
        }







    }

}
