package com.tek_genie.hangman;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Chronometer;


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
    private Chronometer chronometer;
    private View decorView;
    public static Bitmap bmImage;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                        | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        );

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

        gameClue = (TextView) findViewById(R.id.labelClue);
        gameClue.setText(nameAndInfo[6]);

        chronometer = (Chronometer) findViewById(R.id.chronometer);
        chronometer.setBase(SystemClock.elapsedRealtime());
        chronometer.start();


        new DownloadImageTask()
                .execute(nameAndInfo[4]);

        newGame = (Button) findViewById(R.id.buttonGameComplete);
        newGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numberOfFailedTries = maxFailedTries + 1;
                testForWinOrLoss();
            }
        });

        final Button letterA = (Button) findViewById(R.id.buttonA);
        letterA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processClickedLetter("A");
                letterA.setBackgroundColor(Color.RED);
            }
        });

        final Button letterB = (Button) findViewById(R.id.buttonB);
        letterB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processClickedLetter("B");
                letterB.setBackgroundColor(Color.RED);
            }
        });

        final Button letterC = (Button) findViewById(R.id.buttonC);
        letterC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processClickedLetter("C");
                letterC.setBackgroundColor(Color.RED);
            }
        });

        final Button letterD = (Button) findViewById(R.id.buttonD);
        letterD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processClickedLetter("D");
                letterD.setBackgroundColor(Color.RED);
            }
        });

        final Button letterE = (Button) findViewById(R.id.buttonE);
        letterE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processClickedLetter("E");
                letterE.setBackgroundColor(Color.RED);
            }
        });

        final Button letterF = (Button) findViewById(R.id.buttonF);
        letterF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processClickedLetter("F");
                letterF.setBackgroundColor(Color.RED);
            }
        });

        final Button letterG = (Button) findViewById(R.id.buttonG);
        letterG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processClickedLetter("G");
                letterG.setBackgroundColor(Color.RED);
            }
        });

        final Button letterH = (Button) findViewById(R.id.buttonH);
        letterH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processClickedLetter("H");
                letterH.setBackgroundColor(Color.RED);
            }
        });

        final Button letterI = (Button) findViewById(R.id.buttonI);
        letterI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processClickedLetter("I");
                letterI.setBackgroundColor(Color.RED);
            }
        });

        final Button letterJ = (Button) findViewById(R.id.buttonJ);
        letterJ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processClickedLetter("J");
                letterJ.setBackgroundColor(Color.RED);
            }
        });

        final Button letterK = (Button) findViewById(R.id.buttonK);
        letterK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processClickedLetter("K");
                letterK.setBackgroundColor(Color.RED);
            }
        });

        final Button letterL = (Button) findViewById(R.id.buttonL);
        letterL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processClickedLetter("L");
                letterL.setBackgroundColor(Color.RED);
            }
        });

        final Button letterM = (Button) findViewById(R.id.buttonM);
        letterM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processClickedLetter("M");
                letterM.setBackgroundColor(Color.RED);
            }
        });

        final Button letterN = (Button) findViewById(R.id.buttonN);
        letterN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processClickedLetter("N");
                letterN.setBackgroundColor(Color.RED);
            }
        });

        final Button letterO = (Button) findViewById(R.id.buttonO);
        letterO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processClickedLetter("O");
                letterO.setBackgroundColor(Color.RED);
            }
        });

        final Button letterP = (Button) findViewById(R.id.buttonP);
        letterP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processClickedLetter("P");
                letterP.setBackgroundColor(Color.RED);
            }
        });

        final Button letterQ = (Button) findViewById(R.id.buttonQ);
        letterQ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processClickedLetter("Q");
                letterQ.setBackgroundColor(Color.RED);
            }
        });

        final Button letterR = (Button) findViewById(R.id.buttonR);
        letterR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processClickedLetter("R");
                letterR.setBackgroundColor(Color.RED);
            }
        });

        final Button letterS = (Button) findViewById(R.id.buttonS);
        letterS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processClickedLetter("S");
                letterS.setBackgroundColor(Color.RED);
            }
        });

        final Button letterT = (Button) findViewById(R.id.buttonT);
        letterT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processClickedLetter("T");
                letterT.setBackgroundColor(Color.RED);
            }
        });

        final Button letterU = (Button) findViewById(R.id.buttonU);
        letterU.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processClickedLetter("U");
                letterU.setBackgroundColor(Color.RED);
            }
        });

        final Button letterV = (Button) findViewById(R.id.buttonV);
        letterV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processClickedLetter("V");
                letterV.setBackgroundColor(Color.RED);
            }
        });

        final Button letterW = (Button) findViewById(R.id.buttonW);
        letterW.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processClickedLetter("W");
                letterW.setBackgroundColor(Color.RED);
            }
        });

        final Button letterX = (Button) findViewById(R.id.buttonX);
        letterX.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processClickedLetter("X");
                letterX.setBackgroundColor(Color.RED);
            }
        });

        final Button letterY = (Button) findViewById(R.id.buttonY);
        letterY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processClickedLetter("Y");
                letterY.setBackgroundColor(Color.RED);
            }
        });

        final Button letterZ = (Button) findViewById(R.id.buttonZ);
        letterZ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processClickedLetter("Z");
                letterZ.setBackgroundColor(Color.RED);
            }
        });

        final Button letterDASH = (Button) findViewById(R.id.buttonDASH);
        letterDASH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processClickedLetter("-");
                letterDASH.setBackgroundColor(Color.RED);
            }
        });

        final Button letterQUOTE = (Button) findViewById(R.id.buttonQUOTE);
        letterQUOTE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processClickedLetter("'");
                letterQUOTE.setBackgroundColor(Color.RED);
            }
        });

    }

    private void processClickedLetter (String s) {
        int isLetterFound = checkNameForLetter(s);
        if (isLetterFound == 0){
            numberOfFailedTries += 1;
        }
        testForWinOrLoss();
        updateNumberOfFailedTriesImage();
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

    private String gameNameForDisplay (StringBuilder underscoreName){
        String nameForDisplay = "";
        for (int i = 0; i < underscoreName.length(); i++) {
            if (underscoreName.substring(i, i+1).equals(" ")) {
                nameForDisplay = nameForDisplay + "\n";
            }
            else if (underscoreName.substring(i, i+1).equals("_")) {
                nameForDisplay = nameForDisplay + "_ ";
            }
            else {
                nameForDisplay = nameForDisplay + underscoreName.substring(i, i+1);
            }
        }
        return nameForDisplay;
    }

    private void updateGameNameDisplay () {
        String displayThis = gameNameForDisplay(gameName);
        gameNameLabel = (TextView) findViewById(R.id.labelGameName);
        gameNameLabel.setText(displayThis);
    }

    private void updateNumberOfFailedTriesImage () {
        // String temp = "R.drawable.stick" + numberOfFailedTries;
        // int stickPersonNumber = Integer.valueOf(temp);
        ImageView triesImage = (ImageView) findViewById(R.id.imageTryNumber);
        if (numberOfFailedTries == 1) {
            triesImage.setImageResource(R.drawable.happy1);
        }
        else if (numberOfFailedTries == 2) {
            triesImage.setImageResource(R.drawable.happy2);
        }
        else if (numberOfFailedTries == 3) {
            triesImage.setImageResource(R.drawable.happy3);
        }
        else if (numberOfFailedTries == 4) {
            triesImage.setImageResource(R.drawable.happy4);
        }
        else if (numberOfFailedTries == 5) {
            triesImage.setImageResource(R.drawable.happy5);
        }
        else if (numberOfFailedTries == 6) {
            triesImage.setImageResource(R.drawable.happy6);
        }
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
            chronometer.stop();
            CharSequence timeEnd = chronometer.getText();
            Log.i(TAG, "Timer End: " + timeEnd.toString());

            Intent intent = new Intent();
            intent.putExtra("gameResultIntentExtra", gameWonLostText);
            intent.putExtra("gameTimeIntentExtra", timeEnd.toString());
            setResult(RESULT_OK, intent);
            finish();
        }
    }

    public void onResume() {
        super.onResume();
        decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                        | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        );
    }
}
