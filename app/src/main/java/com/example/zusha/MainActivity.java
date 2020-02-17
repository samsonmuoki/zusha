package com.example.zusha;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

//    public static final String EXTRA_STRING = ""

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button vehicleDetailsButton = (Button) findViewById(R.id.vehicleDetailsButton);
        vehicleDetailsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText regNoEditText = (EditText) findViewById(R.id.regNoEditText);
                EditText saccoEditText = (EditText) findViewById(R.id.saccoEditText);
                EditText driverNameEditText = (EditText) findViewById(R.id.driverNameEditText);

                String regNo = regNoEditText.getText().toString();
                String sacco = saccoEditText.getText().toString();
                String driver = driverNameEditText.getText().toString();

                String regNoDetails = regNo;
                String saccoDetails = sacco;
                String driverDetails = driver;

                Intent i = new Intent(MainActivity.this, Main2Activity.class);
                i.putExtra("regNoDetails", regNoDetails);
                i.putExtra("saccoDetails", saccoDetails);
                i.putExtra("driverDetails", driverDetails);
                startActivity(i);
                finish();
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
