package com.example.zusha;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.firebase.client.Firebase;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ReportsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports);

        final EditText searchVehicleEditText = (EditText) findViewById(R.id.searchVehicleEditText);
        Button searchVehicleButton = (Button) findViewById(R.id.searchVehicleButton);

        searchVehicleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String regno = searchVehicleEditText.getText().toString();
                String url = "zusha.duckdns.org/reports/vehicles/" + regno;

                Intent viewReports = new Intent(ReportsActivity.this, AllReportsActivity.class);
                viewReports.putExtra("url", url);
                startActivity(viewReports);
//                finish();
            }
        });

        final EditText searchSaccoEditText = (EditText) findViewById(R.id.searchSaccoEditText);
        Button searchSaccoButton = (Button) findViewById(R.id.searchSaccoButton);

        searchSaccoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sacco = searchSaccoEditText.getText().toString();
                String url = "zusha.duckdns.org/reports/saccos/" + sacco;

                Intent viewReports = new Intent(ReportsActivity.this, AllReportsActivity.class);
                viewReports.putExtra("url", url);
                startActivity(viewReports);
            }
        });

        CardView allReportsCard = (CardView) findViewById(R.id.allReportsCard);
        allReportsCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "zusha.duckdns.org";
                Intent viewReports = new Intent(ReportsActivity.this, AllReportsActivity.class);
                viewReports.putExtra("url", url);
                startActivity(viewReports);
            }
        });
    }
}
