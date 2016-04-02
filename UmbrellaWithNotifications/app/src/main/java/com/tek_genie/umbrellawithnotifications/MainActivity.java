package com.tek_genie.umbrellawithnotifications;

import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, com.google.android.gms.location.LocationListener {

    protected GoogleApiClient mGoogleApiClient;
    protected Location mCurrentLocation;
    double latitude;
    double longitude;
    protected String[] latlongArray;
    //protected boolean locationUpdatesStarted = false;

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
        /*
        TextView textview = (TextView) findViewById(R.id.hello);
        while (locationUpdatesStarted == false) {
            textview.setText("Finding out if you need an umbrella.");
            textview.setText("Finding out if you need an umbrella..");
            textview.setText("Finding out if you need an umbrella...");
        }
        updateUmbrella();
        */
        }

    @Override
    public void onConnected(Bundle connectionHint) {
        /* mCurrentLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
        updateLocation();
        */
        startLocationUpdates();
        updateUmbrella();

        }

    public void updateUmbrella() {
        mCurrentLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

        if (mGoogleApiClient == null) {
        Log.i("Is mGoogleApiClient null: ", "Yes!");
        }
        else {
        Log.i("Is mGoogleApiClient null: ", "No!");
        }
        if (mCurrentLocation == null) {
        Log.i("Is mCurrentLocation null: ", "Yes!");
        }
        else {
        Log.i("mCurrentLocation is not null, it's", mCurrentLocation.toString());
        }

        if (mCurrentLocation != null) {
        latitude = mCurrentLocation.getLatitude();
        longitude = mCurrentLocation.getLongitude();
        }
        else {
        latitude = 47.6063716;
        longitude = -122.3322141;  //Seattle, WA
        //latitude = 40.666954;
        //longitude = -73.715123;  //Valley Stream, NY
        //latitude = 40.778246;
        //longitude = -73.9677407; //Near Central Park
        }
        Log.i("Where am I?", "Latitude: " + latitude + ", Longitude:" + longitude);

        TextView textview = (TextView) findViewById(R.id.messageText);
        textview.setText("Finding out if you need an umbrella...");

        //wait(10000);

        String latitudeString = String.valueOf(latitude);
        String longitudeString = String.valueOf(longitude);
        latlongArray = new String[] {latitudeString, longitudeString};

        WebServiceTask webserviceTask = new WebServiceTask();
        webserviceTask.execute(latlongArray);
        }

    @Override
    public void onConnectionSuspended(int i) {
        }
    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        }

    @Override
    protected void onPause() {
        super.onPause();
        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        //locationUpdatesStarted = false;
        }
    @Override
    public void onResume() {
        super.onResume();
        TextView textview = (TextView) findViewById(R.id.messageText);
        textview.setText("Finding out if you need an umbrella...");
        if (mGoogleApiClient.isConnected()) {
        //setUpMapIfNeeded();    // <-from previous tutorial
        startLocationUpdates();
        updateUmbrella();
        }
        else{
        mGoogleApiClient.connect();
        }

        }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mGoogleApiClient = new GoogleApiClient.Builder(this)
        .addConnectionCallbacks(this)
        .addOnConnectionFailedListener(this)
        .addApi(LocationServices.API)
        .build();

        Button alarmButton = (Button) findViewById(R.id.btnSetAlarm);
        alarmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Pressed ", "ALARM button");
                setAlarm(latlongArray);
            }
        });
        Button cancelAlarmButton = (Button) findViewById(R.id.btnCancelAlarm);
        cancelAlarmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Pressed ", "CANCEL ALARM button");
                cancelAlarm(latlongArray);
            }
        });
    }

    protected void setAlarm(String [] llArray){
        Alarm alarm = new Alarm();
        alarm.setAlarm(this, llArray);
    }

    protected void cancelAlarm(String [] llArray){
        Alarm alarm = new Alarm();
        alarm.cancelAlarm(this, llArray);
    }

    protected void displayNotification() {
        Notifier notifier = new Notifier();
        notifier.createNotification(this, "This is your notification");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    protected void startLocationUpdates() {
        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        //locationUpdatesStarted = true;
        }

    @Override
    public void onLocationChanged(Location location) {
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


    private class WebServiceTask extends AsyncTask<String, String, String> {

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            TextView textview = (TextView) findViewById(R.id.messageText);
            textview.setText(s);
        }

        @Override
        protected String doInBackground(String... params) {
            UmbrellaNeeded umbrella = new UmbrellaNeeded();
            String doINeedAnUmbrella = umbrella.doINeedAnUmbrella(params[0], params[1]);
            return doINeedAnUmbrella;
        }
    }
}
