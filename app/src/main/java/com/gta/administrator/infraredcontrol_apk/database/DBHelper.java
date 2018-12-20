package com.gta.administrator.infraredcontrol_apk.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by yanwen on 16/11/10.
 */
public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "ESP8266";
    private static final int VERSION = 1;
    public static final String IR_CODE = "IR_CODE";
    public static final String USER_REG = "USER_REG";


    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // 创建IR_CODE表格，将从远程mysql获取到的红外码保存到本地
        db.execSQL("CREATE TABLE IF NOT EXISTS " + IR_CODE + " (id INTEGER PRIMARY KEY " +
                "AUTOINCREMENT, Brand_models VARCHAR, IR_fun VARCHAR, " + "IR_code VARCHAR)");
        // 创建USER_REG表格，用户保存但的当前用户信息
        db.execSQL("CREATE TABLE IF NOT EXISTS " + USER_REG + " (idUSER_REG INTEGER PRIMARY KEY " +
                "AUTOINCREMENT, USER_REG_PHONE VARCHAR, USER_REG_PASSWORD VARCHAR, " +
                "USER_REG_PRODUCT VARCHAR)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("ALTER TABLE person ADD COLUMN other STRING");
    }
}
