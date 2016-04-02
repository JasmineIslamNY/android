package com.tek_genie.umbrellawithnotifications;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by jasmineislam on 4/1/16.
 */
public class UmbrellaNeeded {
    public UmbrellaNeeded(){}

    protected String doINeedAnUmbrella(String latitude, String longitude) {
        String urlForCityState = determineCityStateURL(latitude, longitude);

        HttpURLConnection urlConnection = null;
        InputStream jsonFileInputStream = null;
        String cityAndState = "";
        try {
            URL url = new URL(urlForCityState);
            urlConnection = (HttpURLConnection) url.openConnection();
            jsonFileInputStream = urlConnection.getInputStream();
            cityAndState = determineCityState(jsonFileInputStream);
        } catch (IOException e) {
            Log.e("MainActivity", "Error ", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }

        //String cityAndState = determineCityState(jsonFileInputStream);
        String urlForUmbrella = doINeedAnUmbrellaURL(cityAndState);

        urlConnection = null;
        jsonFileInputStream = null;
        String doINeedAnUmbrella = "";
        try {
            URL url2 = new URL(urlForUmbrella);
            urlConnection = (HttpURLConnection) url2.openConnection();
            jsonFileInputStream = urlConnection.getInputStream();
            doINeedAnUmbrella = determineDoINeedAndUmbrella(jsonFileInputStream);
        } catch (IOException e) {
            Log.e("MainActivity", "Error ", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }

        //String doINeedAnUmbrella = determineDoINeedAndUmbrella(jsonFileInputStream);
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
