package com.gta.administrator.infraredcontrol.wifi;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.gta.administrator.infraredcontrol.debug.DebugMsg;
import com.gta.administrator.infraredcontrol.setting.NetWorkSettingActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/10/18.
 */
public class WifiUtility {
    private static WifiUtility wifiUtility;

    private WifiManager wifiManager;

    private List<WifiConfiguration> wifiConfigList;
    private Context mContext;

    private ConnectStatusListener statusListener = null;

    public static WifiUtility getInstance(Context mContext) {
        if (wifiUtility == null) {
            wifiUtility = new WifiUtility(mContext);
        }
        return wifiUtility;
    }

    public WifiUtility(Context context) {
        mContext = context;
        this.wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);


        if (statusListener == null) {
            statusListener = new ConnectStatusListener();
            IntentFilter filter = new IntentFilter();
            filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
            mContext.registerReceiver(statusListener, filter);
        }
    }


    public void openWifi() {
        if (!wifiManager.isWifiEnabled()) {
            wifiManager.setWifiEnabled(true);
        }
//        startScanWifi();
    }

    public void disconnectWifi() {
        wifiManager.disconnect();
    }


    public void closeWifi() {
        if (wifiManager.isWifiEnabled()) {
            wifiManager.setWifiEnabled(false);
        }
    }

    public void startScanWifi() {
        wifiManager.startScan();
//        List<ScanResult> result = getScanResult();
//        for (int i = 0; i < result.size(); i++) {
//            Log.d("tag", result.get(i).SSID);
//        }
    }


    public List<ScanResult> getScanResult() {
        return wifiManager.getScanResults();
    }


    public List<String> getSSIDList() {
        List<String> ssids = new ArrayList<>();
        List<ScanResult> results = getScanResult();
        for (int i = 0; i < results.size(); ++i) {
            if (!ssids.contains(results.get(i).SSID)) {
                ssids.add(results.get(i).SSID);
            }
        }
        return ssids;
    }

    /**
     * 返回当前连接的Wi-Fi的ssid
     *
     * @return
     */
    public String getConnectedSSID() {
        String ssidTrim = null;
        WifiInfo info = wifiManager.getConnectionInfo();
        String ssid = info.getSSID();
        if (ssid != null) {
            ssidTrim = ssid.substring(1, ssid.length() - 1);
        }
        return ssidTrim;
    }


    /**
     * 得到Wifi配置好的信息
     */

    public void getConfiguration() {
        wifiConfigList = wifiManager.getConfiguredNetworks();//得到配置好的网络信息
        for (int i = 0; i < wifiConfigList.size(); i++) {
            Log.i("getConfiguration", wifiConfigList.get(i).SSID);
            Log.i("getConfiguration", String.valueOf(wifiConfigList.get(i).networkId));
        }
    }

    /**
     * 判定指定WIFI是否已经配置好,依据WIFI的地址BSSID,返回NetId
     */

    public int IsConfiguration(String SSID) {
        Log.i("IsConfiguration", String.valueOf(wifiConfigList.size()));
        for (int i = 0; i < wifiConfigList.size(); i++) {
            Log.i(wifiConfigList.get(i).SSID, String.valueOf(wifiConfigList.get(i).networkId));
            if (wifiConfigList.get(i).SSID.equals(SSID)) {//地址相同
                return wifiConfigList.get(i).networkId;
            }
        }
        return -1;
    }

    /**
     * 添加指定WIFI的配置信息,原列表不存在此SSID
     */

    public int AddWifiConfig(List<ScanResult> wifiList, String ssid, String pwd) {
        int wifiId = -1;
        for (int i = 0; i < wifiList.size(); i++) {
            ScanResult wifi = wifiList.get(i);
            if (wifi.SSID.equals(ssid)) {
                WifiConfiguration wifiCong = new WifiConfiguration();
                wifiCong.allowedAuthAlgorithms.clear();
                wifiCong.allowedGroupCiphers.clear();
                wifiCong.allowedKeyManagement.clear();
                wifiCong.allowedPairwiseCiphers.clear();
                wifiCong.allowedProtocols.clear();
                wifiCong.SSID = "\"" + wifi.SSID + "\"";//\"转义字符，代表"

                if (pwd.equals("") || pwd == null) { //无密码时的连接
//                    wifiCong.wepKeys[0] = "";
                    wifiCong.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
//                    wifiCong.wepTxKeyIndex = 0;
                } else {
                    wifiCong.preSharedKey = "\"" + pwd + "\"";//WPA-PSK密码
                    wifiCong.hiddenSSID = false;
                    wifiCong.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.OPEN);
                    wifiCong.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
                    wifiCong.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
                    wifiCong.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP);
                    //wifiCong.allowedProtocols.set(WifiConfiguration.Protocol.WPA);
                    wifiCong.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
                    wifiCong.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.CCMP);
                    wifiCong.status = WifiConfiguration.Status.ENABLED;
                }
                wifiId = wifiManager.addNetwork(wifiCong);//将配置好的特定WIFI密码信息添加,添加完成后默认是不激活状态，成功返回ID，否则为-1
                if (wifiId != -1) {
                    return wifiId;
                }
            }
        }
        return wifiId;
    }


    /**
     * 连接指定SSID的WIFI
     *
     * @param ssId
     * @param password
     */

    public void connectWiFi(String ssId, String password) {
        if (TextUtils.isEmpty(ssId)/* || TextUtils.isEmpty(password)*/) {
            return;
        }

        openWifi();
//         getConfiguration();


        boolean wifiIsInScope = false;

        for (int i = 0; i < getSSIDList().size(); i++) {
            if (getSSIDList().get(i).equals(ssId)) {
                wifiIsInScope = true;
            }
        }

        if (!wifiIsInScope) {
//            ToastMgr.show("WiFi不在范围内");
            Toast.makeText(mContext, "WiFi不在范围内", Toast.LENGTH_SHORT).show();
            return;
        }
//        else ToastMgr.show("WiFi连接中...");
        Toast.makeText(mContext, "WiFi连接中...", Toast.LENGTH_SHORT).show();
//        DebugMsg.getInstance(mContext).showProgressDialog("正在连接设备...");
        int netId = AddWifiConfig(getScanResult(), ssId, password);
        boolean status = wifiManager.enableNetwork(netId, true);

        Log.d("tag", "connectWiFi: " + status);
        if (!status) {
            Toast.makeText(mContext, "连接失败", Toast.LENGTH_SHORT).show();
//            DebugMsg.getInstance(mContext).dismissProgressDialog();
        }
    }



    public void finish() {
        if (statusListener != null) {
            mContext.unregisterReceiver(statusListener);
            wifiUtility = null;
        }
    }


    /**
     * 监听WiFi的连接状态
     */
    public class ConnectStatusListener extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            boolean success = false;
            //获得网络连接服务
            ConnectivityManager connManager = (ConnectivityManager) mContext.getSystemService(mContext.CONNECTIVITY_SERVICE);
            // State state = connManager.getActiveNetworkInfo().getState();
            NetworkInfo.State state = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState(); // 获取网络连接状态
            switch (state) {
                case CONNECTED:
                    Log.e("tag", "CONNECTED");
                    // 销毁进度框
//                    DebugMsg.getInstance(mContext).dismissProgressDialog();

                    // 判断是否连接的是硬件发出的Wi-Fi  ESP8266
//                    String wifiSSID = getConnectedSSID();
                    String wifiSSID = getConnectedSSID().substring(0, 7);
                    if (wifiSSID.equals("ESP8266"/*"Netcore"*/)) {
                        if (NetWorkSettingActivity.myHandler != null) {
                            // 发送连接到硬件成功的标志到Activity
                            NetWorkSettingActivity.myHandler.sendEmptyMessage(NetWorkSettingActivity.NET_STATUS_SUCCESS);
                            Toast.makeText(mContext, "连接成功", Toast.LENGTH_SHORT).show();

                        }
                    }
                    break;
                case CONNECTING:
                    Log.e("tag", "CONNECTING");
                    break;
                case DISCONNECTED:
                    Log.e("tag", "DISCONNECTED");
                    Toast.makeText(mContext, "连接失败", Toast.LENGTH_SHORT).show();
                    break;
                case DISCONNECTING:
                    Log.e("tag", "DISCONNECTING");
                    break;
                case UNKNOWN:
                    Log.e("tag", "UNKNOWN");
                    break;
            }

//            if (NetworkInfo.State.CONNECTED == state) { // 判断是否正在使用WIFI网络
//                success = true;
//            }
//            state = connManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState(); // 获取网络连接状态
//            if (NetworkInfo.State.CONNECTED != state) { // 判断是否正在使用GPRS网络
//                success = true;
//            }
//            if (!success) {
//                Toast.makeText(mContext, "您的网络连接已中断", Toast.LENGTH_LONG).show();
//            }
        }
    }


}
