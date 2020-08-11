package com.example.sensorapps;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager manager;
    private Sensor sensor;
    private TextView tvX;
    private TextView tvY;
    private TextView tvZ;
    private TextView tvDetail;
    private Sensor sensorProx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        manager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensor = manager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorProx = manager.getDefaultSensor(Sensor.TYPE_PROXIMITY);

        tvX = findViewById(R.id.tvX);
        tvY = findViewById(R.id.tvY);
        tvZ = findViewById(R.id.tvZ);
        tvDetail = findViewById(R.id.tvDetail);

        String name = sensor.getName();
        float maxRange = sensor.getMaximumRange();
        float power = sensor.getPower();
        String vendor = sensor.getVendor();
        tvDetail.setText(name);
        tvDetail.append("\nvendor :" + vendor);
        tvDetail.append("\nmax :" + maxRange);
        tvDetail.append("\npower :" + power);

    }

    @Override
    protected void onResume() {
        super.onResume();
        manager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
        manager.registerListener(this, sensorProx, SensorManager.SENSOR_DELAY_FASTEST);
    }

    @Override
    protected void onPause() {
        super.onPause();
        manager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

        switch (sensorEvent.sensor.getType()) {
            case Sensor.TYPE_ACCELEROMETER:
                float[] values = sensorEvent.values;
                tvX.setText(String.format("%.3f", values[0]));
                tvY.setText(String.format("%.3f", values[1]));
                tvZ.setText(String.format("%.3f", values[2]));
                break;
            case Sensor.TYPE_PROXIMITY:
                float proximity = sensorEvent.values[0];
                tvX.setTextSize(proximity * 5);
                tvY.setTextSize(proximity * 5);
                tvZ.setTextSize(proximity * 5);
                break;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}