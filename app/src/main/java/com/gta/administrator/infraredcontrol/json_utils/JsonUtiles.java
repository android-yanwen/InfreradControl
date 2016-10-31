package com.gta.administrator.infraredcontrol.json_utils;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by yanwen on 16/10/15.
 */
public class JsonUtiles {
    private String jsonString;
    private static final String TotalCount = "totalCount";
    private static final String Result = "result";
    private static final String MqttHostName = "mqttHostname";
    private static final String EndpointName = "endpointName";

    private JSONObject object;
    public JsonUtiles(String jsonString) {
        this.jsonString = jsonString;
        try {
            object = new JSONObject(jsonString);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    /**
     * 返回endpoint总数
     * @return
     */
    public int getEndpointCount() {
        int count = 0;
        try {
            count = object.getInt(TotalCount);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return count;
    }

    /**
     * 获得MqttHostName，即请求的地址
     * @return
     */
    public String[] getMqttHostName() {
        String[] hosts = null;
        for (int i = 0; i < getEndpointCount(); i++) {
            try {
                hosts[i] = getObjectArray()[i].getString(MqttHostName);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return hosts;
    }

    /**
     * 返回所有endpointName
     * @return
     */
    public String[] getEndpointsName() {
        int count = getEndpointCount();
        String[] names = new String[count];
        JSONObject[] arry = getObjectArray();
        for (int i = 0; i < count; i++) {
            try {
                names[i] = arry[i].getString(EndpointName);
//                JSONArray arr = object.getJSONArray(Result);
//                JSONObject obj = arr.getJSONObject(i);
//                Log.d("tag", "obj="+obj.toString());
//                names[i] = obj.optString(EndpointName);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return names;
    }

    public JSONObject[] getObjectArray() {
        JSONArray array = getResult();
        int lenght = array.length();
        JSONObject[] objects = new JSONObject[lenght];
        for (int i = 0; i < lenght; i++) {
            try {
                objects[i] = array.getJSONObject(i);
//                Log.d("tag", "getObjectArray: " + objects[i]);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        return objects;
    }

    /**
     * 获取全部的endpoint数组
     * @return
     */
    private JSONArray getResult() {
        JSONArray array = null;
        try {
            array = object.getJSONArray(Result);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return array;
    }
}
