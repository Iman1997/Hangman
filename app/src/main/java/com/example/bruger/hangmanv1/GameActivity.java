package com.example.bruger.hangmanv1;

        import android.content.Context;
        import android.content.Intent;
        import android.content.SharedPreferences;
        import android.preference.PreferenceManager;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.ImageView;
        import android.widget.TextView;
        import com.google.gson.Gson;
        import com.google.gson.reflect.TypeToken;

        import java.util.ArrayList;

public class GameActivity extends AppCompatActivity implements View.OnClickListener {

    Button check;
    Button homeButton;
    Button reset;
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

        check = (Button) findViewById(R.id.check);
        check.setOnClickListener(this);

        homeButton = (Button) findViewById(R.id.home);
        homeButton.setOnClickListener(this);

        reset = (Button) findViewById(R.id.reset);
        reset.setOnClickListener(this);

        Pics = (ImageView) findViewById(R.id.Pics);

        Word.setText(Logic.getSynligtOrd());

        Used.setText("Used Letters: " + Logic.getBrugteBogstaver());

        hangingOfTheMan();

        Input.setText("");

        Score.setText("Score: " + score);

        shared = getSharedPreferences("prefs", Context.MODE_PRIVATE);

        System.out.println(Logic.getOrdet());
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.check) {
            String guess = Input.getText().toString();
            Logic.gætBogstav(guess);
            Word.setText(Logic.getSynligtOrd());
            System.out.println(Logic.getOrdet());
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
                saveHighScore(score, Logic.getAntalForkerteBogstaver());

                finish();

            }
            else if (Logic.erSpilletTabt()) {
                Intent lose = new Intent(GameActivity.this, LoseActivity.class);
                lose.putExtra("ActualWord",Logic.getOrdet());
                lose.putExtra("Attempts",Logic.getBrugteBogstaver().size());
                GameActivity.this.startActivity(lose);

                finish();
            }
        }
        else if (view.getId() == R.id.home){
            Intent Home = new Intent(GameActivity.this, MainActivity.class);
            GameActivity.this.startActivity(Home);

        }
        else if (view.getId() == R.id.reset) {
            Logic.nulstil();
            Word.setText(Logic.getSynligtOrd());
            hangingOfTheMan();
            Used.setText("Used Letters: " + Logic.getBrugteBogstaver());
            Input.setText("");
            score = 0;
            Score.setText("Score: " + score);
            System.out.println(Logic.getSynligtOrd());

        }

    //Hjælp fra Troels Just Christoffersen s120052
    }
    private void saveHighScore (int score, int Letters) {
        SharedPreferences prefobj = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = prefobj.edit();
        Gson gsonobj = new Gson();
        String highscoreJson = prefobj.getString("highscore", null);

        ArrayList<String> highscores;
        if(highscoreJson !=null) {
            highscores = gsonobj.fromJson(highscoreJson, new TypeToken<ArrayList<String>>() {
            }.getType());
        } else {
            highscores = new ArrayList<String>();
        }

        highscores.add("Score: " + score + " Used letters: " + Logic.getOrdet().length());
        System.out.println("HIGHSCORES: " +  highscores.toString());
        editor.putString("highscore", gsonobj.toJson(highscores)).apply();
    }
}



