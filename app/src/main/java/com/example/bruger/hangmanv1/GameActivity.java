package com.example.bruger.hangmanv1;

        import android.content.Context;
        import android.content.Intent;
        import android.content.SharedPreferences;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.ImageView;
        import android.widget.TextView;

public class GameActivity extends AppCompatActivity implements View.OnClickListener {

    Button Check;
    Button HomeButton;
    Button Reset;
    TextView Word;
    TextView Used;
    TextView Score;
    EditText Input;
    ImageView Pics;
    int score = 0;
    SharedPreferences shared;
    public static int numberOfVictories;


    public static Galgelogik Logic = new Galgelogik();

    public void hangingOfTheMan () {
        switch (Logic.getAntalForkerteBogstaver()) {
            case 0 : Pics.setImageResource(R.drawable.galge);
                break;
            case 1 : Pics.setImageResource(R.drawable.forkert1);
                break;
            case 2 : Pics.setImageResource(R.drawable.forkert2);
                break;
            case 3 : Pics.setImageResource(R.drawable.forkert3);
                break;
            case 4 : Pics.setImageResource(R.drawable.forkert4);
                break;
            case 5 : Pics.setImageResource(R.drawable.forkert5);
                break;
            case 6 : Pics.setImageResource(R.drawable.forkert6);
                break;
        }

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        Word = (TextView) findViewById(R.id.WordButton);
        Used = (TextView) findViewById(R.id.Used);
        Score = (TextView) findViewById(R.id.Score);

        Input = (EditText) findViewById(R.id.Input);

        Check = (Button) findViewById(R.id.Check);
        Check.setOnClickListener(this);

        HomeButton = (Button) findViewById(R.id.HomeButton);
        HomeButton.setOnClickListener(this);

        Reset = (Button) findViewById(R.id.Reset);
        Reset.setOnClickListener(this);

        Pics = (ImageView) findViewById(R.id.Pics);

        Word.setText(Logic.getSynligtOrd());

        System.out.println(Logic.getOrdet());

        Used.setText("Used Letters: " + Logic.getBrugteBogstaver());

        hangingOfTheMan();

        Input.setText("");

        Score.setText("Score: " + score);

        shared = getSharedPreferences("prefs", Context.MODE_PRIVATE);

    }

    @Override
    public void onClick(View view) {

        if (view == Check) {
            String guess = Input.getText().toString();
            Logic.gætBogstav(guess);
            Word.setText(Logic.getSynligtOrd());
            hangingOfTheMan();
            Used.setText("Used Letters: " + Logic.getBrugteBogstaver());

            Input.setText("");

            if (Logic.erSidsteBogstavKorrekt()) {
                score ++;
                Score.setText("Score: " + score);

            } else
            {
                if (score != 0) {
                    score --;
                    Score.setText("Score: " + score);
                }
            }

            if (Logic.erSpilletVundet()) {

                numberOfVictories = shared.getInt("numberOfVictories", numberOfVictories);
                System.out.println(numberOfVictories);
                numberOfVictories += 1;

                SharedPreferences.Editor editor = shared.edit();
                editor.putInt("numberOfVictories", numberOfVictories);
                editor.putInt("lastScore", score);
                editor.apply();

                System.out.println(numberOfVictories);

                // Har fået hjælp af Sarina Bibæk

                Intent win = new Intent(GameActivity.this, WinActivity.class);
                win.putExtra("Attempts",Logic.getBrugteBogstaver().size());
                GameActivity.this.startActivity(win);

            }
            else if (Logic.erSpilletTabt()) {
                Intent lose = new Intent(GameActivity.this, LoseActivity.class);
                lose.putExtra("ActualWord",Logic.getOrdet());
                lose.putExtra("Attempts",Logic.getBrugteBogstaver().size());
                GameActivity.this.startActivity(lose);

            }
        }


        else if (view == HomeButton){
            Intent Home = new Intent(GameActivity.this, MainActivity.class);
            GameActivity.this.startActivity(Home);

        }
        else if (view == Reset) {
            Logic.nulstil();
            Word.setText(Logic.getSynligtOrd());
            hangingOfTheMan();
            Used.setText("Used Letters: " + Logic.getBrugteBogstaver());
            Input.setText("");
            score = 0;
            Score.setText("Score: " + score);
            System.out.println(Logic.getOrdet());

        }


    }
}


