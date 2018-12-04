package com.gta.administrator.infraredcontrol;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2016/6/30.
 */
public class RegisterIdentificationWindow extends AppCompatActivity {
    private CheckBox register_accept_protocol_checkbox;
    private Button register_confirm_btn;
    private TextView register_service_protocol;
    private TextView register_input_phone_number_edittext, register_again_input_password_edittext;
    private Context context;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_identification_window);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        context = RegisterIdentificationWindow.this;
        initView();
    }

    private void initView() {
        register_accept_protocol_checkbox = (CheckBox) findViewById(R.id.register_accept_protocol_checkbox);
        register_accept_protocol_checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                register_confirm_btn.setEnabled(isChecked);
                register_input_phone_number_edittext.setEnabled(isChecked);
                register_again_input_password_edittext.setEnabled(isChecked);
            }
        });
        register_confirm_btn = (Button) findViewById(R.id.register_confirm_btn);
        register_confirm_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNumber = register_input_phone_number_edittext.getText().toString();
                if (checkPhone(phoneNumber)) {
                    finish();
                } else {
//                    Toast.makeText(context, "请输入正确的手机号码", Toast.LENGTH_SHORT).show();
                    register_input_phone_number_edittext.setError("请输入正确的手机号码");
                }
            }
        });
        register_service_protocol = (TextView) findViewById(R.id.register_service_protocol);
        register_service_protocol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("服务协议");
                builder.setMessage("这里填写注册服务协议");
                builder.setPositiveButton("确定", null);
                builder.show();
            }
        });
        register_input_phone_number_edittext = (TextView) findViewById(R.id.register_input_phone_number_edittext);
        register_again_input_password_edittext = (TextView) findViewById(R.id.register_again_input_password_edittext);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 判断是否是手机号
     * @param phone
     * @return
     */
    public static boolean checkPhone(String phone) {
        Pattern pattern = Pattern
                .compile("^(13[0-9]|15[0-9]|153|15[6-9]|180|18[23]|18[5-9])\\d{8}$");
        Matcher matcher = pattern.matcher(phone);

        if (matcher.matches()) {
            return true;
        }
        return false;
    }
}
