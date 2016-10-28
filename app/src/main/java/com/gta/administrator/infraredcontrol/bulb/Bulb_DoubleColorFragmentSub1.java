package com.gta.administrator.infraredcontrol.bulb;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.Toast;

import com.gta.administrator.infraredcontrol.NetworkRequest;
import com.gta.administrator.infraredcontrol.R;
import com.gta.administrator.infraredcontrol.bean.NetworkInterface;
import com.gta.administrator.infraredcontrol.infrared_code.BulbCode;
import com.gta.smart.slideswitch.SlideSwitch;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.math.BigDecimal;

/**
 * A simple {@link Fragment} subclass.
 */
public class Bulb_DoubleColorFragmentSub1 extends Fragment {
    private SlideSwitch on_off_slide;
    private SeekBar lamp1_seek, lamp2_seek;
    private View view;

    private RGB rgba;
    private NetworkInterface networkInterface;

    public Bulb_DoubleColorFragmentSub1() {
        // Required empty public constructor
        rgba = new RGB();
        rgba.f_r = 255;
        rgba.f_g = 255;
        rgba.f_b = 255;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_bulb__double_color_fragment_sub1, container, false);

        initView();
        initNetwork();
        return view;
    }


    private void initView() {
        on_off_slide = (SlideSwitch) view.findViewById(R.id.on_off_slide);
        on_off_slide.setOnStateChangedListener(new SlideSwitch.OnStateChangedListener() {
            @Override
            public void onStateChanged(boolean state) {
                if (state) {
                    networkInterface.sendData(BulbCode.getBulbColorSwitchCode(true) ,true);
                } else {
                    networkInterface.sendData(BulbCode.getBulbColorSwitchCode(false) ,true);
                }
            }
        });
        lamp1_seek = (SeekBar) view.findViewById(R.id.lamp1_seek);
        lamp1_seek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                float progress = seekBar.getProgress()/100f;
                float f_r = reserveTwoDeciamls(rgba.f_r * progress);
                float f_g = reserveTwoDeciamls(rgba.f_g * progress);
                float f_b = reserveTwoDeciamls(rgba.f_b * progress);
                networkInterface.sendData(BulbCode.getBulbColorCode(Float.toString(f_r),Float.toString(f_g),Float.toString(f_b), "0"), true);

            }
        });


        lamp2_seek = (SeekBar) view.findViewById(R.id.lamp2_seek);
        lamp2_seek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                float progress = seekBar.getProgress()/100f;
                float f_r = reserveTwoDeciamls(rgba.f_r * progress);
                float f_g = reserveTwoDeciamls(rgba.f_g * progress);
                float f_b = reserveTwoDeciamls(rgba.f_b * progress);
                networkInterface.sendData(BulbCode.getBulbColorCode(Float.toString(f_r),Float.toString(f_g),Float.toString(f_b), "255"), true);
            }
        });
    }

    private void initNetwork() {
        networkInterface = NetworkRequest.getInstance(getActivity());
        networkInterface.setCallbackListener(new NetworkInterface.CallbackListener() {
            @Override
            public void connectionLost(Throwable cause) {
                // 如果连接丢失则重新连接
                networkInterface.openConnect();
            }

            @Override
            public void messageArrived(String topic, MqttMessage message) {

            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {
                Toast.makeText(getActivity(), "发送成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSendError() {
                Toast.makeText(getActivity(), "发送失败，请检查网络连接。", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void socketReceiveData(String data) {
                Toast.makeText(getActivity(), "Socket发送成功", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!networkInterface.isConnected()) {
            networkInterface.openConnect();
        }
    }

    /**
     * 去掉小数部分
     * @param f
     * @return
     */
    private float reserveTwoDeciamls(float f) {
        BigDecimal bigDecimal = new BigDecimal(f);
        // 0去掉小数   1保留一位小数   2保留两位小数   3保留三位小数 依次类推
        float f1 = bigDecimal.setScale(0, BigDecimal.ROUND_HALF_UP).floatValue();
        return f1;
    }

    class RGB {
        public float f_r;
        public float f_g;
        public float f_b;
    }
}
