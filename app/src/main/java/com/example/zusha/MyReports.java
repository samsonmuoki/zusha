package com.example.zusha;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MyReports extends AppCompatActivity {

    SQLiteDatabaseHelper mydb;
    Button myReportsBtn;
    private ListView listView;
    private static final String TAG = "MyReports";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_reports);
        mydb = new SQLiteDatabaseHelper(this);

        listView = (ListView) findViewById(R.id.listview);

        myReportsBtn = (Button) findViewById(R.id.myReportsBtn);
        viewMyReports();
        populateListView();

    }

    public void populateListView() {
//        Log.d(TAG, )

        Cursor data = mydb.getAllData();
        ArrayList<String> listData = new ArrayList<>();
        while (data.moveToNext()) {
            listData.add(data.getString(0));
//            listData.add(data.getInt(0));
        }
        ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listData);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long id) {
                String caseId = adapterView.getItemAtPosition(i).toString();
//                String caseId = adapterView.getItemAtPosition(i).toString();

                Cursor data = mydb.getReport(caseId);
                int itemID = -1;
                String regNo = "";
                String sacco = "";
                String time = "";
                String location = "";
                String speed = "";
                String driver = "";

                while (data.moveToNext()){
                    itemID = data.getInt(0);
                    regNo = data.getString(1);
                    sacco = data.getString(2);
                    time = data.getString(3);
                    location = data.getString(4);
                    speed = data.getString(5);
                    driver = data.getString(6);

                }
                if(itemID > -1){
                    Log.d(TAG, "onItemClick: The Id is: " + itemID);
                    Intent viewSingleReport = new Intent(MyReports.this, SingleLocalReportActivity.class);
//                    viewSingleReport.putExtra("regNo", itemID);
//                    viewSingleReport.putExtra("regNo", caseId);
                    viewSingleReport.putExtra("regNo", regNo);
                    viewSingleReport.putExtra("sacco", sacco);
                    viewSingleReport.putExtra("driver", driver);
                    viewSingleReport.putExtra("speed", speed);
                    viewSingleReport.putExtra("location", location);
                    viewSingleReport.putExtra("time", time);

                    startActivity(viewSingleReport);
                }
                else{
                    toastMessage("No ID associated with that id");
                }
            }


        });

    }

    private void toastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void viewMyReports() {
        myReportsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor res = mydb.getAllData();

                if(res.getCount() == 0) {
                    showMessage("Error", "No data found!");
                    return;
                }

                StringBuffer buffer = new StringBuffer();
                while (res.moveToNext()) {
                    buffer.append("id: "+ res.getString(0)+ "\n");
                    buffer.append("RegNo: "+ res.getString(1)+ "\n");
                    buffer.append("Sacco: "+ res.getString(2)+ "\n");
//                    Buffer.append("Time: "+ res.getString(3)+ "\n");
//                    Buffer.append("Location: "+ res.getString(4)+ "\n");
//                    Buffer.append("Speed: "+ res.getString(5)+ "\n");
//                    Buffer.append("Driver: "+ res.getString(6)+ "\n");

                }

                showMessage("Data:", buffer.toString());
            }
        });
    }

    public void showMessage(String Title, String Message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(Title);
        builder.setMessage(Message);
        builder.show();
    }
}
