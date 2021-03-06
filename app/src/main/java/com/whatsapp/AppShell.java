package com.whatsapp;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;

import id.delta.whatsapp.R;
import id.delta.whatsapp.utils.ExceptionHandler;
import id.delta.whatsapp.utils.Keys;
import id.delta.whatsapp.utils.Prefs;
import id.delta.whatsapp.value.Themes;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by DELTA on 10/2/2017.
 */

public class AppShell extends Application {

    public static Context ctx;

    public AppShell() {
        ctx = getApplicationContext();
    }

    public final Context getApplicationContext(){
        return this;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        if(Prefs.getBoolean(Keys.KEY_CUSTOM_FONT, false)){
            CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                    .setDefaultFontPath("fonts/billabong.ttf")
                    .setFontAttrId(R.attr.fontPath)
                    .build());
        }


    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        ExceptionHandler.setCustomException(base);
    }
}
