package com.tek_genie.hangman;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Random;

/**
 * Created by jasmineislam on 5/31/16.
 */
public class GameView extends AppCompatActivity {
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

        @Override
        protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final HangmanDAO gameObject = new HangmanDAO();

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

        newGame = (Button) findViewById(R.id.buttonNewGame);
        newGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "Clicked New Game Button");
                tries = (TextView) findViewById(R.id.labelNumberOfTries);
                tries.setText("0 Tries");
                letters = (TextView) findViewById(R.id.labelLettersTried);
                letters.setText("No Letters Tried Yet");
                String[] name = gameObject.nextName();
                Log.i(TAG, name[0] +" "+ name[1]);

                Random r = new Random();
                int number = r.nextInt(2);

                gameObject.gameCompleted(number);
                String gmWonTotal = gameObject.gamesWonTotal();

                countWonTotal = (TextView) findViewById(R.id.countWonTotal);
                countWonTotal.setText(gmWonTotal);




            }
        });




    }

        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
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

}
