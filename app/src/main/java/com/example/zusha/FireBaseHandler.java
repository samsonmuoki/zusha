package com.example.zusha;

import android.app.Application;

import com.firebase.client.Firebase;

public class FireBaseHandler extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Firebase.setAndroidContext(this);
    }
}
