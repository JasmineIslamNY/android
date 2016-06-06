package com.tek_genie.hangman;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Random;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class Hangman extends AppCompatActivity {
    private Button submitLetter;
    private Button newGame;
    final String TAG = "MyData";
    public Integer totalGames;
    public Integer totalWonGames;
    public TextView tries;
    public TextView letters;
    public TextView countWonTotal;
    public TextView labelLettersTried;
    public TextView labelNumberOfTries;
    private String [] name;
    private String gmWonTotal;




    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_hangman);
        Intent intent = getIntent();
        name = intent.getStringArrayExtra("nameIntentExtra");
        gmWonTotal = intent.getStringExtra("gamesWonTotalIntentExtra");
        Log.i(TAG, name[0] +" "+ name[1]);
        Log.i(TAG, gmWonTotal);

        tries = (TextView) findViewById(R.id.labelNumberOfTries);
        tries.setText("0 Tries");
        letters = (TextView) findViewById(R.id.labelLettersTried);
        letters.setText("No Letters Tried Yet");
        countWonTotal = (TextView) findViewById(R.id.countWonTotal);
        countWonTotal.setText(gmWonTotal);

        submitLetter = (Button) findViewById(R.id.buttonSubmitLetter);
        submitLetter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText nextLetter = (EditText) findViewById(R.id.edittextEnterNextLetter);
                Log.i(TAG, "Entered: " + nextLetter.getText().toString());

                updateLettersTried(nextLetter.getText().toString());
                nextLetter.setText("");
            }
        });

        newGame = (Button) findViewById(R.id.buttonGameComplete);
        newGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Random r = new Random();
                int number = r.nextInt(2);
                Log.i(TAG, "Game Won or Lost: " + number);
                String gameWonLostText = Integer.toString(number);

                Intent intent = new Intent();
                intent.putExtra("gameResultIntentExtra", gameWonLostText);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    private void updateLettersTried (String nextLetter) {
        labelLettersTried = (TextView) findViewById(R.id.labelLettersTried);
        if (labelLettersTried.getText().toString() == "" || labelLettersTried.getText().toString() == "No Letters Tried Yet") {
            labelLettersTried.setText(nextLetter);
        }
        else{
            labelLettersTried.setText(labelLettersTried.getText().toString() + ", " + nextLetter);
        }
    }
}
