package com.jimtrinh9985gmail.swingtracker;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.wearable.DataApi;
import com.google.android.gms.wearable.DataEventBuffer;
import com.google.android.gms.wearable.PutDataMapRequest;
import com.google.android.gms.wearable.PutDataRequest;
import com.google.android.gms.wearable.Wearable;
import com.jimtrinh9985gmail.swingtracker.myDatabase.MyDataFragment;
import com.jimtrinh9985gmail.swingtracker.myDatabase.MyDatabase;

import java.util.Calendar;

import wearprefs.WearPrefs;

public class MainActivity extends AppCompatActivity implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        DataApi.DataListener {

    public final String LOG_TAG = MainActivity.class.getSimpleName();

    private Button button, button1;
    private int forehand, backhand, overhead;
    GoogleApiClient mGoogleApiClient;

    public static final String SP_KEY_NAME = "com.swingtracker.NAME";
    public static final String SP_KEY_HEIGHT = "com.swingtracker.HEIGHT";
    public static final String SP_KEY_WEIGHT = "com.swingtracker.WEIGHT";
    public static final String SP_KEY_RACKET = "com.swingtracker.RACKET";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        WearPrefs.init(this, "com.swingtracker.FOREHAND");
        WearPrefs.init(this, "com.swingtracker.BACKHAND");
        WearPrefs.init(this, "com.swingtracker.OVERHEAD");

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Wearable.API)
                .build();

        button = (Button) findViewById(R.id.chart_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recreate();
            }
        });

        button1 = (Button) findViewById(R.id.save_button);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertWorkoutData(v);
            }
        });
    }

    // Insert Workout Data to SQLiteDB //
    public void insertWorkoutData(View view) {

        int status = 0;

        SharedPreferences forehands = this.getSharedPreferences
                ("com.swingtracker.FOREHAND", Context.MODE_PRIVATE);
        forehand = (forehands.getInt("com.swingtracker.FOREHAND", 0));

        SharedPreferences backhands = this.getSharedPreferences
                ("com.swingtracker.BACKHAND", Context.MODE_PRIVATE);
        backhand = (backhands.getInt("com.swingtracker.BACKHAND", 0));

        SharedPreferences overheads = this.getSharedPreferences
                ("com.swingtracker.OVERHEAD", Context.MODE_PRIVATE);
        overhead = (overheads.getInt("com.swingtracker.OVERHEAD", 0));

        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DATE);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);
        String date = month + "-" + day + "-" + year;

        MyDatabase database = new MyDatabase(this);

        long id = database.insertData(status, forehand, backhand, overhead, date);
        Toast.makeText(this, "Workout Data Saved!", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
        Log.d(LOG_TAG, "Mobile onStart - Connect to Wearable!");
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Wearable.DataApi.addListener(mGoogleApiClient, this);
        Log.d(LOG_TAG, "onConnected to Wearable!");
        updateProfile();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d(LOG_TAG, "onConnectionFailed!");
    }

    @Override
    public void onDataChanged(DataEventBuffer dataEventBuffer) {
    }

    public void updateProfile() {

        if (mGoogleApiClient.isConnected()) {

            PutDataMapRequest putDataMapRequest = PutDataMapRequest.create("/swing-data");

            putDataMapRequest.getDataMap().putString(SP_KEY_NAME,
                    UtilityProfile.getPrefProfileName(this));
            putDataMapRequest.getDataMap().putString(SP_KEY_HEIGHT,
                    UtilityProfile.getPrefProfileHeight(this));
            putDataMapRequest.getDataMap().putString(SP_KEY_WEIGHT,
                    UtilityProfile.getPrefProfileWeight(this));
            putDataMapRequest.getDataMap().putString(SP_KEY_RACKET,
                    UtilityProfile.getPrefProfileRacket(this));

            PutDataRequest putDataRequest = putDataMapRequest.asPutDataRequest();

            Wearable.DataApi.putDataItem(mGoogleApiClient, putDataRequest)
                    .setResultCallback(new ResultCallback<DataApi.DataItemResult>() {
                        @Override
                        public void onResult(@NonNull DataApi.DataItemResult dataItemResult) {
                            if (dataItemResult.getStatus().isSuccess()) {
                                Log.d(LOG_TAG, "Profile Data sent successfully!");
                            } else {
                                Log.d(LOG_TAG, "Profile Data failed to send!" +
                                        dataItemResult.getStatus().getStatusCode());
                            }
                        }
                    });
        } else {
            Log.d(LOG_TAG, "mGoogleApiClient failed to connect!");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_chart:
                return true;
            case R.id.menu_profile:
                Intent intent = new Intent(this, ProfileFragment.class);
                startActivity(intent);
                return true;
            case R.id.menu_data:
                Intent intent1 = new Intent(this, MyDataFragment.class);
                startActivity(intent1);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
