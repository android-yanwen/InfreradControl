package com.gta.administrator.infraredcontrol.database;


import android.util.Log;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Created by Administrator on 2016/11/8.
 */

public class Ir_code {
    private String ir_device;
    private String ir_fun;
    private String ir_code;
    private Connection connect = null;
    private Statement statement = null;
    private ResultSet resultSet = null;
    private  String TAG = "debug";
    private String driverName = "org.sqldroid.SQLDroidDriver";
    private String MYSQL_IP = "120.76.232.239";
    private String MYSQL_DBNAME = "ESP8266";
    private String MYSQL_USERNAME = "user";
    private String MYSQL_PASSWORD = "userpassword";
    private String path = "jdbc:mysql://" + MYSQL_IP + "/" + MYSQL_DBNAME + "?" + "user=" + MYSQL_USERNAME + "&password=" + MYSQL_PASSWORD;
    protected void loadJDBCDriver() {
        try {
//              DriverManager.registerDriver((Driver) Class.forName("org.sqldroid.SQLDroidDriver").newInstance());
            Class.forName("com.mysql.jdbc.Driver");
        } catch (Exception e) {
             throw new RuntimeException("Failed to register SQLDroidDriver");
           }
    }

    public String getIr_code(String ir_device,String ir_fun) throws Exception {
        this.ir_device=ir_device;
        this.ir_fun=ir_fun;

        String script = "SELECT * FROM IR_CODE WHERE Brand_models = '"+this.ir_device+"' and IR_fun = '"+this.ir_fun+"'" ;
        loadJDBCDriver();
        connect = (Connection) DriverManager.getConnection(path);
         if (connect!=null) {
            Log.e(TAG, "connection is success");
          }
         else
          {
            Log.e(TAG, "connection is failed");
          }
            statement = (Statement) connect.createStatement();
            resultSet = (ResultSet) statement.executeQuery(script);
            while (resultSet.next()) {
                ir_code = resultSet.getString("IR_code");
                Log.d(TAG, "getIr_code: "+ir_code);
            }
        close();
        return ir_code;
    }

    public ArrayList<Ir_codes> getIr_code(String ir_device) throws Exception {
        ArrayList<Ir_codes> results = new ArrayList<Ir_codes>();
        this.ir_device=ir_device;
        String script = "SELECT * FROM IR_CODE WHERE Brand_models = '"+ir_device+"'" ;
            Class.forName("com.mysql.jdbc.Driver");
            connect = (Connection) DriverManager.getConnection(path);
            statement = (Statement) connect.createStatement();
            resultSet = (ResultSet) statement.executeQuery(script);
            while (resultSet.next()) {
                Ir_codes ir_codes = new Ir_codes();
                ir_codes.Brand_models = resultSet.getString("Brand_models");
                ir_codes.ir_fun= resultSet.getString("IR_fun");
                ir_codes.ir_code = resultSet.getString("IR_code");
                results.add(ir_codes);
            }
        close();
        return results;
    }

    private void close() {
        try {
            if (resultSet != null) {
                resultSet.close();
            }

            if (statement != null) {
                statement.close();
            }

            if (connect != null) {
                connect.close();
            }
        } catch (Exception e) {
        }
    }
    public class Ir_codes{
        private String Brand_models = "";
        private String ir_code = "";
        private String ir_fun = "";

        public String getIr_fun() {
            return ir_fun;
        }
        public void setIr_fun(String ir_fun) {
            this.ir_fun = ir_fun;
        }
        public String getIr_code() {
            return ir_code;
        }

        public void setIr_code(String ir_code) {
            this.ir_code = ir_code;
        }

        public String getBrand_models() {
            return Brand_models;
        }

        public void setBrand_models(String brand_models) {
            Brand_models = brand_models;
        }
    }
}
