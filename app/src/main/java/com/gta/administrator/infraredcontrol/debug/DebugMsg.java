package com.gta.administrator.infraredcontrol.debug;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.widget.Toast;

/**
 * Created by yanwen on 16/10/22.
 */
public class DebugMsg {
    private static DebugMsg debugMsg;
    private ProgressDialog progressDialog;
    private Activity mContext;

    public static DebugMsg getInstance(Context mContext) {
        if (debugMsg == null) {
            debugMsg = new DebugMsg(mContext);
        }
        return debugMsg;
    }

    public DebugMsg(Context mContext) {
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
