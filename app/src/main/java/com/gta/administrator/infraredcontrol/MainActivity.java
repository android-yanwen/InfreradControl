package com.gta.administrator.infraredcontrol;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.gta.administrator.infraredcontrol.adapter.DividerGridItemDecoration;
import com.gta.administrator.infraredcontrol.adapter.RecycleViewAdapter;
import com.gta.administrator.infraredcontrol.bean.DevicesModule;
import com.gta.administrator.infraredcontrol.bulb.BulbActivity;
import com.gta.administrator.infraredcontrol.dvd.DvdBrandsListActivity;
import com.gta.administrator.infraredcontrol.fan.FanBrandsListActivity;
import com.gta.administrator.infraredcontrol.powerAmplification.PowerAmplification_BrandsActivity;
import com.gta.administrator.infraredcontrol.projectors.ProjectorsBrandsSelectActivity;
import com.gta.administrator.infraredcontrol.satellite_tv.SatelliteTvBrandsActivity;
import com.gta.administrator.infraredcontrol.setTopBoxes.SetTopBoxesBrandsListActivity;
import com.gta.administrator.infraredcontrol.tv.TvBrandsListActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private ActivityManager activityManager;
    private RecyclerView my_recycle_view;


    private static final int[] devicesImg = {
            R.mipmap.icon_mi_tv,
            R.mipmap.icon_ari_condition,
            R.mipmap.icon_tv,
            R.mipmap.icon_set_top_boxes,
            R.mipmap.icon_ari_fan,
            R.mipmap.icon_box,
            R.mipmap.icon_sound,
            R.mipmap.icon_dvd,
            R.mipmap.icon_projector,
            R.mipmap.icon_satellite_tv,
            R.mipmap.icon_camaro,
            R.mipmap.icon_bulb
    };
    private static final String[] devicesName = {
            "小米电视/盒子",
            "空调",
            "电视",
            "电视机顶盒",
            "风扇",
            "盒子",
            "功放",
            "DVD",
            "投影仪",
            "卫星电视",
            "单反",
            "灯泡"
    };

    private List<DevicesModule> devicesModuleList = new ArrayList<>();
    private RecycleViewAdapter adapter;


    private ImageButton ari_condition_imgbtn;
    private ImageButton tv_imgbtn;

    private static Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

/*        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        mContext = MainActivity.this;
        activityManager = ActivityManager.getInstance(mContext);

//        initView();

        initData();

        my_recycle_view = (RecyclerView) findViewById(R.id.my_recycle_view);
        my_recycle_view.setLayoutManager(new GridLayoutManager(this, 3));
        adapter = new RecycleViewAdapter(mContext, devicesModuleList);
        my_recycle_view.setAdapter(adapter);
        my_recycle_view.addItemDecoration(new DividerGridItemDecoration(this));
        adapter.setOnItemClickListener(new RecycleViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(MainActivity.this, "position=" + position, Toast.LENGTH_SHORT).show();


                switch (position) {
                    case 0:
                        break;
                    case 1:
                        activityManager.startActivity(AirConditionControlActivity.class);

                        break;
                    case 2:
                        startActivity(new Intent(mContext, TvBrandsListActivity.class));
                        break;
                    case 3:
                        startActivity(new Intent(mContext, SetTopBoxesBrandsListActivity.class));
                        break;
                    case 4:
                        startActivity(new Intent(mContext, FanBrandsListActivity.class));
//                        startActivity(FanBrandsListActivity.cla);
                        break;
                    case 6:
                        startActivity(new Intent(mContext, PowerAmplification_BrandsActivity.class));
                        break;
                    case 7:
                        startActivity(new Intent(mContext, DvdBrandsListActivity.class));
                        break;
                    case 8:
                        startActivity(new Intent(mContext, ProjectorsBrandsSelectActivity.class));
                        break;
                    case  9:
                        startActivity(new Intent(mContext, SatelliteTvBrandsActivity.class));
                        break;
                    case 11:
//                        startActivity(new Intent(mContext, BulbActivity.class));
                        activityManager.startActivity(BulbActivity.class);
                        break;

                }

            }

            @Override
            public void onItemLongClick(View view, int position) {
                Toast.makeText(MainActivity.this, "long click position=" + position, Toast.LENGTH_SHORT).show();
            }
        });


    }



    private void initData() {
        for (int i = 0; i < devicesImg.length; ++i) {
            DevicesModule devicesModule = new DevicesModule(devicesImg[i], devicesName[i]);
            devicesModuleList.add(devicesModule);
        }
    }


}
