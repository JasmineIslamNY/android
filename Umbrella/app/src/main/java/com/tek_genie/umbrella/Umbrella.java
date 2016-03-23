package com.tek_genie.umbrella;

import android.location.Location;
import android.location.LocationListener;
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
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Umbrella extends AppCompatActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

    protected GoogleMap mMap;
    protected GoogleApiClient mGoogleApiClient;
    protected Location mCurrentLocation;
    double latitude;
    double longitude;
    InputStream returnFromBackground;

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
        onLocationChanged(mCurrentLocation);
    }

    @Override
    public void onConnected(Bundle connectionHint) {
        mCurrentLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
        updateLocation();
    }

    protected void updateLocation() {
        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        //LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
    }

    protected String doINeedAnUmbrella(String location){
        String urlStringStart = "http://api.wunderground.com/api/5a0befd8a35cbb56/conditions/q/";
        String urlStringEnd = ".json";
        String urlStringLocation = location;
        String urlString = urlStringStart + urlStringLocation + urlStringEnd;
        InputStream umbrellaJSON;

        WebServiceTask webserviceTask = new WebServiceTask();
        webserviceTask.execute(urlString); //this will return an InputStream as returnFromBackground

        if (returnFromBackground == null) {
            Log.i("ReturnFromonPostExecute", "Error - return from webserviceTask is null");
        }
        else {
            Log.i("ReturnFromonPostExecute", "Success - return from webserviceTask  is not null");
        }

        umbrellaJSON = returnFromBackground;  //renaming as umbrellaJSON to make it more readable
        String umbrellaYesNo = getResultFromJSON(returnFromBackground, "umbrella"); //this will return "Yes" if there is rain or snow
        return umbrellaYesNo;
    }

    protected String determineCityState (double latitude, double longitude) {
        String urlStringStart = "http://api.wunderground.com/api/5a0befd8a35cbb56/geolookup/q/";
        String urlStringMiddle = ",";
        String urlStringEnd = ".json";
        String latitudeString = String.valueOf(latitude);
        String longitudeString = String.valueOf(longitude);
        String urlString = urlStringStart + latitudeString + urlStringMiddle + longitudeString + urlStringEnd;
        InputStream cityStateJSON;

        WebServiceTask webserviceTask = new WebServiceTask();
        webserviceTask.execute(urlString); //this will return an InputStream as returnFromBackground

        if (returnFromBackground == null) {
            Log.i("ReturnFromonPostExecute", "Error - return from webserviceTask is null");
        }
        else {
            Log.i("ReturnFromonPostExecute", "Success - return from webserviceTask  is not null");
        }

        cityStateJSON = returnFromBackground;  //renaming as cityStateJSON to make it more readable
        String cityAndState = getResultFromJSON(returnFromBackground, "cityState"); //this will return State/City as a string, i.e. CA/San Francisco
        return cityAndState;
    }

    protected String getResultFromJSON (InputStream inputJSON, String type) {
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader bufferedReader = null;

        if (inputJSON == null) {
            Log.i("Error", "inputJSON is null");
        }
        try {

            bufferedReader = new BufferedReader(new InputStreamReader(inputJSON));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line + "\n");
            }
            if (type == "cityState") {
            String returnedCity = getJSONKeyPairValue(stringBuilder, "city");
            String returnedState = getJSONKeyPairValue(stringBuilder, "state");
            String cityAndState = returnedCity + "/" + returnedState;
            return cityAndState;
            }
            else {
                String umbrella = getJSONKeyPairValue(stringBuilder, "weather");
                return umbrella;
            }

            //Log.i("Returned data", stringBuilder.toString());
        } catch (Exception e) {
            Log.e("MainActivity", "Error", e);
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (final IOException e) {
                    Log.e("PlaceholderFragment", "Error closing stream", e);
                }
            }
        }
        return "Error!";
    }

    protected String getJSONKeyPairValue (StringBuilder stringBuilder, String returnKey) {
        String textToReturn = null;
        if (returnKey == "city" || returnKey == "state"){
            String cityState = null;
            try {
                JSONObject jsonObject = new JSONObject(stringBuilder.toString());
                JSONObject location = jsonObject.getJSONObject("location");
                cityState = location.getString(returnKey);
                textToReturn = cityState;
                return cityState;

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        else {
            try {
                JSONObject jsonObject = new JSONObject(stringBuilder.toString());
                if (jsonObject.has("rain") || jsonObject.has("snow")) {
                    textToReturn = "Yes";
                    return("Yes");
                } else {
                    textToReturn = "No";
                    return("No");
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
        return textToReturn;
    }


    @Override
    public void onLocationChanged(Location location) {
        //showLocation(location);
        mCurrentLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
        if (mCurrentLocation != null) {
            latitude = mCurrentLocation.getLatitude();
            longitude = mCurrentLocation.getLongitude();
        }
        else {
            latitude = 40.666954;
            longitude = -73.715123;
        }
        Log.i("Where am I?", "Latitude: " + latitude + ", Longitude:" + longitude);
        String cityAndState = determineCityState(latitude, longitude);
        String umbrellaToday = doINeedAnUmbrella(cityAndState);
        Log.i("Do I need an umbrella today", "? " + umbrellaToday);
    }

    @Override
    public void onConnectionSuspended(int i) {
    }
    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
    }


    @Override
    public void onProviderDisabled(String s) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onStatusChanged(String s,int i, Bundle bundle) {

    }


    @Override
    protected void onPause() {
        super.onPause();
        //stop location updates
        //LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
    }
    @Override
    public void onResume() {
        super.onResume();
        if (mGoogleApiClient.isConnected()) {
            updateLocation();
        }
        onLocationChanged(mCurrentLocation);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMyLocationEnabled(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_umbrella);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

           // SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
               // .findFragmentById(R.id.map);
        //mapFragment.getMapAsync(this);
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_umbrella, menu);
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


    private class WebServiceTask extends AsyncTask<String, String, InputStream> {

        
        @Override
        protected void onPostExecute(InputStream s) {
            super.onPostExecute(s);
            /*
            if (s == null) {
                Log.i("onPostExecute", "Error - return from doInBackground is null");
            }
            else {
                    Log.i("onPostExecute", "Success - return from doInBackground  is not null");
                }
              */
            return s;

        }

        @Override
        protected InputStream doInBackground(String... params) {
                HttpURLConnection urlConnection = null;
                InputStream jsonFile = null;

                try {
                    URL url = new URL(params[0]);
                    urlConnection = (HttpURLConnection) url.openConnection();
                    jsonFile = urlConnection.getInputStream();
                    return jsonFile;
                } catch (IOException e) {
                    Log.e("MainActivity", "Error ", e);
                } finally {
                    if (urlConnection != null) {
                        urlConnection.disconnect();
                    }
                }
                return jsonFile;
            }
    }
}
