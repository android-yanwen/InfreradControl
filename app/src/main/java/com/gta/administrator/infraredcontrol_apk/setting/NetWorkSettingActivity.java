package com.gta.administrator.infraredcontrol_apk.setting;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.gta.administrator.infraredcontrol_apk.Main1Activity;
import com.gta.administrator.infraredcontrol_apk.NetworkRequest;
import com.gta.administrator.infraredcontrol_apk.bean.NetworkInterface;
import com.gta.administrator.infraredcontrol_apk.infrared_code.WifiCommand;
import com.gta.administrator.infraredcontrol_apk.socket.SocketUlitity;
import com.gta.administrator.infraredcontrol_apk.R;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class NetWorkSettingActivity extends AppCompatActivity {
    private static final String TAG = "NetWorkSettingActivity";
    private static Context mContext;

    private ListView my_listview;
    private List<String> wifiList = new ArrayList<>();
    private ArrayAdapter<String> adapter;

    public static String routerSSID;
    public static String routerPWD;
    public static String hardwareSSID;
    private static SocketUlitity socketUlitity;
    private Timer timer;

    public static final int NET_STATUS_SUCCESS = 1;
    public static final int HARDWARE_RESPONSE_SUCCESS = 2;
    public static Handler myHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_net_work_setting);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("");



        mContext = this;


        my_listview = (ListView) findViewById(R.id.my_listview);

        adapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_expandable_list_item_1, wifiList);
        my_listview.setAdapter(adapter);
        my_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                hardwareSSID = wifiList.get(position);
                // 连接wifi前先关闭socket
                SocketUlitity.getInstance().closeConnect();
                Main1Activity.wifiUtility.connectWiFi(hardwareSSID, SocketUlitity.ESP8266_PWD/*"GTA!@2016"*/);

//                Log.d(TAG, "onItemClick: connectedSSID" + connectedSSID);
            }
        });


        // 如果手机连接到了自家路由器，则获取到路由器的SSID
        routerSSID = Main1Activity.wifiUtility.getConnectedSSID();

//        wifiUtility.disconnectWifi();

        myHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
//            Log.d(TAG, "handleMessage: " + msg.what);

                // 连接硬件设备成功
                if (msg.what == NET_STATUS_SUCCESS) {
                    Toast.makeText(NetWorkSettingActivity.this, "已连接到设备硬件，点击标题栏'发送配置'", Toast.LENGTH_LONG).show();
                }
                // 硬件回应Socket成功
                if (msg.what == HARDWARE_RESPONSE_SUCCESS) {
                    Toast.makeText(NetWorkSettingActivity.this, "硬件入网成功，点击标题栏设置‘网控制方式’", Toast.LENGTH_LONG).show();
                    // 硬件入网成功后选择控制方式
//                    startActivity(new Intent(mContext, NetChooseActivity.class));
//                    NetWorkSettingActivity.this.finish();
                }
            }
        };
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 进入网络设置页面时关闭MQTT
        NetworkRequest.getInstance(mContext).closeConnect();
        // 每隔5s扫描周围Wi-Fi信号
        if (timer == null) {
            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
//                Log.d(TAG, "run: timer task");
                    Main1Activity.wifiUtility.startScanWifi();
                    wifiList.clear();
                    for (String ssid : Main1Activity.wifiUtility.getSSIDList()) {
                        wifiList.add(ssid);//保存8266发出的ssid到list
                    }
                    notifyDataSetChanged();
                }
            }, 0, 2000);
        }
    }


    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        wifiUtility.finish();
        timer.cancel();
        timer = null;
    }


    private void notifyDataSetChanged() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                adapter.notifyDataSetChanged();

            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_net_choose) {//网络访问方式选择
            startActivity(new Intent(mContext, NetChooseActivity.class));
//            finish();

        }else if (id == R.id.action_send_config) {//发送配置
            initSocket();
            AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
            builder.setTitle("配置Wi-Fi");
            View view = LayoutInflater.from(mContext).inflate(R.layout.network_config_dialog, null);
            final EditText s_ssid = (EditText) view.findViewById(R.id.ssid_edit);
            final EditText s_pwd = (EditText) view.findViewById(R.id.password_edit);
            ListView wifi_list = (ListView) view.findViewById(R.id.wifi_list);
            wifi_list.setAdapter(adapter);
            if (routerSSID != null && !routerSSID.equals("")) {
                s_ssid.setText(routerSSID);
            }
            wifi_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    s_ssid.setText(wifiList.get(position));
                }
            });
            builder.setView(view);
            builder.setNegativeButton("发送配置", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String str_ssid = s_ssid.getText().toString();
                    String str_pwd = s_pwd.getText().toString();
                    routerPWD = str_pwd;//保存用户输入的路由器密码
                    // 保存8266的ssid、路由器的ssid、路由器的密码等信息
                    SharedPreferences preferences = getSharedPreferences(NetConfig.CONFIGURATION, MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString(NetConfig.KEY_ESP8266_SSID, hardwareSSID);
                    editor.putString(NetConfig.KEY_ROUTER_SSID, routerSSID);
                    editor.putString(NetConfig.KEY_ROUTER_PWD, routerPWD);
                    editor.commit();

                    WifiCommand command = new WifiCommand(str_ssid, str_pwd);
                    String protocal_ssid = command.getSSIDProtocal();
                    String protocal_pwd = command.getPWDProtocal();

                    // 通过socket发送路由器的SSID和密码
                    socketUlitity.sendData(protocal_ssid, true);
                    socketUlitity.sendData(protocal_pwd, true);
                }
            });
            builder.setCancelable(true);
            builder.show();
        }else if (id == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_net_config, menu);
        return super.onCreateOptionsMenu(menu);
    }


    private void initSocket() {
        // 获得socket通信实例
        socketUlitity = SocketUlitity.getInstance();
        // 设置连接过程状态的监听
        socketUlitity.setCallbackConnectListener(new NetworkInterface.CallbackConnectListener() {
            @Override
            public void onStartConn() {
                Log.d(TAG, "onStartConn: 打开socket");
            }

            @Override
            public void onSuccess() {
                Log.d(TAG, "onSuccess: Socket打开成功");
            }

            @Override
            public void onFaild() {
                Log.d(TAG, "onFaild: Socket打开失败");
                socketUlitity.closeConnect();
            }
        });
        // 打开socket连接
        socketUlitity.openConnect();
        // socket回应监听
        socketUlitity.setCallbackListener(new NetworkInterface.CallbackListener() {
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
                // 硬件回应 ID13898501:ir_send is received
                Log.d(TAG, "socketReceiveData: " + data);
                myHandler.sendEmptyMessage(HARDWARE_RESPONSE_SUCCESS);

            }
        });
    }
}
