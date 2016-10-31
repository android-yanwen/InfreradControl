package com.gta.administrator.infraredcontrol.bulb.bulb_brands;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.gta.administrator.infraredcontrol.NetworkRequest;
import com.gta.administrator.infraredcontrol.R;
import com.gta.administrator.infraredcontrol.baidu_iot_hub.MqttRequest;
import com.gta.administrator.infraredcontrol.bean.NetworkInterface;
import com.gta.administrator.infraredcontrol.debug.DebugMsg;
import com.gta.administrator.infraredcontrol.infrared_code.BulbCode;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.io.UnsupportedEncodingException;

public class XiaoZhiBrandsActivity extends AppCompatActivity implements View.OnClickListener {

    private Context mContext;
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
    private NetworkInterface networkInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xiao_zhi_brands);
        mContext = this;

        initView();
        initNetwork();
    }

    private void initNetwork() {
        networkInterface = NetworkRequest.getInstance(mContext);
        networkInterface.setCallbackListener(new NetworkInterface.CallbackListener() {
            @Override
            public void connectionLost(Throwable cause) {
                // 如果连接丢失则重新连接
                networkInterface.openConnect();
            }

            @Override
            public void messageArrived(String topic, MqttMessage message) {

            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {
                Toast.makeText(mContext, "发送成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSendError() {
                Toast.makeText(mContext, "发送失败，请检查网络连接。", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void socketReceiveData(String data) {
                Toast.makeText(mContext, "Socket发送成功", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        networkInterface.openConnect();
    }

    private void initView() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);



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
            setResult(2);
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.open_btn:
                networkInterface.sendData(BulbCode.get_ir_Code(BulbCode.OpenCode), true);
                break;
            case R.id.close_btn:
                networkInterface.sendData(BulbCode.get_ir_Code(BulbCode.CloseCode), true);
                break;
            case R.id.tv_up_btn:
                networkInterface.sendData(BulbCode.get_ir_Code(BulbCode.UpCode), true);
                break;
            case R.id.tv_down_btn:
                networkInterface.sendData(BulbCode.get_ir_Code(BulbCode.DownCode), true);

                break;
            case R.id.tv_left_btn:
                networkInterface.sendData(BulbCode.get_ir_Code(BulbCode.LeftCode), true);
                break;
            case R.id.tv_right_btn:
                networkInterface.sendData(BulbCode.get_ir_Code(BulbCode.RightCode), true);

                break;
            case R.id.tv_ok_btn:
                networkInterface.sendData(BulbCode.get_ir_Code(BulbCode.BrightessCode), true);
                break;

            case R.id.light_source_btn:
                networkInterface.sendData(BulbCode.get_ir_Code(BulbCode.LightSourceCode), true);
                break;

            case R.id.color_temp_reduce_btn:
                networkInterface.sendData(BulbCode.get_ir_Code(BulbCode.ColorTempReduceCode), true);
                break;
            case R.id.color_temp_plus_btn:
                networkInterface.sendData(BulbCode.get_ir_Code(BulbCode.ColorTempPlusCode), true);
                break;
            case R.id.section_btn:
                networkInterface.sendData(BulbCode.get_ir_Code(BulbCode.SectionCode), true);
                break;
        }
    }

    

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
