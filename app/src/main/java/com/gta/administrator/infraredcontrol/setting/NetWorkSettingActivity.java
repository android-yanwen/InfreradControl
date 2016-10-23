package com.gta.administrator.infraredcontrol.setting;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.gta.administrator.infraredcontrol.R;
import com.gta.administrator.infraredcontrol.bean.NetworkInterface;
import com.gta.administrator.infraredcontrol.debug.DebugMsg;
import com.gta.administrator.infraredcontrol.infrared_code.WifiCommand;
import com.gta.administrator.infraredcontrol.socket.SocketUlitity;
import com.gta.administrator.infraredcontrol.wifi.WifiUtility;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.zip.Inflater;

public class NetWorkSettingActivity extends AppCompatActivity {
    private static final String TAG = "NetWorkSettingActivity";
    private static Context mContext;

    private ListView my_listview;
    private List<String> wifiList = new ArrayList<>();
    private ArrayAdapter<String> adapter;

    private static String routerSSID;
    private WifiUtility wifiUtility;
    private static SocketUlitity socketUlitity;
    private Timer timer;

    public static final int NET_STATUS_SUCCESS = 1;
    public static final int HARDWARE_RESPONSE_SUCCESS = 2;
    public static Handler myHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_net_work_setting);
        mContext = this;




        my_listview = (ListView) findViewById(R.id.my_listview);

        adapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_expandable_list_item_1, wifiList);
        my_listview.setAdapter(adapter);
        my_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String curSSID = wifiList.get(position);
                // 连接wifi前先关闭socket
                SocketUlitity.getInstance().closeConnect();
                wifiUtility.connectWiFi(curSSID, SocketUlitity.ESP8266_PWD/*"GTA!@2016"*/);

//                Log.d(TAG, "onItemClick: connectedSSID" + connectedSSID);


            }
        });


        // 获得Wi-Fi操作实例
        wifiUtility = WifiUtility.getInstance(mContext);
        // 如果连接到了自家路由器，则获取到路由器的SSID
        routerSSID = wifiUtility.getConnectedSSID();

        wifiUtility.disconnectWifi();



        myHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
//            Log.d(TAG, "handleMessage: " + msg.what);

                // 连接硬件设备成功
                if (msg.what == NET_STATUS_SUCCESS) {

                    // 获得socket通信实例
                    socketUlitity = SocketUlitity.getInstance();
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
                            myHandler.sendEmptyMessage(HARDWARE_RESPONSE_SUCCESS);

                        }
                    });
//                    socketUlitity.setReceiveListener(new SocketUlitity.Receive() {
//                        @Override
//                        public void receive(String val) {
////                Log.d(TAG, "receive: " + val);
//                            // 硬件回应 ID13898501:ir_send is received
//                            myHandler.sendEmptyMessage(HARDWARE_RESPONSE_SUCCESS);
//                        }
//                    });


                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                    builder.setTitle("配置Wi-Fi");
                    LinearLayout linearlayout = new LinearLayout(mContext);
                    LinearLayout.LayoutParams paranms = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT);
                    linearlayout.setLayoutParams(paranms);
                    linearlayout.setOrientation(LinearLayout.VERTICAL);
                    final EditText s_ssid = new EditText(mContext);
                    s_ssid.setHint("Wi-Fi名称");
                    if (routerSSID != null && !routerSSID.equals("")) {
                        s_ssid.setText(routerSSID);
                    }
                    final EditText s_pwd = new EditText(mContext);
                    s_pwd.setHint("Wi-Fi密码");
                    linearlayout.addView(s_ssid);
                    linearlayout.addView(s_pwd);
                    builder.setView(linearlayout);
                    builder.setNegativeButton("发送配置", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String str_ssid = s_ssid.getText().toString();
                            String str_pwd = s_pwd.getText().toString();
                            WifiCommand command = new WifiCommand(str_ssid, str_pwd);
                            String protocal_ssid = command.getSSIDProtocal();
                            String protocal_pwd = command.getPWDProtocal();

                            // 通过socket发送路由器的SSID和密码
                            socketUlitity.sendData(protocal_ssid);
                            socketUlitity.sendData(protocal_pwd);
                            // 发送完命令立马等待回应
//                            socketUlitity.receiveData();
                        }
                    });
                    builder.setCancelable(false);
                    builder.show();
                }
                // 硬件回应Socket成功
                if (msg.what == HARDWARE_RESPONSE_SUCCESS) {
                    Toast.makeText(NetWorkSettingActivity.this, "硬件入网成功", Toast.LENGTH_SHORT).show();
                }
            }
        };
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (timer == null) {
            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
//                Log.d(TAG, "run: timer task");
                    wifiUtility.startScanWifi();

                    wifiList.clear();
                    for (String ssid : wifiUtility.getSSIDList()) {
                        wifiList.add(ssid);
                    }
                    notifyDataSetChanged();

                }
            }, 0, 5000);
        }
    }


    @Override
    protected void onPause() {
        super.onPause();
        timer.cancel();
        timer = null;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        wifiUtility.finish();
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
        if (id == R.id.action_net_choose) {
            startActivity(new Intent(mContext, NetChooseActivity.class));

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_net_config, menu);
        return super.onCreateOptionsMenu(menu);
    }
}
