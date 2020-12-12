package com.example.tom.projetcsc;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.DecimalFormat;

public class ShakeActivity extends AppCompatActivity {

    public double accLimit = 75;
    public ShakeActivity activity;
    public SensorManager manager;
    public SensorEventListener sel;
    public int countDownLength = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shake);

        activity=this;

        TextView tvShakeCountdown = (TextView)findViewById(R.id.tvShakeCountdown);

        DecimalFormat df = new DecimalFormat(("0.00"));
        CountDownTimer cdt = new CountDownTimer(countDownLength, 10){
            public void onTick(long millisUntilFinished){
                String goodFormat = df.format(((float)millisUntilFinished)/1000);
                tvShakeCountdown.setText(goodFormat);
            }
            public void onFinish(){
                Toast.makeText(getApplicationContext(), "Too slow!!", Toast.LENGTH_SHORT).show();
                manager.unregisterListener(sel ,manager.getDefaultSensor(Sensor.TYPE_LIGHT) );
                activity.finish();
            }
        }.start();

        manager = (SensorManager)getSystemService(SENSOR_SERVICE);
        sel = new SensorEventListener() {
            public void onSensorChanged(SensorEvent event) {
                if (event.sensor.getType() == Sensor.TYPE_LINEAR_ACCELERATION){
                    if(event.values[0]>accLimit || event.values[1]>accLimit || event.values[2]>accLimit){
                        cdt.cancel();
                        Toast.makeText(getApplicationContext(), "You shake it!!", Toast.LENGTH_SHORT).show();
                        manager.unregisterListener(this ,manager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION) );
                        activity.finish();
                    }
                }
            }
            public void onAccuracyChanged(Sensor sensor, int accuracy) {
                Log.i("listener","accuracy changed");
            }
        };

        manager.registerListener(sel, manager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION), SensorManager.SENSOR_DELAY_UI );
    }
}