package com.gta.administrator.infraredcontrol.bulb.bulb_brands;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.gta.administrator.infraredcontrol.R;
import com.gta.administrator.infraredcontrol.baidu_iot_hub.MqttRequest;
import com.gta.administrator.infraredcontrol.infrared_code.BulbCode;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class XiaoZhiBrandsActivity extends AppCompatActivity implements View.OnClickListener {

    private Button open_btn;
    private Button close_btn;
    private Button light_source_btn;
    private Button color_temp_plus_btn;
    private Button color_temp_reduce_btn;
    private Button section_btn;
    private ImageButton tv_up_btn;
    private ImageButton tv_down_btn;
    private ImageButton tv_left_btn;
    private ImageButton tv_right_btn;
    private ImageButton tv_ok_btn;
    private MqttRequest mqttRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xiao_zhi_brands);

        initView();

        mqttRequest = MqttRequest.getInstance();
        mqttRequest.setCallbackListener(new MqttRequest.MqttCallbackListener() {
            @Override
            public void connectionLost(Throwable cause) {

            }

            @Override
            public void messageArrived(String topic, MqttMessage message) {

            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {
                showErrorMsg("发送成功");
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!mqttRequest.isConnected()) {
            mqttRequest.openConnect();
        }


    }

    private void initView() {
        open_btn = (Button) findViewById(R.id.open_btn);
        open_btn.setOnClickListener(this);
        close_btn = (Button) findViewById(R.id.close_btn);
        close_btn.setOnClickListener(this);
        tv_up_btn = (ImageButton) findViewById(R.id.tv_up_btn);
        tv_up_btn.setOnClickListener(this);
        tv_down_btn = (ImageButton) findViewById(R.id.tv_down_btn);
        tv_down_btn.setOnClickListener(this);
        tv_left_btn = (ImageButton) findViewById(R.id.tv_left_btn);
        tv_left_btn.setOnClickListener(this);
        tv_right_btn = (ImageButton) findViewById(R.id.tv_right_btn);
        tv_right_btn.setOnClickListener(this);
        tv_ok_btn = (ImageButton) findViewById(R.id.tv_ok_btn);
        tv_ok_btn.setOnClickListener(this);


        light_source_btn = (Button) findViewById(R.id.light_source_btn);
        light_source_btn.setOnClickListener(this);

        color_temp_reduce_btn = (Button) findViewById(R.id.color_temp_reduce_btn);
        color_temp_reduce_btn.setOnClickListener(this);
        color_temp_plus_btn = (Button) findViewById(R.id.color_temp_plus_btn);
        color_temp_plus_btn.setOnClickListener(this);
        section_btn = (Button) findViewById(R.id.section_btn);
        section_btn.setOnClickListener(this);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
//            Intent intent = new Intent();
            setResult(2);
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.open_btn:
                mqttRequest.publishMessage(BulbCode.getOpenCode(), new MqttRequest.MqttPublishListener() {
                    @Override
                    public void onError() {
                        showErrorMsg("请检查网络连接");

                    }
                });
                break;
            case R.id.close_btn:
                mqttRequest.publishMessage(BulbCode.getCloseCode(),new MqttRequest.MqttPublishListener() {
                    @Override
                    public void onError() {
                        showErrorMsg("请检查网络连接");

                    }
                });

                break;
            case R.id.tv_up_btn:
                mqttRequest.publishMessage(BulbCode.getUpCode(),new MqttRequest.MqttPublishListener() {
                    @Override
                    public void onError() {
                        showErrorMsg("请检查网络连接");

                    }
                });
                break;
            case R.id.tv_down_btn:
                mqttRequest.publishMessage(BulbCode.getDownCode(),new MqttRequest.MqttPublishListener() {
                    @Override
                    public void onError() {
                        showErrorMsg("请检查网络连接");

                    }
                });

                break;
            case R.id.tv_left_btn:
                mqttRequest.publishMessage(BulbCode.getLeftCode(),new MqttRequest.MqttPublishListener() {
                    @Override
                    public void onError() {
                        showErrorMsg("请检查网络连接");

                    }
                });
                break;
            case R.id.tv_right_btn:
                mqttRequest.publishMessage(BulbCode.getRightCode(),new MqttRequest.MqttPublishListener() {
                    @Override
                    public void onError() {
                        showErrorMsg("请检查网络连接");

                    }
                });

                break;
            case R.id.tv_ok_btn:
                mqttRequest.publishMessage(BulbCode.getBrightessCode(),new MqttRequest.MqttPublishListener() {
                    @Override
                    public void onError() {
                        showErrorMsg("请检查网络连接");

                    }
                });
                break;

            case R.id.light_source_btn:
                mqttRequest.publishMessage(BulbCode.getLightSourceCode(),new MqttRequest.MqttPublishListener() {
                    @Override
                    public void onError() {
                        showErrorMsg("请检查网络连接");

                    }
                });
                break;

            case R.id.color_temp_reduce_btn:
                mqttRequest.publishMessage(BulbCode.getColorTempReduceCode(),new MqttRequest.MqttPublishListener() {
                    @Override
                    public void onError() {
                        showErrorMsg("请检查网络连接");

                    }
                });
                break;
            case R.id.color_temp_plus_btn:
                mqttRequest.publishMessage(BulbCode.getColorTempPlusCode(),new MqttRequest.MqttPublishListener() {
                    @Override
                    public void onError() {
                        showErrorMsg("请检查网络连接");

                    }
                });
                break;
            case R.id.section_btn:
                mqttRequest.publishMessage(BulbCode.getSectionCode(),new MqttRequest.MqttPublishListener() {
                    @Override
                    public void onError() {
                        showErrorMsg("请检查网络连接");
                    }
                });
                break;
        }
    }

    
    private void showErrorMsg(final String msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(XiaoZhiBrandsActivity.this, msg, Toast.LENGTH_SHORT).show();
            }
        });
    }

}
