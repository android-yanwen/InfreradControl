package com.gta.administrator.infraredcontrol_apk.air_condition;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.gta.administrator.infraredcontrol_apk.ActivityManager;
import com.gta.administrator.infraredcontrol_apk.adapter.SortAdapter;
import com.gta.administrator.infraredcontrol_apk.bean.DataSortModel;
import com.gta.administrator.infraredcontrol_apk.utils.PinyinUtils;
import com.gta.administrator.infraredcontrol_apk.view.SideBar;
import com.gta.administrator.infraredcontrol_apk.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AirConditionBrandsListActivity extends AppCompatActivity {

    @BindView(R.id.sidrbar)
    SideBar sidrbar;
    @BindView(R.id.dialog)
    TextView dialog;
    private Context mContext;
    @BindView(R.id.air_condition_brands_list)
    ListView air_condition_brands_listview;

    //    private List<String> air_condition_brands;
    private List<DataSortModel> air_condition_brands_list;
    //    private ArrayAdapter adapter;
    private SortAdapter adapter;
    private ActivityManager activityManager;
    private String[] airConditionBrands = {
            "格力空调-1",
            "格力空调-2",
            "格力空调-3",
            "格力空调-4",
            "格力空调-5",
            "康佳空调-1",
            "美的空调-1",
            "国美空调-1",
            "小米空调",
            "百度空调",
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
//        adapter = new ArrayAdapter(mContext, R.layout.my_list_item_text_1, air_condition_brands);
        adapter = new SortAdapter(mContext, air_condition_brands_list);
        air_condition_brands_listview.setAdapter(adapter);
        //ListView的点击事件
        air_condition_brands_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
//                Toast.makeText(getApplication(), ((DataSortModel) adapter.getItem(position))
//                        .getName(), Toast.LENGTH_SHORT).show();
                startActivity(new Intent(AirConditionBrandsListActivity.this,
                        AddTelecontrolActivity.class));
            }
        });
//        air_condition_brands_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
////                activityManager.startActivity(AirConditionControlActivity.class,
//// air_condition_brands.get(position));
//                startActivity(new Intent(AirConditionBrandsListActivity.this,
//                        AddTelecontrolActivity.class));
//            }
//        });
        //设置右侧触摸监听
        sidrbar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {
            @Override
            public void onTouchingLetterChanged(String s) {
                //该字母首次出现的位置
                int position = adapter.getPositionForSection(s.charAt(0));
                if (position != -1) {
                    air_condition_brands_listview.setSelection(position + 1);
                }
            }
        });
        sidrbar.setTextView(dialog);
    }

    private void initData() {
//        air_condition_brands = new ArrayList<>();
//        for (int i = 0; i < airConditionBrands.length; i++) {
//            air_condition_brands.add(airConditionBrands[i]);
//        }
//        air_condition_brands = filledData(getResources().getStringArray(R.array.city));
        air_condition_brands_list = filledData(airConditionBrands);

        activityManager = ActivityManager.getInstance(mContext);
    }


    private List<DataSortModel> filledData(String[] date) {
        List<DataSortModel> mSortList = new ArrayList<>();
        ArrayList<String> indexString = new ArrayList<>();

        for (int i = 0; i < date.length; i++) {
            DataSortModel sortModel = new DataSortModel();
            sortModel.setName(date[i]);
            String pinyin = PinyinUtils.getPingYin(date[i]);
            String sortString = pinyin.substring(0, 1).toUpperCase();
            if (sortString.matches("[A-Z]")) {
                sortModel.setSortLetters(sortString.toUpperCase());
                if (!indexString.contains(sortString)) {
                    indexString.add(sortString);
                }
            }
            mSortList.add(sortModel);
        }
        Collections.sort(indexString);
        sidrbar.setIndexText(indexString);
        return mSortList;
    }
}
