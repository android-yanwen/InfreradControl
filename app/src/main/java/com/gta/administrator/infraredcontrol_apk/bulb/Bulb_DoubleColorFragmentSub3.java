package com.gta.administrator.infraredcontrol_apk.bulb;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gta.administrator.infraredcontrol_apk.adapter.Bulb_DoubleColorBrandsSelectAdapter;
import com.gta.administrator.infraredcontrol_apk.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class Bulb_DoubleColorFragmentSub3 extends Fragment {
    private static final String TAG = "Bulb_DoubleColorFragmentSub3";
    private BulbActivity mActivity;

    private RecyclerView my_recycler_view;
    private View view;
    private List<String> brandsList = new ArrayList<>();
    private Bulb_DoubleColorBrandsSelectAdapter adapter;

    public Bulb_DoubleColorFragmentSub3() {
        mActivity = (BulbActivity) getActivity();

        brandsList.add("晓智电子");
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_bulb__double_color_fragment_sub3, container, false);
        my_recycler_view = (RecyclerView) view.findViewById(R.id.my_recycle_view);
        my_recycler_view.setLayoutManager(new LinearLayoutManager(mActivity));
        adapter = new Bulb_DoubleColorBrandsSelectAdapter(this.getContext(), brandsList, getActivity().getSharedPreferences("device", Context.MODE_PRIVATE).getBoolean("isChecked", false));
        my_recycler_view.setAdapter(adapter);
        adapter.setOnCheckedChangedListener(new Bulb_DoubleColorBrandsSelectAdapter.onCheckedChangedListener() {
            @Override
            public void onCheckedChanged(int position, boolean isChecked) {
                Log.d(TAG, "onCheckedChanged: " + position + ", " + isChecked);
                saveBrands(isChecked);
            }
        });


//        Log.d(TAG, getActivity().getSharedPreferences("device", Context.MODE_PRIVATE).getString("device", "eeee"));
        return view;
    }


    /**
     * 是否保存品牌
     * @param isSave
     */
    private void saveBrands(boolean isSave) {
        SharedPreferences preferences = getActivity().getSharedPreferences("device", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("id", 1);
        editor.putString("name", "晓智电子");
        editor.putBoolean("isChecked", isSave);
        editor.commit();
    }
}
