package com.example.bruger.hangmanv1;

// Hjælp taget fra https://www.youtube.com/watch?v=_cV7cgQFDo0&t=8s

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class HighScoreActivity extends AppCompatActivity implements View.OnClickListener{

    TextView highscore;
    Button BackButton;
    int lastScore;
    int Best1, Best2, Best3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_score);

        BackButton = (Button) findViewById(R.id.BackButton);
        BackButton.setOnClickListener(this);

        highscore = (TextView) findViewById(R.id.highscore);

        SharedPreferences preferences = getSharedPreferences("prefs", 0);
        lastScore = preferences.getInt("lastScore", 0);
        Best1 = preferences.getInt("Best1", 0);
        Best2 = preferences.getInt("Best2", 0);
        Best3 = preferences.getInt("Best3", 0);

        if (lastScore > Best1) {
            int temp = Best1;
            Best1 = lastScore;
            Best2 = temp;
            SharedPreferences.Editor editor = preferences.edit();
            editor.putInt("Best2", Best2);
            editor.putInt("Best1", Best1);
            editor.apply();
        }

        else if (lastScore > Best2) {
            int temp = Best2;
            Best2 = lastScore;
            Best3 = temp;
            SharedPreferences.Editor editor = preferences.edit();
            editor.putInt("Best3", Best3);
            editor.putInt("Best2", Best2);
            editor.apply();
        }

        else if (lastScore > Best3) {
            Best3 = lastScore;
            SharedPreferences.Editor editor = preferences.edit();
            editor.putInt("Best3", Best3);
            editor.apply();
        }

        highscore.setText(String.format("Last Score: %d\nBest 1: %d\nBest 2: %d\nBest 3: %d", lastScore, Best1, Best2, Best3));
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.BackButton:
                Intent i = new Intent(HighScoreActivity.this, MainActivity.class);
                HighScoreActivity.this.startActivity(i);
                break;
        }

    }
}
