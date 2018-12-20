package com.gta.administrator.infraredcontrol_apk.bulb;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.gta.administrator.infraredcontrol_apk.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class Bulb_DoubleColorFragment extends Fragment {

    private static final String TAG = "Bulb_DoubleColor";
    private View view;
    private BulbActivity mActivity;

    private ViewPager my_viewpager;
    private List<Fragment> fragments = new ArrayList<>(3);
    private Bulb_DoubleColorFragmentSub1 bulb_doubleColorFragmentSub1 = new Bulb_DoubleColorFragmentSub1();
    private Bulb_DoubleColorFragmentSub2 bulb_doubleColorFragmentSub2 = new Bulb_DoubleColorFragmentSub2();
    private Bulb_DoubleColorFragmentSub3 bulb_doubleColorFragmentSub3 = new Bulb_DoubleColorFragmentSub3();

    private MyViewPager myViewPager;

    public Bulb_DoubleColorFragment() {
       // Required empty public constructor
        mActivity = (BulbActivity) getActivity();
        fragments.add(bulb_doubleColorFragmentSub1);
        fragments.add(bulb_doubleColorFragmentSub2);
        fragments.add(bulb_doubleColorFragmentSub3);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_bulb__double_color, container, false);
        my_viewpager = (ViewPager) view.findViewById(R.id.my_viewpage);//获取到ViewPager

        //这里注意必须传入getChildFragmentManager()不要传入getFragmentManager()，否则第二次进来Fragment不会显示
        //getFragmentManager()会调用父的FragmentManager
        myViewPager = new MyViewPager(getChildFragmentManager());
        my_viewpager.setAdapter(myViewPager);
        Log.d(TAG, "onCreateView()");
        return view;
    }

    private class MyViewPager extends FragmentPagerAdapter {

        public MyViewPager(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            return super.instantiateItem(container, position);
        }

        @Override
        public Fragment getItem(int position) {
            Log.d(TAG, "getItem()");

            return fragments.get(position);
        }

        @Override
        public int getCount() {
//            Log.d(TAG, "getCount()");

            return fragments.size();
        }

    }

}
