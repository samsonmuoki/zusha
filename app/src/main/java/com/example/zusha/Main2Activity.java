package com.example.zusha;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

public class Main2Activity extends AppCompatActivity implements LocationListener, OnMapReadyCallback {

    private static final String TAG = "Main2Activity";

    private static final int ERROR_DIALOG_REQUEST = 9001;

    Location currentLocation;
    FusedLocationProviderClient fusedLocationProviderClient;
    private static final int REQUEST_CODE = 101;

    private GoogleMap mMap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
//        fetchLastLocation();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        TextView regNoTextView = (TextView) findViewById(R.id.regNoTextView);
        TextView saccoTextView = (TextView) findViewById(R.id.saccoTextView);
        TextView driverTextView = (TextView) findViewById(R.id.driverTextView);

        String regNoDetails = getIntent().getExtras().getString("regNoDetails");
        String saccoDetails = getIntent().getExtras().getString("saccoDetails");
        String driverDetails = getIntent().getExtras().getString("driverDetails");

        regNoTextView.setText(regNoDetails);
        saccoTextView.setText(saccoDetails);
        driverTextView.setText(driverDetails);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1000);
        } else {

            //start the program if permission is granted
            if(isServicesOK()){
                trackSpeed();
                fetchLastLocation();

            }

        }

    }

        public boolean isServicesOK(){
            Log.d(TAG, "isServicesOK: checking google services version");

            int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(Main2Activity.this);

            if(available == ConnectionResult.SUCCESS){
                //everything is fine and user can make map requests
                Log.d(TAG, "isServicesOK: Google Play Services is working");
                return true;
            }
            else if(GoogleApiAvailability.getInstance().isUserResolvableError(available)){
                //an error occured but we can resolve it
                Log.d(TAG, "isServicesOK: an error occured but it can be resolved");
                Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(Main2Activity.this, available, ERROR_DIALOG_REQUEST);
                dialog.show();
            }
            else{
                Toast.makeText(this,"You can't make map requests", Toast.LENGTH_SHORT).show();
            }
            return false;
        }


    @Override
    public void onLocationChanged(Location location) {
        TextView speedTextView = (TextView) this.findViewById(R.id.speedTextView);
        TextView speedStatusTextView = (TextView) this.findViewById(R.id.speedStatusTextView);

        double longitude = currentLocation.getLongitude();
        double latitude = currentLocation.getLatitude();

        if (location==null){

            speedTextView.setText("0");
        } else {
            float currentSpeed = location.getSpeed() * 3.6f;
            speedTextView.setText(String.format("%.2f", currentSpeed)+ "" );
            if (currentSpeed < 25){
                speedStatusTextView.setText("Over Speeding");
                LatLng speedingLocation = new LatLng(currentLocation.getLatitude(),currentLocation.getLongitude());
                mMap.addMarker(new MarkerOptions().position(speedingLocation)
                .title("Over speeding Location"));
            } else {
                speedStatusTextView.setText("Below Limit");
            }

        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 1000) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                trackSpeed();
                fetchLastLocation();

            } else {

                finish();
            }

        }
//        switch (requestCode){
//            case REQUEST_CODE:
//               if (grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
//                   fetchLastLocation();
//                    trackSpeed();
//               }
//        }

    }

    private void updateLocationUI(){
        if (mMap == null) {
            return;
        }
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
    }


    private void fetchLastLocation() {
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(this, new String[]
//                    {Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
//            return;
//        }
        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null){
                    currentLocation = location;
                    double longitude = currentLocation.getLongitude();
                    double latitude = currentLocation.getLatitude();
//                    TextView locationTextView = (TextView) findViewById(R.id.locationTextView);
//                    locationTextView.setText("Latitude: "+latitude+"\n Longitude: "+longitude);

                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                            new LatLng(currentLocation.getLatitude(),
                                    currentLocation.getLongitude()), 15));
//                    SupportMapFragment supportMapFragment = (SupportMapFragment)
//                            getSupportFragmentManager().findFragmentById(R.id.map);
//                    supportMapFragment.getMapAsync(Main2Activity.this);
                }
            }
        });

    }

    private void trackSpeed(){
        LocationManager lm = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        if (lm != null){
            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,this);
            //commented, this is from the old version
            // this.onLocationChanged(null);
        }
        Toast.makeText(this,"Waiting for GPS connection!", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;

        updateLocationUI();

        fetchLastLocation();

//        LatLng myLocation = new LatLng(latitude, longitude);
////        LatLng myLocation = new LatLng(currentLocation.getLatitude(),currentLocation.getLongitude());
//        googleMap.addMarker(new MarkerOptions().position(myLocation)
//                .title("My Location"));
//        googleMap.moveCamera(CameraUpdateFactory.newLatLng(myLocation));
//        googleMap.animateCamera(CameraUpdateFactory.newLatLng(myLocation));
//        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(myLocation, 5));

    }

}
