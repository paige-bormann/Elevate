package com.example.elevate;

import android.app.Application;

import timber.log.Timber;

public class ElevateApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
    }
}

