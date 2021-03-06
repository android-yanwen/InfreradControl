package com.gta.administrator.infraredcontrol_apk.bulb;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.gta.administrator.infraredcontrol_apk.bean.NetworkInterface;
import com.gta.administrator.infraredcontrol_apk.infrared_code.BulbCode;
import com.gta.administrator.infraredcontrol_apk.view.BallScrollView;
import com.gta.administrator.infraredcontrol_apk.R;

import java.math.BigDecimal;

import static java.lang.Math.abs;

/**
 * A simple {@link Fragment} subclass.
 */
public class Bulb_DoubleColorFragmentSub1 extends Fragment {
    private static final String TAG = "Bulb_DoubleColor1";
    private BulbActivity bulbActivity;


    //    private SlideSwitch on_off_slide;
    private BallScrollView brightness_value_ballscrollview;
    private SeekBar color_temperature_seek;
    private ImageButton on_off_switch_btn;
    private TextView brightness_value_text;
    private boolean powerIsOn = false;
    private View view;

    private int n_color_R = 255;
    private int n_color_G = 255;
    private int n_color_B = 255;
    private int n_color_W = 255;
    private float f_color_temperature = 0f;
    private float f_brightness = 0.5f;
    private float f_brightness_bak = 0f;

    private NetworkInterface networkInterface;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
        }
    };


    public Bulb_DoubleColorFragmentSub1() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_bulb__double_color_fragment_sub1, container, false);
        powerIsOn = false;
        initView();
        initNetwork();
        return view;
    }


    private void initView() {
        brightness_value_text = (TextView) view.findViewById(R.id.brightness_value_text);
        brightness_value_ballscrollview = (BallScrollView) view.findViewById(R.id
                .brightness_value_ballscrollview);
        brightness_value_ballscrollview.initLight(BallScrollView.LIGHT_DEFAULT);//设置默认亮度值
        brightness_value_ballscrollview.setIntensityBallListener(new BallScrollView
                .OnTrackballDidScrollListener() {
            @Override
            public void notifyTrackBallHasScrolled(int paramInt1, int paramInt2, boolean
                    paramBoolean) {
                if (f_brightness != paramInt1 / 100f) {
                    f_brightness = paramInt1 / 100f;
                    brightness_value_text.setText("亮度：" + paramInt1 + "%");
                    if (abs(f_brightness-f_brightness_bak)>0.05){
                        f_brightness_bak = f_brightness;
                        sendData();
                    }
                    else
                    {
                        Log.d(TAG, "亮度调整: 变态太小放弃发送！");
                    }
                }
            }
        });
        color_temperature_seek = (SeekBar) view.findViewById(R.id.color_temperature_seek);
        f_color_temperature = color_temperature_seek.getProgress() / 100f;
        color_temperature_seek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                f_color_temperature = seekBar.getProgress() / 100f;
                sendData();
            }
        });

        on_off_switch_btn = (ImageButton) view.findViewById(R.id.on_off_switch_btn);
        on_off_switch_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                f_color_temperature = color_temperature_seek.getProgress() / 100f;
                int n_color_r = reserveTwoDeciamls(n_color_R * f_brightness * (1 - f_color_temperature));
                int n_color_g = reserveTwoDeciamls(n_color_G * f_brightness * (1 - f_color_temperature));
                int n_color_b = reserveTwoDeciamls(n_color_B * f_brightness * (1 - f_color_temperature));
                int n_color_w = reserveTwoDeciamls(n_color_W * f_brightness * f_color_temperature);
                if (powerIsOn) {
                    powerIsOn = false;
                    networkInterface.sendData(BulbCode.getBulbColorSwitchCode(BulbCode.SWITCH_OFF,Integer.toString(n_color_r),Integer.toString(n_color_g),Integer.toString(n_color_b),Integer.toString(n_color_w)), true);
                    on_off_switch_btn.setBackgroundResource(R.mipmap.power_off_icon);
                } else {
                    powerIsOn = true;
                    networkInterface.sendData(BulbCode.getBulbColorSwitchCode(BulbCode.SWITCH_ON,Integer.toString(n_color_r),Integer.toString(n_color_g),Integer.toString(n_color_b),Integer.toString(n_color_w)), true);
                    on_off_switch_btn.setBackgroundResource(R.mipmap.power_on_icon);
                }
            }
        });






    }

    private void initNetwork() {
        this.bulbActivity = (BulbActivity)getActivity();
        this.networkInterface = this.bulbActivity.networkInterface;
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
    private int reserveTwoDeciamls(float f) {
        BigDecimal bigDecimal = new BigDecimal(f);
        // 0去掉小数   1保留一位小数   2保留两位小数   3保留三位小数 依次类推
        float f1 = bigDecimal.setScale(0, BigDecimal.ROUND_HALF_UP).floatValue();
        return (int)f1;
    }


    private void showMessage(final String message) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
            }
        });

    }



    private void sendData() {

        int n_color_r = reserveTwoDeciamls(n_color_R * f_brightness * (1 - f_color_temperature));
        int n_color_g = reserveTwoDeciamls(n_color_G * f_brightness * (1 - f_color_temperature));
        int n_color_b = reserveTwoDeciamls(n_color_B * f_brightness * (1 - f_color_temperature));
        int n_color_w = reserveTwoDeciamls(n_color_W * f_brightness * f_color_temperature);
        networkInterface.sendData(BulbCode.getBulbColorCode(Integer.toString(n_color_r), Integer.toString(n_color_g), Integer.toString(n_color_b), Integer.toString(n_color_w)), true);
    }
}
