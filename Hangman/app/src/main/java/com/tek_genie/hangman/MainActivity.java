package com.tek_genie.hangman;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    Button newGameMain;
    private HangmanDAO gameObject;
    private HangmanNameItem nameItem;
    public Context context;
    private String gamesTotalString;
    private String gamesWonString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        HangmanDBHelper.getInstance(this).getReadableDatabase();

        gameObject = new HangmanDAO(this);

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
        String gmWonTotal = gamesWonString + " / " + gamesTotalString;
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
            new UpdateStatisticsTask(this, gameResult, gameTime, nameItem.getID(), nameItem.getDisplayedCount()).execute();
            showGameResult(gameResult);
        }
    }

    private String convertSecondsForDisplay (Integer seconds) {
        String returnTime;
        if (seconds < 10) {
            returnTime = "00:0" + seconds;
        }
        else if (seconds < 60) {
            returnTime = "00:" + seconds;
        }
        else if (seconds < 600) {
            returnTime = "0" + seconds/60 + ":";
            if ((seconds % 60) < 10) {
                returnTime = returnTime + "0" + (seconds % 60);
            }
            else {
                returnTime = returnTime + (seconds % 60);
            }
        }
        else if (seconds < 3600){
            returnTime = seconds/60 + ":";
            if ((seconds % 60) < 10) {
                returnTime = returnTime + "0" + (seconds % 60);
            }
            else {
                returnTime = returnTime + (seconds % 60);
            }
        }
        else {
            returnTime = "More than 1 Hour!!";
        }

        return returnTime;
    }

    public void displayGameStats(){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        Integer lastGameTimeInteger = Integer.valueOf(prefs.getString("LastGameTime", "0"));
        Integer fastestTimeInteger = Integer.valueOf(prefs.getString("FastestTime", "0"));
        Integer slowestTimeInteger = Integer.valueOf(prefs.getString("SlowestTime", "0"));
        Integer averageTimeInteger = Integer.valueOf(prefs.getString("AverageTime", "0"));
        gamesTotalString = prefs.getString("GamesTotal", "0");
        gamesWonString = prefs.getString("GamesWon", "0");

        Log.i("MainActivity", "In displayGameStats: gamesWon " + gamesWonString);
        Log.i("MainActivity", "In displayGameStats: gamesTotal " + gamesTotalString);
        Log.i("MainActivity", "In displayGameStats: lastGameTime " + lastGameTimeInteger);
        Log.i("MainActivity", "In displayGameStats: fastestTime " + fastestTimeInteger);
        Log.i("MainActivity", "In displayGameStats: slowestTime " + slowestTimeInteger);
        Log.i("MainActivity", "In displayGameStats: averageTime " + averageTimeInteger);

        TextView countWonTotal = (TextView) findViewById(R.id.labelGamesWonDisplay);
        countWonTotal.setText(gamesWonString);

        TextView countTotalGames = (TextView) findViewById(R.id.labelGamesTotalDisplay);
        countTotalGames.setText(gamesTotalString);

        TextView lastGameTime = (TextView) findViewById(R.id.labelLastGameTimeDisplay);
        lastGameTime.setText(convertSecondsForDisplay(lastGameTimeInteger));

        TextView fastestTime = (TextView) findViewById(R.id.labelFastestTimeDisplay);
        fastestTime.setText(convertSecondsForDisplay(fastestTimeInteger));

        TextView averageTime = (TextView) findViewById(R.id.labelAverageTimeDisplay);
        averageTime.setText(convertSecondsForDisplay(averageTimeInteger));

        TextView slowestTime = (TextView) findViewById(R.id.labelSlowestTimeDisplay);
        slowestTime.setText(convertSecondsForDisplay(slowestTimeInteger));
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
        if (id == R.id.action_about) {
            DialogFragment newFragment = new AboutDialogFragment();
            newFragment.show(getSupportFragmentManager(), "about");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onResume() {
        super.onResume();
        displayGameStats();
    }

}
