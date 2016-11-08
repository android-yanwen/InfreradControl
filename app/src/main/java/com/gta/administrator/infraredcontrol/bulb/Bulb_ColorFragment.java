package com.gta.administrator.infraredcontrol.bulb;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.gta.administrator.infraredcontrol.NetworkRequest;
import com.gta.administrator.infraredcontrol.R;
import com.gta.administrator.infraredcontrol.bean.NetworkInterface;
import com.gta.administrator.infraredcontrol.infrared_code.BulbCode;
import com.gta.administrator.infraredcontrol.view.BallScrollView;
import com.lukedeighton.wheelview.WheelView;
import com.lukedeighton.wheelview.adapter.WheelArrayAdapter;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class Bulb_ColorFragment extends Fragment {
    private Context mContext;
    private static final String TAG = "Bulb_ColorFragment";
    private TextView txtColor;
//    private ColorPickView myView;
    private SeekBar color_picker_brightness_seekbar;
    private WheelView wheelView;
    private BallScrollView track_ball;
    private TextView brightness_value_text;
    private View view;


    private static final String color_W = "0";
    private int n_color_R = 0;
    private int n_color_G = 0;
    private int n_color_B = 0;
    private float f_color_Alpha = 0f;

    private NetworkInterface network;
    private Bitmap afterBitmap, baseBitmap;
    private Paint paint;
    private Canvas canvas;
    private ImageView bulb_icon_img;
    //    private SlideSwitch on_off_slide;
    // 电源开关标志位
    private boolean powerIsOn = false;
    private ImageButton power_switch_button;



    private static final int ITEM_COUNT = 24;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
        }
    };

    public Bulb_ColorFragment() {
        // Required empty public constructor
        mContext = getActivity();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_bulb__color, container, false);
        // Inflate the layout for this fragment
        initView();
        initNetwork();
        return view;
    }

    /**
     * initView() 初始化页面资源
     */
    private void initView() {
        powerIsOn = false;
        power_switch_button = (ImageButton) view.findViewById(R.id.power_switch_button);
        power_switch_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Resources resources = getActivity().getResources();
//                Drawable drawable = resources.getDrawable(R.mipmap.power_on_icon);
                // on
                if (powerIsOn) {
                    powerIsOn = false;
                    power_switch_button.setBackgroundResource(R.mipmap.power_off_icon);
                    network.sendData(BulbCode.getBulbColorSwitchCode(BulbCode.SWITCH_OFF), true);
                } else {//off
                    powerIsOn = true;
                    network.sendData(BulbCode.getBulbColorSwitchCode(BulbCode.SWITCH_ON), true);
                    power_switch_button.setBackgroundResource(R.mipmap.power_on_icon);
                    // 打开灯500ms之后发送一条控灯指令
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            RGB rgba = calcRGB(f_color_Alpha);
                            dispColor(rgba);
                            sendColorData(rgba);
                        }
                    }, 500);
                }

            }

        });


        bulb_icon_img = (ImageView) view.findViewById(R.id.bulb_icon_img);

        txtColor = (TextView) view.findViewById(R.id.txt_color);
//        myView = (ColorPickView) view.findViewById(R.id.color_picker_view);
//        myView.setOnColorChangedListener(new ColorPickView.OnColorChangedListener() {
//            RGB rgba;
//
//            @Override
//            public void onActionDown() {
//                Log.d(TAG, "onActionDown: ");
//            }
//
//            @Override
//            public void onColorChange(int color) {
//
////                Log.d(TAG, "color=" + color + ":" + Integer.toHexString(color));
////                Toast.makeText(getActivity(), "color:" + Integer.toHexString(color), Toast.LENGTH_SHORT).show();
//                txtColor.setTextColor(color);
//
//                n_color_R = color >> 16 & 0xff;//取出颜色R值
//                n_color_G = color >> 8 & 0xff;//取出颜色G值
//                n_color_B = color & 0xff;//取出颜色B值
//
//                rgba = calcRGB(f_color_Alpha);
//                // 颜色显示在控件上
//                dispColor(rgba);
//            }
//
//            @Override
//            public void onActionUp() {
//                sendColorData(rgba);
//            }
//
//        });

//        // 进度条可调节亮度
//        color_picker_brightness_seekbar = (SeekBar) view.findViewById(R.id.color_picker_brightness_seekbar);
////        int progress = color_picker_brightness_seekbar.getProgress();
////        color_W = Integer.toHexString(progress);//获取颜色W值
//        color_picker_brightness_seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
//            RGB rgba;
//
//            @Override
//            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//                f_color_Alpha = progress / 100f;//计算透明度值
//                // 将亮度alpha计算到rgb值当中
//                rgba = calcRGB(f_color_Alpha);
//                // 颜色显示在控件上
//                dispColor(rgba);
//            }
//
//            @Override
//            public void onStartTrackingTouch(SeekBar seekBar) {
////                Log.d(TAG, "onStartTrackingTouch: " + seekBar);
//            }
//
//            @Override
//            public void onStopTrackingTouch(SeekBar seekBar) {
////                Log.d(TAG, "onStopTrackingTouch: " + seekBar);
//                sendColorData(rgba);
//            }
//        });
        initColorBulb();


        // 车轮环形控件
        wheelView = (WheelView) view.findViewById(R.id.wheelview);
        wheelView.setWheelColor(android.R.color.transparent);
        //create data for the adapter
        List<Map.Entry<String, Integer>> entries = new ArrayList<Map.Entry<String, Integer>>(ITEM_COUNT);
        for (int i = 0; i < ITEM_COUNT; i++) {
            Map.Entry<String, Integer> entry = MaterialColor.random(getActivity(), "\\D*_"+(i+1)+"$");
            entries.add(entry);
        }
        //populate the adapter, that knows how to draw each item (as you would do with a ListAdapter)
        wheelView.setAdapter(new MaterialColorAdapter(entries));
        //a listener for receiving a callback for when the item closest to the selection angle changes
        wheelView.setOnWheelItemSelectedListener(new WheelView.OnWheelItemSelectListener() {
            RGB rgba;

            @Override
            public void onWheelItemSelected(WheelView parent, Drawable itemDrawable, int position) {
                //get the item at this position
                Map.Entry<String, Integer> selectedEntry = ((MaterialColorAdapter) parent
                        .getAdapter()).getItem(position);
                int color = getContrastColor(selectedEntry);
                parent.setSelectionColor(getContrastColor(selectedEntry));

//                Log.i(TAG, "onWheelItemSelected: " + getContrastColor(selectedEntry));
                txtColor.setTextColor(color);

                n_color_R = color >> 16 & 0xff;//取出颜色R值
                n_color_G = color >> 8 & 0xff;//取出颜色G值
                n_color_B = color & 0xff;//取出颜色B值
                rgba = calcRGB(f_color_Alpha);
                // 颜色显示在控件上
                dispColor(rgba);
                sendColorData(rgba);
            }
        });
        int color = getResources().getColor(R.color.COLOR_CIRCLE_1);
        n_color_R = color >> 16 & 0xff;//取出颜色R值
        n_color_G = color >> 8 & 0xff;//取出颜色G值
        n_color_B = color & 0xff;//取出颜色B值


        // 球形控件调节亮度
        track_ball = (BallScrollView) view.findViewById(R.id.track_ball);
        track_ball.initLight(BallScrollView.LIGHT_DEFAULT);
        track_ball.setIntensityBallListener(new BallScrollView.OnTrackballDidScrollListener() {
            RGB rgba;

            @Override
            public void notifyTrackBallHasScrolled(int paramInt1, int paramInt2, boolean
                    paramBoolean) {
//                Log.i(TAG, "notifyTrackBallHasScrolled: " + paramInt1 + ";" + paramInt2 + ";" +
//                        paramBoolean);
                if (f_color_Alpha != paramInt1 / 100f) {
                    f_color_Alpha = paramInt1 / 100f;//计算透明度值
                    // 将亮度alpha计算到rgb值当中
                    rgba = calcRGB(f_color_Alpha);
                    // textview显示当前值
                    brightness_value_text.setText(paramInt1 + "%");
                    // 颜色显示在控件上
                    dispColor(rgba);
                    sendColorData(rgba);
                }
            }
        });
        brightness_value_text = (TextView) view.findViewById(R.id.brightness_value_text);
    }


    private void initNetwork() {
        network = NetworkRequest.getInstance(getActivity());
        network.setCallbackListener(new NetworkInterface.CallbackListener() {
            @Override
            public void connectionLost(Throwable cause) {
                network.openConnect();//异常断开后重新打开链接
                Log.d(TAG, "Lost reconnected");
//                Toast.makeText(getActivity(), "连接丢失，网络不稳定！", Toast.LENGTH_SHORT).show();
//                network.closeConnect();
//                getActivity().finish();
            }

            @Override
            public void messageArrived(String topic, MqttMessage message) {

            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {
//                toastMsg("发送成功");
                Log.i(TAG, "deliveryComplete: " + "发送成功。");

            }

            @Override
            public void onSendError() {
//                toastMsg("发送失败，请重试。");
                Log.i(TAG, "deliveryComplete: " + "发送失败，请重试。");
            }

            @Override
            public void socketReceiveData(String data) {

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        network.openConnect();
    }



    private void toastMsg(final String msg) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void initColorBulb() {
        baseBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.icon_color_bulb);
        afterBitmap = Bitmap.createBitmap(baseBitmap.getWidth(), baseBitmap.getHeight(), baseBitmap.getConfig());
        canvas = new Canvas(afterBitmap);
        paint = new Paint();
    }
    private void adjustColorBulb(float f_R, float f_G, float f_B) {
        float f_color_R = f_R / 128f;
        float f_color_G = f_G / 128f;
        float f_color_B = f_B / 128f;
//        float f_color_W = Integer.parseInt(color_W) / 128f;
        //RGBA矩阵
        float[] src = new float[]{
                f_color_R, 0, 0, 0, 0,
                0, f_color_G, 0, 0, 0,
                0, 0, f_color_B, 0, 0,
                0, 0, 0, 255/128f, 0,
        };
        // 定义ColorMatrix指定RGBA矩阵
        ColorMatrix matrix = new ColorMatrix();
        matrix.set(src);
        paint.setColorFilter(new ColorMatrixColorFilter(matrix));
        canvas.drawBitmap(baseBitmap, new Matrix(), paint);
        bulb_icon_img.setImageBitmap(afterBitmap);
    }

    /**
     * 显示颜色
     * @param rgba 颜色值
     */
    private void dispColor(RGB rgba) {
        // 灯泡图片颜色刷新
        adjustColorBulb(rgba.f_r, rgba.f_g, rgba.f_b);
        // RGB颜色数值显示
        String command = "(" + Integer.toString(rgba.f_r) + "," + Integer.toString(rgba.f_g) + "," + Integer.toString(rgba.f_b) + "," + color_W + ")";
        txtColor.setText(command);
    }

    private void sendColorData(RGB rgba) {
        String command = BulbCode.getBulbColorCode(Integer.toString(rgba.f_r), Integer.toString(rgba.f_g), Integer.toString(rgba.f_b), color_W);
        network.sendData(command, true);
    }


    /**
     * 保留两位小数
     * @param f
     * @return
     */
    private int reserveTwoDeciamls(float f) {
        BigDecimal bigDecimal = new BigDecimal(f);
        // 0去掉小数   1保留一位小数   2保留两位小数   3保留三位小数 依次类推
        float f1 = bigDecimal.setScale(0, BigDecimal.ROUND_HALF_UP).floatValue();
        return (int)f1;
    }

    class RGB {
        public int f_r;
        public int f_g;
        public int f_b;
    }

    /**
     * 计算将亮度加入RGB三色当中
     * @param alpha 亮度值0.0f～1.0f
     * @return RGB值
     */
    private RGB calcRGB(float alpha) {
        RGB rgb = new RGB();
        rgb.f_r = reserveTwoDeciamls(n_color_R * alpha);
        rgb.f_g = reserveTwoDeciamls(n_color_G * alpha);
        rgb.f_b = reserveTwoDeciamls(n_color_B * alpha);
        return rgb;
    }




    //get the materials darker contrast
    private int getContrastColor(Map.Entry<String, Integer> entry) {
        String colorName = MaterialColor.getColorName(entry);
        // Log.d(TAG, "MainActivity_getContrastColor->colorName:"+colorName);
        return MaterialColor.getContrastColor(colorName);
    }

    static class MaterialColorAdapter extends WheelArrayAdapter<Map.Entry<String, Integer>> {
        MaterialColorAdapter(List<Map.Entry<String, Integer>> entries) {
            super(entries);
        }

        @Override
        public Drawable getDrawable(int position) {
            Drawable[] drawable = new Drawable[] {
                    createOvalDrawable(getItem(position).getValue()),
                    // new TextDrawable(String.valueOf(position))
            };
            return new LayerDrawable(drawable);
        }

        private Drawable createOvalDrawable(int color) {
            ShapeDrawable shapeDrawable = new ShapeDrawable(new OvalShape());
            shapeDrawable.getPaint().setColor(color);
            return shapeDrawable;
        }
    }
}
