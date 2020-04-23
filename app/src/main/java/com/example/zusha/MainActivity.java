package com.example.zusha;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class MainActivity extends AppCompatActivity {

//    public static final String EXTRA_STRING = ""

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Button trackSpeedButton = (Button) findViewById(R.id.trackSpeedButton);
//        trackSpeedButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent enterVehicleDetails = new Intent(MainActivity.this, EnterVehicleDetailsActivity.class);
//                startActivity(enterVehicleDetails);
//            }
//        });
//        Button trackSpeedButton = (Button) findViewById(R.id.trackSpeedButton);
//        trackSpeedButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                Intent scanvehicledetails = new Intent(MainActivity.this, ScanVehicleDetailsActivity.class);
////                startActivity(scanvehicledetails);
//                Intent captureVehicleDetails = new Intent(MainActivity.this, ScanVehicleQRCode.class);
//                startActivity(captureVehicleDetails);
//            }
//        });
//
//        Button viewReportsButton = (Button) findViewById(R.id.viewReportButton);
//        viewReportsButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent reports = new Intent(MainActivity.this, ReportsActivity.class);
//                startActivity(reports);
//            }
//        });
//        ImageView trackSpeedImage = (ImageView)findViewById(R.id.trackSpeedImage);
//        trackSpeedImage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent enterVehicleDetails = new Intent(MainActivity.this, ScanVehicleQRCode.class);
//                startActivity(enterVehicleDetails);
//            }
//        });
//        ImageView viewReportsImage = (ImageView)findViewById(R.id.viewReportsImage);
//        viewReportsImage.setOnClickListener((new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent reports = new Intent(MainActivity.this, ReportsActivity.class);
//                startActivity(reports);
//            }
//        }));

        CardView trackSpeedCard = (CardView)findViewById(R.id.trackSpeedCard);
        trackSpeedCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent enterVehicleDetails = new Intent(MainActivity.this, ScanVehicleQRCode.class);
                startActivity(enterVehicleDetails);
            }
        });
        CardView viewReportsCard = (CardView)findViewById(R.id.viewReportsCard);
        viewReportsCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent reports = new Intent(MainActivity.this, ReportsActivity.class);
                Intent reports = new Intent(MainActivity.this, AllReportsActivity.class);
                startActivity(reports);
            }
        });

    }
}
