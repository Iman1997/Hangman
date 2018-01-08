package com.example.bruger.hangmanv1;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class LoseActivity extends AppCompatActivity implements View.OnClickListener{

    String word;
    TextView LoseMessage;
    Button Replaybutton;
    Button HomeButton;
    Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lose);
        Intent w = getIntent();
        Bundle b = w.getExtras();

        Replaybutton = (Button) findViewById(R.id.Replaybutton);
        Replaybutton.setOnClickListener(this);

        HomeButton = (Button) findViewById(R.id.HomeButton);
        HomeButton.setOnClickListener(this);

        if (b != null) {
            word = b.getString("ActualWord");
        }

        LoseMessage = (TextView) findViewById(R.id.LoseMessage);
        LoseMessage.setText("Sorry, You have lost. The right word was: " + word);

        MediaPlayer lose= MediaPlayer.create(LoseActivity.this,R.raw.lose);
        lose.start();
        // Hjælp taget fra http://abhiandroid.com/androidstudio/add-audio-android-studio.html
    }

    @Override
    public void onClick(View view) {

        if (view == Replaybutton) {
            i = new Intent(LoseActivity.this, GameActivity.class);
            LoseActivity.this.startActivity(i);
        }

        if (view == HomeButton) {
            i = new Intent(LoseActivity.this, MainActivity.class);
            LoseActivity.this.startActivity(i);
        }
    }
}