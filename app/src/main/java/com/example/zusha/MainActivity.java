package com.example.zusha;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

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
                TextView vehicleDetailsTextView = (TextView) findViewById(R.id.vehicleDetailsTextView);

                String regNo = regNoEditText.getText().toString();
                String sacco = saccoEditText.getText().toString();
                String driver = driverNameEditText.getText().toString();

                String vehicleDetails = "REG: " + regNo + "\n" + "SACCO:" + sacco + "\n" + "DRIVER:"  + driver;
//                vehicleDetailsTextView.setText(vehicleDetails);

                Intent i = new Intent(MainActivity.this, Main2Activity.class);
                i.putExtra("vehicleDetails", vehicleDetails);
                startActivity(i);
                finish();
            }
        });
    }
}
