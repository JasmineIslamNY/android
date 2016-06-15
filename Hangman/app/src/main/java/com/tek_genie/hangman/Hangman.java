package com.tek_genie.hangman;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
    private int numberOfFailedTries = 0;
    public TextView letters;
    public TextView countWonTotal;
    public TextView labelLettersTried;
    private String[] nameAndInfo;
    private String gmWonTotal;
    private String secretName;
    private StringBuilder gameName;
    private TextView gameNameLabel;
    private TextView gameClue;
    private int maxFailedTries;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_hangman);
        Intent intent = getIntent();
        nameAndInfo = intent.getStringArrayExtra("nameIntentExtra");
        gmWonTotal = intent.getStringExtra("gamesWonTotalIntentExtra");
        maxFailedTries = intent.getIntExtra("maxFailedTriesIntentExtra", 6);

        Log.i(TAG, "nameAndInfo 0 and 1: " + nameAndInfo[0] + " " + nameAndInfo[1]);
        Log.i(TAG, "gmWonTotal: " + gmWonTotal);
        secretName = nameAndInfo[0] + " " + nameAndInfo[1];
        Log.i(TAG, "secretName = " + secretName);

        gameName = new StringBuilder(underscoreName());
        updateGameNameDisplay();
        Log.i(TAG, "gameName in onCreate: " + gameName);
        updateNumberOfFailedTries();

        letters = (TextView) findViewById(R.id.labelLettersTried);
        letters.setText("No Letters Tried Yet");
        countWonTotal = (TextView) findViewById(R.id.countWonTotal);
        countWonTotal.setText(gmWonTotal);
        gameClue = (TextView) findViewById(R.id.labelClue);
        gameClue.setText(nameAndInfo[6]);

        submitLetter = (Button) findViewById(R.id.buttonSubmitLetter);
        submitLetter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText nextLetter = (EditText) findViewById(R.id.edittextEnterNextLetter);
                Log.i(TAG, "Entered: " + nextLetter.getText().toString());

                int isLetterFound = checkNameForLetter(nextLetter.getText().toString());
                if (isLetterFound == 0){
                    numberOfFailedTries += 1;
                    updateNumberOfFailedTries();
                }

                testForWinOrLoss();
                updateNumberOfFailedTriesImage();
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

    private void updateLettersTried(String nextLetter) {
        labelLettersTried = (TextView) findViewById(R.id.labelLettersTried);
        if (labelLettersTried.getText().toString() == "" || labelLettersTried.getText().toString() == "No Letters Tried Yet") {
            labelLettersTried.setText(nextLetter);
        } else {
            labelLettersTried.setText(labelLettersTried.getText().toString() + ", " + nextLetter);
        }
    }

    private String underscoreName() {
        String name = "";
        for (int i = 0; i < secretName.length(); i++) {
            if (secretName.substring(i, i+1).equals(" ")) {
                name = name + " ";
            } else {
                name = name + "_";
            }
        }
        return name;
    }

    private int checkNameForLetter(String letter) {
        int foundLetter = 0;
        for (int i = 0; i < secretName.length(); i++) {
            if (letter.toUpperCase().equals(secretName.substring(i, i+1).toUpperCase())) {
                foundLetter = 1;
                String letterToUpper = letter.toUpperCase();
                gameName.setCharAt(i, letterToUpper.charAt(0));
            }
        }
        updateGameNameDisplay();

        return foundLetter;
    }

    private void updateGameNameDisplay () {
        gameNameLabel = (TextView) findViewById(R.id.labelGameName);
        gameNameLabel.setText(gameName);
    }

    private void updateNumberOfFailedTries () {
        TextView tries = (TextView) findViewById(R.id.labelNumberOfFailedTries);
        tries.setText(numberOfFailedTries + " Failed Tries");
    }

    private void updateNumberOfFailedTriesImage () {
        ImageView triesImage = (ImageView) findViewById(R.id.imageTryNumber);
        triesImage.setImageResource(R.drawable.stick1);
    }

    private void testForWinOrLoss(){
        String gameWonLostText = "";
        int gameCompleted = 0;
        Log.i(TAG, "secretName " + secretName.toUpperCase());
        Log.i(TAG, "gameName " + gameName.toString());

        if ((secretName.toUpperCase()).equals(gameName.toString())) {
            gameWonLostText = "1";
            gameCompleted = 1;
        }
        else if (!((secretName.toUpperCase()).equals(gameName)) && (numberOfFailedTries > maxFailedTries)) {
            gameWonLostText = "0";
            gameCompleted = 1;
        }

        if (gameCompleted == 1) {
            Intent intent = new Intent();
            intent.putExtra("gameResultIntentExtra", gameWonLostText);
            setResult(RESULT_OK, intent);
            finish();
        }
    }
}
