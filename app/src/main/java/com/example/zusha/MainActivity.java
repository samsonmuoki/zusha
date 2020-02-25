package com.example.zusha;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

//    public static final String EXTRA_STRING = ""

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button trackSpeedButton = (Button) findViewById(R.id.trackSpeedButton);
        trackSpeedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent enterVehicleDetails = new Intent(MainActivity.this, EnterVehicleDetailsActivity.class);
                startActivity(enterVehicleDetails);
            }
        });

        Button viewReportsButton = (Button) findViewById(R.id.viewReportButton);
        viewReportsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent reports = new Intent(MainActivity.this, ReportsActivity.class);
                startActivity(reports);
            }
        });
    }
}
