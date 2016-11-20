package com.gta.administrator.infraredcontrol.baidu_iot_hub;

import com.baidubce.services.iothub.IotHubClient;
import com.baidubce.services.iothub.model.BaseResponse;
import com.baidubce.services.iothub.model.ListResponse;
import com.baidubce.services.iothub.model.QueryEndpointResponse;

/**
 * Created by yanwen on 16/11/1.
 */
public class Endpoint {
    private static final String TAG = "Endpoint";
//    private InternalRequest request; //请求百度API通过此对象传入参数

    public enum Method {
        CREATE_ENDPOINT,
        LIST_ENDPOINT,
        QUERY_ENDPOINT,
        DELETE_ENDPOINT
    }

    private IotHubClient iotHubClient;
    public Endpoint() {
        iotHubClient = Baidu_IotHubModule.getInstance().getIotHubClient();
    }


    public void requestEndpoint(Method method,String endpointName, RequestEndpointCallbakcListener callbakcListener) {
        switch (method) {
            case CREATE_ENDPOINT:
                createEndpoint(endpointName, callbakcListener);
                break;
            case LIST_ENDPOINT:
                listEndpoints(callbakcListener);
                break;
            case QUERY_ENDPOINT:
                queryEndpoint(endpointName, callbakcListener);
                break;
            case DELETE_ENDPOINT:
                deleteEndpoint(endpointName, callbakcListener);
                break;
            default:
                break;
        }
    }

    public interface RequestEndpointCallbakcListener {

        void createEndpointCallback(QueryEndpointResponse response);

        void listEndpointCallback(ListResponse response);

        void queryEndpointCallback(QueryEndpointResponse response);

        void deleteEndpointCallback(BaseResponse request);

    }


    private void createEndpoint(final String endpointName, final RequestEndpointCallbakcListener callbakcListener) {
        if (iotHubClient == null) {
            return;
        }
        if (callbakcListener == null) {
            return;
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                QueryEndpointResponse response = iotHubClient.createEndpoint(endpointName);
                callbakcListener.createEndpointCallback(response);
            }
        }).start();
    }

    private void listEndpoints(final RequestEndpointCallbakcListener callbakcListener) {
        if (iotHubClient == null) {
            return;
        }
        if (callbakcListener == null) {
            return;
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                ListResponse response = iotHubClient.listEndpoints();
                callbakcListener.listEndpointCallback(response);
            }
        }).start();
    }

    private void queryEndpoint(final String endpointName, final RequestEndpointCallbakcListener callbakcListener) {
        if (iotHubClient == null) {
            return;
        }
        if (callbakcListener == null) {
            return;
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                QueryEndpointResponse response = iotHubClient.queryEndpoint(endpointName);
                callbakcListener.queryEndpointCallback(response);
            }
        }).start();
    }

    private void deleteEndpoint(final String endpointName, final RequestEndpointCallbakcListener callbakcListener) {
        if (iotHubClient == null) {
            return;
        }
        if (callbakcListener == null) {
            return;
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                BaseResponse request = iotHubClient.deleteEndpoint(endpointName);
                callbakcListener.deleteEndpointCallback(request);
            }
        }).start();

    }



//
//    /**
//     *  请求访问endpoint，提供三种访问方式：
//     *  1、获取所有endpoint信息
//     *  2、获取指定名称的endpoint信息
//     *  3、创建新的endpoint，需要传入endpoints名称
//     * @param method   HttpMethodName.GET
//     *                 HttpMethodName.DELETE
//     *                 HttpMethodName.POST
//     * @param endpointName    endpoint名称，GET时可以为空
//     * @param requestEndpointListener    当请求被回应时获取数据调用此接口
//     */
//    public void requestEndpoint(HttpMethodName method, String endpointName, final
//    RequestEndpointListener requestEndpointListener) {
//        // 获取BceHttpClient对象
//        final BceHttpClient client = Baidu_IotHubModule.initClient();
//        URI uri;
//        if (method == HttpMethodName.GET) {
//            if (endpointName != null && !endpointName.isEmpty()) {  // 获取指定名称的endpoint
//                uri = URI.create(Baidu_IotHubModule.Host + Baidu_IotHubModule.URI_Endpoint + "/"
//                        + endpointName);
//            } else { // 获取全部的endpoint
//                uri = URI.create(Baidu_IotHubModule.Host + Baidu_IotHubModule.URI_Endpoint);
//            }
//            // 设置请求参数
//            request = new InternalRequest(method, uri);
//            request.addHeader("Content-Type", "application/json; charset=utf-8");
//            request.addHeader("Host", Baidu_IotHubModule.HostBody);
//        } else if (method == HttpMethodName.DELETE) {
//            if (endpointName != null && !endpointName.isEmpty()) {  // 删除指定名称的endpoint
//                uri = URI.create(Baidu_IotHubModule.Host + Baidu_IotHubModule.URI_Endpoint +
//                        "/" + endpointName);
//                // 设置请求参数
//                request = new InternalRequest(method, uri);
//                request.addHeader("Content-Type", "application/json; charset=utf-8");
//                request.addHeader("Host", Baidu_IotHubModule.HostBody);
//            } else {
//                // 没有传入endpoint名称
//            }
//        } else if (method == HttpMethodName.POST) {
//            if (endpointName != null && !endpointName.isEmpty()) {  // 指定endpoint名称
//                uri = URI.create(Baidu_IotHubModule.Host + Baidu_IotHubModule.URI_Endpoint);
//                request = new InternalRequest(method, uri);
//                request.addHeader("Content-Type", "application/json; charset=utf-8");
//                request.addHeader("Host", Baidu_IotHubModule.HostBody);
//                String body = "{\"endpointName\":\"" + endpointName + "\"}";
//                request.setContent(RestartableInputStream.wrap(body.getBytes()));
//            } else {
//                // 没有输入endpoint名称
//            }
//        }
//
//        // 请求被响应时回调HttpResponseHandler接口
//        final HttpResponseHandler hanlder1 = new BceJsonResponseHandler() {
//            @Override
//            public boolean handle(BceHttpResponse httpResponse, AbstractBceResponse response)
//                    throws Exception {
//                InputStream content = httpResponse.getContent();
//                if (content != null) {
//                    BufferedReader reader = new BufferedReader(new InputStreamReader(content));
//                    String line;
//                    StringBuffer buffer = new StringBuffer();
//                    while ((line = reader.readLine()) != null) {
//                        buffer.append(line);
//                    }
//                    if (requestEndpointListener != null) {
//                        requestEndpointListener.onResponse(buffer);
//                    }
//                    content.close();
//                }
//                return true;
//            }
//        };
////        final HttpResponseHandler hanler2 = new BceErrorResponseHandler() {
////            @Override
////            public boolean handle(BceHttpResponse httpResponse, AbstractBceResponse response)
////                    throws Exception {
////                Log.d(TAG, "handle: " + "error!!!");
////                return super.handle(httpResponse, response);
////            }
////        };
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                // 执行请求
//                client.execute(request, AbstractBceResponse.class, new
//                        HttpResponseHandler[]{hanlder1});
//            }
//        }).start();
//    }
//
//    /**
//     *  获取到数据回调此接口
//     */
//    public interface RequestEndpointListener {
//        void onResponse(final StringBuffer result);
//    }


}
