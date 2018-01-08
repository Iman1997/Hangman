package com.example.bruger.hangmanv1;

import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import nl.dionsegijn.konfetti.KonfettiView;
import nl.dionsegijn.konfetti.models.Size;

import static com.example.bruger.hangmanv1.GameActivity.*;
import static nl.dionsegijn.konfetti.KonfettiView.*;


public class WinActivity extends AppCompatActivity implements View.OnClickListener {

    Button Replaybutton;
    Button HomeButton;
    TextView WinMessage;
    int attempts;
    KonfettiView konfettiView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_win);

        MediaPlayer win= MediaPlayer.create(WinActivity.this,R.raw.win);
        win.start();

        // Hjælp taget fra http://abhiandroid.com/androidstudio/add-audio-android-studio.html

        Galgelogik Winning = new Galgelogik();

        Replaybutton = (Button) findViewById(R.id.Replaybutton);
        Replaybutton.setOnClickListener(this);

        HomeButton = (Button) findViewById(R.id.HomeButton);
        HomeButton.setOnClickListener(this);



        Intent w = getIntent();
        Bundle b = w.getExtras();

        if (b != null) {
            attempts = b.getInt("Attempts");
        }
        WinMessage = (TextView) findViewById(R.id.WinMessage);
        WinMessage.setText("Congratulations! You have won! Your number of attempts were: " + attempts);

        konfettiView = (KonfettiView) findViewById(R.id.confetti);

        konfettiView.build()
                .addColors(Color.YELLOW, Color.GREEN, Color.MAGENTA, Color.BLUE, Color.BLACK)
                .setDirection(0.0, 359.0)
                .setSpeed(2f, 10f)
                .setFadeOutEnabled(true)
                .setTimeToLive(2000L)
                .addShapes(nl.dionsegijn.konfetti.models.Shape.CIRCLE, nl.dionsegijn.konfetti.models.Shape.RECT)
                .addSizes(new Size(10, 10f))
                .setPosition(-50f, konfettiView.getWidth() + 50f, -50f, -50f)
                .stream(800, 5000L);
    }

    @Override
    public void onClick(View view) {

        if (view == Replaybutton) {
            Intent i = new Intent(WinActivity.this, GameActivity.class);
            WinActivity.this.startActivity(i);
        }

        if (view == HomeButton) {
            Intent i = new Intent(WinActivity.this, MainActivity.class);
            WinActivity.this.startActivity(i);
        }

    }
}
