package com.gta.administrator.infraredcontrol.setting;

import android.app.AlertDialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.gta.administrator.infraredcontrol.R;
import com.gta.administrator.infraredcontrol.wifi.WifiUtility;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class NetWorkSettingActivity extends AppCompatActivity {
    private static final String TAG = "NetWorkSettingActivity";
    private  Context mContext;

    private ListView my_listview;
    private List<String> wifiList = new ArrayList<>();
    private ArrayAdapter<String> adapter;

    private WifiUtility wifiUtility;
    private Timer timer;
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
                wifiUtility.connectWiFi(curSSID, "");



                String connectedSSID = wifiUtility.getConnectedSSID();

//                Log.d(TAG, "onItemClick: connectedSSID" + connectedSSID);



            }
        });


        wifiUtility = WifiUtility.getInstance(mContext);


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
    }


    private void notifyDataSetChanged() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                adapter.notifyDataSetChanged();

            }
        });
    }
}
