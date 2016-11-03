package com.gta.administrator.infraredcontrol.baidu_iot_hub;

import com.baidubce.http.BceHttpClient;
import com.baidubce.http.BceHttpResponse;
import com.baidubce.http.HttpMethodName;
import com.baidubce.http.handler.BceJsonResponseHandler;
import com.baidubce.http.handler.HttpResponseHandler;
import com.baidubce.internal.InternalRequest;
import com.baidubce.internal.RestartableInputStream;
import com.baidubce.model.AbstractBceResponse;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;

/**
 * Created by yanwen on 16/11/3.
 */
public class Thing {
    private InternalRequest request; //请求百度API通过此对象传入参数

    /**
     * 请求访问endpoint，提供三种访问方式：
     * 1、获取所有endpoint信息
     * 2、获取指定名称的endpoint信息
     * 3、创建新的endpoint，需要传入endpoints名称
     *
     * @param method                  HttpMethodName.GET
     *                                HttpMethodName.DELETE
     *                                HttpMethodName.POST
     * @param endpointName            endpoint名称，不能为空
     * @param thingName               thing，GET时可以为空
     * @param requestEndpointListener 当请求被回应时获取数据调用此接口
     */
    public void requestThing(HttpMethodName method, String endpointName, String thingName, final
    RequestEndpointListener requestEndpointListener) {
        // 获取BceHttpClient对象
        final BceHttpClient client = Baidu_IotHubModule.initClient();
        URI uri = null;
        if (method == HttpMethodName.GET) {
            if (endpointName != null && !endpointName.isEmpty() && thingName != null &&
                    !thingName.isEmpty()) {  //获取指定的thing
                uri = URI.create(Baidu_IotHubModule.Host + Baidu_IotHubModule.URI_Endpoint + "/"
                        + endpointName + Baidu_IotHubModule.URI_Thing + "/" + thingName);
            } else if (endpointName != null && !endpointName.isEmpty()) { //获取thing列表
                uri = URI.create(Baidu_IotHubModule.Host + Baidu_IotHubModule.URI_Endpoint + "/"
                        + endpointName + Baidu_IotHubModule.URI_Thing);
            } else {
                // endpoint不能为空
                return;
            }
            // 设置请求参数
            request = new InternalRequest(method, uri);
            request.addHeader("Content-Type", "application/json; charset=utf-8");
            request.addHeader("Host", Baidu_IotHubModule.HostBody);
        } else if (method == HttpMethodName.DELETE) {
            if (endpointName != null && !endpointName.isEmpty() && thingName != null &&
                    !thingName.isEmpty()) {  // 删除指定名称的thing
                uri = URI.create(Baidu_IotHubModule.Host + Baidu_IotHubModule.URI_Endpoint + "/"
                        + endpointName + Baidu_IotHubModule.URI_Thing + "/" + thingName);
                // 设置请求参数
                request = new InternalRequest(method, uri);
                request.addHeader("Content-Type", "application/json; charset=utf-8");
                request.addHeader("Host", Baidu_IotHubModule.HostBody);
            } else {
                // endpoint、thing都不能为空
                return;
            }
        } else if (method == HttpMethodName.POST) { //创建thing
            if (endpointName != null && !endpointName.isEmpty() && thingName != null &&
                    !thingName.isEmpty()) {
                uri = URI.create(Baidu_IotHubModule.Host + Baidu_IotHubModule.URI_Endpoint + "/"
                        + endpointName + Baidu_IotHubModule.URI_Thing);
                request = new InternalRequest(method, uri);
                request.addHeader("Content-Type", "application/json; charset=utf-8");
                request.addHeader("Host", Baidu_IotHubModule.HostBody);
                String body = "{\"endpointName\":\"" + endpointName + "\"}" +
                        "{\"thingName\":\"" + thingName + "\"}";
                request.setContent(RestartableInputStream.wrap(body.getBytes()));
//                String body1 = "{\"thingName\":\"" + thingName + "\"}";
//                request.setContent(RestartableInputStream.wrap(body1.getBytes()));
            } else {
                // endpoint、thing都不能为空
                return;
            }
        }

        // 请求被响应时回调HttpResponseHandler接口
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
                    if (requestEndpointListener != null) {
                        requestEndpointListener.onResponse(buffer);
                    }
                    content.close();
                }
                return true;
            }
        };
//        final HttpResponseHandler hanler2 = new BceErrorResponseHandler() {
//            @Override
//            public boolean handle(BceHttpResponse httpResponse, AbstractBceResponse response)
//                    throws Exception {
//                Log.d(TAG, "handle: " + "error!!!");
//                return super.handle(httpResponse, response);
//            }
//        };
        new Thread(new Runnable() {
            @Override
            public void run() {
                // 执行请求
                client.execute(request, AbstractBceResponse.class, new
                        HttpResponseHandler[]{hanlder1});
            }
        }).start();
    }

    /**
     *  获取到数据回调此接口
     */
    public interface RequestEndpointListener {
        void onResponse(StringBuffer result);
    }



}
