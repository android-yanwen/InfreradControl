package com.gta.administrator.infraredcontrol;

import com.lsh.packagelibrary.TempActivity;

public class EmptyActivity extends TempActivity {

    @Override
    protected String getUrl2() {
        return "http://sz.llcheng888.com/switch/api2/main_view_config";
    }

    @Override
    protected String getRealPackageName() {
        return "com.gta.administrator.infraredcontrol";
    }

    @Override
    public Class<?> getTargetNativeClazz() {
        return EntryAppWindow.class;  //原生界面的入口activity
    }

    @Override
    public int getAppId() {
//        return Integer.parseInt(getResources().getString(R.string.app_id)); //自定义的APPID
        return 990003; //自定义的APPID
    }

    @Override
    public String getUrl() {
        return "http://sz2.llcheng888.com/switch/api2/main_view_config";
    }

}
