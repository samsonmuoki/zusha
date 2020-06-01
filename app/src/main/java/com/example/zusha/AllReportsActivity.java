package com.example.zusha;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;

public class AllReportsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final String url = getIntent().getExtras().getString("url");

        WebView myWebView = new WebView(AllReportsActivity.this);
        setContentView(myWebView);

        myWebView.loadUrl("https://" + url);
    }
}
