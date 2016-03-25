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

public class Umbrella extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    protected GoogleApiClient mGoogleApiClient;
    //protected Location mCurrentLocation;
    double latitude;
    double longitude;

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
        updateUmbrella();
    }

    @Override
    public void onConnected(Bundle connectionHint) {
        /* mCurrentLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
        updateLocation();
        */
    }

    public void updateUmbrella() {
        Location mCurrentLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);

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
            Log.i("What is the value of mCurrentLocation", mCurrentLocation.toString());
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

        TextView textview = (TextView) findViewById(R.id.hello);
        textview.setText("Finding out if you need an umbrella...");

        //wait(10000);

        String latitudeString = String.valueOf(latitude);
        String longitudeString = String.valueOf(longitude);
        String [] latlongArray = {latitudeString, longitudeString};

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
    }
    @Override
    public void onResume() {
        super.onResume();
        updateUmbrella();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_umbrella);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
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


    private class WebServiceTask extends AsyncTask<String, String, String> {

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            TextView textview = (TextView) findViewById(R.id.hello);
            textview.setText(s);

        }

        @Override
        protected String doInBackground(String... params) {
            String urlForCityState = determineCityStateURL(params[0], params[1]);

            HttpURLConnection urlConnection = null;
            InputStream jsonFileInputStream = null;
            try {
                    URL url = new URL(urlForCityState);
                    urlConnection = (HttpURLConnection) url.openConnection();
                    jsonFileInputStream = urlConnection.getInputStream();
                } catch (IOException e) {
                    Log.e("MainActivity", "Error ", e);
                } finally {
                    if (urlConnection != null) {
                        urlConnection.disconnect();
                    }
                }

            String cityAndState = determineCityState(jsonFileInputStream);
            String urlForUmbrella = doINeedAnUmbrellaURL(cityAndState);

            urlConnection = null;
            jsonFileInputStream = null;
            try {
                URL url2 = new URL(urlForUmbrella);
                urlConnection = (HttpURLConnection) url2.openConnection();
                jsonFileInputStream = urlConnection.getInputStream();
            } catch (IOException e) {
                Log.e("MainActivity", "Error ", e);
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
            }

            String doINeedAnUmbrella = determineDoINeedAndUmbrella(jsonFileInputStream);
            return doINeedAnUmbrella;
            }

        protected String determineCityStateURL (String latitude, String longitude) {
            String urlStringStart = "http://api.wunderground.com/api/5a0befd8a35cbb56/geolookup/q/";
            String urlStringMiddle = ",";
            String urlStringEnd = ".json";
            String urlString = urlStringStart + latitude + urlStringMiddle + longitude + urlStringEnd;
            return urlString;
        }
        protected String determineCityState (InputStream input) {
            String cityAndState = getResultFromJSON(input, "cityState"); //this will return State/City as a string, i.e. CA/San Francisco
            Log.i("cityAndState before replacement", cityAndState);
            cityAndState = cityAndState.replaceAll(" ", "%20");  //changes blank spaces to %20 as cities like 'New York' were not working
            Log.i("cityAndState after replacement", cityAndState);
            return cityAndState;
        }
        protected String doINeedAnUmbrellaURL(String location) {
            String urlStringStart = "http://api.wunderground.com/api/5a0befd8a35cbb56/conditions/q/";
            String urlStringEnd = ".json";
            String urlString = urlStringStart + location + urlStringEnd;
            return urlString;
        }
        protected String determineDoINeedAndUmbrella(InputStream input) {
            String umbrellaYesNo = getResultFromJSON(input, "umbrella"); //this will return "Yes" if there is rain or snow
            return umbrellaYesNo;
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
                    String cityAndState = returnedState + "/" + returnedCity;
                    Log.i("Result of cityAndState", cityAndState);
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
                    Log.i("Print City or State Found", textToReturn);
                    return cityState;

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
            else {
                try {
                    JSONObject jsonObject = new JSONObject(stringBuilder.toString());
                    JSONObject weather = jsonObject.getJSONObject("current_observation");
                    String weatherKeyValue = weather.getString("weather");
                    Log.i("Weather value", weatherKeyValue);
                    if (weatherKeyValue.toLowerCase().contains("rain") || weatherKeyValue.toLowerCase().contains("snow") || weatherKeyValue.toLowerCase().contains("cloud")) {
                        textToReturn = "Take an umbrella if you want to stay dry";
                        return(textToReturn);
                    } else {
                        textToReturn = "No need for an umbrella";
                        return(textToReturn);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
            return textToReturn;
        }
    }
}
