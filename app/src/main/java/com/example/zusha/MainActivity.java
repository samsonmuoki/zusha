package com.example.zusha;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
                Intent reports = new Intent(MainActivity.this, ReportsActivity.class);
                startActivity(reports);
            }
        });
        CardView myReportsCard = (CardView) findViewById(R.id.myHistoryCard);
        myReportsCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent viewMyReports = new Intent(MainActivity.this, MyReports.class);
                startActivity(viewMyReports);
            }
        });

    }
}
