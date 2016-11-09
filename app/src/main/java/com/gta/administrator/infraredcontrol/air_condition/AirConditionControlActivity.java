package com.gta.administrator.infraredcontrol.air_condition;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.TextureView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.gta.administrator.infraredcontrol.NetworkRequest;
import com.gta.administrator.infraredcontrol.R;
import com.gta.administrator.infraredcontrol.baidu_iot_hub.MqttRequest;
import com.gta.administrator.infraredcontrol.bean.NetworkInterface;
import com.gta.administrator.infraredcontrol.infrared_code.AirConditionCode;
import com.gta.administrator.infraredcontrol.mysql.Ir_code;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.sql.SQLException;
import java.util.ArrayList;

public class AirConditionControlActivity extends AppCompatActivity {
    private Context mContext;

    private static final String tag = "AirConditionControlActivity";

    private Button power_switch_button;
    private TextView current_temperature_disp_textview;

    private NetworkInterface networkInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_air_condition_control);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mContext = this;

        initView();
        networkInterface = NetworkRequest.getInstance(mContext);
//        ((MqttRequest) mqttRequest).setCallbackListener(new MqttRequest.MqttCallbackListener() {
//            @Override
//            public void connectionLost(Throwable cause) {
//                mqttRequest.openConnect();//异常断开后重新打开链接
//                Log.d(tag, "connectionLost");
//
//            }
//
//            @Override
//            public void messageArrived(String topic, MqttMessage message) {
//                Log.d(tag, "messageArrived");
//
//            }
//
//            @Override
//            public void deliveryComplete(IMqttDeliveryToken token) {
//                Log.d(tag, "deliveryComplete");
//            }
//
//            @Override
//            public void onSendError() {
//                // 发送消息失败调用此接口
//            }
//        });

    }


    private void initView() {
        power_switch_button = (Button) findViewById(R.id.power_switch_button);
        power_switch_button.setOnClickListener(new ButtonListener());
        current_temperature_disp_textview = (TextView) findViewById(R.id.current_temperature_disp_textview);
     }


    private class ButtonListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.power_switch_button:
                    // 发送开空调电源码
                  //  networkInterface.sendData(AirConditionCode.getOpenCode(), false);
                    new Thread(new Runnable(){
                        ArrayList<Ir_code.Ir_codes> results = new ArrayList<Ir_code.Ir_codes>();
                        public void run(){
                            Ir_code code = new Ir_code();
                            try {
                                results=code.getIr_code("AIR_GELI-01");
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            current_temperature_disp_textview.post(new Runnable(){
                                public void run(){
                                    current_temperature_disp_textview.setText(results.get(1).getIr_code());
                                }
                            });
                        }
                    }).start();
                    break;
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
