package com.example.tom.projetcsc;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.concurrent.ThreadLocalRandom;

import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

public class PokemonActivity extends AppCompatActivity {

    public String curPokemonName;
    public int pokemonGuessed = 1;

    private Toolbar toolbar;

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.scores:
                Intent launchScores = new Intent(getApplicationContext(), ScoresActivity.class);
                startActivity(launchScores);
                break;
            case R.id.options:
                Intent goToPrefs = new Intent(getApplicationContext(), GamePreferences.class);
                startActivity(goToPrefs);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokemon);

        toolbar=findViewById(R.id.myToolBar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        Button btnShowPokemonName = (Button)findViewById(R.id.btnShowPokemonName);
        TextView tvName = (TextView)findViewById(R.id.tvPokemonName);
        Button btnRightPokemon = (Button)findViewById(R.id.btnRightPokemon);
        Button btnWrongPokemon = (Button)findViewById(R.id.btnWrongPokemon);
        LinearLayout lyVerifyPokemonAnswer = (LinearLayout)findViewById(R.id.lyVerifyPokemonAnswer);

        lyVerifyPokemonAnswer.setVisibility(View.GONE);


        btnRightPokemon.setOnClickListener(new GetActivityOnClickListener(this){
            @Override
            public void onClick(View v){
                Log.i("Player","win 1 point");
                tvName.setText("");
                AsyncGetPokemonJSON asyncGPJSON = new AsyncGetPokemonJSON((PokemonActivity)this.activity);
                int rd = ThreadLocalRandom.current().nextInt(1,152);
                asyncGPJSON.execute(Integer.toString(rd));
                pokemonGuessed+=1;
                if (pokemonGuessed == 5){
                    this.activity.finish();
                }
                lyVerifyPokemonAnswer.setVisibility(View.GONE);
            }
        });

        btnWrongPokemon.setOnClickListener(new GetActivityOnClickListener(this){
            @Override
            public void onClick(View v){
                Log.i("Player","lose 1 point");
                tvName.setText("");
                AsyncGetPokemonJSON asyncGPJSON = new AsyncGetPokemonJSON((PokemonActivity)this.activity);
                int rd = ThreadLocalRandom.current().nextInt(1,152);
                asyncGPJSON.execute(Integer.toString(rd));
                pokemonGuessed+=1;
                if (pokemonGuessed == 5){
                    this.activity.finish();
                }
                lyVerifyPokemonAnswer.setVisibility(View.GONE);
            }
        });

        btnShowPokemonName.setOnClickListener(new GetActivityOnClickListener(this) {
            @Override
            public void onClick(View v) {
                tvName.setText(curPokemonName);
                lyVerifyPokemonAnswer.setVisibility(View.VISIBLE);
            }
        });

        AsyncGetPokemonJSON asyncGPJSON = new AsyncGetPokemonJSON(this);
        int rd = ThreadLocalRandom.current().nextInt(1,152);
        asyncGPJSON.execute(Integer.toString(rd));

    }
}