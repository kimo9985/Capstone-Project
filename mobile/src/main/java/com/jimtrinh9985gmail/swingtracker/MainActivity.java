package com.jimtrinh9985gmail.swingtracker;

import android.content.Intent;
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

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.wearable.DataApi;
import com.google.android.gms.wearable.DataEvent;
import com.google.android.gms.wearable.DataEventBuffer;
import com.google.android.gms.wearable.DataMapItem;
import com.google.android.gms.wearable.PutDataMapRequest;
import com.google.android.gms.wearable.PutDataRequest;
import com.google.android.gms.wearable.Wearable;

import java.util.Random;


public class MainActivity extends AppCompatActivity implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        DataApi.DataListener {

    public final String LOG_TAG = MainActivity.class.getSimpleName();

    private Button button, button1;

    public static final String SP_KEY_FOREHAND = "com.swingtracker.FOREHAND";
    public static final String SP_KEY_BACKHAND = "com.swingtracker.BACKHAND";
    public static final String SP_KEY_OVERHEAD = "com.swingtracker.OVERHEAD";
    private static final String COUNT_KEY = "count";
    private int count = 0;

    private static int forehand, backhand, overhead;
    GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

        button1 = (Button) findViewById(R.id.get_data_button);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSwingData();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
        Log.d(LOG_TAG, "onStart - Connect to Wearable!");
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Wearable.DataApi.addListener(mGoogleApiClient, this);
        Log.d(LOG_TAG, "onConnected to Wearable!");
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

        Log.d(LOG_TAG, "Mobile onDataChanged: " + dataEventBuffer);

    }

    public void getSwingData() {

        count = count + 1;
        Log.d(LOG_TAG, "getSwingData: " + count);

        if (mGoogleApiClient.isConnected()) {

            Log.d(LOG_TAG, "mGoogleApiClient.isConnected!");

            PutDataMapRequest putDataMapRequest = PutDataMapRequest.create("/swing-data");

            //forehand = Utilities.getPrefForehand(getActivity());
            //backhand = Utilities.getPrefBackhand(getActivity());
            //overhead = Utilities.getPrefOverhead(getActivity());

            putDataMapRequest.getDataMap().putInt(COUNT_KEY, count);
            //putDataMapRequest.getDataMap().getInt(SP_KEY_FOREHAND, forehand);
            //putDataMapRequest.getDataMap().getInt(String.valueOf(forehand));
            //putDataMapRequest.getDataMap().getString(SP_KEY_BACKHAND);
            //putDataMapRequest.getDataMap().getInt(SP_KEY_OVERHEAD, overhead);
            //forehand = putDataMapRequest.getDataMap().getInt(String.valueOf(forehand));

            PutDataRequest putDataRequest = putDataMapRequest.asPutDataRequest();

            Wearable.DataApi.putDataItem(mGoogleApiClient, putDataRequest)
                    .setResultCallback(new ResultCallback<DataApi.DataItemResult>() {
                        @Override
                        public void onResult(@NonNull DataApi.DataItemResult dataItemResult) {
                            if (dataItemResult.getStatus().isSuccess()) {
                                Log.d(LOG_TAG, "Swing Data sent successfully!");
                            } else {
                                Log.d(LOG_TAG, "Swing Data failed to send!" +
                                        dataItemResult.getStatus().getStatusCode());
                            }
                        }
                    });
        } else {
            Log.d(LOG_TAG, "mGoogleApiClient failed to connect!");
        }
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
        }
        return super.onOptionsItemSelected(item);
    }
}
