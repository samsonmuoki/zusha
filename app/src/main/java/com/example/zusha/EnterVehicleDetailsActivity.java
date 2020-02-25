package com.example.zusha;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class EnterVehicleDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_vehicle_details);

        Button enterVehicleDetailsButton = (Button) findViewById(R.id.enterVehicleDetailsButton);
        enterVehicleDetailsButton.setOnClickListener(new View.OnClickListener() {
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

                Intent trackSpeed = new Intent(EnterVehicleDetailsActivity.this, Main2Activity.class);
//                Intent trackSpeed = new Intent(EnterVehicleDetailsActivity.this, TrackSpeedActivity.class);
                trackSpeed.putExtra("regNoDetails", regNoDetails);
                trackSpeed.putExtra("saccoDetails", saccoDetails);
                trackSpeed.putExtra("driverDetails", driverDetails);
                startActivity(trackSpeed);
                finish();

            }
        });
    }
}
