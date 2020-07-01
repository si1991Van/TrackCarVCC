package com.vcc.trackcar.ui.base;


import android.app.Application;
import android.content.Context;

import es.dmoral.toasty.Toasty;

/**
 * Created by bacnd4 on 28/02/2020.
 */
public class App extends Application {
    private static App instance;
    public static Context mContext;
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        mContext = getApplicationContext();
    }

    public static App getInstance() {
        return instance;
    }

    public static Context getContext() {
        return mContext;
    }

}
