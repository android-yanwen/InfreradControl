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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

//import com.baidubce.http.DefaultRetryPolicy;
import com.baidubce.BceClientConfiguration;
import com.baidubce.auth.BceV1Signer;
import com.baidubce.auth.DefaultBceCredentials;
import com.baidubce.http.BceHttpClient;
import com.baidubce.http.BceHttpResponse;
import com.baidubce.http.HttpMethodName;
import com.baidubce.http.handler.BceErrorResponseHandler;
import com.baidubce.http.handler.BceJsonResponseHandler;
import com.baidubce.http.handler.HttpResponseHandler;
import com.baidubce.internal.InternalRequest;
import com.baidubce.internal.RestartableInputStream;
import com.baidubce.model.AbstractBceResponse;
import com.gta.administrator.infraredcontrol.baidu_iot_hub.Baidu_IotHubModule;
import com.gta.administrator.infraredcontrol.baidu_iot_hub.Endpoint;
import com.gta.administrator.infraredcontrol.baidu_iot_hub.MqttRequest;
import com.gta.administrator.infraredcontrol.baidu_iot_hub.Thing;
import com.gta.administrator.infraredcontrol.bean.NetworkInterface;
import com.gta.administrator.infraredcontrol.other.MyGradLayoutItem;
import com.gta.administrator.infraredcontrol.wifi.WifiUtility;

//import org.slf4j.LoggerFactory;
//import org.slf4j.helpers.SubstituteLoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class Main1Activity extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = "Main1Activity";

//    private ImageView main_background_image;

    public static WifiUtility wifiUtility;


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
//        main_background_image = (ImageView) findViewById(R.id.main_background_image);

        // 获得Wi-Fi操作实例
        wifiUtility = WifiUtility.getInstance(this);

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
            public void onPageScrolled(int position, float positionOffset, int
                    positionOffsetPixels) {
                Log.d(TAG, position + ":" + positionOffset + ":" + positionOffsetPixels);

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


        // 测试代码
        baidu_api_test();


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

        WifiUtility.getInstance(this).finish();
    }



    void baidu_api_test() {
        final Endpoint module = new Endpoint();
        module.requestEndpoint(HttpMethodName.GET, "", new Endpoint.RequestEndpointListener() {
            @Override
            public void onResponse(StringBuffer result) {
                Log.d(TAG, "onResponse: " + result);
            }
        });
        Thing thing = new Thing();
        thing.requestThing(HttpMethodName.GET, "helloworld", "", new Thing.RequestEndpointListener() {

            @Override
            public void onResponse(StringBuffer result) {
                Log.d(TAG, "onResponse: " + result);
            }
        });


    }
}
