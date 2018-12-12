package com.gta.administrator.infraredcontrol;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2016/6/30.
 */
public class RegisterIdentificationWindow extends AppCompatActivity {
    private CheckBox register_accept_protocol_checkbox;
    private Button register_confirm_btn;
    private TextView register_service_protocol;
    private EditText register_input_phone_number_edittext, register_again_input_password_edittext;
    private Context context;
    private EditText register_input_password_edittext;
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
                register_input_password_edittext.setEnabled(isChecked);
            }
        });
        register_confirm_btn = (Button) findViewById(R.id.register_confirm_btn);
        register_confirm_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNumber = register_input_phone_number_edittext.getText().toString();
                if (checkPhone(phoneNumber)) {
                    if (!register_input_password_edittext.getText().toString().equals(register_again_input_password_edittext.getText().toString())) {
                        Toast.makeText(context, "两次输入密码不一样", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (register_input_password_edittext.getText().toString().isEmpty() ||
                            register_again_input_password_edittext.getText().toString().isEmpty()) {
                        Toast.makeText(context, "密码不能为空", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    SharedPreferences sharedPreferences = getSharedPreferences("login", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("phone", phoneNumber);
                    editor.putString("password", register_again_input_password_edittext.getText().toString());
                    editor.commit();
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
        register_input_phone_number_edittext = (EditText) findViewById(R.id.register_input_phone_number_edittext);
        register_again_input_password_edittext = (EditText) findViewById(R.id.register_again_input_password_edittext);
        register_input_password_edittext = findViewById(R.id.register_input_password_edittext);
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
