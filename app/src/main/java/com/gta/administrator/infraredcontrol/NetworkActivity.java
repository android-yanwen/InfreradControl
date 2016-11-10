package com.gta.administrator.infraredcontrol;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.gta.administrator.infraredcontrol.NetworkRequest;
import com.gta.administrator.infraredcontrol.air_condition.AirConditionControlActivity;
import com.gta.administrator.infraredcontrol.bean.NetworkInterface;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.io.UnsupportedEncodingException;
import static android.content.ContentValues.TAG;

/**
 * Created by Administrator on 2016/11/10.
 */

public class NetworkActivity  extends AppCompatActivity {
    private Context mContext;
    public NetworkInterface networkInterface;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext=this;
        networkInterface = NetworkRequest.getInstance(mContext);
        networkInterface.setCallbackListener(new NetworkInterface.CallbackListener() {
            @Override
            public void connectionLost(Throwable cause) {
                networkInterface.openConnect();//异常断开后重新打开链接
                Log.d(TAG, "Lost reconnected");
            }
            @Override
            public void messageArrived(String topic, MqttMessage message) {
                String s_data = null;
                try {
                    s_data = new String(message.getPayload(), "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                Log.d(TAG, "messageArrived: " + s_data);
            }
            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {
                toastMsg("发送成功");
//                Toast.makeText(mContext, "发送成功", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onSendError() {
                toastMsg("发送失败请重试");
                networkInterface.openConnect();
//                Toast.makeText(mContext, "发送失败，请检查网络连接。", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void socketReceiveData(String data) {
                Log.d(TAG, "socketReceiveData: " + data);
            }
        });
    }
    private void toastMsg(final String msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
