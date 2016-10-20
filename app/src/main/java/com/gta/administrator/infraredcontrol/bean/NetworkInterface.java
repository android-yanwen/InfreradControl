package com.gta.administrator.infraredcontrol.bean;

/**
 * Created by Administrator on 2016/10/20.
 */
public interface NetworkInterface {

    void openConnect();

    void closeConnect();

    void sendData(String data);

    void receiveData();


}
