package com.tek_genie.hangman;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class HangmanResult extends AppCompatActivity {
    private String[] nameAndInfo;
    private String didYouWin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

        ImageView personPicture = (ImageView) findViewById(R.id.imageOfPerson);
        Uri url = Uri.parse(nameAndInfo[4]);
        personPicture.setImageURI(url);

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
}
