package com.gta.administrator.infraredcontrol_apk.debug;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * Created by yanwen on 16/10/22.
 */
public class DebugMsg {
    private static DebugMsg debugMsg;
    private ProgressDialog progressDialog;
    private static Context mContext;

    public static DebugMsg getInstance(Context context) {
        mContext = context;
        if (debugMsg == null) {
            debugMsg = new DebugMsg();
        }
        return debugMsg;
    }


    public DebugMsg() {
        // 进度框
        progressDialog = new ProgressDialog(mContext);
    }



    public void showProgressDialog(String msg) {
        progressDialog.setMessage(msg);
        progressDialog.setCancelable(true);
        progressDialog.show();
    }

    public void dismissProgressDialog() {
        progressDialog.dismiss();
    }




}
