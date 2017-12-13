
package com.govenderaneshen.weathernow;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

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

    /*
     * Key value assigned to the different weather condition icons
     */
    private int key = 1;


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
         * Initialization of the condition ImageView
         */
        condition = (ImageView)findViewById(R.id.imgCondition);


        /*
         * Switch statement to allocate the correct icon based on the weather condition
         */
        switch(key)
        {
            case 1:
                condition.setImageResource(R.drawable.d3);
                break;
            case 2:
                condition.setImageResource(R.drawable.n);
                break;
        }





    }

}
