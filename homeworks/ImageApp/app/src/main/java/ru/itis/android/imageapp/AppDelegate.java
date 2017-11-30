package ru.itis.android.imageapp;

import android.app.Application;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by Users on 20.11.2017.
 */

public class AppDelegate extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Roboto-BoldCondensed.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
    }
}
