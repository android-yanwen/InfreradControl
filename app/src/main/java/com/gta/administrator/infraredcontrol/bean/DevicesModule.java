package com.gta.administrator.infraredcontrol.bean;

/**
 * Created by yanwen on 16/9/30.
 */
public class DevicesModule {

    private int deviceImg;
    private String deviceName;


    public DevicesModule(int deviceImg, String deviceName) {
        this.deviceImg = deviceImg;
        this.deviceName = deviceName;
    }

    public int getDeviceImg() {
        return deviceImg;
    }

    public String getDeviceName() {
        return deviceName;
    }
}
