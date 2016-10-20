package com.gta.administrator.infraredcontrol;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.gta.administrator.infraredcontrol.bulb.bulb_brands.XiaoZhiBrandsActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yanwen on 16/10/4.
 */
public class FragmentDevices extends Fragment {
    private static final String TAG = "FragmentDevices";
    private boolean isChecked = false;
    private View view;
    private List<Map> devices = new ArrayList<>();
    private Map<String, View> device = new HashMap<>();
    private boolean isDeviceBack = false;
    private Context mContext;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        isChecked = getIsChecked();
        mContext = getContext();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_devices, container, false);
        return view;
    }

    @Override
    public void onResume() {
        RelativeLayout layout = (RelativeLayout) view;
        super.onResume();
        isChecked = getIsChecked();
        if (isChecked) {
            if (isDeviceBack) {
                return;
            }
            Button button = new Button(getContext());
            button.setLayoutParams(
                    new RelativeLayout.LayoutParams(
                            ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT)
            );
            button.setText(getName());
            button.setTag(getName());
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (getName().equals(((Button) v).getTag())) {
                        Log.d(TAG, "onClick: " + ((Button) v).getTag());

                        new ActivityManager(mContext).startActivity(XiaoZhiBrandsActivity.class);
//                        startActivityForResult(new Intent(getActivity(), XiaoZhiBrandsActivity.class), 1);
                        isDeviceBack = true;


                    }
                }
            });
            device.put(getName(), button);
            devices.add(device);
            layout.addView(button);
        } else {
            isDeviceBack = false;
            for (int i = 0; i < devices.size(); ++i) {
                Map<String, View> map = devices.get(i);
                Button button = (Button) map.get(getName());
                if (button.getTag() != null) {
                    layout.removeView(button);
                    devices.remove(i);
                }
            }
        }
        Log.d(TAG, "onCreateView: " + isChecked);
    }

    private String getName() {
        SharedPreferences preferences = getActivity().getSharedPreferences("device", Context.MODE_PRIVATE);
        return preferences.getString("name", "name");
    }

    private boolean getIsChecked() {
        SharedPreferences preferences = getActivity().getSharedPreferences("device", Context.MODE_PRIVATE);
        return preferences.getBoolean("isChecked", false);
    }


/*    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResult: " + requestCode + ", " + resultCode);

    }*/
}
