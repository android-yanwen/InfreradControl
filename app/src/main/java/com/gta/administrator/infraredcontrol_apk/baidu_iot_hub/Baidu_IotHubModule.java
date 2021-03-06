package com.gta.administrator.infraredcontrol_apk.baidu_iot_hub;

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
        import com.baidubce.auth.DefaultBceCredentials;
        import com.baidubce.services.iothub.IotHubClient;

//import org.slf4j.LoggerFactory;


/**
 * Created by yanwen on 16/10/12.
 */
public class Baidu_IotHubModule {
    private static Baidu_IotHubModule baidu_iotHubModule = null;
    private IotHubClient iotHubClient = null;
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
    public static final String URI_Endpoint = "/v1/endpoint";
    // Thing
    public static final String URI_Thing = "/thing";
    // thingName
    public static final String URI_ThingName = "?thingName=";
    // principal
    public static final String URI_Principal = "/principal";

//    public static IotHubClient initClient() {
////        DefaultBceCredentials BceCredentials = new DefaultBceCredentials(AK, SK);
////        BceClientConfiguration config= new BceClientConfiguration();
////        config.setCredentials(BceCredentials);
////        BceV1Signer bcev1Signer = new BceV1Signer();
////        BceHttpClient client = new BceHttpClient(config, bcev1Signer);
//
//        return iotHubClient;
//    }

    public static Baidu_IotHubModule getInstance() {
        if (baidu_iotHubModule == null) {
            baidu_iotHubModule = new Baidu_IotHubModule();
        }
        return baidu_iotHubModule;
    }

    public Baidu_IotHubModule() {
//        BceClientConfiguration configuration = new BceClientConfiguration()
//                .withCredentials(new DefaultBceCredentials(Baidu_IotHubModule.AK,
//                        Baidu_IotHubModule.SK))
//                .withEndpoint(Baidu_IotHubModule.HostBody);

        BceClientConfiguration configuration = new BceClientConfiguration();
        DefaultBceCredentials BceCredentials = new DefaultBceCredentials(Baidu_IotHubModule.AK,
                Baidu_IotHubModule.SK);
        configuration.setCredentials(BceCredentials);
        configuration.setEndpoint(Baidu_IotHubModule.HostBody);
        iotHubClient = new IotHubClient(configuration);
    }

    public IotHubClient getIotHubClient() {
        return iotHubClient;
    }
}
