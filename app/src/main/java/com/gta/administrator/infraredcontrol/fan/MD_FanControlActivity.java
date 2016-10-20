package com.gta.administrator.infraredcontrol.fan;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.gta.administrator.infraredcontrol.R;
import com.gta.administrator.infraredcontrol.baidu_iot_hub.MqttRequest;
import com.gta.administrator.infraredcontrol.bean.NetworkInterface;
import com.gta.administrator.infraredcontrol.infrared_code.AirConditionCode;
import com.gta.administrator.infraredcontrol.infrared_code.FanCode;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class MD_FanControlActivity extends AppCompatActivity {

    private Button on_off_switch_btn;
    private NetworkInterface mqttRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_md__fan_control);

        mqttRequest = MqttRequest.getInstance();
        ((MqttRequest) mqttRequest).setCallbackListener(new MqttRequest.MqttCallbackListener() {
            @Override
            public void connectionLost(Throwable cause) {
                mqttRequest.openConnect();//异常断开后重新打开链接
//                        Log.d(tag, "connectionLost");

            }

            @Override
            public void messageArrived(String topic, MqttMessage message) {
//                        Log.d(tag, "messageArrived");

            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {
//                        Log.d(tag, "deliveryComplete");
            }

            @Override
            public void onSendError() {

            }
        });


        on_off_switch_btn = (Button) findViewById(R.id.on_off_switch_btn);
        on_off_switch_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mqttRequest.sendData(FanCode.getOnFanCode());

            }
        });
    }
}
