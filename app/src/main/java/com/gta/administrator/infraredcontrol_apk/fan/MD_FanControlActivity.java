package com.gta.administrator.infraredcontrol_apk.fan;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.gta.administrator.infraredcontrol_apk.NetworkRequest;
import com.gta.administrator.infraredcontrol_apk.bean.NetworkInterface;
import com.gta.administrator.infraredcontrol_apk.infrared_code.FanCode;
import com.gta.administrator.infraredcontrol_apk.R;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class MD_FanControlActivity extends AppCompatActivity {
    private Context mContext;
    private Button on_off_switch_btn;
    private NetworkInterface networkInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_md__fan_control);
        mContext = this;

        networkInterface = NetworkRequest.getInstance(mContext);
        networkInterface.setCallbackListener(new NetworkInterface.CallbackListener() {
            @Override
            public void connectionLost(Throwable cause) {

            }

            @Override
            public void messageArrived(String topic, MqttMessage message) {

            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {

            }

            @Override
            public void onSendError() {

            }

            @Override
            public void socketReceiveData(String data) {

            }
        });
//        ((MqttRequest) mqttRequest).setCallbackListener(new MqttRequest.MqttCallbackListener() {
//            @Override
//            public void connectionLost(Throwable cause) {
//                mqttRequest.openConnect();//异常断开后重新打开链接
////                        Log.d(tag, "connectionLost");
//
//            }
//
//            @Override
//            public void messageArrived(String topic, MqttMessage message) {
////                        Log.d(tag, "messageArrived");
//
//            }
//
//            @Override
//            public void deliveryComplete(IMqttDeliveryToken token) {
////                        Log.d(tag, "deliveryComplete");
//            }
//
//            @Override
//            public void onSendError() {
//
//            }
//        });


        on_off_switch_btn = (Button) findViewById(R.id.on_off_switch_btn);
        on_off_switch_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                networkInterface.sendData(FanCode.getOnFanCode(),false);

            }
        });
    }
}
