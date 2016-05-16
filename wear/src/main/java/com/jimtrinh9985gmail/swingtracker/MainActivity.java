package com.jimtrinh9985gmail.swingtracker;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.wearable.view.WatchViewStub;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends Activity implements SensorEventListener {

    public final String LOG_TAG = MainActivity.class.getSimpleName();

    SensorManager sensorManager;
    Sensor sensor;

    // Swings //
    private TextView forehandText, backhandText, overheadText;
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
    private float alpha = .3f;
    private float oneMinusAlpha = (1.0f - alpha);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupViews();

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        if (sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE) != null &&
                sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION) != null) {
            sensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
            sensor = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
            sensor = sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);
            sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
        } else {
            Log.d(LOG_TAG, "Failed to initiate Gyroscope or/and Accelerometer!");
        }
    }

    public void setupViews() {
        forehandText = (TextView) findViewById(R.id.forehand_count);
        backhandText = (TextView) findViewById(R.id.backhand_count);
        overheadText = (TextView) findViewById(R.id.overhead_count);

    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        // Get sensor readings //
        if (sensor.getType() == Sensor.TYPE_LINEAR_ACCELERATION) {
            deltaX = Math.abs(lastX - event.values[0]);
            deltaY = Math.abs(lastY - event.values[1]);
            deltaZ = Math.abs(lastZ - event.values[2]);
        } else if (sensor.getType() == Sensor.TYPE_GRAVITY) {
            gravityX = event.values[0];
            gravityY = event.values[1];
            gravityZ = event.values[2];
        } else if (sensor.getType() == Sensor.TYPE_GYROSCOPE) {
            deltaGY = event.values[1];
        }

        calculateReading();
        determineSwing();
        displayCount();
    }

    public void calculateReading() {

        // Apply Low-Pass Filter //
        gravityX = alpha * gravityX + oneMinusAlpha * deltaX;
        gravityY = alpha * gravityY + oneMinusAlpha * deltaY;
        gravityZ = alpha * gravityZ + oneMinusAlpha * deltaZ;

        deltaXLPF = deltaX - gravityX;
        deltaYLPF = deltaY - gravityY;
        deltaZLPF = deltaZ - gravityZ;
        deltaGYLPF = deltaGY - gravityY;

        // Removing Additional Noise //
        if (deltaXLPF < .5)
            deltaXLPF = 0;
        if (deltaYLPF < .5)
            deltaYLPF = 0;
        if (deltaZLPF < .5)
            deltaZLPF = 0;
    }

    public void determineSwing() {
        if (deltaXLPF > 4 && deltaGYLPF > 2) {
            backHandCount++;
        }
        if (deltaXLPF > 4 && deltaGYLPF < -2) {
            foreHandCount++;
        }
        if (deltaZLPF > 4 && deltaGYLPF < -2) {
            overHeadCount++;
        }
        return;
    }

    public void displayCount() {
        if (foreHandCount != 0) {
            forehandText.setText(String.valueOf(foreHandCount));
        }
        if (backHandCount != 0) {
            backhandText.setText(String.valueOf(backHandCount));
        }
        if (overHeadCount != 0) {
            overheadText.setText(String.valueOf(overHeadCount));
        }
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
}
