package com.example.zusha;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


public class SingleLocalReportActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private static final String KEY_CAMERA_POSITION = "camera_position";
    private static final String KEY_LOCATION = "location";
    private CameraPosition mCameraPosition;
    private String[] coordinates;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_local_report);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        TextView saccoTextView = (TextView) findViewById(R.id.saccoTextView);
        TextView vehicleTextView = (TextView) findViewById(R.id.vehicleTextView);
        TextView driverTextView = (TextView) findViewById(R.id.driverTextView);
        TextView dateTextView = (TextView) findViewById(R.id.dateTextView);
        final String regNo = getIntent().getExtras().getString("regNo");
        final String sacco = getIntent().getExtras().getString("sacco");
        final String driver = getIntent().getExtras().getString("driver");
        final String date = getIntent().getExtras().getString("time");
        final String speed = getIntent().getExtras().getString("speed");
        final String location = getIntent().getExtras().getString("location");

        coordinates = location.split(";");

        vehicleTextView.setText(regNo);
        saccoTextView.setText(sacco);
        driverTextView.setText(driver);
        dateTextView.setText(date);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        double latitude = Double.parseDouble(coordinates[0]);
        double longitude = Double.parseDouble(coordinates[1]);

        LatLng myLocation = new LatLng(latitude, longitude);
        googleMap.addMarker(new MarkerOptions().position(myLocation)
                .title("Speeding Location"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(myLocation));
        googleMap.animateCamera(CameraUpdateFactory.newLatLng(myLocation));
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(myLocation, 10));

    }
}
