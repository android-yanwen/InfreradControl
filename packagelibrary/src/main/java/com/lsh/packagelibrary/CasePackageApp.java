package com.lsh.packagelibrary;

import android.app.Application;

import com.umeng.commonsdk.UMConfigure;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.PushAgent;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.cookie.CookieJarImpl;
import com.zhy.http.okhttp.cookie.store.PersistentCookieStore;

import okhttp3.OkHttpClient;


public class CasePackageApp extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
//        JPushInterface.setDebugMode(true);
        CookieJarImpl cookieJar = new CookieJarImpl(new PersistentCookieStore(getApplicationContext()));
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .cookieJar(cookieJar)
                //其他配置
                .build();
        OkHttpUtils.initClient(okHttpClient);
        UMConfigure.init(this, "5bf2d7f5b465f52bd00003b4", "umeng", UMConfigure.DEVICE_TYPE_PHONE, "be7304bb2ee49cfe2f2d7f043283d0fc");
        PushAgent mPushAgent = PushAgent.getInstance(this);
//注册推送服务，每次调用register方法都会回调该接口
        mPushAgent.register(new IUmengRegisterCallback() {
            @Override
            public void onSuccess(String deviceToken) {
                //注册成功会返回device token
            }
            @Override
            public void onFailure(String s, String s1) {
            }
        });

        UMConfigure.setLogEnabled(true);
    }
}
