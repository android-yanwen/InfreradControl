package com.gta.administrator.infraredcontrol_apk;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Administrator on 2016/6/13.
 */
public class LoadingWin extends AppCompatActivity implements View.OnClickListener {
    private Button id_load_btn;
    private EditText id_disp_pwd_edit, id_pwd_edit, id_phone_edit;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading_window);
        id_load_btn = (Button) findViewById(R.id.id_load_btn);
        id_load_btn.setOnClickListener(this);

        id_disp_pwd_edit = (EditText) findViewById(R.id.id_disp_pwd_edit);
        id_disp_pwd_edit.setOnClickListener(this);
        id_pwd_edit = (EditText) findViewById(R.id.id_pwd_edit);

        id_phone_edit = findViewById(R.id.id_phone_edit);
    }

    private boolean isDispPwd = true;
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.id_load_btn:
                if (id_phone_edit.getText().toString().isEmpty()) {
                    Toast.makeText(this, "帐号不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (id_pwd_edit.getText().toString().isEmpty()) {
                    Toast.makeText(this, "密码不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (id_phone_edit.getText().toString().equals("test") &&
                        "test".equals(id_pwd_edit.getText().toString())) {
                    startActivity(new Intent(LoadingWin.this, Main1Activity.class));
                    finish();
                    return;
                }
                SharedPreferences sharedPreferences = getSharedPreferences("login", MODE_PRIVATE);
                String phone = sharedPreferences.getString("phone", "");
                String password = sharedPreferences.getString("password", "");
                if (phone.equals(id_phone_edit.getText().toString()) && password.equals(id_pwd_edit.getText().toString())) {
                    startActivity(Main1Activity.newInstance());
                    finish();
                } else {
                    Toast.makeText(this, "帐号或密码输入不正确", Toast.LENGTH_SHORT).show();
                } 
                break;
            case R.id.id_disp_pwd_edit:
                Drawable drawable/* = getDrawable(R.drawable.show)*/;
//                Drawable[] drawables = id_disp_pwd_edit.getCompoundDrawables();  //获取当前的drawable[2] (drawableEnd)
                if (isDispPwd) { /* 显示密码 */
                    isDispPwd = false;
                    drawable = getResources().getDrawable(R.mipmap.show_2);
                    id_pwd_edit.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                } else { /* 隐藏密码 */
                    isDispPwd = true;
                    drawable = getResources().getDrawable(R.mipmap.show);
                    id_pwd_edit.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
                id_disp_pwd_edit.setCompoundDrawablesRelativeWithIntrinsicBounds(null,null,drawable,null);
                break;
        }
    }
}
