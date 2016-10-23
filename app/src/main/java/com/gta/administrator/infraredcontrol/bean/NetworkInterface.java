package com.gta.administrator.infraredcontrol.bean;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 * Created by Administrator on 2016/10/20.
 */
public interface NetworkInterface {

    void openConnect();

    void closeConnect();

    void sendData(String data);

//    void receiveData();

    void setCallbackListener(CallbackListener callbackListener);


    interface CallbackListener {
        void connectionLost(Throwable cause);

        void messageArrived(String topic, MqttMessage message);

        void deliveryComplete(IMqttDeliveryToken token);

        void onSendError(); //publish发送失败


        void socketReceiveData(String data);
//        void socketSendData(String data);
    }


}
