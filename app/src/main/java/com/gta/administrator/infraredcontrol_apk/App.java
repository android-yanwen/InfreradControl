package com.gta.administrator.infraredcontrol_apk;


import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.lsh.packagelibrary.CasePackageApp;

public class App extends MultiDexApplication {
    //public class App extends CasePackageApp {
    private static App app;

    @Override
    public void onCreate() {
        super.onCreate();
        app = App.this;
    }

    public static Context getContext() {
        return app;
    }


    @Override
    protected void attachBaseContext(Context base)
    {
        super.attachBaseContext(base);
        MultiDex.install(base);
    }
}
