package com.thinkful.fizzbuzzunit1;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.view.View;
import android.util.Log;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;


public class MainActivity extends Activity {

    TextView textView;
    EditText editText;
    String fizzString = "Fizz";
    String buzzString = "Buzz";
    String fizzBuzzString = "FizzBuzz";
    String valueString;
    int [] fizzArray = new int[10];
    int fizzIndex = 0;
    int [] buzzArray = new int[10];
    int buzzIndex = 0;
    String TAG = "MyData";
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
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

    public void doButton(View view) {
        // Implement the function of the button here
        // Use numberOfValues, and create a string array of both the numbers and words,
        // replacing the appropriate numbers with the words fizz or buzz

        editText = (EditText) findViewById(R.id.editText); // user input
        textView = (TextView) findViewById(R.id.fizzText); // where the text will display
        textView.setText("" + "\n");

        int numberOfValues = Integer.parseInt(editText.getText().toString());

        for (int i = 1; i <= numberOfValues; i++) {
            valueString = checkValue(i);
            textView.append(valueString + "\n"); // display in the textView
        }
        for (int i = 0; i < fizzArray.length; i++) {
            Log.i("MyData", "fizzArray[" + i + "] = " + fizzArray[i]);
        }
        for (int i = 0; i < buzzArray.length; i++) {
            Log.i("MyData", "buzzArray[" + i + "] = " + buzzArray[i]);
        }

    }

    public String checkValue(int valueToCheck) {
        String checkResult;
        if (valueToCheck % 3 == 0 && valueToCheck % 5 == 0) {
            checkResult = fizzBuzzString;
        } else if (valueToCheck % 3 == 0) {
            checkResult = fizzString;
            fizzArray[fizzIndex] = valueToCheck;
            fizzIndex++;
        } else if (valueToCheck % 5 == 0) {
            checkResult = buzzString;
            buzzArray[buzzIndex] = valueToCheck;
            buzzIndex++;
        } else {
            checkResult = String.valueOf(valueToCheck);
        }
        return checkResult;
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.thinkful.fizzbuzzunit1/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.thinkful.fizzbuzzunit1/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}
