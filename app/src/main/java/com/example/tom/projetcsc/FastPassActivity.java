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

public class FastPassActivity extends AppCompatActivity {

    public FastPassActivity activity;
    public int countDownLength = 4000;
    public SensorManager manager;
    public SensorEventListener sel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fast_pass);

        activity=this;
        TextView tvFastPassTarget = (TextView)findViewById(R.id.tvFastPassTarget);

        Player target = PlayerList.getRandomPlayerDifferentFrom(PlayerList.currentNarrator);

        tvFastPassTarget.setText(target.toString());

        PlayerList.changeNarrator(PlayerList.currentNarrator, target);

        TextView tvFastPassCountdown = (TextView)findViewById(R.id.tvFastPassCountdown);

        DecimalFormat df = new DecimalFormat(("0.00"));
        CountDownTimer cdt = new CountDownTimer(countDownLength, 10){
            public void onTick(long millisUntilFinished){
                String goodFormat = df.format(((float)millisUntilFinished)/1000);
                tvFastPassCountdown.setText(goodFormat);
            }
            public void onFinish(){
                Toast.makeText(getApplicationContext(), "Too slow!!", Toast.LENGTH_SHORT).show();
                activity.finish();
            }
        }.start();

        Button btnFastPassFinish = (Button)findViewById(R.id.btnFastPassFinish);
        btnFastPassFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Well done!!", Toast.LENGTH_SHORT).show();
                cdt.cancel();
                activity.finish();
            }
        });

    }
}