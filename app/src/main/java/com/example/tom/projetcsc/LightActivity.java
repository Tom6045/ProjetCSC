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

import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.util.concurrent.ThreadLocalRandom;

public class LightActivity extends AppCompatActivity {

    public int highBrightness = 2000;
    public int lowBrightness = 1;
    public LightActivity activity;
    public int countDownLength;
    private SensorManager manager;
    private SensorEventListener sel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_light);

        activity=this;

        TextView tvLightOrDark = (TextView)findViewById(R.id.tvLightOrDark);

        int rd = ThreadLocalRandom.current().nextInt(0,2);

        if (rd==0){
            tvLightOrDark.setText("Put me in the light!");
            countDownLength = 5000;
        }else{
            tvLightOrDark.setText("Put me in the dark!");
            countDownLength = 3000;
        }


        TextView tvLightCountdown = (TextView)findViewById(R.id.tvLightCountdown);

        DecimalFormat df = new DecimalFormat(("0.00"));
        CountDownTimer cdt = new CountDownTimer(countDownLength, 10){
            public void onTick(long millisUntilFinished){
                String goodFormat = df.format(((float)millisUntilFinished)/1000);
                tvLightCountdown.setText(goodFormat);
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
                if (event.sensor.getType() == Sensor.TYPE_LIGHT){
                    float lightLevel = event.values[0];
                    Log.i("Luminosité",lightLevel+"");

                    if(lightLevel>highBrightness && rd==0){
                        cdt.cancel();
                        Toast.makeText(getApplicationContext(), "Well that was bright!!", Toast.LENGTH_SHORT).show();
                        manager.unregisterListener(this ,manager.getDefaultSensor(Sensor.TYPE_LIGHT) );
                        activity.finish();
                    }else if(lightLevel<lowBrightness && rd==1){
                        cdt.cancel();
                        Toast.makeText(getApplicationContext(), "Well that was dark!!", Toast.LENGTH_SHORT).show();
                        manager.unregisterListener(this ,manager.getDefaultSensor(Sensor.TYPE_LIGHT) );
                        activity.finish();
                    }
                }
            }
            public void onAccuracyChanged(Sensor sensor, int accuracy) {
                Log.i("listener","accuracy changed");
            }
        };

        manager.registerListener(sel, manager.getDefaultSensor(Sensor.TYPE_LIGHT), SensorManager.SENSOR_DELAY_UI );


    }
}