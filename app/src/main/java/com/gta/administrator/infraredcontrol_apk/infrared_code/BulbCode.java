package com.gta.administrator.infraredcontrol_apk.infrared_code;




/**
 * Created by yanwen on 16/10/16.
 */
public class BulbCode {

    /****************************************彩灯控制命令****************************************/
    private final static String len = "len:";
    private final static String command = "|command:";
    private final static String RGBW = "RGBW|";
    private final static String ir_send = "ir_send|";
    private final static String lamp_power = "lamp_power|";
    public final static boolean SWITCH_ON = true;
    public final static boolean SWITCH_OFF = false;

    public static String OpenCode="{9052,4491,602,561,576,560,577,559,578,558,579,557,580,556,571,566,572,1673,602,1670,605,1667,598,1674,601,1671,604,1669,606,1666,599,1673,602,561,576,1669,606,557,581,556,571,565,572,564,573,563,574,563,574,562,575,561,576,1669,606,1665,599,1673,602,1669,606,1666,599,1673,602,1669,606,39626,9046,2229,599,96383,9051,2224}";
    public static String CloseCode="{9046,4488,605,530,607,529,597,538,599,537,600,535,602,534,603,533,604,1666,598,1672,603,1668,606,1664,600,1671,604,1668,607,1664,600,1670,605,531,606,1665,600,563,574,536,602,535,602,534,603,533,604,532,606,531,606,530,607,1664,600,1671,604,1667,607,1664,601,1670,604,1667,598,1673,602,39624,9047,2228,600,96383,9054,2217}";
    public static String BrightessCode="{9038,4496,597,566,572,564,573,563,574,562,575,560,577,559,578,557,580,1663,601,1669,605,1665,599,1671,604,1667,598,1673,602,1668,607,1664,601,561,576,1668,607,1664,601,1669,605,1665,600,1671,604,1666,598,1672,603,1667,597,565,572,563,574,562,575,560,577,559,578,558,579,556,581,555,572,39619,9042,2228,600,96362,9049,2220}";

    public static String UpCode="{9040,4498,658,478,597,539,598,537,600,536,601,534,603,533,604,532,605,1665,652,1619,656,1615,649,1622,653,1618,604,1667,598,1673,602,1669,605,531,606,530,597,1674,601,536,601,535,602,1669,606,531,606,530,607,530,597,1674,601,536,601,1670,605,1667,608,529,598,1673,602,1669,606,1666,599,39633,9048,2227,601,96379,9052,2218}";
    public static String DownCode="{9045,4496,597,539,599,538,599,537,600,536,601,535,602,534,603,534,603,1668,606,1666,599,1673,602,1670,605,1667,597,1674,600,1671,604,1668,607,529,597,539,598,1673,602,1670,605,1667,598,1674,601,536,601,535,602,534,603,1667,607,529,598,538,599,537,600,536,601,1670,605,1666,599,1673,602,39631,9049,2224,604,96382,9047,2224}";
    public static String LeftCode="{9051,4490,603,533,603,559,578,559,578,558,579,557,580,530,607,555,571,1673,602,1670,605,1666,598,1674,601,1670,605,1666,598,1673,602,1669,606,531,606,530,607,1664,601,1671,604,1667,598,1673,601,535,602,534,603,533,604,1667,598,539,598,538,599,537,600,536,601,1670,604,1667,608,1664,601,39626,9052,2225,603,96375,9050,2227}";

    public static String RightCode="{9047,4492,600,535,602,534,603,533,604,532,605,530,607,529,597,538,599,1672,603,1668,606,1665,600,1671,604,1667,608,1664,601,1670,604,1666,598,538,599,537,600,1671,604,532,605,532,605,1666,599,537,600,536,601,535,602,1669,605,531,606,1665,599,1672,603,533,604,1667,660,1612,653,1618,656,39575,9104,2171,657,96321,9053,2221}";
    public static String LightSourceCode="{9048,4493,600,535,602,534,602,534,603,533,604,533,604,532,605,531,606,1666,598,1673,601,1670,604,1667,607,1664,600,1672,603,1669,606,1666,599,538,599,537,600,1672,655,481,604,1668,659,1612,652,484,601,535,601,535,654,1617,658,479,606,1665,651,485,652,485,652,1619,656,1616,659,1613,652,39577,9059,2219,651,96326,9054,2220}";
    public static String ColorTempReduceCode="{9047,4488,604,558,579,530,607,529,598,538,599,537,600,536,601,535,602,1668,607,1665,600,1671,603,1668,607,1664,600,1671,604,1667,608,1663,601,562,575,1669,606,1665,599,564,573,536,601,535,603,534,604,532,605,532,605,531,606,530,607,1664,600,1671,604,1668,607,1664,600,1671,603,1668,607,39627,9051,2225,603,96389,9049,2227}";

    public static String ColorTempPlusCode="{9042,4496,607,555,571,564,573,563,574,561,576,560,577,559,578,557,580,1664,600,1671,603,1668,607,1664,601,1671,604,1667,608,1664,600,1671,604,560,578,559,578,1667,597,566,571,565,572,564,573,563,574,562,575,561,576,1669,606,557,580,1663,601,1670,605,1666,599,1672,603,1668,606,1665,599,39628,9050,2222,606,96376,9046,2223}";
    public static String SectionCode="{9042,4498,605,557,580,556,570,539,598,565,572,565,572,537,600,563,574,1670,604,1667,607,1664,600,1671,604,1667,597,1673,601,1669,605,1666,598,564,573,563,574,1670,604,1667,597,566,571,565,572,564,573,563,574,562,575,1669,605,557,580,557,580,1664,600,1671,604,1667,597,1673,601,1670,605,39632,9045,2228,600,96377,9046,2226}";



    public static String get_ir_Code(String ir_code) {
        String code = command + ir_send + ir_code;
        code = len + getProtocalLen(code) + code;
        return code;
    }



    /****************************************彩灯控制命令****************************************/


    public static String getBulbColorCode(String s_R, String s_G, String s_B, String s_W) {
        String code = len + getProtocalLength(s_R, s_G, s_B, s_W) + command + RGBW + "{" + s_R + "," + s_G + "," + s_B + "," + s_W + "}";
        return code;
    }

    private static int getProtocalLength(String s_R, String s_G, String s_B, String s_W) {
        int length = 0;
        //    len:33|command:set_ssid|{ESP8266}  格式
        // 头的长度   +   命令字长度     +     关键字长度     +     内容
        length = len.length() + command.length() + RGBW.length() + s_R.length() + 1 + s_G.length() + 1 + s_B.length() + 1 + s_W.length() + 2;
        int length_len = Integer.toString(length).length();//得到length的长度的位数
        int n_pro_len = length_len + length;
        int y_pro_len = Integer.toString(n_pro_len).length() + length;

        // 返回整条协议的长度数值
        return y_pro_len;
    }

    /**
     * 获取电源开关命令协议长度
     * @param val
     * @return
     */
    private static int getProtocalLength(String val) {
        int length = 0;
        //    len:33|command:set_ssid|{ESP8266}  格式
        // 头的长度   +   命令字长度     +     关键字长度     +     内容
        length = len.length() + command.length() + lamp_power.length() + val.length() + 2;
        int length_len = Integer.toString(length).length();//得到length的长度的位数
        int n_pro_len = length_len + length;
        int y_pro_len = Integer.toString(n_pro_len).length() + length;
        // 返回整条协议的长度数值
        return y_pro_len;
    }

    private static int getProtocalLen(String code) {
        int length ;
        length = len.length() +  code.length();
        int length_len = Integer.toString(length).length();//得到length的长度的位数
        int n_pro_len = length_len + length;
        int y_pro_len = Integer.toString(n_pro_len).length() + length;
        // 返回整条协议的长度数值
        return y_pro_len;
    }

    /**
     * 获取电源开关协议
     * @param isOn  true开灯  false关灯
     * @return
     */
    public static String getBulbColorSwitchCode(boolean isOn,String r,String g,String b,String w) {
        String cmd;
        String code_open = command + lamp_power + "{" + "1," +r+"," +g+","+b+","+w+"}";
        code_open = len + getProtocalLen(code_open) + code_open;
        String code_close = command + lamp_power + "{0}";
        code_close = len + getProtocalLen(code_close) + code_close;
        if (isOn) {
            // 开灯
            cmd =code_open;
        } else {
            // 关灯
            cmd =code_close;
        }
        return cmd;
    }
}
