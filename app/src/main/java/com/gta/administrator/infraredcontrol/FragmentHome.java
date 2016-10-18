package com.gta.administrator.infraredcontrol;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.gta.administrator.infraredcontrol.wifi.WifiUtility;

/**
 * Created by yanwen on 16/10/4.
 */
public class FragmentHome extends Fragment {

    private Button open_btn;
    private Button close_btn;
    private Button conn_btn;
    WifiUtility wifiUtility;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        open_btn = (Button) view.findViewById(R.id.open_wifi_btn);
        open_btn.setOnClickListener(new ButtonClickedListener());
        close_btn = (Button) view.findViewById(R.id.close_wifi_btn);
        close_btn.setOnClickListener(new ButtonClickedListener());
        conn_btn = (Button) view.findViewById(R.id.conn_wifi_btn);
        conn_btn.setOnClickListener(new ButtonClickedListener());
        wifiUtility = new WifiUtility(getActivity());



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
                case R.id.conn_wifi_btn:
                    android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getActivity());
                    builder.setTitle("连接到Wifi");
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


                    break;
            }
        }
    }


}
