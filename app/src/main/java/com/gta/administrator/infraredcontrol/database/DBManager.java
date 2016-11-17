package com.gta.administrator.infraredcontrol.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.gta.administrator.infraredcontrol.mysql.Ir_code;
import com.gta.administrator.infraredcontrol.user.UserInfo;
//import com.gta.yanwen.mysqllibrary.Ir_code;

import java.util.List;

/**
 * Created by yanwen on 16/11/10.
 */
public class DBManager {
    private DBHelper helper;
    private SQLiteDatabase db;

    public DBManager(Context context) {
        helper = new DBHelper(context);
        db = helper.getWritableDatabase();
    }


    /**
     * 向数据库中插入红外码的相关信息
     * @param ir_codes  红外码信息
     */
    public void insert(List<Ir_code.Ir_codes> ir_codes) {
        for (Ir_code.Ir_codes codes : ir_codes) {
            ContentValues values = new ContentValues();
            values.put("Brand_models", codes.getBrand_models());
            values.put("IR_fun", codes.getIr_fun());
            values.put("IR_code", codes.getIr_code());
            db.insert(DBHelper.IR_CODE, null, values);
        }
    }

    /**
     * 删除指定品牌的红外码信息
     * @param ir_codes  红外码信息
     */
    public void delete(Ir_code.Ir_codes ir_codes) {
        db.delete(DBHelper.IR_CODE, null, new String[]{ir_codes.getBrand_models()});
    }

    /**
     *  查询指定品牌的对应键红外码
     * @param irBrand_models   品牌
     * @param ir_fun   键
     * @return  红外码
     */
    public String query(String irBrand_models, String ir_fun) {
        String ir_code = null;
        Cursor cursor = db.rawQuery("SELECT * FROM " + DBHelper.IR_CODE + " WHERE Brand_models = " +
                "? AND IR_fun = ?", new String[]{irBrand_models, ir_fun});
        while (cursor.moveToNext()) {
            ir_code = cursor.getString(cursor.getColumnIndex("IR_code"));
        }
        return ir_code;
    }





    /**
     * 向数据库中插入已注册用户信息
     * @param info  用户信息
     */
    public void insert(UserInfo info) {
        ContentValues values = new ContentValues();
        values.put("USER_REG_PHONE", info.getUser_phone());
        values.put("USER_REG_PASSWORD", info.getUser_password());
        values.put("USER_REG_PRODUCT", info.getUser_product());
        db.insert(DBHelper.USER_REG, null, values);
    }


    /**
     * 删除指定用户账号（手机号）的用户信息
     * @param phone  用户账号
     */
    public void delete(String phone) {
        ContentValues values = new ContentValues();
        db.delete(DBHelper.USER_REG, "USER_REG_PHONE=?", new String[]{phone});
    }

    /**
     * 查询数据库并返回用户信息
     * @return userInfo 用户信息
     */
    public UserInfo query() {
        UserInfo info = new UserInfo();
        Cursor cursor = db.rawQuery("SELECT * FROM " + DBHelper.USER_REG, null);
        if (cursor.moveToNext()) {
            info.setUser_phone(cursor.getString(cursor.getColumnIndex("USER_REG_PHONE")));
            info.setUser_password(cursor.getString(cursor.getColumnIndex("USER_REG_PASSWORD")));
            info.setUser_product(cursor.getString(cursor.getColumnIndex("USER_REG_PRODUCT")));
        }
        return info;
    }

    public void Close() {
        if (db!=null)
        {
            db.close();
            db=null;
        }
        if (helper!=null){
            helper.close();
            helper=null;
        }
    }
}
