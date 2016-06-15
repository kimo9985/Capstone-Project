package com.jimtrinh9985gmail.swingtracker;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.widget.ImageView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.DataApi;
import com.google.android.gms.wearable.DataEvent;
import com.google.android.gms.wearable.DataEventBuffer;
import com.google.android.gms.wearable.DataMapItem;
import com.google.android.gms.wearable.Wearable;

import java.util.Timer;
import java.util.TimerTask;

import wearprefs.WearPrefs;


public class MainActivity extends Activity implements SensorEventListener,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        DataApi.DataListener {

    public final String LOG_TAG = MainActivity.class.getSimpleName();

    private static int forehand, backhand, overhead;
    private GoogleApiClient mGoogleApiClient;
    private int mCount = 0;
    private String profileName, profileHeight, profileWeight, profileRacket;

    // Delay between sensor readings //
    private static final long TIME_THRESHOLD_NS = 400000000;  // in nanoseconds (.4 seconds)
    private long mLastTime = 0;
    private long timeStamp;

    // Screen Timer //
    private static final long SCREEN_ON_TIMEOUT_MS = 300000; // in milliseconds
    private Timer timer;
    private TimerTask timerTask;

    // Sensors //
    SensorManager sensorManager;
    Sensor sensor;

    // Pager Fragments //
    private ViewPager viewPager;
    private ImageView firstPageIndicator;
    private ImageView secondPageIndicator;
    private CountFragment countFragment;
    private SettingsFragment settingsFragment;

    // Swings //
    private int foreHandCount = 0;
    private int backHandCount = 0;
    private int overHeadCount = 0;
    public boolean gripSetting;

    // Accelerometer //
    private float lastX, lastY, lastZ;
    private float deltaX = 0;
    private float deltaY = 0;
    private float deltaZ = 0;
    private float deltaXLPF = 0;
    private float deltaYLPF = 0;
    private float deltaZLPF = 0;

    // Linear Acceleration //
    private float deltaLX = 0;
    private float deltaLY = 0;
    private float deltaLZ = 0;

    // Gravity //
    private float gravityX = 0;
    private float gravityY = 0;
    private float gravityZ = 0;

    // Gyroscope //
    private float deltaGX = 0;
    private float deltaGY = 0;
    private float deltaGZ = 0;
    private float deltaGXLPF = 0;
    private float deltaGYLPF = 0;
    private float deltaGZLPF = 0;

    // Low-Pass Filter //
    private float alpha = .5f;
    private float oneMinusAlpha = (1.0f - alpha);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupViews();

        WearPrefs.init(this, "com.swingtracker.FOREHAND");
        WearPrefs.init(this, "com.swingtracker.BACKHAND");
        WearPrefs.init(this, "com.swingtracker.OVERHEAD");

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Wearable.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

        gripSetting = Utilities.getPrefGrip(this);
        foreHandCount = Utilities.getPrefForehand(this);
        backHandCount = Utilities.getPrefBackhand(this);
        overHeadCount = Utilities.getPrefOverhead(this);
        renewTimer();

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        if (sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null ) {

            sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_FASTEST);

            sensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE_UNCALIBRATED);
            sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_FASTEST);

            sensor = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
            sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_FASTEST);

        } else {
            Log.d(LOG_TAG, "Failed to initiate Accelerometer");
        }


    }

    public void setupViews() {

        viewPager = (ViewPager) findViewById(R.id.pager);
        firstPageIndicator = (ImageView) findViewById(R.id.indicator_0);
        secondPageIndicator = (ImageView) findViewById(R.id.indicator_1);
        final PagerAdapter adapter = new PagerAdapter(getFragmentManager());
        countFragment = new CountFragment();
        settingsFragment = new SettingsFragment();
        adapter.addFragment(countFragment);
        adapter.addFragment(settingsFragment);
        setIndicator(0);

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i2) {
            }

            @Override
            public void onPageSelected(int i) {
                setIndicator(i);
                renewTimer();
            }

            @Override
            public void onPageScrollStateChanged(int i) {
            }
        });
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        determineSwing();

        // Get sensor readings //
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            deltaX = Math.abs(lastX - event.values[0]);
            deltaY = Math.abs(lastY - event.values[1]);
            deltaZ = Math.abs(lastZ - event.values[2]);
            timeStamp = event.timestamp;
        } else
        if (event.sensor.getType() == Sensor.TYPE_GRAVITY) {
            gravityX = event.values[0];
            gravityY = event.values[1];
            gravityZ = event.values[2];
        } else
        if (event.sensor.getType() == Sensor.TYPE_GYROSCOPE_UNCALIBRATED) {
            deltaGX = event.values[0];
            deltaGY = event.values[1];
            deltaGZ = event.values[2];
        } else
        if (event.sensor.getType() == Sensor.TYPE_LINEAR_ACCELERATION) {
            deltaLX = event.values[0];
            deltaLY = event.values[1];
            deltaLZ = event.values[2];
        }
        calculateReading();
    }

    public void calculateReading() {

        // Apply Low-Pass Filter //
        gravityX = alpha * gravityX + oneMinusAlpha * deltaX;
        gravityY = alpha * gravityY + oneMinusAlpha * deltaY;
        gravityZ = alpha * gravityZ + oneMinusAlpha * deltaZ;

        deltaXLPF = deltaX - gravityX;
        deltaYLPF = deltaY - gravityY;
        deltaZLPF = deltaZ - gravityZ;
        deltaGXLPF = deltaGX - gravityX;
        deltaGYLPF = deltaGY - gravityY;
        deltaGZLPF = deltaGZ - gravityZ;

        // Removing Additional Noise //
        if (deltaXLPF < .5)
            deltaXLPF = 0;
        if (deltaYLPF < .5)
            deltaYLPF = 0;
        if (deltaZLPF < .5)
            deltaZLPF = 0;
    }

    public void determineSwing() {
        if ((timeStamp - mLastTime) > TIME_THRESHOLD_NS) {
            if (gripSetting) {
                determineSwingForRighty();
            } else {
                determineSwingForLefty();
            }
        }
    }

    public void determineSwingForRighty() {
        if (deltaXLPF > 6 && deltaGZLPF > -30 && deltaLX > 0) {
            backHandCount++;
            setBackhandCounter(backHandCount);
            mLastTime = timeStamp;
        }
        if (deltaXLPF > 6 && deltaGZLPF < -40 && deltaLX > 0) {
            if (deltaLY < -7.5) {
                foreHandCount++;
                setForehandCounter(foreHandCount);
                mLastTime = timeStamp;
            } else if (deltaLY > -7.5) {
                overHeadCount++;
                setOverheadCounter(overHeadCount);
                mLastTime = timeStamp;
            }
        }
        renewTimer();
    }

    public void determineSwingForLefty() {
        if (deltaXLPF > 6 && deltaGZLPF > -30 && deltaLX < 0) {
            backHandCount++;
            setBackhandCounter(backHandCount);
            mLastTime = timeStamp;
        }
        if (deltaXLPF > 6 && deltaGZLPF < -40 && deltaLX < 0) {
            if (deltaLY < -7.5) {
                foreHandCount++;
                setForehandCounter(foreHandCount);
                mLastTime = timeStamp;
            } else if (deltaLY > -7.5) {
                overHeadCount++;
                setOverheadCounter(overHeadCount);
                mLastTime = timeStamp;
            }
        }
        renewTimer();
    }

    public void setForehandCounter(int i) {
        countFragment.setForehandCounter(i);
        Utilities.savePrefForehand(this, i);
    }

    public void setBackhandCounter(int i) {
        countFragment.setBackhandCounter(i);
        Utilities.savePrefBackhand(this, i);
    }

    public void setOverheadCounter(int i) {
        countFragment.setOverheadCounter(i);
        Utilities.savePrefOverhead(this, i);
    }

    public void resetCounter() {
        foreHandCount = 0;
        backHandCount = 0;
        overHeadCount = 0;
        setForehandCounter(0);
        setBackhandCounter(0);
        setOverheadCounter(0);
        renewTimer();
    }

    public void reinitializeGrip() {
        gripSetting = Utilities.getPrefGrip(this);
        renewTimer();
    }

    private void renewTimer() {
        if (null != timer) {
            timer.cancel();
        }
        timerTask = new TimerTask() {
            @Override
            public void run() {
                if (Log.isLoggable(LOG_TAG, Log.DEBUG)) {
                    Log.d(LOG_TAG, "Removing the FLAG_KEEP_SCREEN_ON flag to allow going to background");
                }
            }
        };
        timer = new Timer();
        timer.schedule(timerTask, SCREEN_ON_TIMEOUT_MS);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        mGoogleApiClient.connect();
        if (sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL)) {
            if (Log.isLoggable(LOG_TAG, Log.DEBUG)) {
                Log.d(LOG_TAG, "Successfully registered for the sensor updates");
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        Wearable.DataApi.removeListener(mGoogleApiClient, this);
        mGoogleApiClient.disconnect();
        sensorManager.unregisterListener(this);
        if (Log.isLoggable(LOG_TAG, Log.DEBUG)) {
            Log.d(LOG_TAG, "Unregistered for sensor events");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.d(LOG_TAG, "onConnected:  Successfully connected to Google API Client");
        Wearable.DataApi.addListener(mGoogleApiClient, this);
        Log.d(LOG_TAG, "mCount: " + mCount);
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.d(LOG_TAG, "onConnectionSuspended:  Connected to Google API Client suspended");
    }

    @Override
    public void onDataChanged(DataEventBuffer dataEventBuffer) {

        Log.d(LOG_TAG, "Wearable onDataChanged: " + dataEventBuffer);

        for (DataEvent dataEvent : dataEventBuffer) {
            if (dataEvent.getType() == DataEvent.TYPE_CHANGED) {
                String path = dataEvent.getDataItem().getUri().getPath();
                if (path.equals("/swing-data")) {
                    DataMapItem dataMapItem = DataMapItem.fromDataItem(dataEvent.getDataItem());
                    mCount = dataMapItem.getDataMap()
                            .getInt(MyWearableListenerService.COUNT_KEY);
                    profileName = dataMapItem.getDataMap()
                            .getString(MyWearableListenerService.SP_KEY_NAME);
                    profileHeight = dataMapItem.getDataMap()
                            .getString(MyWearableListenerService.SP_KEY_HEIGHT);
                    profileWeight = dataMapItem.getDataMap()
                            .getString(MyWearableListenerService.SP_KEY_WEIGHT);
                    profileRacket = dataMapItem.getDataMap()
                            .getString(MyWearableListenerService.SP_KEY_RACKET);

                    Utilities.saveProfileName(this, profileName);
                    Utilities.saveProfileHeight(this, profileHeight);
                    Utilities.saveProfileWeight(this, profileWeight);
                    Utilities.saveProfileRacket(this, profileRacket);

                    Log.d(LOG_TAG, "mCount: " + mCount);
                    Log.d(LOG_TAG, "Profile Name: " + profileName);
                    Log.d(LOG_TAG, "Profile Height: " + profileHeight);
                    Log.d(LOG_TAG, "Profile Weight: " + profileWeight);
                    Log.d(LOG_TAG, "Profile Racket: " + profileRacket);
                }
            }
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d(LOG_TAG, "onConnectionFailed:  Failed to connect, with result: " + connectionResult);
    }

    // Set page indicator for ViewPager //
    private void setIndicator(int i) {
        switch (i) {
            case 0:
                firstPageIndicator.setImageResource(R.drawable.full_10);
                secondPageIndicator.setImageResource(R.drawable.empty_10);
                break;
            case 1:
                firstPageIndicator.setImageResource(R.drawable.empty_10);
                secondPageIndicator.setImageResource(R.drawable.full_10);
                break;
        }
    }
}
