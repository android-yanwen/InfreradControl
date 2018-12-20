package com.gta.administrator.infraredcontrol_apk.socket;

import android.util.Log;

import com.gta.administrator.infraredcontrol_apk.bean.NetworkInterface;

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
                try {
                    Log.d(TAG, "write socket now ! data:"+data);
                    outputStream.write(data.getBytes("UTF-8"));
                } catch (IOException e) {
                    e.printStackTrace();
                    closeConnect();
                    callbackListener.onSendError();
                }
                Log.d(TAG, "发送数据完毕，开启接收线程 ! ");
                receive();
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
                try {
                    byte[] b_data = new byte[1024];
                    int length = -1;
                    int i=0;
                    //等待数据到来，250ms超时，断开连接，放弃接收！
                    while ((length=inputStream.read(b_data)) == -1)
                    {
                        i++;
                        Thread.sleep(1);
                        Log.d(TAG, "receive wait time :"+i);
                        if (i>5) {
                            closeConnect();
                            Log.d(TAG, "receive out of time !: ");
                            return;
                        }
                    }
                        byte[] a_data = Arrays.copyOf(b_data, length);
                        data = new String(a_data, "UTF-8");
                    Log.d(TAG, "receive over ! 准备关闭连接！");
                    closeConnect();
                    if (callbackListener != null) {
                        callbackListener.socketReceiveData(data);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    closeConnect();
                    e.printStackTrace();
                }
            }
        }).start();
    }


    @Override
    public void openConnect() {
//  短连接不允许外部自己打开连接
    }

    public void openConnect(String data) {
        class OpenConnect extends Thread{
            private String data;
            public OpenConnect(String data) {
                this.data=data;
            }
            public void run()
            {
                Log.d(TAG, "socket:openConnect !");
                try {
                    connectListener.onStartConn();//开始创建socket
                    mSocket = new Socket(IP_ADDRESS, PORT);
                    outputStream = mSocket.getOutputStream();
                    inputStream = mSocket.getInputStream();
                    connectListener.onSuccess();// 创建socket成功
                    Log.d(TAG, "run: 创建socket成功,并准备写出socket"+data);
                    write(data);
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.d(TAG, "socket open failed");
                    connectListener.onFaild();// 创建socket失败
                }
            }
        }
        if (mSocket==null) {
            OpenConnect openconnet = new OpenConnect(data);
            openconnet.start();
        }
    }

    @Override
    public void closeConnect() {
        try {
            if (mSocket!=null){
                mSocket.close();
                mSocket = null;
            }
            if (outputStream != null){
                outputStream.close();
                outputStream = null;
            }
            Log.d(TAG, "closeConnect: 关闭成功！");
        } catch (IOException e) {
            Log.d(TAG, "closeConnect: 关闭失败！");
            e.printStackTrace();
        }

    }

    /**
     * 发送数据
     * @param data  发送的数据
     * @param isReceived  是否允许接收
     */
    @Override
    public void sendData(String data, boolean isReceived) {
        if (mSocket==null) {
            Log.d(TAG, "open connect and sendData: "+data);
            openConnect(data);
        }
    }

    @Override
    public boolean isConnected() {
        return mSocket != null ? mSocket.isConnected() : false;
    }


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
}
