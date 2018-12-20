package com.gta.administrator.infraredcontrol_apk.user;

/**
 * Created by yanwen on 16/11/10.
 */
public class UserInfo {
    private String user_phone;
    private String user_password;
    private String user_product;


    public void setUser_password(String user_password) {
        this.user_password = user_password;
    }

    public String getUser_password() {
        return user_password;
    }

    public void setUser_phone(String user_phone) {
        this.user_phone = user_phone;
    }

    public String getUser_phone() {
        return user_phone;
    }

    public void setUser_product(String user_product) {
        this.user_product = user_product;
    }

    public String getUser_product() {
        return user_product;
    }
}
