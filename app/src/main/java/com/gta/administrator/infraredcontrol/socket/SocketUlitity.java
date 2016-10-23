package com.gta.administrator.infraredcontrol.socket;

import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.NetworkInterface;
import java.net.Socket;
import java.util.Arrays;

/**
 * Created by Administrator on 2016/10/19.
 */
public class SocketUlitity implements com.gta.administrator.infraredcontrol.bean.NetworkInterface{
    private static final String TAG = "SocketUlitity";

    private static SocketUlitity socketUlitity;

//    private InputStream inputStream;
    private OutputStream outputStream = null;
    private InputStream inputStream = null;
    private static Socket mSocket = null;

    private static final String IP_ADDRESS = "10.0.0.1";//硬件的SSID
    private static final int PORT = 6666;//端口
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
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (outputStream == null);
                try {
                    outputStream.write(data.getBytes("UTF-8"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    private void receive() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (inputStream == null);
                byte[] b_data = new byte[1024];
                try {
                    int len = inputStream.read(b_data);

                    if (len > 0) {
                        byte[] a_data = Arrays.copyOf(b_data, len);
                        data = new String(a_data, "UTF-8");
                        while (receive == null);
                        receive.receive(data);
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
                    try {
                        mSocket = new Socket(IP_ADDRESS, PORT);
                        outputStream = mSocket.getOutputStream();
                        inputStream = mSocket.getInputStream();
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
    public void receiveData() {
//        Log.d(TAG, "receiveData: ");
        receive();
    }



    private Receive receive;
    public void setReceiveListener(Receive receiveListener) {
        receive = receiveListener;
    }
    public interface Receive {
        void receive(final String val);
    }

}
