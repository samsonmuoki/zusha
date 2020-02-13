package com.example.zusha;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.firebase.client.Firebase;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ReportsActivity extends AppCompatActivity {

    private Firebase mRef;
    DatabaseReference reff;
    TextView sampleReportTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports);

        sampleReportTextView = (TextView) findViewById(R.id.sampleReportTextView);
        mRef = new Firebase("https://deep-cascade-240110.firebaseio.com/Reports");
//        reff = FirebaseDatabase.getInstance().getReference().child("Reports").child(String.valueOf(0));
        reff = FirebaseDatabase.getInstance().getReference().child("Reports");

//        mRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                Log.v("E_Value", "Data: "+ dataSnapshot.getValue());
//
//            }
//
//            @Override
//            public void onCancelled(FirebaseError firebaseError) {
//
//            }
//        });


        reff.addValueEventListener(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(@NonNull com.google.firebase.database.DataSnapshot dataSnapshot) {

                String sampleReport = dataSnapshot.getValue().toString();

                sampleReportTextView.setText(sampleReport);

                Log.v("E_Value", "Data: "+ dataSnapshot.getValue());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
