package com.gta.administrator.infraredcontrol.socket;

import android.util.Log;
import com.gta.administrator.infraredcontrol.bean.NetworkInterface;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Arrays;

/**
 * Created by Administrator on 2016/10/19.
 */
public class SocketUlitity implements NetworkInterface{
    private static final String TAG = "SocketUlitity";

    private static SocketUlitity socketUlitity;

//    private InputStream inputStream;
    private OutputStream outputStream = null;
    private InputStream inputStream = null;
    private static Socket mSocket = null;

    private static final String IP_ADDRESS = "10.0.0.1";//硬件的SSID
    private static final int PORT = 6666;//端口
    public static final String ESP8266_PWD = "";//硬件ap的密码
    private String data;

    public static SocketUlitity getInstance() {
        if (socketUlitity == null) {
            socketUlitity = new SocketUlitity();
        }
        return socketUlitity;
    }

    public SocketUlitity() {
    }

    private void write(final String data) {
        if (mSocket == null) {
            return;
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (outputStream == null) ;
                try {
                    outputStream.write(data.getBytes("UTF-8"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    private void receive() {
        if (mSocket == null) {
            return;
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (inputStream == null) ;
                byte[] b_data = new byte[1024];
                try {
                    int len = inputStream.read(b_data);

                    if (len > 0) {
                        byte[] a_data = Arrays.copyOf(b_data, len);
                        data = new String(a_data, "UTF-8");
//                        while (receive == null) ;
//                        receive.receive(data);
                        if (callbackListener != null) {
                            callbackListener.socketReceiveData(data);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


    @Override
    public void openConnect() {
//        Log.d(TAG, "openConnect: networkInterface");
        if (mSocket == null) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    // 延时100ms
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    try {
                        connectListener.onStartConn();//开始创建socket
                        mSocket = new Socket(IP_ADDRESS, PORT);
                        outputStream = mSocket.getOutputStream();
                        inputStream = mSocket.getInputStream();
                        connectListener.onSuccess();// 创建socket成功
                    } catch (IOException e) {
                        e.printStackTrace();
                        Log.d(TAG, "socket open failed");
                        connectListener.onFaild();// 创建socket失败
                    }
                }
            }).start();
        }
    }

    @Override
    public void closeConnect() {
        if (mSocket == null) {
            return;
        }
        try {
            mSocket.close();
            outputStream.close();
            mSocket = null;
            outputStream = null;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void sendData(String data) {
        write(data);
        receive();//发送完数据，立马开启线程等待接收
//        receiveData();
    }

    @Override
    public boolean isConnected() {
        return mSocket != null ? mSocket.isConnected() : false;
    }

//    @Override
//    private void receiveData() {
////        Log.d(TAG, "receiveData: ");
//        receive();
//    }


    private CallbackListener callbackListener = null;
    @Override
    public void setCallbackListener(CallbackListener callbackListener) {
        this.callbackListener = callbackListener;
    }

    private CallbackConnectListener connectListener;
    @Override
    public void setCallbackConnectListener(CallbackConnectListener callbackConnectListener) {
        connectListener = callbackConnectListener;
    }


//    private Receive receive;
//    public void setReceiveListener(Receive receiveListener) {
//        receive = receiveListener;
//    }
//    public interface Receive {
//        void receive(final String val);
//    }

}
