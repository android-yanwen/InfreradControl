package com.gta.administrator.infraredcontrol.socket;

import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.NetworkInterface;
import java.net.Socket;

/**
 * Created by Administrator on 2016/10/19.
 */
public class SocketUlitity implements com.gta.administrator.infraredcontrol.bean.NetworkInterface{
    private static final String TAG = "SocketUlitity";

    private static SocketUlitity socketUlitity;

//    private InputStream inputStream;
    private OutputStream outputStream = null;
    private static Socket mSocket = null;

    private static final String IP_ADDRESS = "192.168.1.1";
    private static final int PORT = 6666;

    public static SocketUlitity getInstance() {
        if (socketUlitity == null) {
            socketUlitity = new SocketUlitity();
        }
        return socketUlitity;
    }

    public SocketUlitity() {
    }

    private void write(String data) {
        if (outputStream != null) {
            try {
                outputStream.write(data.getBytes("UTF-8"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public void openConnect() {
//        Log.d(TAG, "openConnect: networkInterface");
        if (mSocket == null) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        mSocket = new Socket(IP_ADDRESS, PORT);
                        outputStream = mSocket.getOutputStream();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }

    @Override
    public void closeConnect() {
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
    }

    @Override
    public String receiveData() {
        return null;
    }
}
