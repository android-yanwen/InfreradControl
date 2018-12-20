package com.gta.administrator.infraredcontrol_apk;

import android.content.Context;
import android.content.SharedPreferences;

import com.gta.administrator.infraredcontrol_apk.baidu_iot_hub.MqttRequest;
import com.gta.administrator.infraredcontrol_apk.bean.NetworkInterface;
import com.gta.administrator.infraredcontrol_apk.setting.NetChooseActivity;
import com.gta.administrator.infraredcontrol_apk.socket.SocketUlitity;

/**
 * Created by yanwen on 16/10/23.
 */
public class NetworkRequest {
    private static NetworkInterface networkInterface;
    private static Context mContext;


    public static NetworkInterface getInstance(Context mContext) {
        SharedPreferences preferences = mContext.getSharedPreferences("device", Context.MODE_PRIVATE);
        int type = preferences.getInt(NetChooseActivity.NET_TYPE_KEY, NetChooseActivity.NET_TYPE_INTERNET);
        if (type == NetChooseActivity.NET_TYPE_INTERNET) {
            networkInterface = MqttRequest.getInstance();
        } else if (type == NetChooseActivity.NET_TYPE_LOCAL_INTERNET) {
            networkInterface = SocketUlitity.getInstance();
        }
        return networkInterface;
    }


//    public void setCallbackListener(NetworkInterface.CallbackListener callbackListener) {
//        networkInterface.setCallbackListener(callbackListener);
//    }


}
