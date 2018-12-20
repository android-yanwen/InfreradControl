package com.gta.administrator.infraredcontrol_apk.baidu_iot_hub;

import com.baidubce.services.iothub.IotHubClient;
import com.baidubce.services.iothub.model.ListPermissionResponse;
import com.baidubce.services.iothub.model.Operation;
import com.baidubce.services.iothub.model.QueryPermissionResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yanwen on 16/11/3.
 */
public class Permission {
    private IotHubClient iotHubClient;

    public enum Method {
        CREATE_PERMISSION,
        UPDATE_PERMISSION,
        LIST_PERMISSION,
        QUERY_PERMISSION
    }

    public Permission() {
        iotHubClient = Baidu_IotHubModule.getInstance().getIotHubClient();
    }

    public void requestPermission(Method method, String endpointName, String policyName, String
            topicName, String permissionUuid, RequestPermissionCallbackListener callbackListener) {

        switch (method) {
            case CREATE_PERMISSION:
                createPermission(endpointName, policyName, topicName, callbackListener);
                break;
            case UPDATE_PERMISSION:
                updatePermission(endpointName, permissionUuid, topicName, callbackListener);
                break;
            case LIST_PERMISSION:
                listPermission(endpointName, policyName, callbackListener);
                break;
            case QUERY_PERMISSION:
                queryPermission(endpointName, permissionUuid, callbackListener);
                break;
        }
    }

    private void createPermission(final String endpointName, final String policyName, final String topicName,
                                  final RequestPermissionCallbackListener callbackListener) {
        if (callbackListener == null) {
            return;
        }
        if (endpointName == null) {
            return;
        }
        if (policyName == null) {
            return;
        }
        if (topicName == null) {
            return;
        }
        final List<Operation> operations = new ArrayList<>();
        operations.add(Operation.PUBLISH);
        operations.add(Operation.SUBSCRIBE);
        new Thread(new Runnable() {
            @Override
            public void run() {
                QueryPermissionResponse response = iotHubClient.createPermission(endpointName,
                        policyName, operations, topicName);
                callbackListener.permissionInfoCallback(response);
            }
        }).start();
    }

    private void updatePermission(final String endpointName, final String permissionUuid, final String topicName,
                                  final RequestPermissionCallbackListener callbackListener) {
        if (callbackListener == null) {
            return;
        }
        if (endpointName == null) {
            return;
        }
        if (permissionUuid == null) {
            return;
        }
        if (topicName == null) {
            return;
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                QueryPermissionResponse response = iotHubClient.updatePermission(endpointName,
                        permissionUuid, null, topicName);
                callbackListener.permissionInfoCallback(response);
            }
        }).start();
    }

    private void listPermission(final String endpointName, final String policyName,
                                final RequestPermissionCallbackListener callbackListener) {
        if (callbackListener == null) {
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
                ListPermissionResponse response = iotHubClient.listPermission(endpointName,
                        policyName);
                callbackListener.listPermissionCallback(response);
            }
        }).start();
    }


    private void queryPermission(final String endpointName, final String permissionUuid,
                                 final RequestPermissionCallbackListener callbackListener) {
        if (callbackListener == null) {
            return;
        }
        if (endpointName == null) {
            return;
        }
        if (permissionUuid == null) {
            return;
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                QueryPermissionResponse response = iotHubClient.queryPermission(endpointName,
                        permissionUuid);
                callbackListener.permissionInfoCallback(response);
            }
        }).start();
    }

    public interface RequestPermissionCallbackListener {
        void permissionInfoCallback(QueryPermissionResponse response);

        void listPermissionCallback(ListPermissionResponse response);
    }

}
