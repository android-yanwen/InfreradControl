package com.gta.administrator.infraredcontrol_apk.baidu_iot_hub;

import com.baidubce.services.iothub.IotHubClient;
import com.baidubce.services.iothub.model.BaseResponse;
import com.baidubce.services.iothub.model.ListResponse;
import com.baidubce.services.iothub.model.QueryPolicyResponse;

/**
 * Created by yanwen on 16/11/3.
 */
public class Policy {


    private IotHubClient iotHubClient;

    public enum Method {
        CREATE_POLICY,
        LIST_POLICY,
        QUERY_POLICY,
        ATTACH_PRINCIPAL_TO_POLICY,
        REMOVE_PRINCIPAL_TO_POLICY,
        DELETE_POLICY
    }

    public Policy() {
        iotHubClient = Baidu_IotHubModule.getInstance().getIotHubClient();
    }

    public void requestPolicy(Method method, String endpointName, String principalName, String
            policyName, RequestPolicyCallbakcListener callbakcListener) {
        switch (method) {
            case CREATE_POLICY:
                createPolicy(endpointName, policyName, callbakcListener);
                break;
            case LIST_POLICY:
                listPolicy(endpointName, principalName, callbakcListener);
                break;
            case QUERY_POLICY:
                queryPolicy(endpointName, policyName, callbakcListener);
                break;
            case ATTACH_PRINCIPAL_TO_POLICY:
                attachPrincipalToPolicy(endpointName, principalName, policyName, callbakcListener);
                break;
            case REMOVE_PRINCIPAL_TO_POLICY:
                removePrincipalToPolicy(endpointName, principalName, policyName, callbakcListener);
                break;
            case DELETE_POLICY:
                deletePolicy(endpointName, policyName, callbakcListener);
                break;
        }
    }

    public interface RequestPolicyCallbakcListener {

        void createOrQueryPolicyCallback(QueryPolicyResponse response);

        void listPolicyCallback(ListResponse response);

        void attachOrRemovePrincipalToPolicy(BaseResponse response);

    }

    private void createPolicy(final String endpointName, final String policyName,
                              final RequestPolicyCallbakcListener callbakcListener) {
        if (callbakcListener == null) {
            return;
        }
        if (endpointName == null) {
            return;
        }
        if (policyName == null) {
            return;
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                QueryPolicyResponse response = iotHubClient.createPolicy(endpointName, policyName);
                callbakcListener.createOrQueryPolicyCallback(response);
            }
        }).start();
    }

    private void listPolicy(final String endpointName, final String principalName, final RequestPolicyCallbakcListener
            callbakcListener) {
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
                if (principalName == null) {
                    response = iotHubClient.listPolicy(endpointName);
                } else {
                    response = iotHubClient.listPolicy(endpointName, principalName);
                }
                callbakcListener.listPolicyCallback(response);
            }
        }).start();
    }

    private void queryPolicy(final String endpointName, final String policyName,
                             final RequestPolicyCallbakcListener callbakcListener) {
        if (callbakcListener == null) {
            return;
        }
        if (endpointName == null) {
            return;
        }
        if (policyName == null) {
            return;
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                QueryPolicyResponse response = iotHubClient.queryPolicy(endpointName, policyName);
                callbakcListener.createOrQueryPolicyCallback(response);
            }
        }).start();
    }

    private void attachPrincipalToPolicy(final String endpointName, final String principalName, final String
            policyName, final RequestPolicyCallbakcListener callbakcListener) {
        if (callbakcListener == null) {
            return;
        }
        if (endpointName == null) {
            return;
        }
        if (principalName == null) {
            return;
        }
        if (policyName == null) {
            return;
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                BaseResponse response = iotHubClient.attachPrincipalToPolicy(endpointName,
                        principalName, policyName);
                callbakcListener.attachOrRemovePrincipalToPolicy(response);
            }
        }).start();
    }

    private void removePrincipalToPolicy(final String endpointName, final String principalName, final String
            policyName, final RequestPolicyCallbakcListener callbakcListener) {
        if (callbakcListener == null) {
            return;
        }
        if (endpointName == null) {
            return;
        }
        if (principalName == null) {
            return;
        }
        if (policyName == null) {
            return;
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                BaseResponse response = iotHubClient.removePrincipalToPolicy(endpointName,
                        principalName, policyName);
                callbakcListener.attachOrRemovePrincipalToPolicy(response);
            }
        }).start();
    }

    private void deletePolicy(final String endpointName, final String policyName,
                              final RequestPolicyCallbakcListener callbakcListener) {
        if (callbakcListener == null) {
            return;
        }
        if (endpointName == null) {
            return;
        }
        if (policyName == null) {
            return;
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                BaseResponse response = iotHubClient.deletePolicy(endpointName, policyName);
                callbakcListener.attachOrRemovePrincipalToPolicy(response);
            }
        }).start();
    }



}
