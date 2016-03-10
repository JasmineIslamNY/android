package com.tek_genie.mapper;

import android.location.Location;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import java.util.logging.Handler;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.*;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, ConnectionCallbacks, OnConnectionFailedListener, LocationListener {

    private GoogleMap mMap;
    private GoogleApiClient mGoogleApiClient;
    private Location mCurrentLocation;
    double latitude = 40.666954;
    double longitude = -73.715123;
    double lastLatitude;
    double lastLongitude;
    PolylineOptions newLines = new PolylineOptions();

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }
    @Override
    public void onConnected(Bundle connectionHint) {
        mCurrentLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
        showLocation(mCurrentLocation);
        startLocationUpdates();
    }
    protected void showLocation(Location mCurrentLocation) {
        if (mCurrentLocation != null) {
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                    new LatLng(mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude()), 15));
        }
    }

    protected void startLocationUpdates() {
        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        LocationServices.FusedLocationApi.requestLocationUpdates(
                mGoogleApiClient, mLocationRequest, this);
    }

    public void drawLines(double lat, double lon) {
            LatLng temp = new LatLng(lat, lon);
            newLines.add(temp);
            Polyline polyline = mMap.addPolyline(newLines);
        }

    @Override
    public void onLocationChanged(Location location) {
        showLocation(location);
        mCurrentLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
        Log.i("Where am I?", "Latitude: " + mCurrentLocation.getLatitude() + ", Longitude:" + mCurrentLocation.getLongitude());
        if (mCurrentLocation.getLatitude() == lastLatitude) {
            latitude += 0.001;
            Log.i("Latitude didn't change, ", "still " + lastLatitude);
        }
        else {
            lastLatitude = mCurrentLocation.getLatitude();
            latitude = lastLatitude;
        }
        if (mCurrentLocation.getLongitude() == lastLongitude) {
            longitude += 0.001;
            Log.i("Longitude didn't change, ", "still " + lastLongitude);
        }
        else {
            lastLongitude = mCurrentLocation.getLongitude();
            longitude = lastLongitude;
        }
        drawLines(latitude, longitude);
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
        //stop location updates
        LocationServices.FusedLocationApi.removeLocationUpdates(
                mGoogleApiClient, this);
    }
    @Override
    public void onResume() {
        super.onResume();
        if (mGoogleApiClient.isConnected()) {
            //setUpMapIfNeeded();    // <-from previous tutorial
            startLocationUpdates();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMyLocationEnabled(true);


        //mMap.animateCamera(CameraUpdateFactory.zoomTo(19), 2000, null);
            //.moveCamera(CameraUpdateFactory.newLatLngZoom(mMap.setMyLocationEnabled(true), 10));
        /*
        // Add a marker in Sydney and move the camera
        //LatLng sydney = new LatLng(-34, 151);
        //LatLng home = new LatLng(40.666954, -73.715123);
        //mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        //mMap.addMarker(new MarkerOptions().position(home).title("Marker at Home"));
        LatLng thinkful = new LatLng(40.72493, -73.996599);
        mMap.addMarker(new MarkerOptions()
                .position(thinkful)
                .title("Thinkful Headquarters")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.thinkful))
                .snippet("On a mission to reinvent education"))
                .showInfoWindow()
                ;
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(thinkful, 10));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(19), 2000, null);
        *//**
        Handler handler;
        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mMap.animateCamera(CameraUpdateFactory.zoomTo(19), 2000, null);
            }
        }, 2000);
         */

    }
}
