package com.gta.administrator.infraredcontrol.setting;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.baidubce.services.iothub.model.BaseResponse;
import com.baidubce.services.iothub.model.ListResponse;
import com.baidubce.services.iothub.model.QueryEndpointResponse;
import com.baidubce.services.iothub.model.QueryThingResponse;
import com.gta.administrator.infraredcontrol.Main1Activity;
import com.gta.administrator.infraredcontrol.R;
import com.gta.administrator.infraredcontrol.baidu_iot_hub.Endpoint;
import com.gta.administrator.infraredcontrol.baidu_iot_hub.Thing;

import java.util.ArrayList;
import java.util.List;

public class BindDeviceActivity extends AppCompatActivity {

    private static final String TAG = "BindDeviceActivity";

    private ListView bind_device_list, unbind_device_list;
    private List<String> bindList;
    private ArrayAdapter<String> bindArrayAdapter;
    private List<String> unbindList;
    private ArrayAdapter<String> unbindArrayAdapter;
    private List<String> allEsp8266SSID;
    ListOnItemClickListener onItemClickListener;

    private Endpoint endpoint;
    private EndpointCallbakcListener endpointCallbakcListener;
    private String currentEndpointName;

    private Thing thing;
    private ThingCallbackListener thingCallbackListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bind_device);

        initData();
        initView();
        initWifi();

        endpoint = new Endpoint();
        endpointCallbakcListener = new EndpointCallbakcListener();
        // 查询所有的endpoint
        endpoint.requestEndpoint(Endpoint.Method.LIST_ENDPOINT, null, endpointCallbakcListener);

        thing = new Thing();
        thingCallbackListener = new ThingCallbackListener();
        // 查询endpointName下面所有的thing
        thing.requestThing(Thing.Method.LIST_THING, currentEndpointName, null, thingCallbackListener);
    }

    private void initData() {
        bindList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            bindList.add(i+"");
        }
        unbindList = new ArrayList<>();
        for (int i = 11; i < 20; i++) {
            unbindList.add(i+"");
        }
    }

    private void initView() {
        onItemClickListener = new ListOnItemClickListener();
        bind_device_list = (ListView) findViewById(R.id.bind_device_list);
        bindArrayAdapter = new ArrayAdapter<String>(BindDeviceActivity.this, android.R.layout
                .simple_list_item_1, bindList);
        bind_device_list.setAdapter(bindArrayAdapter);
        bind_device_list.setOnItemClickListener(onItemClickListener);

        unbind_device_list = (ListView) findViewById(R.id.unbind_device_list);
        unbindArrayAdapter = new ArrayAdapter<String>(BindDeviceActivity.this, android.R.layout
                .simple_list_item_1, unbindList);
        unbind_device_list.setAdapter(unbindArrayAdapter);
        unbind_device_list.setOnItemClickListener(onItemClickListener);

    }

    private void initWifi() {
        Main1Activity.wifiUtility.startScanWifi();
        // 获取到所有esp8266发出的ssid
        allEsp8266SSID = Main1Activity.wifiUtility.getEsp8266SSID();
    }

    private class ListOnItemClickListener implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            switch (parent.getId()) {
                case R.id.bind_device_list:
                    Log.i(TAG, "onItemClick: bind_device_list" + position);
                    break;
                case R.id.unbind_device_list:
                    Log.i(TAG, "onItemClick: unbind_device_list"+position);
                    break;
            }
        }
    }


    /**
     * 访问Endpoint后回调的接口
     */
    private class EndpointCallbakcListener implements Endpoint.RequestEndpointCallbakcListener {

        @Override
        public void createEndpointCallback(QueryEndpointResponse response) {
            // 创建Endpoint成功后回调
        }

        @Override
        public void listEndpointCallback(ListResponse response) {
            // 获取所有EndpointName后回调
        }

        @Override
        public void queryEndpointCallback(QueryEndpointResponse response) {
            // 获取指定EndpotName后回调
        }

        @Override
        public void deleteEndpointCallback(BaseResponse request) {
            // 删除Endpoint后回调
        }
    }

    /**
     * 访问Thing后回调的接口
     */
    private class ThingCallbackListener implements Thing.RequestThingCallbakcListener {

        @Override
        public void createThingCallback(QueryThingResponse response) {

        }

        @Override
        public void listThingCallback(ListResponse response) {

        }

        @Override
        public void queryThingCallback(QueryThingResponse response) {

        }

        @Override
        public void deleteThingCallback(BaseResponse response) {

        }
    }

}
