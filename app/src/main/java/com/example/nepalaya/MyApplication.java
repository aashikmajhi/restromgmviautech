package com.example.nepalaya;

import android.app.Application;

import com.facebook.stetho.Stetho;

public class MyApplication extends Application {

    public void onCreate() {
        super.onCreate();
        if(BuildConfig.DEBUG) {
            Stetho.initializeWithDefaults(this);
        }
    }
}
