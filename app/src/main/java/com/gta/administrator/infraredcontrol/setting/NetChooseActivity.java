package com.gta.administrator.infraredcontrol.setting;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;

import com.gta.administrator.infraredcontrol.R;
import com.gta.administrator.infraredcontrol.socket.SocketUlitity;

public class NetChooseActivity extends Activity {
    private static final String TAG = "NetChooseActivity";
    public static final String NET_TYPE_KEY = "NetType";
    public static final String PREFERENCE_NAME = "device";
    public static final int NET_TYPE_LOCAL_INTERNET = 0;
    public static final int NET_TYPE_INTERNET = 1;


    private RadioButton internet_control_radio, local_net_control_radio;
    private Button confirm_button;

    SharedPreferences preferences;
    int netType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_net_choose);

        confirm_button = (Button) findViewById(R.id.confirm_button);
        confirm_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = preferences.edit();
                editor.putInt(NET_TYPE_KEY, netType);
                editor.commit();
                finish();
            }
        });
        internet_control_radio = (RadioButton) findViewById(R.id.internet_control_radio);
        internet_control_radio.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                Log.d(TAG, "internet_control_radio: " + isChecked);
                if (isChecked) {
                    netType = NET_TYPE_INTERNET;
                }
            }
        });
        local_net_control_radio = (RadioButton) findViewById(R.id.local_net_control_radio);
        local_net_control_radio.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                Log.d(TAG, "local_net_control_radio: " + isChecked);
                if (isChecked) {
                    netType = NET_TYPE_LOCAL_INTERNET;
                }
            }
        });


        preferences = getSharedPreferences(PREFERENCE_NAME, MODE_PRIVATE);
        netType = preferences.getInt(NET_TYPE_KEY, NET_TYPE_INTERNET);
        //判断是互联网
        if (netType == NET_TYPE_INTERNET) {
            internet_control_radio.setChecked(true);
        } else if (netType == NET_TYPE_LOCAL_INTERNET) {// 判断是局域网
            local_net_control_radio.setChecked(true);
        }

    }


}
