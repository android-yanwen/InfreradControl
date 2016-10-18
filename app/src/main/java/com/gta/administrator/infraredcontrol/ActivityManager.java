package com.gta.administrator.infraredcontrol;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.gta.administrator.infraredcontrol.baidu_iot_hub.MqttRequest;

/**
 * Created by yanwen on 16/10/15.
 */
public class ActivityManager {

    private static final String TAG = "ActivityManager";

    private Context mContext;
    private ProgressDialog progressDialog;

    public ActivityManager(Context mContext) {
        this.mContext = mContext;
    }

    public void startActivity(final Class activity) {
        // 获取MqttRequest实例
        final MqttRequest mqttRequest = MqttRequest.getInstance();
        // 检查是否处于连接状态
        if (mqttRequest.isConnected()) {
            mContext.startActivity(new Intent(mContext, activity));
        } else {
            // 第一次需要打开连接
            mqttRequest.openConnect();
            // 打开连接之后，监听连接过程的状态（连接成功或因网络问题失败）
            mqttRequest.setMqttConnectStatusListener(new MqttRequest.MqttConnectStatusListener() {
                @Override
                public void onStartConn() {
                    Log.d(TAG, "onStartConn: 启动链接");
                    progressShow();//提示用户正在连接
                }

                @Override
                public void onSuccess() {
                    Log.d(TAG, "onSuccess: 链接成功");
                    progressDismiss();
                    mContext.startActivity(new Intent(mContext, activity));
                }

                @Override
                public void onFaild() {
                    Log.d(TAG, "onFaild: 链接失败，请检查网络");
                    mqttRequest.closeMqttRequestThis();
                    progressDismiss();
                    showMessge();

                }
            });
        }
    }

    /**
     * 显示提示状态框
     */
    private void progressShow() {
        ((Activity)mContext).runOnUiThread(new Runnable() {
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
                progressDialog.cancel();
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
}
