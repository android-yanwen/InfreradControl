package com.gta.administrator.infraredcontrol;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.gta.administrator.infraredcontrol.bean.NetworkInterface;
import com.gta.administrator.infraredcontrol.database.DBManager;
import com.gta.administrator.infraredcontrol.infrared_code.AirConditionCode;
import com.gta.administrator.infraredcontrol.infrared_code.BulbCode;
import com.gta.administrator.infraredcontrol.mysql.Ir_code;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;


/**
 * Created by Administrator on 2016/11/10.
 */

public class NetworkActivity  extends AppCompatActivity {
    private Context mContext;
    public NetworkInterface networkInterface;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext=this;
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
                toastMsg("发送成功");
            }
            @Override
            public void onSendError() {
                toastMsg("发送失败请重试");
                networkInterface.closeConnect();
                networkInterface.openConnect();
            }
            @Override
            public void socketReceiveData(String data) {
                Log.d(TAG, "socketReceiveData: " + data);
            }
        });
    }
    private void toastMsg(final String msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
            }
        });
    }
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
                DBManager sqliteDB = new DBManager(mContext);
                Ir_code code = new Ir_code();
                try {
                    Log.d(TAG, "Send_ircode Brand_models:"+Brand_models+"Send_ircode key:"+key);
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


    public void sendColorData(String f_r,String f_g,String f_b,String f_w) {
        String command = BulbCode.getBulbColorCode(f_r,f_g,f_b, f_w);
        networkInterface.sendData(command, true);
        Log.d(TAG, "sendColorData: "+command);
    }
}
