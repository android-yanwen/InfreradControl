package com.gta.administrator.infraredcontrol.bulb;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.gta.administrator.infraredcontrol.ActivityManager;
import com.gta.administrator.infraredcontrol.NetworkRequest;
import com.gta.administrator.infraredcontrol.R;
import com.gta.administrator.infraredcontrol.bean.NetworkInterface;
import com.gta.administrator.infraredcontrol.infrared_code.BulbCode;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 * A simple {@link Fragment} subclass.
 */
public class Bulb_ColorFragment extends Fragment {
    private Context mContext;
    private static final String TAG = "Bulb_ColorFragment";
    private TextView txtColor;
    private ColorPickView myView;
    private SeekBar color_picker_brightness_seekbar;
    private View view;


    private String color_R = "0";
    private String color_G = "0";
    private String color_B = "0";
    private String color_W = "0";


    private NetworkInterface network;


    private Bitmap afterBitmap, baseBitmap;
    private Paint paint;
    private Canvas canvas;
    private ImageView bulb_icon_img;

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

        bulb_icon_img = (ImageView) view.findViewById(R.id.bulb_icon_img);

        myView = (ColorPickView) view.findViewById(R.id.color_picker_view);
        txtColor = (TextView) view.findViewById(R.id.txt_color);
        myView.setOnColorChangedListener(new ColorPickView.OnColorChangedListener() {

            @Override
            public void onActionDown() {
                Log.d(TAG, "onActionDown: ");
            }

            @Override
            public void onColorChange(int color) {

//                Log.d(TAG, "color=" + color + ":" + Integer.toHexString(color));
//                Toast.makeText(getActivity(), "color:" + Integer.toHexString(color), Toast.LENGTH_SHORT).show();
                txtColor.setTextColor(color);
                int n_R = color >> 16 & 0xff;//取出颜色R值
                int n_G = color >> 8 & 0xff;//取出颜色G值
                int n_B = color & 0xff;//取出颜色B值
                color_R = Integer.toString(n_R);
                color_G = Integer.toString(n_G);
                color_B = Integer.toString(n_B);
//                String command = BulbCode.getBulbColorCode(color_R, color_G, color_B, color_W);
                String command = "(" + color_R + "," + color_G + "," + color_B + "," + color_W + ")";
                txtColor.setText(command);


                adjustColorBulb();

            }

            @Override
            public void onActionUp() {
                String command = BulbCode.getBulbColorCode(color_R, color_G, color_B, color_W);
                Log.d(TAG, "onActionUp: " + command);
                network.sendData(command, false);
            }

        });

        color_picker_brightness_seekbar = (SeekBar) view.findViewById(R.id.color_picker_brightness_seekbar);
        int progress = color_picker_brightness_seekbar.getProgress();
        color_W = Integer.toHexString(progress);//获取颜色W值
        color_picker_brightness_seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//                Log.d(TAG, "onProgressChanged: " + seekBar + ":" + progress + ":" + fromUser);
                color_W = Integer.toString(progress);//获取颜色W值
//                String command = BulbCode.getBulbColorCode(color_R, color_G, color_B, color_W);
                String command = "(" + color_R + "," + color_G + "," + color_B + "," + color_W + ")";
                txtColor.setText(command);
                adjustColorBulb();

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
//                Log.d(TAG, "onStartTrackingTouch: " + seekBar);
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
//                Log.d(TAG, "onStopTrackingTouch: " + seekBar);
                network.sendData(BulbCode.getBulbColorCode(color_R, color_G, color_B, color_W), false);


            }
        });


        initColorBulb();

    }


    private void initNetwork() {
        network = NetworkRequest.getInstance(getActivity());
        network.setCallbackListener(new NetworkInterface.CallbackListener() {
            @Override
            public void connectionLost(Throwable cause) {
                network.openConnect();//异常断开后重新打开链接
                Log.d(TAG, "Lost reconnected");
            }

            @Override
            public void messageArrived(String topic, MqttMessage message) {

            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {
                toastMsg("发送成功");
            }

            @Override
            public void onSendError() {
                toastMsg("网络超时，请退出后重试。");
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
    private void adjustColorBulb() {
        float f_color_R = Integer.parseInt(color_R) / 128f;
        float f_color_G = Integer.parseInt(color_G) / 128f;
        float f_color_B = Integer.parseInt(color_B) / 128f;
        float f_color_W = Integer.parseInt(color_W) / 128f;
        //RGBA矩阵
        float[] src = new float[]{
                f_color_R, 0, 0, 0, 0,
                0, f_color_G, 0, 0, 0,
                0, 0, f_color_B, 0, 0,
                0, 0, 0, f_color_W, 0,
        };
        // 定义ColorMatrix指定RGBA矩阵
        ColorMatrix matrix = new ColorMatrix();
        matrix.set(src);
        paint.setColorFilter(new ColorMatrixColorFilter(matrix));
        canvas.drawBitmap(baseBitmap, new Matrix(), paint);
        bulb_icon_img.setImageBitmap(afterBitmap);
    }
}
