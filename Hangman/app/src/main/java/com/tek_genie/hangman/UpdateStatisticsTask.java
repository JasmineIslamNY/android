package com.tek_genie.hangman;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;

/**
 * Created by jasmineislam on 6/27/16.
 */
public class UpdateStatisticsTask extends AsyncTask<String, Void, Void> {
    private Context context;
    private String gameResult;
    private String gameTime;

    public UpdateStatisticsTask(Context context, String gameResult, String gameTime) {
        this.context = context;
        this.gameResult = gameResult;
        this.gameTime = gameTime;
    }

    @Override
    protected Void doInBackground(String... params) {
        Integer seconds = 0;

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        String lastGameTime = prefs.getString("LastGameTime", "0");
        String fastestTime = prefs.getString("FastestTime", "0");
        String slowestTime = prefs.getString("SlowestTime", "0");
        String averageTime = prefs.getString("AverageTime", "0");
        String gamesTotal = prefs.getString("GamesTotal", "0");
        String gamesWon = prefs.getString("GamesWon", "0");

        // Split gameTime into segments
        String [] segments = gameTime.split(":");
        Log.i("UpdateStatisticsTask", "segments contents: " + segments[0]);
        Log.i("UpdateStatisticsTask", "segments contents: " + segments[1]);
        if (segments.length == 3){
            Log.i("UpdateStatisticsTask", "segments contents: " + segments[2]);
        }

        // if only minutes and seconds
        if (segments.length == 2){
            seconds = (60 * Integer.valueOf(segments[0])) + Integer.valueOf(segments[1]);
        }
        else if (segments.length == 3) {
            seconds = (60 * 60 * Integer.valueOf(segments[0])) + (60 * Integer.valueOf(segments[1])) + Integer.valueOf(segments[2]);
        }

        if (seconds != null) {
            lastGameTime = String.valueOf(seconds);
            if ((Integer.valueOf(fastestTime) > seconds && seconds != 0) || Integer.valueOf(fastestTime) == 0) {
                fastestTime = String.valueOf(seconds);
            }
            if (Integer.valueOf(slowestTime) < seconds || Integer.valueOf(slowestTime) == 0) {
                slowestTime = String.valueOf(seconds);

            }

            averageTime = String.valueOf(((Integer.valueOf(averageTime) * Integer.valueOf(gamesTotal)) + seconds) / (Integer.valueOf(gamesTotal) + 1));
        }

        gamesTotal = String.valueOf(Integer.valueOf(gamesTotal) + 1);
        gamesWon = String.valueOf(Integer.valueOf(gamesWon) + Integer.valueOf(gameResult));


        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("GamesWon", gamesWon);
        editor.putString("GamesTotal", gamesTotal);
        editor.putString("LastGameTime", lastGameTime);
        editor.putString("FastestTime", fastestTime);
        editor.putString("SlowestTime", slowestTime);
        editor.putString("AverageTime", averageTime);
        editor.commit();

        Log.i("UpdateStatisticsTask", "In doInBackground: gamesWon " + gamesWon);
        Log.i("UpdateStatisticsTask", "In doInBackground: gamesTotal " + gamesTotal);
        Log.i("UpdateStatisticsTask", "In doInBackground: lastGameTime " + lastGameTime);
        Log.i("UpdateStatisticsTask", "In doInBackground: fastestTime " + fastestTime);
        Log.i("UpdateStatisticsTask", "In doInBackground: slowestTime " + slowestTime);
        Log.i("UpdateStatisticsTask", "In doInBackground: averageTime " + averageTime);

        return null;
    }

    protected void onPostExecute() {

    }
}


