package com.gta.administrator.infraredcontrol;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
    private Intent intent=new Intent();
    private ProgressDialog progressDialog;

    private NetworkInterface networkInterface;
    private Class jumpActivity;// 跳转到的activity
    private String parameter;  // 跳转到新的activity所携带的参数，如果没有可以转送""空字符串
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

    public void startActivity(final Class activity,String parameter) {
        this.jumpActivity = activity;
        this.parameter=parameter;
        Log.d(TAG, "startActivity: "+parameter);
        // 获取MqttRequest实例
        networkInterface = NetworkRequest.getInstance(mContext);
        // 检查是否处于连接状态

        intent.setClass(mContext, activity);
        Bundle bundle = new Bundle();
        bundle.putString("parameter", parameter);
        intent.putExtras(bundle);
        Log.d(TAG, "startActivity: "+parameter);
        if (networkInterface.isConnected()) {
            mContext.startActivity(intent);
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
            mContext.startActivity(intent);
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
