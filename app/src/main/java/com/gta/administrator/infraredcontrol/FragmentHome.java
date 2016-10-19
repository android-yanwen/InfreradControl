package com.gta.administrator.infraredcontrol;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.gta.administrator.infraredcontrol.socket.SocketUlitity;
import com.gta.administrator.infraredcontrol.wifi.WifiUtility;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yanwen on 16/10/4.
 */
public class FragmentHome extends Fragment {

    private Button open_btn;
    private Button close_btn;
    private Button send_btn;
    private Button scan_btn;
    private ListView my_listview;
    private ArrayAdapter<String> adapter;
    private List<String> list = new ArrayList<>();


    WifiUtility wifiUtility;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        open_btn = (Button) view.findViewById(R.id.open_wifi_btn);
        open_btn.setOnClickListener(new ButtonClickedListener());
        close_btn = (Button) view.findViewById(R.id.close_wifi_btn);
        close_btn.setOnClickListener(new ButtonClickedListener());
        send_btn = (Button) view.findViewById(R.id.send_btn);
        send_btn.setOnClickListener(new ButtonClickedListener());
        scan_btn = (Button) view.findViewById(R.id.scan_wifi_btn);
        scan_btn.setOnClickListener(new ButtonClickedListener());

        wifiUtility =  WifiUtility.getInstance(getActivity());


        my_listview = (ListView) view.findViewById(R.id.my_listview);
        adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_expandable_list_item_1, list);
        my_listview.setAdapter(adapter);

        my_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getActivity());
                builder.setTitle("连接到Wifi:" + list.get(position));
                LinearLayout linearLayout = new LinearLayout(getActivity());
                linearLayout.setLayoutParams(new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT));
                linearLayout.setOrientation(LinearLayout.VERTICAL);
                final EditText text = new EditText(getActivity());
                text.setHint("输入SSID");
                final EditText text1 = new EditText(getActivity());
                text1.setHint("输入密码");
                linearLayout.addView(text);
                linearLayout.addView(text1);
                builder.setView(linearLayout);
                builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        wifiUtility.connectWiFi(text.getText().toString(), text1.getText().toString());
                    }
                });
                builder.show();
            }
        });

        return view;
    }


    private class ButtonClickedListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {

            switch (v.getId()) {
                case R.id.open_wifi_btn:
                    wifiUtility.openWifi();

                    break;
                case R.id.close_wifi_btn:
                    wifiUtility.closeWifi();
                    break;
                case R.id.send_btn:

                    SocketUlitity socketUlitity = SocketUlitity.getInstance();
                    socketUlitity.write("hello socket");


                    break;

                case R.id.scan_wifi_btn:

                    wifiUtility.startScanWifi();

                    list.clear();
                    for (String ssid : wifiUtility.getSSIDList()) {
                        list.add(ssid);
                    }
                    adapter.notifyDataSetChanged();


                    break;
            }
        }
    }


}
