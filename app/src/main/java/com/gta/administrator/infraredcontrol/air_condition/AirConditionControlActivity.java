package com.gta.administrator.infraredcontrol.air_condition;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;


import com.gta.administrator.infraredcontrol.NetworkActivity;
import com.gta.administrator.infraredcontrol.R;
import com.gta.administrator.infraredcontrol.infrared_code.AirConditionCode;



public class AirConditionControlActivity extends NetworkActivity {

    private static final String TAG = "AirCondition";
    private Context mContext;
    private TextView air_name;
    private Button air_button_MORE_FUN;
    private Button power_switch_button;
    private TextView air_temp;
    protected Button mode_button;
    private TextView air_mode_type;
    private ImageView air_mode_pic;
    //  protected Button wind_speed_button;
    //  protected Button wind_to_button;
    //  protected Button wind_sweep_button;
    private ImageButton temp_add_button;
    private ImageButton temp_dec_button;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_air_condition_control);
        mContext = this;
        initView();
    }
    private void initView() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        String brand_models=bundle.getString("parameter");
        power_switch_button = (Button) findViewById(R.id.power_switch_button);
        power_switch_button.setOnClickListener(new Air_OnClickListener(brand_models));
        temp_add_button = (ImageButton) findViewById(R.id.air_button_TEMP_ADD);
        temp_add_button.setOnClickListener(new Air_OnClickListener(brand_models));
        temp_dec_button = (ImageButton) findViewById(R.id.air_button_TEMP_DEC);
        temp_dec_button.setOnClickListener(new Air_OnClickListener(brand_models));
        air_temp = (TextView) findViewById(R.id.air_temp);
        air_temp.setOnClickListener(new Air_OnClickListener(brand_models));
        air_button_MORE_FUN = (Button) findViewById(R.id.air_button_MORE_FUN);
        air_button_MORE_FUN.setOnClickListener(new Air_OnClickListener(brand_models));
        air_name = (TextView) findViewById(R.id.air_name);
        air_name.setText(brand_models);
        mode_button= (Button) findViewById(R.id.air_mode_switch_button);
        mode_button.setOnClickListener(new Air_OnClickListener(brand_models));
        air_mode_type = (TextView) findViewById(R.id.air_mode_type);
        air_mode_pic= (ImageView) findViewById(R.id.air_mode_pic);
        air_mode_pic.setImageDrawable(getResources().getDrawable(R.mipmap.air_mode_zn));
           }



class  Air_OnClickListener implements View.OnClickListener{
    private  String brand_models;
    public Air_OnClickListener(String  brand_models) {
       this.brand_models=brand_models;
    }
    public void onClick(View v) {
        int temp;
        switch (v.getId()) {
            case R.id.power_switch_button:
                if (power_switch_button.getText().equals("开启"))
                {
                    networkInterface.sendData(AirConditionCode.get_ir_Code(AirConditionCode.POWER_ON), true);
                    power_switch_button.setText("关闭");
                    power_switch_button.setTextColor(Color.BLACK);
                }
                else
                {
                    networkInterface.sendData(AirConditionCode.get_ir_Code(AirConditionCode.POWER_OFF), true);
                    power_switch_button.setText("开启");
                    power_switch_button.setTextColor(Color.RED);
                }
                break;
            case R.id.air_button_TEMP_ADD:
                Log.d(TAG, "onClick: Brand_models:::"+this.brand_models);
                temp = Integer.parseInt(air_temp.getText().toString());
                temp=temp+1;
                if(temp>30){
                    temp=30;
                }
                if (temp<16) {
                    temp=16;
                }
                Send_ircode(this.brand_models,"温度"+temp);
                air_temp.setText(temp+"");
                break;
            case R.id.air_button_TEMP_DEC:
                Log.d(TAG, "onClick: Brand_models:::"+this.brand_models);
                temp = Integer.parseInt(air_temp.getText().toString());
                temp=temp-1;
                if(temp>30){
                    temp=30;
                }
                if (temp<16) {
                    temp=16;
                }
                Send_ircode(this.brand_models,"温度"+temp);
                air_temp.setText(temp+"");
                break;
            case R.id.air_mode_switch_button:
                if (air_mode_type.getText().equals("制冷")) {
                    air_mode_type.setText("自动");
                    air_mode_pic.setImageDrawable(getResources().getDrawable(R.mipmap.air_mode_auto));
                    Send_ircode(this.brand_models,"模式自动");
                    break;
                }
                if (air_mode_type.getText().equals("自动")) {
                    air_mode_type.setText("通风");
                    air_mode_pic.setImageDrawable(getResources().getDrawable(R.mipmap.air_mode_tf));
                    Send_ircode(this.brand_models,"模式通风");
                    break;
                }
                if (air_mode_type.getText().equals("通风")) {
                    air_mode_type.setText("除湿");
                    air_mode_pic.setImageDrawable(getResources().getDrawable(R.mipmap.air_mode_tf));
                    Send_ircode(this.brand_models,"模式除湿");
                    break;
                }
                if (air_mode_type.getText().equals("除湿")) {
                    air_mode_type.setText("制热");
                    air_mode_pic.setImageDrawable(getResources().getDrawable(R.mipmap.air_mode_zr));
                    Send_ircode(this.brand_models,"模式制热");
                    break;
                }
                if (air_mode_type.getText().equals("制热")) {
                    air_mode_type.setText("制冷");
                    air_mode_pic.setImageDrawable(getResources().getDrawable(R.mipmap.air_mode_zn));
                    Send_ircode(this.brand_models,"模式制冷");
                    break;
                }
                break;
        }
    }
}
}

