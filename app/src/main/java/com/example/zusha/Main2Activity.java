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

public class Main2Activity extends AppCompatActivity implements LocationListener {

    private static final String TAG = "Main2Activity";

    private static final int ERROR_DIALOG_REQUEST = 9001;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

//        TextView vehicleDetailsTextView = (TextView) findViewById(R.id.vehicleDetailsTextView);
        TextView regNoTextView = (TextView) findViewById(R.id.regNoTextView);
        TextView saccoTextView = (TextView) findViewById(R.id.saccoTextView);
        TextView driverTextView = (TextView) findViewById(R.id.driverTextView);

//        String vehicleDetails = getIntent().getExtras().getString("vehicleDetails");
        String regNoDetails = getIntent().getExtras().getString("regNoDetails");
        String saccoDetails = getIntent().getExtras().getString("saccoDetails");
        String driverDetails = getIntent().getExtras().getString("driverDetails");

//        vehicleDetailsTextView.setText(vehicleDetails);
        regNoTextView.setText(regNoDetails);
        saccoTextView.setText(saccoDetails);
        driverTextView.setText(driverDetails);

//        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
//
//        LocationManager lm = (LocationManager) this.getSystemService(getApplicationContext().LOCATION_SERVICE);
//        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);

//        this.onLocationChanged(null);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1000);
        } else {

            //start the program if permission is granted
            if(isServicesOK()){
                trackSpeed();
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

        if (location==null){

            speedTextView.setText("0");
        } else {
            float currentSpeed = location.getSpeed() * 3.6f;
            speedTextView.setText(String.format("%.2f", currentSpeed)+ "" );
            if (currentSpeed > 80){
                speedStatusTextView.setText("Over Speeding");
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
            } else {

                finish();
            }

        }

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
}
