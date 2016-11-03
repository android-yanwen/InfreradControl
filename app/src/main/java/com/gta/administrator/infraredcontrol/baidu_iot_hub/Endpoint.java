package com.gta.administrator.infraredcontrol.baidu_iot_hub;

import android.util.Log;

import com.baidubce.BceClientConfiguration;
import com.baidubce.auth.BceV1Signer;
import com.baidubce.auth.DefaultBceCredentials;
import com.baidubce.http.BceHttpClient;
import com.baidubce.http.BceHttpResponse;
import com.baidubce.http.HttpMethodName;
import com.baidubce.http.handler.BceErrorResponseHandler;
import com.baidubce.http.handler.BceJsonResponseHandler;
import com.baidubce.http.handler.HttpResponseHandler;
import com.baidubce.internal.InternalRequest;
import com.baidubce.internal.RestartableInputStream;
import com.baidubce.model.AbstractBceResponse;
import com.gta.administrator.infraredcontrol.json_utils.JsonUtiles;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yanwen on 16/11/1.
 */
public class Endpoint {


    private static final String TAG = "Endpoint";

    /**
     * 请求获取所有的Endpoint
     * @param responseCallbackListener 获取到数据以后调用接口
     */
    public void requestGetEndpointList(final GetEndpointListCallbackListener responseCallbackListener) {
        final BceHttpClient client = Baidu_IotHubModule.initClient();
        URI uri = URI.create(Baidu_IotHubModule.Host + Baidu_IotHubModule.URI_EndpointList) ;
        final InternalRequest request = new InternalRequest(HttpMethodName.GET,uri);
        request.addHeader("Content-Type", "application/json; charset=utf-8");
        request.addHeader("Host", Baidu_IotHubModule.HostBody);
//		request.addParameter("endpointName", "endpoint_one");
//		request.addParameter("thingName", "thing02");
//		String body = "{\"endpointName\":\"endpoint03\"}";
//		request.setContent(RestartableInputStream.wrap(body.getBytes()));
        final HttpResponseHandler hanlder1 = new BceJsonResponseHandler() {
            @Override
            public boolean handle(BceHttpResponse httpResponse, AbstractBceResponse response) throws Exception {
                InputStream content = httpResponse.getContent();
                if (content != null) {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(content));
                    String line;
                    StringBuffer buffer = new StringBuffer();
                    while ((line = reader.readLine()) != null) {
                        buffer.append(line);
                    }
                    if (responseCallbackListener != null) {
                        responseCallbackListener.onResponse(parseEndpoints(buffer));
                    }
                    content.close();
                }
                return true;
            }
        };
        final HttpResponseHandler hanler2 = new BceErrorResponseHandler(){
            @Override
            public boolean handle(BceHttpResponse httpResponse, AbstractBceResponse response)
                    throws Exception {
                Log.d(TAG, "handle: " + "error!!!");
                return super.handle(httpResponse, response);
            }
        };
        new Thread(new Runnable() {
            @Override
            public void run() {
                client.execute(request, AbstractBceResponse.class, new HttpResponseHandler[]{hanlder1, hanler2});
            }
        }).start();
    }

    public interface GetEndpointListCallbackListener {
        // 回应成功
        void onResponse(List<String> endpoints);
    }


    /**
     * 将接收到的json数据进行解析，返回出endpointName列表
     * @param buffer   json数据
     * @return    解析出的endpointNames列表
     */
    public List<String> parseEndpoints(StringBuffer buffer) {
        List<String> endpointNames = new ArrayList<>();
        JsonUtiles jsonUtiles = new JsonUtiles(buffer.toString());
        String[] names = jsonUtiles.getEndpointsName();
        for (String name : names) {
//            Log.d(tag, name.toString());
            endpointNames.add(name);
        }
        return endpointNames;
    }

    public String parseEndpoint(StringBuffer buffer) {
        JsonUtiles jsonUtiles = new JsonUtiles(buffer.toString());
        return jsonUtiles.getEndpointName();
    }


    /**
     * 获取指定的Endpoint
     * @param endpointName    指定的EndpointName
     * @param responseCallbackListener  回调接口
     */
    public void requestGetEndpointName(String endpointName, final GetEndpointCallbackListener responseCallbackListener) {
        final BceHttpClient client = Baidu_IotHubModule.initClient();
        URI uri = URI.create(Baidu_IotHubModule.Host + Baidu_IotHubModule.URI_EndpointList + "/" + endpointName);
        final InternalRequest request = new InternalRequest(HttpMethodName.GET,uri);
        request.addHeader("Content-Type", "application/json; charset=utf-8");
        request.addHeader("Host", Baidu_IotHubModule.HostBody);
        final HttpResponseHandler hanlder1 = new BceJsonResponseHandler() {
            @Override
            public boolean handle(BceHttpResponse httpResponse, AbstractBceResponse response) throws Exception {
                InputStream content = httpResponse.getContent();
                if (content != null) {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(content));
                    String line;
                    StringBuffer buffer = new StringBuffer();
                    while ((line = reader.readLine()) != null) {
                        buffer.append(line);
                    }
//                    Log.d(TAG, "handle: " + buffer.toString());
                    if (responseCallbackListener != null) {
                        responseCallbackListener.onResponse(parseEndpoint(buffer));
                    }
                    content.close();
                }
                return true;
            }
        };
        final HttpResponseHandler hanler2 = new BceErrorResponseHandler();
        new Thread(new Runnable() {
            @Override
            public void run() {
                client.execute(request, AbstractBceResponse.class, new HttpResponseHandler[]{hanlder1, hanler2});
            }
        }).start();
    }

    public interface GetEndpointCallbackListener {
        void onResponse(String name);

    }


    public void requestCreateEndpoint(final CreateEndpointCallbackListener responseCallbackListener) {
//        final BceHttpClient client = Baidu_IotHubModule.initClient();
//        URI uri = URI.create(Baidu_IotHubModule.Host + Baidu_IotHubModule.URI_EndpointList) ;
//        final InternalRequest request = new InternalRequest(HttpMethodName.POST,uri);
//        request.addHeader("Content-Type", "application/json; charset=utf-8");
//        request.addHeader("Host", Baidu_IotHubModule.HostBody);
//		String body = "{\"endpointName\":\"endpoint0000\"}";
//		request.setContent(RestartableInputStream.wrap(body.getBytes()));
//        final HttpResponseHandler hanlder1 = new BceJsonResponseHandler() {
//            @Override
//            public boolean handle(BceHttpResponse httpResponse, AbstractBceResponse response)
// throws Exception {
//                InputStream content = httpResponse.getContent();
//                if (content != null) {
//                    BufferedReader reader = new BufferedReader(new InputStreamReader(content));
//                    String line;
//                    StringBuffer buffer = new StringBuffer();
//                    while ((line = reader.readLine()) != null) {
//                        buffer.append(line);
//                    }
//                    if (responseCallbackListener != null) {
//                        responseCallbackListener.onResponse(buffer.toString());
//                    }
//                    content.close();
//                }
//                return true;
//            }
//        };
//        final HttpResponseHandler hanler2 = new BceErrorResponseHandler();


        DefaultBceCredentials BceCredentials = new DefaultBceCredentials
                ("039557ee1e2b4c43956ee277288ef046", "10667bdc236b4c9eb86bebb8a87e0cc6");

        BceClientConfiguration config = new BceClientConfiguration();
//		config.withProxyPreemptiveAuthenticationEnabled(true);
        config.setCredentials(BceCredentials);

        URI uri = URI.create("http://iot.gz.baidubce.com/v1/endpoint");
        final InternalRequest request = new InternalRequest(HttpMethodName.POST, uri);

        request.addHeader("Content-Type", "application/json; charset=utf-8");
        request.addHeader("Host", "iot.gz.baidubce.com");
//		request.addParameter("endpointName", "endpoint_one");
//		request.addParameter("thingName", "thing02");

        String body = "{\"endpointName\":\"wifimjsss11111\"}";
        request.setContent(RestartableInputStream.wrap(body.getBytes()));

        BceV1Signer bcev1Signer = new BceV1Signer();
        final BceHttpClient client = new BceHttpClient(config, bcev1Signer);

        final HttpResponseHandler hanlder1 = new BceJsonResponseHandler() {
            @Override
            public boolean handle(BceHttpResponse httpResponse, AbstractBceResponse response)
                    throws Exception {
                InputStream content = httpResponse.getContent();
                if (content != null) {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(content));
                    String line;
                    StringBuffer buffer = new StringBuffer();
                    while ((line = reader.readLine()) != null) {
                        buffer.append(line);
                    }
                    if (responseCallbackListener != null) {
                        responseCallbackListener.onResponse(buffer.toString());
                    }
                    content.close();
                }
                return true;
            }
        };
        final HttpResponseHandler hanler2 = new BceErrorResponseHandler();

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
        client.execute(request, AbstractBceResponse.class, new HttpResponseHandler[]{hanlder1,
                hanler2});
//            }
//        }).start();
    }

    public interface CreateEndpointCallbackListener {
        void onResponse(String response);

    }


}
