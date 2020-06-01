package com.example.zusha;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.firebase.client.Firebase;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Main2Activity extends AppCompatActivity implements LocationListener, OnMapReadyCallback {

    private static final String TAG = "Main2Activity";

    private static final String KEY_CAMERA_POSITION = "camera_position";
    private static final String KEY_LOCATION = "location";
    private CameraPosition mCameraPosition;
    private Location mLastKnownLocation;

    private static final int ERROR_DIALOG_REQUEST = 9001;

    Location currentLocation;
    FusedLocationProviderClient fusedLocationProviderClient;
    private static final int REQUEST_CODE = 101;

    private GoogleMap mMap;
    private double speed;
    private double latitude;
    private double longitude;
    private String vehicle;
    private String currentDateandTime;
    private String sacco;
    private String saccoEmail;

    private Button reportingButton;
    private Firebase mRootRef;

    DatabaseReference reff;
    Report report;
    long reportId;


    SQLiteDatabaseHelper mydb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        mydb = new SQLiteDatabaseHelper(this);

//        Load maps previous position
        if (savedInstanceState != null) {
            currentLocation = savedInstanceState.getParcelable(KEY_LOCATION);
            mCameraPosition = savedInstanceState.getParcelable(KEY_CAMERA_POSITION);
        }

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
//        fetchLastLocation();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        TextView regNoTextView = (TextView) findViewById(R.id.regNoTextView);
        TextView saccoTextView = (TextView) findViewById(R.id.saccoTextView);
        TextView driverTextView = (TextView) findViewById(R.id.driverTextView);

        final String regNoDetails = getIntent().getExtras().getString("regNoDetails");
        final String saccoDetails = getIntent().getExtras().getString("saccoDetails");
        final String driverDetails = getIntent().getExtras().getString("driverDetails");
        saccoEmail = getIntent().getExtras().getString("email");

        vehicle = regNoDetails;
        sacco = saccoDetails;

        regNoTextView.setText(regNoDetails);
        saccoTextView.setText(saccoDetails);
        driverTextView.setText(driverDetails);

        mRootRef = new Firebase("https://deep-cascade-240110.firebaseio.com/Reports");

        report = new Report();
        reff = FirebaseDatabase.getInstance().getReference().child("Reports");
        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists())
                    reportId = (dataSnapshot.getChildrenCount());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        reportingButton = (Button) findViewById(R.id.reportingButton);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1000);
        } else {

            //start the program if permission is granted
            if (isServicesOK()) {
                trackSpeed();
                fetchLastLocation();

////               EDUCATREE INCREMENT VALUE OF reportId
//                reportingButton.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//
//                        report.setRegNo(regNoDetails);
//                        report.setSacco(saccoDetails);
////                        report.setSpeed("Example KM/H");
//                        report.setLocation(currentLocation.getLatitude()+","+currentLocation.getLongitude());
////                        report.setCurrentDateandTime(currentDateandTime);
//
//                        reff.child(String.valueOf(reportId+1)).setValue(report);
//                        Toast.makeText(Main2Activity.this, "Report Successful", Toast.LENGTH_SHORT).show();
//                    }
//                });

//                TVAC add values to db
                reportingButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        // SEND EMAIL
                        sendMessage();


                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd 'at' HH:mm:ss");
                        currentDateandTime = sdf.format(new Date());

                        Firebase childReportId = mRootRef.child(String.valueOf(reportId));
                        Firebase childRefRegNo = childReportId.child("RegNo");
                        Firebase childRefSacco = childReportId.child("Sacco");
                        Firebase childRefTime = childReportId.child("Time");
                        Firebase childRefLocation = childReportId.child("Location");
                        Firebase childRefSpeed = childReportId.child("Speed");
                        Firebase childRefDriver = childReportId.child("Driver");

                        childRefRegNo.setValue(regNoDetails);
//                        childRefDriver.setValue(driverDetails);
                        childRefSacco.setValue(saccoDetails);
                        childRefTime.setValue(currentDateandTime);
                        childRefLocation.setValue("Latitude: " + latitude +
                                ", Longitude: " + longitude);
                        childRefSpeed.setValue(speed);
                        // reff.child(String.valueOf(reportId+1)).setValue("Reports");
                        Toast.makeText(Main2Activity.this, "Details successfully captured", Toast.LENGTH_SHORT).show();

                        boolean isInserted = mydb.insertData(regNoDetails, saccoDetails, currentDateandTime, latitude + ";" + longitude, speed);
                        if (isInserted = true)
                            Toast.makeText(Main2Activity.this, "Data recorded locally", Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(Main2Activity.this, "Data not recorded locally", Toast.LENGTH_LONG).show();

                    }
                });

            }

        }

    }

    private void sendMessage() {
        final ProgressDialog dialog = new ProgressDialog(Main2Activity.this);
        dialog.setTitle("Sending Report");
        dialog.setMessage("Please wait");
        dialog.show();

        final String speedingLocation = latitude + ";" + longitude;
        final String message = "Vehicle " + vehicle + " belonging to " + sacco +  " Sacco has been reported for speeding at " + speed + " KM/H in this location[" + speedingLocation + "] at " + currentDateandTime;

        Thread sender = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    MailSender sender = new MailSender("testzusha@gmail.com", "PlainPassword");
                    sender.sendMail("Zusha Report",
                            "" + message,
                            "testzusha@gmail.com",
                            "" + saccoEmail);
                    dialog.dismiss();
                } catch (Exception e) {
                    Log.e("mylog", "Error: " + e.getMessage());
                }
            }
        });
        sender.start();
    }

    public boolean isServicesOK() {
        Log.d(TAG, "isServicesOK: checking google services version");

        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(Main2Activity.this);

        if (available == ConnectionResult.SUCCESS) {
            //everything is fine and user can make map requests
            Log.d(TAG, "isServicesOK: Google Play Services is working");
            return true;
        } else if (GoogleApiAvailability.getInstance().isUserResolvableError(available)) {
            //an error occured but we can resolve it
            Log.d(TAG, "isServicesOK: an error occurred but it can be resolved");
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(Main2Activity.this, available, ERROR_DIALOG_REQUEST);
            dialog.show();
        } else {
            Toast.makeText(this, "You can't make map requests", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    @Override
    public void onLocationChanged(Location location) {
        TextView speedTextView = (TextView) this.findViewById(R.id.speedTextView);
        TextView speedStatusTextView = (TextView) this.findViewById(R.id.speedStatusTextView);

        longitude = location.getLongitude();
        latitude = location.getLatitude();

        // TRACK SPEED
        if (location == null) {

            speedTextView.setText("0");
        } else {
            float currentSpeed = location.getSpeed() * 3.6f;
            speed = Math.round(currentSpeed * 100.0) / 100.0;
            speedTextView.setText(String.format("%.2f", currentSpeed) + "");
            if (currentSpeed > 5) {
                Vibrator vibrator = (Vibrator) getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);
                vibrator.vibrate(1000);
                speedStatusTextView.setText("Over Speeding");
                LatLng speedingLocation = new LatLng(latitude, longitude);
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

    private void updateLocationUI() {
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
                if (location != null) {
                    currentLocation = location;
                    double longitude = currentLocation.getLongitude();
                    double latitude = currentLocation.getLatitude();

                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                            new LatLng(currentLocation.getLatitude(),
                                    currentLocation.getLongitude()), 15));
                }
            }
        });

    }

    private void trackSpeed() {
        LocationManager lm = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        if (lm != null) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        }
        Toast.makeText(this,"Waiting for GPS connection!", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (mMap != null) {
            outState.putParcelable(KEY_CAMERA_POSITION, mMap.getCameraPosition());
//            outState.putParcelable(KEY_LOCATION, mLastKnownLocation);
            outState.putParcelable(KEY_LOCATION, currentLocation);
            super.onSaveInstanceState(outState);
        }
    }

    protected void createLocationRequest() {
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(5000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
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

    }

}
