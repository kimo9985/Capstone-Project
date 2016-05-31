package com.jimtrinh9985gmail.swingtracker;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.BatteryManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.renderscript.Sampler;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.Timer;
import java.util.TimerTask;

import wearprefs.WearPrefs;


public class MainActivity extends Activity implements SensorEventListener {

    public final String LOG_TAG = MainActivity.class.getSimpleName();

    // Delay between sensor readings //
    private static final int TIME_MS = 400000;  // in microseconds (.4 seconds)
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
    private float alpha = .3f;
    private float oneMinusAlpha = (1.0f - alpha);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupViews();

        WearPrefs.init(this, "com.swingtracker.FOREHAND");
        WearPrefs.init(this, "com.swingtracker.BACKHAND");
        WearPrefs.init(this, "com.swingtracker.OVERHEAD");

        foreHandCount = Utilities.getPrefForehand(this);
        backHandCount = Utilities.getPrefBackhand(this);
        overHeadCount = Utilities.getPrefOverhead(this);
        renewTimer();

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        if (sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null) {

            sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            sensorManager.registerListener(this, sensor, TIME_MS);

            sensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE_UNCALIBRATED);
            sensorManager.registerListener(this, sensor, TIME_MS);

            sensor = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
            sensorManager.registerListener(this, sensor, TIME_MS);

        } else {
            Log.d(LOG_TAG, "Failed to initiate Accelerometer!");
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

        if ((timeStamp - mLastTime) > TIME_THRESHOLD_NS && Utilities.SP_KEY_GRIP.equals(true)) {
            determineSwingForRighty();
        } else {
            if ((timeStamp - mLastTime) > TIME_THRESHOLD_NS && Utilities.SP_KEY_GRIP.equals(false)) {
                determineSwingForLefty();
            } else {
                return;
            }
        }
    }

    public void determineSwingForRighty() {
        if (deltaXLPF > 4 && deltaGZLPF > -30 && deltaLX > 0) {
            backHandCount++;
            setBackhandCounter(backHandCount);
            vibrate();
            Log.d(LOG_TAG, "Righty Backhand:");
            Log.d(LOG_TAG, "DeltaXLPF: " + deltaXLPF + " " + "DeltaYLPF: " + deltaYLPF + " " + "DeltaZLPF: " + deltaZLPF);
            Log.d(LOG_TAG, "DeltaGYLPF: " + deltaGYLPF + " " + "DeltaGX: " + deltaGXLPF + " " + "DeltaGZ: " + deltaGZLPF);
            Log.d(LOG_TAG, "GravityX: " + gravityX + " " + "GravityY: " + gravityY + " " + "GravityZ: " + gravityZ);
            Log.d(LOG_TAG, "Linear Acc X,Y,Z: " + deltaLX + " " + deltaLY + " " + deltaLZ);
            mLastTime = timeStamp;
        }
        if (deltaXLPF > 4 && deltaGZLPF < -40 && deltaLX > 0) {
            foreHandCount++;
            setForehandCounter(foreHandCount);
            vibrate();
            Log.d(LOG_TAG, "Righty Forehand:");
            Log.d(LOG_TAG, "DeltaXLPF: " + deltaXLPF + " " + "DeltaYLPF: " + deltaYLPF + " " + "DeltaZLPF: " + deltaZLPF);
            Log.d(LOG_TAG, "DeltaGYLPF: " + deltaGYLPF + " " + "DeltaGX: " + deltaGXLPF + " " + "DeltaGZ: " + deltaGZLPF);
            Log.d(LOG_TAG, "GravityX: " + gravityX + " " + "GravityY: " + gravityY + " " + "GravityZ: " + gravityZ);
            Log.d(LOG_TAG, "Linear Acc X,Y,Z: " + deltaLX + " " + deltaLY + " " + deltaLZ);
            mLastTime = timeStamp;
        }
        if (deltaXLPF > 4 && deltaGZLPF > -30 && deltaLX < -2) {
            overHeadCount++;
            setOverheadCounter(overHeadCount);
            vibrate();
            Log.d(LOG_TAG, "Righty Overhead:");
            Log.d(LOG_TAG, "DeltaXLPF: " + deltaXLPF + " " + "DeltaYLPF: " + deltaYLPF + " " + "DeltaZLPF: " + deltaZLPF);
            Log.d(LOG_TAG, "DeltaGYLPF: " + deltaGYLPF + " " + "DeltaGX: " + deltaGXLPF + " " + "DeltaGZ: " + deltaGZLPF);
            Log.d(LOG_TAG, "GravityX: " + gravityX + " " + "GravityY: " + gravityY + " " + "GravityZ: " + gravityZ);
            Log.d(LOG_TAG, "Linear Acc X,Y,Z: " + deltaLX + " " + deltaLY + " " + deltaLZ);
            mLastTime = timeStamp;
        }
        renewTimer();
        return;
    }

    public void determineSwingForLefty() {
        if (deltaXLPF > 4 && deltaGZLPF > -30 && deltaLX > 0) {
            foreHandCount++;
            setBackhandCounter(backHandCount);
            vibrate();
            Log.d(LOG_TAG, "Righty Forehand:");
            Log.d(LOG_TAG, "DeltaXLPF: " + deltaXLPF + " " + "DeltaYLPF: " + deltaYLPF + " " + "DeltaZLPF: " + deltaZLPF);
            Log.d(LOG_TAG, "DeltaGYLPF: " + deltaGYLPF + " " + "DeltaGX: " + deltaGXLPF + " " + "DeltaGZ: " + deltaGZLPF);
            Log.d(LOG_TAG, "GravityX: " + gravityX + " " + "GravityY: " + gravityY + " " + "GravityZ: " + gravityZ);
            Log.d(LOG_TAG, "Linear Acc X,Y,Z: " + deltaLX + " " + deltaLY + " " + deltaLZ);
            mLastTime = timeStamp;
        }
        if (deltaXLPF > 4 && deltaGZLPF < -40 && deltaLX > 0) {
            backHandCount++;
            setForehandCounter(foreHandCount);
            vibrate();
            Log.d(LOG_TAG, "Righty Backhand:");
            Log.d(LOG_TAG, "DeltaXLPF: " + deltaXLPF + " " + "DeltaYLPF: " + deltaYLPF + " " + "DeltaZLPF: " + deltaZLPF);
            Log.d(LOG_TAG, "DeltaGYLPF: " + deltaGYLPF + " " + "DeltaGX: " + deltaGXLPF + " " + "DeltaGZ: " + deltaGZLPF);
            Log.d(LOG_TAG, "GravityX: " + gravityX + " " + "GravityY: " + gravityY + " " + "GravityZ: " + gravityZ);
            Log.d(LOG_TAG, "Linear Acc X,Y,Z: " + deltaLX + " " + deltaLY + " " + deltaLZ);
            mLastTime = timeStamp;
        }
        if (deltaXLPF > 4 && deltaGZLPF > -30 && deltaLX < -2) {
            overHeadCount++;
            setOverheadCounter(overHeadCount);
            vibrate();
            Log.d(LOG_TAG, "Righty Overhead:");
            Log.d(LOG_TAG, "DeltaXLPF: " + deltaXLPF + " " + "DeltaYLPF: " + deltaYLPF + " " + "DeltaZLPF: " + deltaZLPF);
            Log.d(LOG_TAG, "DeltaGYLPF: " + deltaGYLPF + " " + "DeltaGX: " + deltaGXLPF + " " + "DeltaGZ: " + deltaGZLPF);
            Log.d(LOG_TAG, "GravityX: " + gravityX + " " + "GravityY: " + gravityY + " " + "GravityZ: " + gravityZ);
            Log.d(LOG_TAG, "Linear Acc X,Y,Z: " + deltaLX + " " + deltaLY + " " + deltaLZ);
            mLastTime = timeStamp;
        }
        renewTimer();
        return;

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
        setForehandCounter(0);
        setBackhandCounter(0);
        setOverheadCounter(0);
        renewTimer();
    }

    private void vibrate() {
        Vibrator vibe = (Vibrator) getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);
        vibe.vibrate(100);
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
        if (sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL)) {
            if (Log.isLoggable(LOG_TAG, Log.DEBUG)) {
                Log.d(LOG_TAG, "Successfully registered for the sensor updates");
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
        if (Log.isLoggable(LOG_TAG, Log.DEBUG)) {
            Log.d(LOG_TAG, "Unregistered for sensor events");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
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
