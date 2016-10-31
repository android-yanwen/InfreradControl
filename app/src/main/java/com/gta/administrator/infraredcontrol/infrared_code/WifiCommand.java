package com.gta.administrator.infraredcontrol.infrared_code;

/**
 * Created by yanwen on 16/10/23.
 */
public class WifiCommand {

//    len:33|command:set_ssid|{ESP8266}  //设置下次连接的wifi名字
//    len:43|command:set_ssid_password|{12345678} //设置下次连接wifi的密码

    private final String len = "len:";
    private final String command = "|command:";
    private final String set_ssid = "set_ssid|";
    private final String set_ssid_password = "set_ssid_password|";
    private final int IS_SSID = 1;
    private final int IS_PWD = 2;

    private String SSID_PROTOCOL;
    private String PWD_PROTOCAL;

    public WifiCommand(String SSID, String PWD) {
        SSID = SSID.trim();
        PWD = PWD.trim();
        this.SSID_PROTOCOL = len + getProtocalLength(SSID, IS_SSID) + command + set_ssid + "{" + SSID + "}";
        this.PWD_PROTOCAL = len + getProtocalLength(PWD, IS_PWD) + command + set_ssid_password + "{" + PWD + "}";
    }

    /**
     * 得到整条协议的长度
     * @param str
     * @param selecte
     * @return
     */
    private int getProtocalLength(String str, int selecte) {
        int length = 0;
        //    len:33|command:set_ssid|{ESP8266}  格式
        // 头的长度   +   命令字长度     +     关键字长度     +     内容    +      两个大括号
        if (selecte == IS_SSID) {
            length = len.length() + command.length() + set_ssid.length() + str.length() + 2;
        } else if (selecte == IS_PWD) {
            length = len.length() + command.length() + set_ssid_password.length() + str.length() + 2;
        }
        int length_len = Integer.toString(length).length();//得到length的长度的位数
        length += length_len;//加上长度本身的位数长度

        // 返回整条协议的长度数值
        return length;

    }

    public String getSSIDProtocal() {
        return SSID_PROTOCOL;
    }

    public String getPWDProtocal() {
        return PWD_PROTOCAL;
    }



}
