package com.example.zusha;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;

public class AllReportsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_all_reports);
//
//        WebView allReportsWebView = (WebView)findViewById(R.id.allReportsWebVIew);
//
//        allReportsWebView.loadUrl("https://www.youtube.com/");
        WebView myWebView = new WebView(AllReportsActivity.this);
        setContentView(myWebView);

        myWebView.loadUrl("https://www.zusha.duckdns.org");


    }
}
