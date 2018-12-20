package com.gta.administrator.infraredcontrol_apk;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

/**
 * Created by yanwen on 16/10/15.
 */
public class ActivityManager {

    private static final String TAG = "ActivityManager";
    private static ActivityManager activityManager;

    private static Context mContext;
    private Intent intent=new Intent();

    private String parameter;


    public static ActivityManager getInstance(Context mContext) {
        ActivityManager.mContext = mContext;
        if (activityManager == null) {
            activityManager = new ActivityManager();
        }
        return activityManager;
    }

    public void startActivity(final Class activity,String parameter) {
        this.parameter=parameter;
        Log.d(TAG, "startActivity: "+parameter);
        intent.setClass(mContext, activity);
        Bundle bundle = new Bundle();
        bundle.putString("parameter", parameter);
        intent.putExtras(bundle);
        Log.d(TAG, "startActivity: "+parameter);
        mContext.startActivity(intent);
    }
}
