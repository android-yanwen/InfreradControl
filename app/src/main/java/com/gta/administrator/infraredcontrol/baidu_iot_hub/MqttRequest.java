package com.gta.administrator.infraredcontrol.baidu_iot_hub;

import android.util.Log;

import com.gta.administrator.infraredcontrol.bean.NetworkInterface;

import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttAsyncClient;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.internal.wire.MqttWireMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;

/**
 * Created by yanwen on 16/10/14.
 */
public class MqttRequest implements NetworkInterface{
    private static MqttRequest mqttRequest = null;

    private MqttClient client = null;
    final String TAG = "MqttRequest";
    // 输入创建时endpoint返回的地址
    final static String endpoint = "tcp://wifimj.mqtt.iot.gz.baidubce.com:1883";//"ssl://endpoint_one.mqtt.iot.gz.baidubce.com:1884";
    // 输入创建时thing返回的username
    final static String username = "wifimj/8266";//"endpoint_one/thing01";
    // 输入创建时principal返回的password
    String password = "898wvxTZXvcgwYQloume7M5QSTK4UD2J2rF5AG6ZGqc=";//"S1JKbYM1g4uyKGIPWp+XZcyp0NEe74Lb8ekX62I6NtU=";
    String userID = /*"b011b9e0888546ccb23f66388408f9bf";//*/"721b6157bff448978218bd5ca94c2461";

    //发布消息主题
    final static String topicA = "topicA";//"policy_one/test-iot-service";
    //订阅消息主题
    final static String topicB = "topicB";

    public static MqttRequest getInstance() {
        if (mqttRequest == null) {
            mqttRequest = new MqttRequest();
        }
        return mqttRequest;
    }

    public MqttRequest() {
        initMqtt();
    }

    /**
     * 设置消息后结构的监听
     * @param callbackListener
     */
//    public void setCallbackListener(MqttCallbackListener callbackListener) {
//        MqttRequest.this.callbackListener = callbackListener;
//        client.setCallback(new MqttCallback() {
//            @Override
//            public void connectionLost(Throwable cause) {
//                //连接丢失后，一般在这里面进行重连
//                Log.d(TAG, "connection lost");
//                MqttRequest.this.callbackListener.connectionLost(cause);
//            }
//
//            @Override
//            public void messageArrived(String topic, MqttMessage message) throws Exception {
//                //subscribe后得到的消息会执行到这里面
//                Log.d(TAG, "messageArrived");
//                Log.d(TAG, "topic=" + topic + " " + new String(message.getPayload()));
//                MqttRequest.this.callbackListener.messageArrived(topic, message);
//
//            }
//
//            @Override
//            public void deliveryComplete(IMqttDeliveryToken token) {
//                //publish后会执行到这里
//                Log.d(TAG, "deliveryComplete");
//                MqttRequest.this.callbackListener.deliveryComplete(token);
//            }
//        });
//
//    }

    private void init() {
        TrustManagerFactory tmf = null;
        try {
            tmf = TrustManagerFactory.getInstance("X509");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        try {
            tmf.init((KeyStore) null);
        } catch (KeyStoreException e) {
            e.printStackTrace();
        }
        TrustManager[] trustManagers = tmf.getTrustManagers();

        SSLContext ctx = null;
        try {
            ctx = SSLContext.getInstance("TLS");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        try {
            ctx.init(null, trustManagers, null);
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
    }


    private void initMqtt()  {
        init();

//        MqttConnectOptions options = setConnectOptions();

        client = instanceMqttClient(userID);

//        openConnect();

    }

    /**
     * 获取MqttClient实例
     * @param userID
     * @return
     */
    private MqttClient instanceMqttClient(String userID) {
        try {
            // 实例化客户端
            client = new MqttClient(endpoint, userID, new MemoryPersistence());
        } catch (MqttException e) {
            e.printStackTrace();
        }
        return client;

    }


    /**
     * 发布一条消息
     * @param msg
     */
    private void publishMessage(final String msg) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                MqttMessage message = new MqttMessage();
                message.setPayload(msg.getBytes());
                try {
                    client.publish(topicA, message);  //发布消息
                } catch (MqttException e) {
                    e.printStackTrace();
                    if (callbackListener != null) {
//                        Log.d(TAG, "请检查网络");
                        callbackListener.onSendError();
                    }


                }
            }
        }).start();

    }

    /**
     * 订阅一次消息
     */
    private void subscribeMessage() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    client.subscribe(topicB);  // 订阅消息
                } catch (MqttException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    /**
     * 设置链接必不可少的参数
     * @return
     */
    public MqttConnectOptions getConnectOptions() {
        MqttConnectOptions options = new MqttConnectOptions();
        options.setCleanSession(true);
        options.setUserName(username);
        options.setPassword(password.toCharArray());
        options.setConnectionTimeout(10);
        options.setKeepAliveInterval(20); //设置心跳链接次数

//        options.setSocketFactory(ctx.getSocketFactory());

        return options;
    }

    public void openConnect(/*final MqttConnectOptions options*/) {
        if (client != null && !client.isConnected()) {
            final MqttConnectOptions options = getConnectOptions();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    //确保链接监听接口不为空,延时100ms
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    try {
                        // 启动链接
                        connectStatus.onStartConn();
                        client.connect(options);
//                    listener = client.connectWithResult(options);
                        // 链接成功
                        connectStatus.onSuccess();
                    } catch (MqttException e) {
                        e.printStackTrace();
                        // 链接失败
                        connectStatus.onFaild();
                    }
                }
            }).start();
        }
    }

    /**
     * 判断是否处于链接状态
     *
     * @return
     */
    public boolean isConnected() {
        if (client == null) {
            return false;
        }
        return client.isConnected();
    }

    public void closeConnect() {
        // 客户机断开连接
        if (client != null && client.isConnected()) {
            try {
                client.disconnect();
                client = null;
            } catch (MqttException e1) {
                e1.printStackTrace();
            }
        }
        closeMqttRequestThis();
    }

    @Override
    public void sendData(String data, boolean isReceived) {
        publishMessage(data);
        // 是否接收，即是否订阅消息
        if (isReceived) {
            subscribeMessage();
        }
    }


    @Override
    public void setCallbackListener(CallbackListener callbackListener) {
        this.callbackListener = callbackListener;
        client.setCallback(new MqttCallback() {
            @Override
            public void connectionLost(Throwable cause) {
                //连接丢失后，一般在这里面进行重连
//                Log.d(TAG, "connection lost");
                MqttRequest.this.callbackListener.connectionLost(cause);
            }

            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {
                //subscribe后得到的消息会执行到这里面
                Log.d(TAG, "messageArrived");
                Log.d(TAG, "topic=" + topic + " " + new String(message.getPayload()));
                MqttRequest.this.callbackListener.messageArrived(topic, message);

            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {
                //publish后会执行到这里
                Log.d(TAG, "deliveryComplete");
                MqttRequest.this.callbackListener.deliveryComplete(token);
            }
        });
    }


    private CallbackConnectListener connectStatus;
    @Override
    public void setCallbackConnectListener(CallbackConnectListener callbackConnectListener) {
        connectStatus = callbackConnectListener;
    }

    /**
     * 关闭当前类实例
     */
    private void closeMqttRequestThis() {
//        if (client != null) {
//            closeConnect();
//        }
        mqttRequest = null;
    }

    private CallbackListener callbackListener = null;






    /**
     * 链接成功后，数据收发过程的监听，以及链接意外丢失的监听
     */
//    public interface MqttCallbackListener {
//        void connectionLost(Throwable cause);
//
//        void messageArrived(String topic, MqttMessage message);
//
//        void deliveryComplete(IMqttDeliveryToken token);
//
//        void onSendError(); //publish发送失败
//    }

//    private MqttConnectStatusListener connectStatus;
//
//    public void setMqttConnectStatusListener(MqttConnectStatusListener connectStatus) {
//        this.connectStatus = connectStatus;
//    }
//
//    /**
//     * 开始连接时网络状态监听
//     */
//    public interface MqttConnectStatusListener {
//        void onStartConn();  //连接刚刚启动时调用
//
//        void onSuccess();   // 连接成功后调用
//
//        void onFaild();   //因网络问题连接失败后调用
//    }

}
