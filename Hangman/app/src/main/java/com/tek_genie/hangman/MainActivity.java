package com.tek_genie.hangman;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.nfc.Tag;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.Serializable;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    Button newGameMain;
    private HangmanDAO gameObject;
    private HangmanNameItem nameItem;
    private TextView countWonTotal;
    private TextView countTotalGames;
    private TextView lastGameTime;
    private TextView fastestTime;
    private TextView averageTime;
    private TextView slowestTime;
    public Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        HangmanDBHelper.getInstance(this).getReadableDatabase();

        gameObject = new HangmanDAO();

        newGameMain = (Button) findViewById(R.id.buttonNewGameMain);
        newGameMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newGame(v);
            }
        });

        displayGameStats();
    }

    public void newGame (View view) {
        nameItem = gameObject.nextName();
        String [] name = nameItem.getNameAndInfo();
        String gmWonTotal = gameObject.statsGamesWon() + " / " + gameObject.statsGamesPlayed();
        Intent intent = new Intent(this, Hangman.class);
        intent.putExtra("nameIntentExtra", name);
        intent.putExtra("gamesWonTotalIntentExtra", gmWonTotal);

        ((Activity) this).startActivityForResult(intent, 1);// 1 is the request code. The request code tells the activity who called it, etc MainActivity

    }

    private void showGameResult(String didYouWin) {
        String [] name = nameItem.getNameAndInfo();
        Intent intent = new Intent(this, HangmanResult.class);
        intent.putExtra("nameIntentExtra", name);
        intent.putExtra("didYouWinIntentExtra", didYouWin);

        ((Activity) this).startActivity(intent);

    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == 1) {
            String gameResult = data.getStringExtra("gameResultIntentExtra");
            String gameTime = data.getStringExtra("gameTimeIntentExtra");

            new UpdateStatisticsTask(this, gameResult, gameTime).execute();

            //Log.i("MainActivity", "gameResult returned as: " + gameResult);
            //Log.i("MainActivity", "gameTime returned as: " + gameTime);
            //int gameResultInt = Integer.valueOf(gameResult);
            //gameObject.gameCompleted(gameResultInt);
            //gameObject.gameTimeProcessor(gameTime);
            showGameResult(gameResult);
        }
    }

    public void displayGameStats(){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String lastGameTimeString = prefs.getString("LastGameTime", "0");
        String fastestTimeString = prefs.getString("FastestTime", "0");
        String slowestTimeString = prefs.getString("SlowestTime", "0");
        String averageTimeString = prefs.getString("AverageTime", "0");
        String gamesTotalString = prefs.getString("GamesTotal", "0");
        String gamesWonString = prefs.getString("GamesWon", "0");

        Log.i("MainActivity", "In displayGameStats: gamesWon " + gamesWonString);
        Log.i("MainActivity", "In displayGameStats: gamesTotal " + gamesTotalString);
        Log.i("MainActivity", "In displayGameStats: lastGameTime " + lastGameTimeString);
        Log.i("MainActivity", "In displayGameStats: fastestTime " + fastestTimeString);
        Log.i("MainActivity", "In displayGameStats: slowestTime " + slowestTimeString);
        Log.i("MainActivity", "In displayGameStats: averageTime " + averageTimeString);

        countWonTotal = (TextView) findViewById(R.id.labelGamesWonDisplay);
        countWonTotal.setText(gamesWonString);

        countTotalGames = (TextView) findViewById(R.id.labelGamesTotalDisplay);
        countTotalGames.setText(gamesTotalString);

        lastGameTime = (TextView) findViewById(R.id.labelLastGameTimeDisplay);
        lastGameTime.setText(lastGameTimeString);

        fastestTime = (TextView) findViewById(R.id.labelFastestTimeDisplay);
        fastestTime.setText(fastestTimeString);

        averageTime = (TextView) findViewById(R.id.labelAverageTimeDisplay);
        averageTime.setText(averageTimeString);

        slowestTime = (TextView) findViewById(R.id.labelSlowestTimeDisplay);
        slowestTime.setText(slowestTimeString);
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

    public void onResume() {
        super.onResume();
        displayGameStats();
    }

}
