package com.gta.administrator.infraredcontrol.baidu_iot_hub;
import android.os.Handler;
import android.util.Log;

//import com.baidubce.BceClientConfiguration;
//import com.baidubce.auth.BceV1Signer;
//import com.baidubce.auth.DefaultBceCredentials;
//import com.baidubce.http.BceHttpClient;
//import com.baidubce.http.BceHttpResponse;
//import com.baidubce.http.HttpMethodName;
//import com.baidubce.http.handler.BceErrorResponseHandler;
//import com.baidubce.http.handler.BceJsonResponseHandler;
//import com.baidubce.http.handler.HttpResponseHandler;
//import com.baidubce.internal.InternalRequest;
//import com.baidubce.model.AbstractBceResponse;

//import com.baidubce.BceClientConfiguration;
//import com.baidubce.auth.BceV1Signer;
//import com.baidubce.auth.DefaultBceCredentials;
//import com.baidubce.http.BceHttpClient;
//import com.baidubce.http.BceHttpResponse;
//import com.baidubce.http.DefaultRetryPolicy;
//import com.baidubce.http.HttpMethodName;
//import com.baidubce.http.handler.BceErrorResponseHandler;
//import com.baidubce.http.handler.BceJsonResponseHandler;
//import com.baidubce.http.handler.HttpResponseHandler;
//import com.baidubce.internal.InternalRequest;
//import com.baidubce.model.AbstractBceResponse;

import com.baidubce.BceClientConfiguration;
import com.baidubce.auth.BceV1Signer;
import com.baidubce.auth.DefaultBceCredentials;
import com.baidubce.http.BceHttpClient;
import com.baidubce.http.BceHttpResponse;
import com.baidubce.http.DefaultRetryPolicy;
import com.baidubce.http.HttpMethodName;
import com.baidubce.http.handler.BceErrorResponseHandler;
import com.baidubce.http.handler.BceJsonResponseHandler;
import com.baidubce.http.handler.HttpResponseHandler;
import com.baidubce.internal.InternalRequest;
import com.baidubce.model.AbstractBceResponse;
import com.gta.administrator.infraredcontrol.json_utils.JsonUtiles;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
//import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;

/**
 * Created by yanwen on 16/10/12.
 */
public class Baidu_IotHubModule {
    private static final String TAG = "Baidu_IotHubModule";
    // Access Key ID
    public static final String AK = /*"af656357a5844e67b008b4b889ef207f";//*/"039557ee1e2b4c43956ee277288ef046";
//    public static final String AK = "af656357a5844e67b008b4b889ef207f";
    // Secret Key ID
    public static final String SK = /*"ec7958abb69541b9957b594a1dda82eb";//*/"10667bdc236b4c9eb86bebb8a87e0cc6";
//    public static final String SK = "ec7958abb69541b9957b594a1dda82eb";
	// Host Body
    public static final String HostBody = "iot.gz.baidubce.com";
    // Host
    public static final String Host = "http://iot.gz.baidubce.com";
    // endpoint
    public static final String URI_EndpointList = "/v1/endpoint";

    public static BceHttpClient initClient() {
        DefaultBceCredentials BceCredentials = new DefaultBceCredentials(AK, SK);
        BceClientConfiguration config= new BceClientConfiguration();
        config.setCredentials(BceCredentials);
        BceV1Signer bcev1Signer = new BceV1Signer();
        BceHttpClient client = new BceHttpClient(config, bcev1Signer);
        return client;
    }




}
