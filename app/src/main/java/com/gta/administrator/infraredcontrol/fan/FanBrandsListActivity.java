package com.gta.administrator.infraredcontrol.fan;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.gta.administrator.infraredcontrol.ActivityManager;
import com.gta.administrator.infraredcontrol.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yanwen on 16/10/2.
 */
public class FanBrandsListActivity extends AppCompatActivity {

    private Context mContext;
    private static final String[] fanBrandsName = {
            "美的"
    };
    private ListView my_listview;
    private List<String> brands = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;


        setContentView(R.layout.activity_fan_brands_list);

        my_listview = (ListView) findViewById(R.id.my_listview);

        my_listview.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, getListData()));

        my_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
//                        startActivity(new Intent(FanBrandsListActivity.this, MD_FanControlActivity.class));
                        new ActivityManager(mContext).startActivity(MD_FanControlActivity.class);
                        break;
                }
            }
        });
    }

    private List<String> getListData() {
        for (int i = 0; i < fanBrandsName.length; i++) {
            brands.add(fanBrandsName[i]);
        }
        return brands;

    }
}
