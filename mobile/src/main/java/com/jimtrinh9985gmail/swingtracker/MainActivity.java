package com.jimtrinh9985gmail.swingtracker;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import wearprefs.WearPrefs;


public class MainActivity extends AppCompatActivity {

    public final String LOG_TAG = MainActivity.class.getSimpleName();

//    private static final String SP_KEY_FOREHAND = "com.swingtracker.FOREHAND";
//    private static final String SP_KEY_BACKHAND = "com.swingtracker.BACKHAND";
//    private static final String SP_KEY_OVERHEAD = "com.swingtracker.OVERHEAD";
//    private String forehandCount;
//    private String backhandCount;
//    private String overheadCount;

    // Swings //
    private TextView forehandText, backhandText, overheadText;

    //private SharedPreferences prefs;

    //private static final String SP_KEY_FOREHAND = "forehandCount";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        WearPrefs.init(this, "com.swingtracker.FOREHAND");
        WearPrefs.init(this, "com.swingtracker.BACKHAND");
        WearPrefs.init(this, "com.swingtracker.OVERHEAD");

        SharedPreferences forehands = getSharedPreferences("com.swingtracker.FOREHAND",
                getApplicationContext().MODE_PRIVATE);
        int forehand = forehands.getInt("com.swingtracker.FOREHAND", 0);

        SharedPreferences backhands = getSharedPreferences("com.swingtracker.BACKHAND",
                getApplicationContext().MODE_PRIVATE);
        int backhand = backhands.getInt("com.swingtracker.BACKHAND", 0);

        SharedPreferences overheads = getSharedPreferences("com.swingtracker.OVERHEAD",
                getApplicationContext().MODE_PRIVATE);
        int overhead = overheads.getInt("com.swingtracker.OVERHEAD", 0);

        Log.d(LOG_TAG, "Shared Preference Forehand: " + forehand);
        Log.d(LOG_TAG, "Shared Preference Backhand: " + backhand);
        Log.d(LOG_TAG, "Shared Preference Overhead: " + overhead);

//        forehandText = (TextView) findViewById(R.id.forehand_count);
//        backhandText = (TextView) findViewById(R.id.backhand_count);
//        overheadText = (TextView) findViewById(R.id.overhead_count);
//
//        setForehandCounter(toString().valueOf(forehand));
//        setBackhandCounter(toString().valueOf(backhand));
//        setOverheadCounter(toString().valueOf(overhead));
    }

    public void setForehandCounter(String text) {
        forehandText.setText(text);
    }

    public void setBackhandCounter(String text) {
        backhandText.setText(text);
    }

    public void setOverheadCounter(String text) {
        overheadText.setText(text);
    }


}
