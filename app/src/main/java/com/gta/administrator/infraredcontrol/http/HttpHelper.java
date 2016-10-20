package com.gta.administrator.infraredcontrol.http;

import android.util.Xml;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Created by yanwen on 16/10/2.
 */
public class HttpHelper {

    public static String post(String strURL, String access_token, String msg
                              /*String info*/) {
        try {
            URL url = new URL(strURL);// 创建连接
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setUseCaches(false);
            connection.setConnectTimeout(8000);
            connection.setReadTimeout(8000);
            connection.setInstanceFollowRedirects(true);
            connection.setRequestMethod("POST"); // 设置请求方式
            connection.setRequestProperty("Accept", "application/json"); // 设置接收数据的格式
            connection.setRequestProperty("Content-Type", "application/json"); // 设置发送数据的格式
            connection.setRequestProperty("Accept-Encoding", "identity");

            // 将token放到头部
            connection.setRequestProperty("Authorization", "Bearer "
                    + access_token);

            connection.connect();

            // 向服务器POST信息
            if (null != msg && msg.length() > 0) {
                OutputStreamWriter out = new OutputStreamWriter(
                        connection.getOutputStream(), "UTF-8"); // 服务器采用UTF-8编码
                out.append(msg);
                out.flush();
                out.close();
            }

            // 读取服务器响应(最大长度10K)
            int length = 10 * 1024;
            // int length =connection.getContentLength();// 获取长度,这里一直返回0,不知道什么原因
            InputStream is = connection.getInputStream();
            if (length != -1) {
                byte[] data = new byte[length];
                byte[] temp = new byte[512];// 每次读取512字节
                int readLen = 0;// 单次读取的长度
                int destPos = 0;// 总字节数
                while ((readLen = is.read(temp)) > 0) {
                    System.arraycopy(temp, 0, data, destPos, readLen);
                    destPos += readLen;
                }
                byte[] fixed_data = Arrays.copyOf(data, destPos);
                String result = new String(fixed_data, "UTF-8"); // 响应也是UTF-8编码
//                System.out.println(info + "服务器返回结果：" + result);
                return result;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "error"; // 自定义错误信息
    }

}
