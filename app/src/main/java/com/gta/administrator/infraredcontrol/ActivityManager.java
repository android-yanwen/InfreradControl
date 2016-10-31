package com.gta.administrator.infraredcontrol;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.gta.administrator.infraredcontrol.baidu_iot_hub.MqttRequest;
import com.gta.administrator.infraredcontrol.bean.NetworkInterface;

/**
 * Created by yanwen on 16/10/15.
 */
public class ActivityManager {

    private static final String TAG = "ActivityManager";
    private static ActivityManager activityManager;

    private static Context mContext;
    private ProgressDialog progressDialog;

    private NetworkInterface networkInterface;
    private Class jumpActivity;// 跳转到的activity

    private NetworkConnectListener connectListener;

    public static ActivityManager getInstance(Context mContext) {
        ActivityManager.mContext = mContext;
        if (activityManager == null) {
            activityManager = new ActivityManager();
        }
        return activityManager;
    }

    private ActivityManager() {
        connectListener = new NetworkConnectListener();
    }

    public void startActivity(final Class activity) {
        jumpActivity = activity;
        // 获取MqttRequest实例
        networkInterface = NetworkRequest.getInstance(mContext);
        // 检查是否处于连接状态
        if (networkInterface.isConnected()) {
            mContext.startActivity(new Intent(mContext, activity));
        } else {
            // 第一次需要打开连接
            networkInterface.openConnect();
            // 打开连接之后，监听连接过程的状态（连接成功或因网络问题失败）
            networkInterface.setCallbackConnectListener(connectListener);
        }
    }

    /**
     * 显示提示状态框
     */
    private void progressShow() {
        ((Activity) mContext).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressDialog = new ProgressDialog(mContext);
                progressDialog.setMessage("连接中...");
                progressDialog.show();
            }
        });
    }

    /**
     * 销毁提示状态框
     */
    private void progressDismiss() {
        ((Activity)mContext).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (progressDialog != null) {
                    progressDialog.cancel();
                }
            }
        });
    }

    private void showMessge() {
        ((Activity)mContext).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(mContext, "连接失败，请检查网络", Toast.LENGTH_SHORT).show();
            }
        });
    }


    /**
     * 网络连接过程监听
     */
    private class NetworkConnectListener implements NetworkInterface.CallbackConnectListener {

        @Override
        public void onStartConn() {
            Log.d(TAG, "onStartConn: 启动链接");
            progressShow();//提示用户正在连接
        }

        @Override
        public void onSuccess() {
            Log.d(TAG, "onSuccess: 链接成功");
            progressDismiss();
            mContext.startActivity(new Intent(mContext, jumpActivity));
        }

        @Override
        public void onFaild() {
            Log.d(TAG, "onFaild: 链接失败，请检查网络");
            networkInterface.closeConnect();
            progressDismiss();
            showMessge();

        }
    }
}
