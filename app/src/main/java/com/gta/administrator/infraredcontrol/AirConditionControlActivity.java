package com.gta.administrator.infraredcontrol;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.gta.administrator.infraredcontrol.baidu_iot_hub.MqttRequest;
import com.gta.administrator.infraredcontrol.bean.NetworkInterface;
import com.gta.administrator.infraredcontrol.infrared_code.AirConditionCode;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class AirConditionControlActivity extends AppCompatActivity {

    private static final String tag = "AirConditionControlActivity";

    private Button power_switch_button;

    private NetworkInterface mqttRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_air_condition_control);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initView();
        mqttRequest = MqttRequest.getInstance();
        ((MqttRequest) mqttRequest).setCallbackListener(new MqttRequest.MqttCallbackListener() {
            @Override
            public void connectionLost(Throwable cause) {
                mqttRequest.openConnect();//异常断开后重新打开链接
                Log.d(tag, "connectionLost");

            }

            @Override
            public void messageArrived(String topic, MqttMessage message) {
                Log.d(tag, "messageArrived");

            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {
                Log.d(tag, "deliveryComplete");
            }

            @Override
            public void onSendError() {
                // 发送消息失败调用此接口
            }
        });

    }


    private void initView() {
        power_switch_button = (Button) findViewById(R.id.power_switch_button);
        power_switch_button.setOnClickListener(new ButtonListener());

    }


    private class ButtonListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.power_switch_button:
                    // 发送开空调电源码
//                    mqttRequest.publishMessage(AirConditionCode.getOpenCode(),null);
                    mqttRequest.sendData(AirConditionCode.getOpenCode());

                    break;
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        mqttRequest.closeConnect();
    }
}
