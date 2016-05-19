package com.jimtrinh9985gmail.swingtracker;

import android.content.Context;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.DataApi;
import com.google.android.gms.wearable.DataEvent;
import com.google.android.gms.wearable.DataEventBuffer;
import com.google.android.gms.wearable.DataMapItem;
import com.google.android.gms.wearable.Wearable;

import wearprefs.WearPrefs;


public class MainActivity extends AppCompatActivity {

    public final String LOG_TAG = MainActivity.class.getSimpleName();

    private static final String SP_KEY_FOREHAND = "forehandCount";
    private static final String SP_KEY_BACKHAND = "backhandCount";
    private static final String SP_KEY_OVERHEAD = "overheadCount";
    private String forehandCount;
    private String backhandCount;
    private String overheadCount;

    // Swings //
    private TextView forehandText, backhandText, overheadText;

    //private SharedPreferences prefs;

    //private static final String SP_KEY_FOREHAND = "forehandCount";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        WearPrefs.init(this, SP_KEY_FOREHAND);
        WearPrefs.init(this, SP_KEY_BACKHAND);
        WearPrefs.init(this, SP_KEY_OVERHEAD);

        SharedPreferences forehands = getSharedPreferences("forehandCount",
                getApplicationContext().MODE_PRIVATE);
        int forehand = forehands.getInt("forehandCount", -1);

        SharedPreferences backhands = getSharedPreferences("backhandCount",
                getApplicationContext().MODE_PRIVATE);
        int backhand = backhands.getInt("backhandCount", -1);

        SharedPreferences overheads = getSharedPreferences("backhandCount",
                getApplicationContext().MODE_PRIVATE);
        int overhead = overheads.getInt("backhandCount", -1);


        Log.d(LOG_TAG, "Shared Preference Forehand: " + forehand);
        Log.d(LOG_TAG, "Shared Preference Backhand: " + backhand);
        Log.d(LOG_TAG, "Shared Preference Overhead: " + overhead);

        forehandText = (TextView) findViewById(R.id.forehand_count);
        backhandText = (TextView) findViewById(R.id.backhand_count);
        overheadText = (TextView) findViewById(R.id.overhead_count);

        //setForehandCounter(SP_KEY_FOREHAND.toString());
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
