package com.healthmeter.coronameter.javaop;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

public class MyAppContext extends Application {

    @SuppressLint("StaticFieldLeak")
    private static Context context;

    public void onCreate() {
        super.onCreate();
        MyAppContext.context = getApplicationContext();
    }

    public static Context getAppContext() {
        return MyAppContext.context;
    }
}
