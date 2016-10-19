package com.gta.administrator.infraredcontrol.socket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Created by Administrator on 2016/10/19.
 */
public class SocketUlitity {
    private static SocketUlitity socketUlitity;

//    private InputStream inputStream;
    private OutputStream outputStream;
    private Socket mSocket;

    private static final String IP_ADDRESS = "192.168.1.1";
    private static final int PORT = 6666;

    public static SocketUlitity getInstance() {
        if (socketUlitity == null) {
            socketUlitity = new SocketUlitity();
        }
        return socketUlitity;
    }

    public SocketUlitity() {
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

    public void write(String data) {
        try {
            outputStream.write(data.getBytes("UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
