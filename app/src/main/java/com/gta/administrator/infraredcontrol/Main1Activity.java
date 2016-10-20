package com.gta.administrator.infraredcontrol;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

//import com.baidubce.http.DefaultRetryPolicy;
import com.gta.administrator.infraredcontrol.baidu_iot_hub.Baidu_IotHubModule;
import com.gta.administrator.infraredcontrol.baidu_iot_hub.MqttRequest;
import com.gta.administrator.infraredcontrol.bean.NetworkInterface;
import com.gta.administrator.infraredcontrol.other.MyGradLayoutItem;
import com.gta.administrator.infraredcontrol.wifi.WifiUtility;

//import org.slf4j.LoggerFactory;
//import org.slf4j.helpers.SubstituteLoggerFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Main1Activity extends AppCompatActivity implements View.OnClickListener{

    private Toolbar toolbar;
    private Button toolbar_start_btn;
    private TextView toolbar_middle_text;
    private Button toolbar_end_btn;

    private ViewPager my_view_pager;

    private List<Fragment> fragments;

    private MyGradLayoutItem home_button, device_button, set_button;
    private List<View> buttons = new ArrayList<>(3);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar_start_btn = (Button) findViewById(R.id.toolbar_start_btn);
        toolbar_start_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        toolbar_middle_text = (TextView) findViewById(R.id.toolbar_middle_text);
        toolbar_end_btn = (Button) findViewById(R.id.toolbar_end_btn);
        toolbar_end_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Main1Activity.this, MainActivity.class));
            }
        });

//        setSupportActionBar(toolbar);
//        toolbar.inflateMenu(R.menu.menu_main1);
//        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(MenuItem item) {
//                return true;
//            }
//        });
//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(Main1Activity.this, MainActivity.class));
//            }
//        });


        my_view_pager = (ViewPager) findViewById(R.id.my_viewpage);

        fragments = new ArrayList<>();
        fragments.add(new FragmentHome());
        fragments.add(new FragmentDevices());
        fragments.add(new FragmentSet());

        my_view_pager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager()));
        my_view_pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < buttons.size(); ++i) {
                    if (i == position) {
                        buttons.get(position).setSelected(true);
                    } else {
                        buttons.get(i).setSelected(false);
                    }

                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        my_view_pager.setCurrentItem(1);//默认设备页
        home_button = (MyGradLayoutItem) findViewById(R.id.home_button);
        home_button.setOnClickListener(this);
        device_button = (MyGradLayoutItem) findViewById(R.id.device_button);
        device_button.setOnClickListener(this);
        set_button = (MyGradLayoutItem) findViewById(R.id.set_button);
        set_button.setOnClickListener(this);
        buttons.add(home_button);
        buttons.add(device_button);
        buttons.add(set_button);
        buttons.get(1).setSelected(true);




//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                Baidu_IotHubModule module = new Baidu_IotHubModule();
//                module.getEndpointList();
////                new Baidu_IotHubModule().request();
//
//            }
//        }).start();


//        SharedPreferences preferences = getSharedPreferences("device", MODE_PRIVATE);
//        SharedPreferences.Editor editor = preferences.edit();
//        editor.putString("device", "test...");
//        editor.commit();

    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.home_button:
                my_view_pager.setCurrentItem(0);
                break;
            case R.id.device_button:
                my_view_pager.setCurrentItem(1);
                break;
            case R.id.set_button:
                my_view_pager.setCurrentItem(2);
                break;
        }

    }


    private class ViewPagerAdapter extends FragmentPagerAdapter {

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        NetworkInterface networkInterface = MqttRequest.getInstance();
        networkInterface.closeConnect();//关闭Mqtt连接

        WifiUtility.getInstance(getApplicationContext()).finish();
    }
}
