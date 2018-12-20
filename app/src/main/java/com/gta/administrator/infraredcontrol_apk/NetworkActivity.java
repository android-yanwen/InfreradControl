package com.gta.administrator.infraredcontrol_apk;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.gta.administrator.infraredcontrol_apk.bean.NetworkInterface;
import com.gta.administrator.infraredcontrol_apk.database.DBManager;
import com.gta.administrator.infraredcontrol_apk.infrared_code.AirConditionCode;
import com.gta.administrator.infraredcontrol_apk.infrared_code.BulbCode;
import com.gta.administrator.infraredcontrol_apk.database.Ir_code;
import com.gta.administrator.infraredcontrol_apk.view.MyCustomDialog;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2016/11/10.
 */

public class NetworkActivity  extends AppCompatActivity {
    private static final String TAG = "NetworkActivity";
    private Context mContext;
    public NetworkInterface networkInterface;
    private MyCustomDialog alertDialog;
    public volatile boolean colorIsSentSuccessfully = true;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext=this;
        alertDialog = new MyCustomDialog(mContext);
        networkInterface = NetworkRequest.getInstance(mContext);
        networkInterface.setCallbackListener(new NetworkInterface.CallbackListener() {
            @Override
            public void connectionLost(Throwable cause) {
                networkInterface.closeConnect();
                networkInterface.openConnect();//异常断开后重新打开链接
                Log.d(TAG, "Lost reconnected");
            }
            @Override
            public void messageArrived(String topic, MqttMessage message) {
                String s_data = null;
                try {
                    s_data = new String(message.getPayload(), "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                Log.d(TAG, "messageArrived: " + s_data);
            }
            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {
//                toastMsg("发送成功");
                dismissDialog();
                handler.post(postRunnable);
//                Log.i(TAG, "run: "  + "deliveryComplete");
            }
            @Override
            public void onSendError() {
//                toastMsg("发送失败请重试");
                networkInterface.closeConnect();
                networkInterface.openConnect();
                dismissDialog();
            }
            @Override
            public void socketReceiveData(String data) {
                Log.d(TAG, "socketReceiveData: " + data);
                handler.post(postRunnable);
            }
        });
        networkInterface.setCallbackConnectListener(new NetworkConnectListener());
        if (!networkInterface.isConnected()){
            networkInterface.openConnect();
        }
    }
//    private void toastMsg(final String msg) {
//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
    public void  Send_ircode(String Brand_models,String key) {
        class Get_ir_code extends Thread{
            private String Brand_models;
            private String key;
            private String results;
            public Get_ir_code(String Brand_models,String key) {
                this.Brand_models=Brand_models;
                this.key=key;
            }
            public void run()
            {
                showDialog();
                DBManager sqliteDB = new DBManager(mContext);
                Ir_code code = new Ir_code();
                try {
                    Log.d(TAG, "Send_ircode 厂家型号:"+Brand_models+"功能按键:"+key);
                    results=sqliteDB.query(Brand_models,key);
                    Log.d(TAG, "get ir_code form sqlite: "+results);
                    if (results==null)
                    {
                        results=code.getIr_code(Brand_models,key);
                        Log.d(TAG, "get ir_code form mysql: "+results);
                        if (results!=null){
                            Ir_code.Ir_codes ir_codes = code.new Ir_codes();
                            List<Ir_code.Ir_codes> list_ir_codes = new ArrayList<Ir_code.Ir_codes>();
                            ir_codes.setBrand_models(Brand_models);
                            ir_codes.setIr_fun(key);
                            ir_codes.setIr_code(results);
                            list_ir_codes.add(ir_codes);
                            sqliteDB.insert(list_ir_codes);
                            sqliteDB.Close();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                networkInterface.sendData(AirConditionCode.get_ir_Code(results), true);
            }
        }
        Get_ir_code get_ir_code = new Get_ir_code(Brand_models,key);
        get_ir_code.start();
    }


    public void sendColorData(String f_r, String f_g, String f_b, String f_w) {
        if (colorIsSentSuccessfully) {
            colorIsSentSuccessfully = false;
            String command = BulbCode.getBulbColorCode(f_r, f_g, f_b, f_w);
            networkInterface.sendData(command, true);
//            Log.d(TAG, "sendColorData: " + command);
            // 设置发送间隔时间
            setSentIntervalTime(500);
//            Log.i(TAG, "run: " + "sendColorData");
        }
    }

    Handler handler = new Handler();
    PostRunnable postRunnable = new PostRunnable();
    private void setSentIntervalTime(int time) {
        handler.removeCallbacks(postRunnable);
        handler.postDelayed(postRunnable, time);
    }
    class PostRunnable implements Runnable {
        @Override
        public void run() {
            colorIsSentSuccessfully = true;
//            Log.i(TAG, "run: " +colorIsSentSuccessfully + "");
        }
    }


    private class NetworkConnectListener implements NetworkInterface.CallbackConnectListener {

        @Override
        public void onStartConn() {
            Log.d(TAG, "onStartConn: 启动链接");
        }

        @Override
        public void onSuccess() {
            Log.d(TAG, "onSuccess: 链接成功");
        }

        @Override
        public void onFaild() {
            Log.d(TAG, "onFaild: 链接失败，请检查网络");
            networkInterface.closeConnect();
        }
    }


    private void showDialog() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                alertDialog.buildProgressDialog();
            }
        });
    }

    private void dismissDialog() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                alertDialog.cancelProgressDialog();
            }
        });
    }

}
