package com.gta.administrator.infraredcontrol.bulb;


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

import com.gta.administrator.infraredcontrol.NetworkRequest;
import com.gta.administrator.infraredcontrol.R;
import com.gta.administrator.infraredcontrol.bean.NetworkInterface;
import com.gta.administrator.infraredcontrol.infrared_code.BulbCode;
import com.gta.administrator.infraredcontrol.view.BallScrollView;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.math.BigDecimal;

/**
 * A simple {@link Fragment} subclass.
 */
public class Bulb_DoubleColorFragmentSub1 extends Fragment {
    private static final String TAG = "Bulb_DoubleColorFragmentSub1";

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
    private float f_brightness = 0f;

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
        /*on_off_slide = (SlideSwitch) view.findViewById(R.id.on_off_slide);
        on_off_slide.setOnStateChangedListener(new SlideSwitch.OnStateChangedListener() {
            @Override
            public void onStateChanged(boolean state) {
                if (state) {
                    networkInterface.sendData(BulbCode.getBulbColorSwitchCode(true) ,true);
                } else {
                    networkInterface.sendData(BulbCode.getBulbColorSwitchCode(false) ,true);
                }
            }
        });*/
        on_off_switch_btn = (ImageButton) view.findViewById(R.id.on_off_switch_btn);
        on_off_switch_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (powerIsOn) {
                    powerIsOn = false;
                    networkInterface.sendData(BulbCode.getBulbColorSwitchCode(BulbCode
                            .SWITCH_OFF), true);
                    on_off_switch_btn.setBackgroundResource(R.mipmap.power_on_icon);
                } else {
                    powerIsOn = true;
                    networkInterface.sendData(BulbCode.getBulbColorSwitchCode(BulbCode.SWITCH_ON), true);
                    on_off_switch_btn.setBackgroundResource(R.mipmap.power_off_icon);
                    // 500ms过后发送一条灯控指令
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            sendData();
                        }
                    }, 1000);
                }
            }
        });

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
                    sendData();
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


//        lamp2_seek = (SeekBar) view.findViewById(R.id.lamp2_seek);
//        lamp2_seek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
//            @Override
//            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//
//            }
//
//            @Override
//            public void onStartTrackingTouch(SeekBar seekBar) {
//
//            }
//
//            @Override
//            public void onStopTrackingTouch(SeekBar seekBar) {
//                float progress = seekBar.getProgress() / 100f;
//                int n_color_w = reserveTwoDeciamls(n_color_W * progress);
//                networkInterface.sendData(BulbCode.getBulbColorCode("0", "0", "0", Integer.toString(n_color_w)), true);
//            }
//        });
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
//                Toast.makeText(getActivity(), "发送成功", Toast.LENGTH_SHORT).show();
//                showMessage("发送成功");
                Log.i(TAG, "deliveryComplete: " + "发送成功");
            }

            @Override
            public void onSendError() {
//                showMessage("发送失败，请检查网络连接。");
                Log.i(TAG, "deliveryComplete: " + "发送失败，请检查网络连接。");
            }

            @Override
            public void socketReceiveData(String data) {
                showMessage("Socket发送成功");
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
