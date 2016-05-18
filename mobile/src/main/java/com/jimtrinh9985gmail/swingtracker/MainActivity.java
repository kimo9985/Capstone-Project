package com.jimtrinh9985gmail.swingtracker;

import android.content.Context;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;



public class MainActivity extends AppCompatActivity {

    public final String LOG_TAG = MainActivity.class.getSimpleName();

    // Swings //
    private TextView forehandText, backhandText, overheadText;

    //private SharedPreferences prefs;

    //private static final String SP_KEY_FOREHAND = "forehandCount";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Log.d(LOG_TAG, "Shared Preference: " + this);

        forehandText = (TextView) findViewById(R.id.forehand_count);
        backhandText = (TextView) findViewById(R.id.backhand_count);
        overheadText = (TextView) findViewById(R.id.overhead_count);

        //setForehandCounter(this.getString(Integer.parseInt(SP_KEY_FOREHAND)));
        //setBackhandCounter(Utilities.getPrefBackhand(getActivity()));
        //setOverheadCounter(Utilities.getPrefOverhead(getActivity()));
    }

//    public void setForehandCounter(String text) {
//        forehandText.setText(text);
//    }

//    public void setForehandCounter(int i) {
//        setForehandCounter(i < 0 ? "0" : String.valueOf(i));
//    }
}
