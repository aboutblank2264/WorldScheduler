package com.aboutblank.worldscheduler;

import android.app.Application;

import net.danlew.android.joda.JodaTimeAndroid;

public class WorldApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        JodaTimeAndroid.init(this);
    }
}
