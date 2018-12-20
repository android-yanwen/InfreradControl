package com.gta.administrator.infraredcontrol_apk.satellite_tv;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;


import com.gta.administrator.infraredcontrol_apk.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yanwen on 16/10/2.
 */
public class HW_TV extends AppCompatActivity {
    private ViewPager my_viewpage;
    private List<View> views = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.hw_iptv_layout);


        my_viewpage = (ViewPager) findViewById(R.id.my_viewpage);

        views.add(getLayoutInflater().inflate(R.layout.layout_satellite_tv_one, null));
        views.add(getLayoutInflater().inflate(R.layout.layout_satellite_tv_tow, null));
        my_viewpage.setAdapter(new MyViewPageAdapter());
        my_viewpage.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    /**
     * 供fragment调用Activity中方向键的资源id
     * @return DirectionResIdHolder  管理资源id的对象
     */
    public DirectionResIdHolder getDirectionResIdHolder() {
        return DirectionResIdHolder.getInstance();
    }


    /**
     * MyViewPageAdapter
     */
    private class MyViewPageAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return views.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(views.get(position));
            return views.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(views.get(position));
        }
    }

    /**
     * 管理方向键的资源id
     */
    public static class DirectionResIdHolder {
        private static DirectionResIdHolder myHolde = null;
        int up;
        int down;
        int left;
        int right;
        int ok;

        public static DirectionResIdHolder getInstance() {
            if (myHolde == null) {
                myHolde = new DirectionResIdHolder();
            }
            return myHolde;
        }

        public DirectionResIdHolder() {
            this(
                    R.id.tv_up_btn,
                    R.id.tv_down_btn,
                    R.id.tv_left_btn,
                    R.id.tv_right_btn,
                    R.id.tv_ok_btn
                    );
        }

        public DirectionResIdHolder(int up, int down, int left, int right, int ok) {
            this.up = up;
            this.down = down;
            this.left = left;
            this.right = right;
            this.ok = ok;
        }

        public int getDown() {
            return down;
        }

        public int getLeft() {
            return left;
        }

        public int getOk() {
            return ok;
        }

        public int getRight() {
            return right;
        }

        public int getUp() {
            return up;
        }

    }

}
