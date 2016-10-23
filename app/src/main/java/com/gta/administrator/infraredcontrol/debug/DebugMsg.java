package com.gta.administrator.infraredcontrol.debug;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

/**
 * Created by yanwen on 16/10/22.
 */
public class DebugMsg {

    /**
     * 显示提示信息
     * @param msg
     */
    public static void showToast(final Activity context, final String msg) {
        context.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(context, "收到数据：" + msg, Toast.LENGTH_SHORT).show();
            }
        });
    }


}
