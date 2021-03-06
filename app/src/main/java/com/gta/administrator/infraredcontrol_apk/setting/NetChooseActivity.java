package com.gta.administrator.infraredcontrol_apk.setting;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;

import com.gta.administrator.infraredcontrol_apk.Main1Activity;
import com.gta.administrator.infraredcontrol_apk.socket.SocketUlitity;
import com.gta.administrator.infraredcontrol_apk.R;

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

                SharedPreferences sharedPreferences = getSharedPreferences(NetConfig.CONFIGURATION, MODE_PRIVATE);
                String routerSSID = sharedPreferences.getString(NetConfig.KEY_ROUTER_SSID, "");
                String routerPWD = sharedPreferences.getString(NetConfig.KEY_ROUTER_PWD, "");
                String esp8266SSID = sharedPreferences.getString(NetConfig.KEY_ESP8266_SSID, "");
                if (preferences.getInt(NET_TYPE_KEY, NET_TYPE_INTERNET) == NET_TYPE_INTERNET) {
                    if (routerSSID != null && routerPWD != null) {
                        Main1Activity.wifiUtility.connectWiFi(routerSSID, routerPWD);
                    }
                }else if (preferences.getInt(NET_TYPE_KEY, NET_TYPE_INTERNET) == NET_TYPE_LOCAL_INTERNET) {
                    // 如果是局域网连接，则连接到8266硬件
                    if (esp8266SSID != null) {
                        Main1Activity.wifiUtility.connectWiFi(esp8266SSID, SocketUlitity.ESP8266_PWD);
                    }
                }
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
