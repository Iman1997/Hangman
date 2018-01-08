package com.example.bruger.hangmanv1;

// Hjælp fra Troels Just Christoffersen s120052

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

public class HighScoreActivity extends AppCompatActivity implements View.OnClickListener {

    Button BackButton;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_score);


        listView = findViewById(R.id.listViewScore);
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, android.R.id.text1, loadHighscore().toArray());

        listView.setAdapter(adapter);


        BackButton = (Button) findViewById(R.id.BackButton);
        BackButton.setOnClickListener(this);

    }

   @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        finish();
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.BackButton:
                Intent i = new Intent(HighScoreActivity.this, MainActivity.class);
                HighScoreActivity.this.startActivity(i);
                break;
        }

    }

    private ArrayList<String> loadHighscore() {
        SharedPreferences prefObj = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        Gson gsonObj = new Gson();
        String highscoreJson = prefObj.getString("highscore", null);

        ArrayList<String> highscores;
        if (highscoreJson != null) {
            highscores = gsonObj.fromJson(highscoreJson, new TypeToken<ArrayList<String>>() {
            }.getType()); //convert Json String to ArrayList object
        } else {
            highscores = new ArrayList<String>();
        }
        return highscores;
    }
}
