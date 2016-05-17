package com.jimtrinh9985gmail.swingtracker;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends Activity implements SensorEventListener {

    public final String LOG_TAG = MainActivity.class.getSimpleName();

    // Screen Timer //
    private static final long SCREEN_ON_TIMEOUT_MS = 300000; // in milliseconds
    private Timer timer;
    private TimerTask timerTask;

    // Sensors //
    SensorManager sensorManager;
    Sensor sensor;
    private int sensorType;

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

    // Linear Acceleration //
    private float lastX, lastY, lastZ;
    private float deltaX = 0;
    private float deltaY = 0;
    private float deltaZ = 0;
    private float deltaXLPF = 0;
    private float deltaYLPF = 0;
    private float deltaZLPF = 0;

    // Gravity //
    private float gravityX = 0;
    private float gravityY = 0;
    private float gravityZ = 0;

    // Gyroscope //
    private float deltaGY = 0;
    private float deltaGYLPF = 0;

    // Low-Pass Filter //
    private float alpha = .4f;
    private float oneMinusAlpha = (1.0f - alpha);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupViews();

        foreHandCount = Utilities.getPrefForehand(this);
        backHandCount = Utilities.getPrefBackhand(this);
        overHeadCount = Utilities.getPrefOverhead(this);
        renewTimer();

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(sensorType);
        if (sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE_UNCALIBRATED) != null &&
                sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION) != null) {

            sensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE_UNCALIBRATED);
            sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);

            sensor = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
            sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);

        } else {
            Log.d(LOG_TAG, "Failed to initiate Gyroscope or/and Accelerometer!");
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

        // Get sensor readings //
        if (event.sensor.getType() == Sensor.TYPE_LINEAR_ACCELERATION) {
            deltaX = Math.abs(lastX - event.values[0]);
            deltaY = Math.abs(lastY - event.values[1]);
            deltaZ = Math.abs(lastZ - event.values[2]);
        } else if (event.sensor.getType() == Sensor.TYPE_GRAVITY) {
            gravityX = event.values[0];
            gravityY = event.values[1];
            gravityZ = event.values[2];
        } else if (event.sensor.getType() == Sensor.TYPE_GYROSCOPE_UNCALIBRATED) {
            deltaGY = event.values[1];
        }

        calculateReading();
        determineSwing();
    }

    public void calculateReading() {

        // Apply Low-Pass Filter //
        gravityX = alpha * gravityX + oneMinusAlpha * deltaX;
        gravityY = alpha * gravityY + oneMinusAlpha * deltaY;
        gravityZ = alpha * gravityZ + oneMinusAlpha * deltaZ;

        deltaXLPF = deltaX - gravityX;
        deltaYLPF = deltaY - gravityY;
        deltaZLPF = deltaZ - gravityZ;
        //deltaGYLPF = deltaGY - gravityY;
        deltaGYLPF = deltaGY;

        // Removing Additional Noise //
        if (deltaXLPF < .5)
            deltaXLPF = 0;
        if (deltaYLPF < .5)
            deltaYLPF = 0;
        if (deltaZLPF < .5)
            deltaZLPF = 0;
    }

    public void determineSwing() {
        determineSwingForRighty();
    }

    public void determineSwingForRighty() {
        if (deltaXLPF > 4 && deltaGYLPF > 2) {
            backHandCount++;
            setBackhandCounter(backHandCount);
        }
        if (deltaXLPF > 4 && deltaGYLPF < -2) {
            foreHandCount++;
            setForehandCounter(foreHandCount);
        }
        if (deltaZLPF > 4 && deltaGYLPF < -2) {
            overHeadCount++;
            setOverheadCounter(overHeadCount);
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
