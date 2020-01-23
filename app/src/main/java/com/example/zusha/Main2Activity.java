package com.example.zusha;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        TextView vehicleDetailsTextView = (TextView) findViewById(R.id.vehicleDetailsTextView);

        String vehicleDetails = getIntent().getExtras().getString("vehicleDetails");

        vehicleDetailsTextView.setText(vehicleDetails);
    }
}
