package com.gta.administrator.infraredcontrol.air_condition;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.gta.administrator.infraredcontrol.ActivityManager;
import com.gta.administrator.infraredcontrol.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindInt;
import butterknife.BindView;
import butterknife.ButterKnife;

public class AirConditionBrandsListActivity extends AppCompatActivity {

    private Context mContext;
    @BindView(R.id.air_condition_brands_list)
    ListView air_condition_brands_list;

    private List<String> air_condition_brands;
    private ArrayAdapter adapter;
    private ActivityManager activityManager;
    private String[] airConditionBrands = {
            "格力空调-1",
            "格力空调-2",
            "格力空调-3",
            "格力空调-4",
            "格力空调-5",
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = AirConditionBrandsListActivity.this;
        setContentView(R.layout.activity_air_condition_brands_list);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.mipmap.yk_ctrl_item_menu);

        ButterKnife.bind(this);


        initData();
        initView();
    }


    private void initView() {
        adapter = new ArrayAdapter(mContext, R.layout.my_list_item_text_1, air_condition_brands);
        air_condition_brands_list.setAdapter(adapter);
        air_condition_brands_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                activityManager.startActivity(AirConditionControlActivity.class, air_condition_brands.get(position));
                startActivity(new Intent(AirConditionBrandsListActivity.this, AddTelecontrolActivity.class));
            }
        });
    }

    private void initData() {
        air_condition_brands = new ArrayList<>();
        for (int i = 0; i < airConditionBrands.length; i++) {
            air_condition_brands.add(airConditionBrands[i]);
        }
        activityManager = ActivityManager.getInstance(mContext);
    }
}
