package com.gta.administrator.infraredcontrol;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.gta.administrator.infraredcontrol.setting.NetWorkSettingActivity;

/**
 * Created by yanwen on 16/10/4.
 */
public class FragmentSet extends Fragment {

    private RelativeLayout network_set_item;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_set, container, false);

        network_set_item = (RelativeLayout) view.findViewById(R.id.network_set_item);
        network_set_item.setOnClickListener(new OnItemClickListener());


        return view;
    }


    /**
     * OnItemClickListener
     * 点击监听
     */
    private class OnItemClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.network_set_item:
                    startActivity(new Intent(getActivity(), NetWorkSettingActivity.class));

                    break;
            }
        }
    }


}
