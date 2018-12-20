package com.gta.administrator.infraredcontrol_apk;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.gta.administrator.infraredcontrol_apk.setting.BindDeviceActivity;
import com.gta.administrator.infraredcontrol_apk.setting.NetWorkSettingActivity;

/**
 * Created by yanwen on 16/10/4.
 */
public class FragmentSet extends Fragment {

    private View view;
    private RelativeLayout network_set_item;
    private RelativeLayout device_add_item;
    private OnItemClickListener onItemClickListener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_set, container, false);
        initView();
        return view;
    }

    private void initView() {

        onItemClickListener = new OnItemClickListener();

        network_set_item = (RelativeLayout) view.findViewById(R.id.network_set_item);
        network_set_item.setOnClickListener(onItemClickListener);

        device_add_item = (RelativeLayout) view.findViewById(R.id.device_add_item);
        device_add_item.setOnClickListener(onItemClickListener);

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
                case R.id.device_add_item:
                    startActivity(new Intent(getActivity(), BindDeviceActivity.class));
                    break;
            }
        }
    }


}
