package com.chiragjn.movieman;

import android.app.Application;
import android.content.Context;

public class MovieManApplication extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
    }


    public static Context getAppContext() {
        return context;
    }
}
