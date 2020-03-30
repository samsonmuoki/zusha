package com.example.zusha;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.Result;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class ScanVehicleQRCode extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    private ZXingScannerView scannerView;
    private TextView scanned_details;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_vehicle_q_r_code);

        scannerView = (ZXingScannerView)findViewById(R.id.zxscan);
        scanned_details = (TextView)findViewById(R.id.scanned_details);

        Dexter.withActivity(this)
                .withPermission(Manifest.permission.CAMERA)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        scannerView.setResultHandler(ScanVehicleQRCode.this);
                        scannerView.startCamera();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        Toast.makeText(ScanVehicleQRCode.this, "Access to camera must be granted", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {

                    }
                })
                .check();
    }

    @Override
    protected void onDestroy() {
        scannerView.stopCamera();

        super.onDestroy();
    }

    @Override
    public void handleResult(Result rawResult) {
//        scanned_details.setText(rawResult.getText());
        Vibrator vibrator = (Vibrator)getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(100);

        String mat_details = rawResult.getText();
        String[] vehicleDetails = mat_details.split(",");
        String regNo = vehicleDetails[0];
        String sacco = vehicleDetails[1];

        Intent trackSpeed = new Intent(ScanVehicleQRCode.this, Main2Activity.class);
        trackSpeed.putExtra("regNoDetails", regNo);
        trackSpeed.putExtra("saccoDetails", sacco);
        startActivity(trackSpeed);

    }
}
