package com.example.zusha;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class SingleLocalReportActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_local_report);

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

        vehicleTextView.setText(regNo);
//        saccoTextView.setText(sacco);
//        driverTextView.setText(driver);
//        dateTextView.setText(date);



    }
}
