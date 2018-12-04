package com.gta.administrator.infraredcontrol.bean;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 * Created by Administrator on 2016/10/20.
 */
public interface NetworkInterface {

    void openConnect();

    void closeConnect();

    void sendData(String data, boolean isReceived);

    boolean isConnected();

//    void receiveData();

    /**
     * 数据接收和发送监听接口
     */
    void setCallbackListener(CallbackListener callbackListener);
    interface CallbackListener {
        void connectionLost(Throwable cause);

        void messageArrived(String topic, MqttMessage message);

        void deliveryComplete(IMqttDeliveryToken token);

        void onSendError(); //publish发送失败


        void socketReceiveData(String data);
//        void socketSendData(String data);
    }


    /**
     * 连接网络时，连接成功或者失败状态的监听接口
     * @param callbackConnectListener
     */
    void setCallbackConnectListener(CallbackConnectListener callbackConnectListener);
    interface CallbackConnectListener {

        void onStartConn();  //连接刚刚启动时调用

        void onSuccess();   // 连接成功后调用

        void onFaild();   //因网络问题连接失败后调用
    }

}
