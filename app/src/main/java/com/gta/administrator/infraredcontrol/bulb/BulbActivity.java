package com.gta.administrator.infraredcontrol.bulb;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.gta.administrator.infraredcontrol.other.MyGradLayoutItem;
import com.gta.administrator.infraredcontrol.R;

public class BulbActivity extends AppCompatActivity {
    private static final String TAG = "BulbActivity";

    private int[] item_id = {
            R.id.id_color_item,
            R.id.id_ct_item,
            R.id.id_mode_item,
            R.id.id_music_item
    };
    private MyGradLayoutItem[] id_bottom_item = new MyGradLayoutItem[item_id.length];

//    private FrameLayout bulb_framelayout;

    private Bulb_DoubleColorFragment bulb_doubleColorFragment;
    private Bulb_ColorFragment bulb_colorFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bulb);
//
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//
//        toolbar.inflateMenu(R.menu.menu_bulb);
//        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(MenuItem item) {
//                return true;
//            }
//        });


//
//        myView = (ColorPickView) findViewById(R.id.color_picker_view);
//        txtColor = (TextView) findViewById(R.id.txt_color);
//        myView.setOnColorChangedListener(new ColorPickView.OnColorChangedListener() {
//
//            @Override
//            public void onColorChange(int color) {
//
//                Log.d(TAG, "color=" + color + ":" + Integer.toHexString(color));
//                txtColor.setTextColor(color);
//            }
//
//        });

        initView();


    }

    private void initView() {
//        bulb_framelayout = (FrameLayout) findViewById(R.id.bulb_framelayout);
        bulb_doubleColorFragment = new Bulb_DoubleColorFragment();
        bulb_colorFragment = new Bulb_ColorFragment();


        for (int i = 0; i < item_id.length; i++) {
            id_bottom_item[i] = (MyGradLayoutItem) findViewById(item_id[i]);
            id_bottom_item[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for (int j = 0; j < item_id.length; j++) {
                        if (v.getId() == item_id[j]) {
                            if (j == 1) {  // 如果为1则跳转到双色灯页面
                                replaceFragment(R.id.bulb_framelayout, bulb_doubleColorFragment);
                            } else {
                                replaceFragment(R.id.bulb_framelayout, bulb_colorFragment);
                            }
                            id_bottom_item[j].setSelected(true);
                        } else {
                            id_bottom_item[j].setSelected(false);
                        }
                    }
                }
            });
        }

        id_bottom_item[0].setSelected(true);
        replaceFragment(R.id.bulb_framelayout, bulb_colorFragment);

    }


    /**
     * Fragment的跳转
     * @param layout
     * @param fragment
     */
    private void replaceFragment(int layout, Fragment fragment) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(layout, fragment);
        transaction.commit();
    }

/*    *//**
     * 获得ViewPager
     * @param id
     * @return
     *//*
    public View obtainAdcitivtyWidget(int id) {
        return findViewById(id);
    }*/
}
