package com.gta.administrator.infraredcontrol_apk.air_condition;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.gta.administrator.infraredcontrol_apk.NetworkActivity;
import com.gta.administrator.infraredcontrol_apk.R;

import butterknife.BindView;
import butterknife.ButterKnife;


public class AirConditionControlActivity extends NetworkActivity {

    private static final String TAG = "AirCondition";

    Context mContext;
    TextView air_name;
    ImageButton air_button_MORE_FUN;
    CheckBox power_switch_button;
    TextView air_temp;
    ImageButton mode_button;
    TextView air_mode_type;
    ImageView air_mode_pic;
    ImageButton air_button_wind_speed;                         //风速
    ImageButton air_button_WIND_SWEEP_MAN;                     //风向
    ImageButton air_button_WIND_SWEEP_AUTO;                    //扫风
    ImageButton temp_add_button;
    ImageButton temp_dec_button;
    @BindView(R.id.air_text_wind_speed) TextView airTextWindSpeed;
    @BindView(R.id.air_mode_wind_angle) TextView airModeWindAngle;
    @BindView(R.id.air_button_addhot) ImageButton airButtonAddhot;
    @BindView(R.id.air_mode_wind_to) TextView airModeWindTo;
    @BindView(R.id.air_sleep) ImageButton airSleep;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_air_condition_control_1);
        ButterKnife.bind(this);
        mContext = this;
        initView();
    }

    private void initView() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.mipmap.yk_ctrl_item_menu);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String brand_models = bundle.getString("parameter");
        power_switch_button = (CheckBox) findViewById(R.id.power_switch_button);
        power_switch_button.setOnClickListener(new Air_OnClickListener(brand_models));
        power_switch_button.setOnCheckedChangeListener(new AirPowerOpen_OnClickListener(brand_models));

        temp_add_button = (ImageButton) findViewById(R.id.air_button_TEMP_ADD);
        temp_add_button.setOnClickListener(new Air_OnClickListener(brand_models));
        temp_dec_button = (ImageButton) findViewById(R.id.air_button_TEMP_DEC);
        temp_dec_button.setOnClickListener(new Air_OnClickListener(brand_models));
        air_temp = (TextView) findViewById(R.id.air_temp);
        air_temp.setOnClickListener(new Air_OnClickListener(brand_models));
        air_button_MORE_FUN = (ImageButton) findViewById(R.id.air_button_MORE_FUN);
        air_button_MORE_FUN.setOnClickListener(new Air_OnClickListener(brand_models));
        air_button_wind_speed = (ImageButton) findViewById(R.id.air_button_wind_speed);
        air_button_wind_speed.setOnClickListener(new Air_OnClickListener(brand_models));
        air_button_WIND_SWEEP_MAN = (ImageButton) findViewById(R.id.air_button_WIND_SWEEP_MAN);
        air_button_WIND_SWEEP_MAN.setOnClickListener(new Air_OnClickListener(brand_models));
        air_button_WIND_SWEEP_AUTO = (ImageButton) findViewById(R.id.air_button_WIND_SWEEP_AUTO);
        air_button_WIND_SWEEP_AUTO.setOnClickListener(new Air_OnClickListener(brand_models));
        air_name = (TextView) findViewById(R.id.air_name);
        air_name.setText(brand_models);
        mode_button = (ImageButton) findViewById(R.id.air_mode_switch_button);
        mode_button.setOnClickListener(new Air_OnClickListener(brand_models));
        air_mode_type = (TextView) findViewById(R.id.air_mode_type);
        air_mode_pic = (ImageView) findViewById(R.id.air_mode_pic);
        air_mode_pic.setImageDrawable(getResources().getDrawable(R.mipmap.air_mode_zn));
        airButtonAddhot.setOnClickListener(new Air_OnClickListener(brand_models));
        airSleep.setOnClickListener(new Air_OnClickListener(brand_models));
    }


    class Air_OnClickListener implements View.OnClickListener {
        private String brand_models;

        public Air_OnClickListener(String brand_models) {
            this.brand_models = brand_models;
        }

        public void onClick(View v) {
            int temp;
            switch (v.getId()) {
                case R.id.air_button_TEMP_ADD:
                    Log.d(TAG, "onClick: Brand_models:::" + this.brand_models);
                    temp = Integer.parseInt(air_temp.getText().toString());
                    temp = temp + 1;
                    if (temp > 30) {
                        temp = 30;
                    }
                    if (temp < 16) {
                        temp = 16;
                    }
                    Send_ircode(this.brand_models, "温度" + temp);
                    air_temp.setText(temp + "");
                    break;
                case R.id.air_button_TEMP_DEC:
                    Log.d(TAG, "onClick: Brand_models:::" + this.brand_models);
                    temp = Integer.parseInt(air_temp.getText().toString());
                    temp = temp - 1;
                    if (temp > 30) {
                        temp = 30;
                    }
                    if (temp < 16) {
                        temp = 16;
                    }
                    Send_ircode(this.brand_models, "温度" + temp);
                    air_temp.setText(temp + "");
                    break;
                case R.id.air_mode_switch_button:
                    if (air_mode_type.getText().equals("制冷")) {
                        air_mode_type.setText("自动");
                        air_mode_pic.setImageDrawable(getResources().getDrawable(R.mipmap.air_mode_auto));
                        Send_ircode(this.brand_models, "模式自动");
                        break;
                    }
                    if (air_mode_type.getText().equals("自动")) {
                        air_mode_type.setText("通风");
                        air_mode_pic.setImageDrawable(getResources().getDrawable(R.mipmap.air_mode_tf));
                        Send_ircode(this.brand_models, "模式通风");
                        break;
                    }
                    if (air_mode_type.getText().equals("通风")) {
                        air_mode_type.setText("除湿");
                        air_mode_pic.setImageDrawable(getResources().getDrawable(R.mipmap.air_mode_tf));
                        Send_ircode(this.brand_models, "模式除湿");
                        break;
                    }
                    if (air_mode_type.getText().equals("除湿")) {
                        air_mode_type.setText("制热");
                        air_mode_pic.setImageDrawable(getResources().getDrawable(R.mipmap.air_mode_zr));
                        Send_ircode(this.brand_models, "模式制热");
                        break;
                    }
                    if (air_mode_type.getText().equals("制热")) {
                        air_mode_type.setText("制冷");
                        air_mode_pic.setImageDrawable(getResources().getDrawable(R.mipmap.air_mode_zn));
                        Send_ircode(this.brand_models, "模式制冷");
                        break;
                    }
                case R.id.air_button_wind_speed:
                    if (airTextWindSpeed.getText().equals("风速自动")) {
                        airTextWindSpeed.setText("风速低");
                        Send_ircode(this.brand_models, "风速低");
                        break;
                    }
                    if (airTextWindSpeed.getText().equals("风速低")) {
                        airTextWindSpeed.setText("风速中");
                        Send_ircode(this.brand_models, "风速中");
                        break;
                    }
                    if (airTextWindSpeed.getText().equals("风速中")) {
                        airTextWindSpeed.setText("风速高");
                        Send_ircode(this.brand_models, "风速高");
                        break;
                    }
                    if (airTextWindSpeed.getText().equals("风速高")) {
                        airTextWindSpeed.setText("风速自动");
                        Send_ircode(this.brand_models, "风速自动");
                        break;
                    }
                case R.id.air_button_WIND_SWEEP_AUTO:
                    if (airModeWindAngle.getText().equals("扫风手动")) {
                        airModeWindAngle.setText("扫风自动");
                        Send_ircode(this.brand_models, "扫风自动");
                        break;
                    }
                    if (airModeWindAngle.getText().equals("扫风自动")) {
                        airModeWindAngle.setText("扫风手动");
                        Send_ircode(this.brand_models, "扫风手动");
                        break;
                    }
                case R.id.air_button_WIND_SWEEP_MAN:
                    if (airModeWindTo.getText().equals("风向下")) {
                        airModeWindTo.setText("风向中");
                        Send_ircode(this.brand_models, "风向中");
                        break;
                    }
                    if (airModeWindTo.getText().equals("风向中")) {
                        airModeWindTo.setText("风向上");
                        Send_ircode(this.brand_models, "风向上");
                        break;
                    }
                    if (airModeWindTo.getText().equals("风向上")) {
                        airModeWindTo.setText("风向下");
                        Send_ircode(this.brand_models, "风向下");
                        break;
                    }
                case R.id.air_button_addhot:
                    Send_ircode(this.brand_models, "辅热");
                    break;
                case R.id.air_sleep:
                    Send_ircode(this.brand_models, "睡眠");
                    break;
            }
        }
    }

    private class AirPowerOpen_OnClickListener implements CompoundButton.OnCheckedChangeListener {
        String brand_models;
        public AirPowerOpen_OnClickListener(String brand_models) {
            this.brand_models = brand_models;
        }

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (isChecked) {
                Send_ircode(this.brand_models, "开电源");
            } else {
                Send_ircode(this.brand_models, "关电源");
            }
        }
    }

}

