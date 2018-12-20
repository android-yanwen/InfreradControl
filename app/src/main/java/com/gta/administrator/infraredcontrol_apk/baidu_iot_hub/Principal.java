package com.gta.administrator.infraredcontrol_apk.baidu_iot_hub;

import com.baidubce.services.iothub.IotHubClient;
import com.baidubce.services.iothub.model.BaseResponse;
import com.baidubce.services.iothub.model.CreatePrincipalResponse;
import com.baidubce.services.iothub.model.ListResponse;

/**
 * Created by yanwen on 16/11/3.
 */
public class Principal {
    private IotHubClient iotHubClient;

    public enum Method {
        CREATE_PRINCIPAL,
        LIST_PRINCIPAL,
        ATTACH_THING_TO_PRINCIPAL,
        DELETE_THING_TO_PRINCIPAL,
        REGENERATE_CERT_PRINCIPAL,
        DELETE_PRINCIPAL
    }
    public Principal() {
        iotHubClient = Baidu_IotHubModule.getInstance().getIotHubClient();
    }


    public void requestPrincipal(Method method, String endpointName, String thingName, String principalName,
                                 RequestPrincipalCallbakcListener callbakcListener) {
        switch (method) {
            case CREATE_PRINCIPAL:
                createPrincipal(endpointName, principalName, callbakcListener);
                break;
            case LIST_PRINCIPAL:
                listPrincipals(endpointName, thingName, callbakcListener);
                break;
            case ATTACH_THING_TO_PRINCIPAL:
                attachThingToPrincipal(endpointName, thingName, principalName, callbakcListener);
                break;
            case DELETE_THING_TO_PRINCIPAL:
                removeThingToPrincipal(endpointName, thingName, principalName, callbakcListener);
                break;
            case REGENERATE_CERT_PRINCIPAL:
                regenerateCert(endpointName, principalName, callbakcListener);
                break;
            case DELETE_PRINCIPAL:
                deletePrincipal(endpointName, principalName, callbakcListener);
                break;
        }
    }


    public interface RequestPrincipalCallbakcListener {
        void createPrincipalCallback(CreatePrincipalResponse response);

        void listPrincipalCallback(ListResponse response);

        void regeneratePrincipalCallback(CreatePrincipalResponse response);

        void attachOrRemoveThingToPrincipalCallback(BaseResponse response);
    }

    private void createPrincipal(final String endpointName, final String principalName,
                                 final RequestPrincipalCallbakcListener callbakcListener) {
        if (endpointName == null) {
            return;
        }
        if (principalName == null) {
            return;
        }
        if (callbakcListener == null) {
            return;
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                CreatePrincipalResponse response = iotHubClient.createPrincipal(endpointName,
                        principalName);
                callbakcListener.createPrincipalCallback(response);
            }
        }).start();
    }

    private void listPrincipals(final String endpointName, final String thingName,
                               final RequestPrincipalCallbakcListener callbakcListener) {
        if (callbakcListener == null) {
            return;
        }
        if (endpointName == null) {
            return;
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                ListResponse response;
                if (thingName == null) {
                    response = iotHubClient.listPrincipals(endpointName);
                } else {
                    response = iotHubClient.listPrincipals(endpointName, thingName);
                }
                callbakcListener.listPrincipalCallback(response);
            }
        }).start();
    }

    private void attachThingToPrincipal(final String endpointName, final String thingName, String
            principalName, final RequestPrincipalCallbakcListener callbakcListener) {
        if (callbakcListener == null) {
            return;
        }
        if (endpointName == null) {
            return;
        }
        if (thingName == null) {
            return;
        }
        if (principalName == null) {
            return;
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                BaseResponse response=iotHubClient.attachThingToPrincipal(endpointName, thingName, endpointName);
                callbakcListener.attachOrRemoveThingToPrincipalCallback(response);
            }
        }).start();
    }
    private void removeThingToPrincipal(final String endpointName, final String thingName, String
            principalName, final RequestPrincipalCallbakcListener callbakcListener) {
        if (callbakcListener == null) {
            return;
        }
        if (endpointName == null) {
            return;
        }
        if (thingName == null) {
            return;
        }
        if (principalName == null) {
            return;
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                BaseResponse response=iotHubClient.removePrincipalToPolicy(endpointName,
                        thingName, endpointName);
                callbakcListener.attachOrRemoveThingToPrincipalCallback(response);
            }
        }).start();
    }

    private void regenerateCert(final String endpointName, final String principalName,
                              final RequestPrincipalCallbakcListener callbakcListener) {
        if (callbakcListener == null) {
            return;
        }
        if (endpointName == null) {
            return;
        }
        if (principalName == null) {
            return;
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                CreatePrincipalResponse response = iotHubClient.regenerateCert(endpointName,
                        principalName, "cert");
                callbakcListener.regeneratePrincipalCallback(response);
            }
        }).start();

    }

    private void deletePrincipal(final String endpointName, final String principalName,
                                 final RequestPrincipalCallbakcListener callbakcListener) {
        if (callbakcListener == null) {
            return;
        }
        if (endpointName == null) {
            return;
        }
        if (principalName == null) {
            return;
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                BaseResponse response = iotHubClient.deletePrincipal(endpointName, principalName);
                callbakcListener.attachOrRemoveThingToPrincipalCallback(response);
            }
        }).start();
    }










//
//    private InternalRequest request; //请求百度API通过此对象传入参数
//    private BceHttpClient client;
//
//    public Principal() {
//        // 获取BceHttpClient对象
//        client = Baidu_IotHubModule.initClient();
//    }
//
//    public void requestPrincipal(HttpMethodName method, String endpointName, String thingName,
//                                 String principalName, boolean isCreatePrincipalPwd, final
//                                 RequestPrincipalListener
//                                         requestEndpointListener) {
//        URI uri = null;
//        if (method == HttpMethodName.GET) {
//            if (endpointName != null && !endpointName.isEmpty()) {
//                if (principalName != null && !principalName.isEmpty()) { //获取指定principal信息
//                    // uri格式：http://iot.gz.baidubce
//                    // .com/v1/endpoint/{endpointname}/principal/{principalName}
//                    uri = URI.create(Baidu_IotHubModule.Host + Baidu_IotHubModule.URI_Endpoint + "/"
//                            + endpointName + Baidu_IotHubModule.URI_Principal + "/" +
//                            principalName);
//                } else {  // 获取principal列表参数thingname可为空，可不为空
//                    // uri格式：http://iot.gz.baidubce
//                    // .com/v1/endpoint/{endpointname}/principal?thingName={thingName}
//                    if (thingName != null && !thingName.isEmpty()) {
//                        uri = URI.create(Baidu_IotHubModule.Host + Baidu_IotHubModule
//                                .URI_Endpoint + "/"
//                                + endpointName + Baidu_IotHubModule.URI_Principal +
//                                Baidu_IotHubModule
//                                .URI_ThingName + thingName);
//                    } else {
//                        uri = URI.create(Baidu_IotHubModule.Host + Baidu_IotHubModule.URI_Endpoint + "/"
//                                + endpointName + Baidu_IotHubModule.URI_Principal);
//                    }
//                }
//            } else {
//                // endpoint 不能为空
//                return;
//            }
//            // 设置请求参数
//            request = new InternalRequest(method, uri);
//            request.addHeader("Content-Type", "application/json; charset=utf-8");
//            request.addHeader("Host", Baidu_IotHubModule.HostBody);
//            if (thingName != null && !thingName.isEmpty()) {
//                request.addParameter("thingName", thingName);
//            }
//        } else if (method == HttpMethodName.DELETE) {
//            if (endpointName != null && !endpointName.isEmpty() && principalName != null &&
//                    !principalName.isEmpty()) {  // 删除指定名称的 principalName
//                // uri格式：http://iot.gz.baidubce
//                // .com/v1/endpoint/{endpointname}/principal/{principalName}
//                uri = URI.create(Baidu_IotHubModule.Host + Baidu_IotHubModule.URI_Endpoint + "/"
//                        + endpointName + Baidu_IotHubModule.URI_Principal + "/" + principalName);
//                // 设置请求参数
//                request = new InternalRequest(method, uri);
//                request.addHeader("Content-Type", "application/json; charset=utf-8");
//                request.addHeader("Host", Baidu_IotHubModule.HostBody);
//            } else {
//                // endpoint、principalName 都不能为空
//                return;
//            }
//        } else if (method == HttpMethodName.POST) {
//            if (endpointName != null && !endpointName.isEmpty()) {
//                if (principalName != null && !principalName.isEmpty()) {
//                    if (!isCreatePrincipalPwd) {//创建principal
//                        // uri格式：http://iot.gz.baidubce.com/v1/endpoint/{endpointname}/principal
//                        // Post Param: endpointName, principalName
//                        uri = URI.create(Baidu_IotHubModule.Host + Baidu_IotHubModule
//                                .URI_Endpoint + "/"
//                                + endpointName + Baidu_IotHubModule.URI_Principal);
//                        request = new InternalRequest(method, uri);
//                        request.addHeader("Content-Type", "application/json; charset=utf-8");
//                        request.addHeader("Host", Baidu_IotHubModule.HostBody);
//                        String body = "{\"endpointName\":\"" + endpointName + "\"}" +
//                                "{\"principalName\":\"" + principalName + "\"}";
//                        request.setContent(RestartableInputStream.wrap(body.getBytes()));
//                    } else {//重新生成密钥
//                        // uri格式：http://iot.gz.baidubce
//                        // .com/v1/endpoint/{endpointname}/principal/{principalName}
//                        uri = URI.create(Baidu_IotHubModule.Host + Baidu_IotHubModule.URI_Endpoint + "/"
//                                + endpointName + Baidu_IotHubModule.URI_Principal + "/" +
//                                principalName);
//                        request = new InternalRequest(method, uri);
//                        request.addHeader("Content-Type", "application/json; charset=utf-8");
//                        request.addHeader("Host", Baidu_IotHubModule.HostBody);
//                        String body = "{\"endpointName\":\"" + endpointName + "\"}" +
//                                "{\"principalName\":\"" + principalName + "\"}";
//                        request.setContent(RestartableInputStream.wrap(body.getBytes()));
//                    }
//                }
//            } else {
//                // endpointName、principalName都不能为空
//                return;
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
//    public interface RequestPrincipalListener {
//        void onResponse(final StringBuffer result);
//    }
//
//    public void attachThingToPrincipal(final String endpoint, final String thing, final String principal) {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                BceClientConfiguration configuration = new BceClientConfiguration()
//                        .withCredentials(new DefaultBceCredentials(Baidu_IotHubModule.AK,
//                                Baidu_IotHubModule.SK))
//                        .withEndpoint(Baidu_IotHubModule.HostBody);
//                IotHubClient iotHubClient = new IotHubClient(configuration);
//                iotHubClient.attachThingToPrincipal(endpoint, thing, principal);
//            }
//        }).start();
//    }




}
