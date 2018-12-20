package com.gta.administrator.infraredcontrol_apk.setTopBoxes;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gta.administrator.infraredcontrol_apk.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class HwTowFragment extends Fragment {

    private HW_TV hw_tvActivity;
    private HW_TV.DirectionResIdHolder myDirectionHolder;

    public HwTowFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        hw_tvActivity = (HW_TV) getActivity();
        myDirectionHolder = hw_tvActivity.getDirectionResIdHolder();//这里获得HW_TV 这个Activity的方向布局资源的id
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment



        return inflater.inflate(R.layout.fragment_hw_tow, container, false);
    }

}
