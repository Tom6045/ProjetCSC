package com.example.tom.projetcsc;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SlowMovementActivity extends AppCompatActivity {

    private boolean ready = false;
    public double accLimit = 1.5;
    public SlowMovementActivity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slow_movement);

        activity=this;
        TextView tvSlowMovementTarget = (TextView)findViewById(R.id.tvSlowMovementTarget);

        Player target = PlayerList.getRandomPlayerDifferentFrom(PlayerList.currentNarrator);

        tvSlowMovementTarget.setText(target.toString());

        PlayerList.changeNarrator(PlayerList.currentNarrator, target);

        SensorManager manager = (SensorManager)getSystemService(SENSOR_SERVICE);
        SensorEventListener sel = new SensorEventListener() {
            public void onSensorChanged(SensorEvent event) {
                if (event.sensor.getType() == Sensor.TYPE_LINEAR_ACCELERATION && ready){
                    if(event.values[0]>accLimit || event.values[1]>accLimit || event.values[2]>accLimit){
                        Toast.makeText(getApplicationContext(), "You were too fast!!", Toast.LENGTH_SHORT).show();
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


        Button btnSlowMovementFinish = (Button)findViewById(R.id.btnSlowMovementFinish);
        btnSlowMovementFinish.setVisibility(View.INVISIBLE);
        btnSlowMovementFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Well done!!", Toast.LENGTH_SHORT).show();
                manager.unregisterListener(sel ,manager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION) );
                activity.finish();
            }
        });

        Button btnSlowMovementStart = (Button)findViewById(R.id.btnSlowMovementStart);

        btnSlowMovementStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ready = true;
                btnSlowMovementStart.setVisibility(View.INVISIBLE);
                btnSlowMovementFinish.setVisibility(View.VISIBLE);
            }
        });
    }
}