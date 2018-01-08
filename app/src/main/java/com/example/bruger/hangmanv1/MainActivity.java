package com.example.bruger.hangmanv1;

        import android.content.Context;
        import android.content.Intent;
        import android.content.SharedPreferences;
        import android.os.AsyncTask;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.Button;
        import android.widget.TextView;

        import static com.example.bruger.hangmanv1.GameActivity.Logic;
        import static com.example.bruger.hangmanv1.GameActivity.numberOfVictories;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public Button Playbutton;
    public Button HighscoreButton;
    TextView NumberofVictories;
    SharedPreferences shared;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Playbutton = (Button) findViewById(R.id.Playbutton);
        Playbutton.setOnClickListener(this);

        HighscoreButton = (Button) findViewById(R.id.HighScoreButton);
        HighscoreButton.setOnClickListener(this);

        NumberofVictories = (TextView) findViewById(R.id.NumberofVictories);

        shared = getSharedPreferences("prefs", Context.MODE_PRIVATE);
        numberOfVictories = shared.getInt("NumberofVictories",  numberOfVictories);
        NumberofVictories.setText("Number of victories " +  numberOfVictories);

        new AsyncTask() {
            @Override
            protected Object doInBackground(Object... arg0) {
                try {
                    Logic.hentOrdFraDr();
                    return "Ordene blev korrekt hentet fra DR's server";
                } catch (Exception e) {
                    e.printStackTrace();
                    return "Ordene blev ikke hentet korrekt: "+e;
                }
            }
            @Override
            protected void onPostExecute(Object resultat) {
                System.out.println("Resultatet er " + resultat);
            }

        }.execute();

        NumberofVictories = (TextView) findViewById(R.id.NumberofVictories);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.Playbutton:
                Intent i = new Intent(MainActivity.this, GameActivity.class);
                MainActivity.this.startActivity(i);
                break;
        }

        switch (view.getId()){
            case R.id.HighScoreButton:
                Intent i = new Intent(MainActivity.this, HighScoreActivity.class);
                MainActivity.this.startActivity(i);
                break;
        }
    }

   @Override
    public void onResume() {
        super.onResume();
        NumberofVictories.setText("Number of victories " + numberOfVictories);
    }
}
