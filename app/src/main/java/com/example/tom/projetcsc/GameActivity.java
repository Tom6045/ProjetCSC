package com.example.tom.projetcsc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class GameActivity extends AppCompatActivity {

    public ArrayList<Player> players;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        ArrayList<Player> players = PlayerList.getPlayerList();
        Button btnPokemonGame = (Button)findViewById(R.id.btnPokemonGame);
        Button btnContactGame = (Button)findViewById(R.id.btnContactGame);
        Button btnSlowMovementGame = (Button)findViewById(R.id.btnSlowMovementGame);
        Button btnShakeGame = (Button)findViewById(R.id.btnShakeGame);
        Button btnLightGame = (Button)findViewById(R.id.btnLightGame);
        Button btnFastPassGame = (Button)findViewById(R.id.btnFastPassGame);
        Button btnMovementGame = (Button)findViewById(R.id.btnMovementGame);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());


        PlayerList.currentNarrator = PlayerList.getRandomPlayer();
        Log.i("the narrator is", PlayerList.currentNarrator.toString());
        Log.i("& has been narrator for",PlayerList.currentNarrator.roundsAsNarrator + "rounds");

        btnPokemonGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlayerList.currentNarrator.roundsAsNarrator+=1;
                Intent launchPokemonGame = new Intent(getApplicationContext(), PokemonActivity.class);
                startActivity(launchPokemonGame);
            }
        });

        btnContactGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean contactGameAllowed = prefs.getBoolean("playWithContactGame", false);
                if(contactGameAllowed) {
                    PlayerList.currentNarrator.roundsAsNarrator += 1;
                    Intent launchContactGame = new Intent(getApplicationContext(), ContactActivity.class);
                    startActivity(launchContactGame);
                }else{
                    Toast.makeText(getApplicationContext(), "ContactGame is not allowed", Toast.LENGTH_LONG).show();
                }
            }
        });

        btnSlowMovementGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlayerList.currentNarrator.roundsAsNarrator+=1;
                Intent launchSlowMovementGame = new Intent(getApplicationContext(), SlowMovementActivity.class);
                startActivity(launchSlowMovementGame);
            }
        });

        btnShakeGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlayerList.currentNarrator.roundsAsNarrator+=1;
                Intent launchShakeGame = new Intent(getApplicationContext(), ShakeActivity.class);
                startActivity(launchShakeGame);
            }
        });

        btnLightGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlayerList.currentNarrator.roundsAsNarrator+=1;
                Intent launchLightGame = new Intent(getApplicationContext(), LightActivity.class);
                startActivity(launchLightGame);
            }
        });

        btnFastPassGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlayerList.currentNarrator.roundsAsNarrator+=1;
                Intent launchFastPassGame = new Intent(getApplicationContext(), FastPassActivity.class);
                startActivity(launchFastPassGame);
            }
        });

        btnMovementGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlayerList.currentNarrator.roundsAsNarrator+=1;
                Intent launchMovementGame = new Intent(getApplicationContext(), MovementActivity.class);
                startActivity(launchMovementGame);
            }
        });
    }

    @Override
    protected void onResume(){
        super.onResume();
        Log.i("the narrator is", PlayerList.currentNarrator.toString());
        Log.i("& has been narrator for",PlayerList.currentNarrator.roundsAsNarrator + "rounds");
    }
}