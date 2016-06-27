package com.tek_genie.hangman;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.tek_genie.hangman.Hangman.*;

public class HangmanResult extends AppCompatActivity {
    private String[] nameAndInfo;
    private String didYouWin;
    private View decorView;

    @Override
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

        setContentView(R.layout.activity_hangman_result);

        Intent intent = getIntent();
        nameAndInfo = intent.getStringArrayExtra("nameIntentExtra");

        didYouWin = intent.getStringExtra("didYouWinIntentExtra");
        if (didYouWin.equals("1")) {
            TextView wonLostMessage = (TextView) findViewById(R.id.labelWonLostMessage);
            wonLostMessage.setText("Congratulations! You got the correct person");
        }
        else {
            TextView wonLostMessage = (TextView) findViewById(R.id.labelWonLostMessage);
            wonLostMessage.setText("Sorry! Better luck next time");
        }

        TextView personInformation = (TextView) findViewById(R.id.labelPersonInformation);
        personInformation.setText(nameAndInfo[5]);

        /*
        new DownloadImageTask((ImageView) findViewById(R.id.imageOfPerson))
                .execute(nameAndInfo[4]);
        */

        /*
        ImageView personPicture = (ImageView) findViewById(R.id.imageOfPerson);
        Uri url = Uri.parse(nameAndInfo[4]);
        personPicture.setImageURI(url);
        */

        if (Hangman.bmImage != null) {
            ImageView personPicture = (ImageView) findViewById(R.id.imageOfPerson);
            Log.i("HangmanResult.java", "Using the file here.");
            personPicture.setImageBitmap(Hangman.bmImage);
        }

        Button close = (Button) findViewById(R.id.buttonClose);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //setResult(RESULT_OK);
                finish();
            }
        });

        Button moreInfo = (Button) findViewById(R.id.buttonMoreInfo);
        moreInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(nameAndInfo[3]));
                startActivity(browserIntent);
            }
        });
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




