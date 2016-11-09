package com.gta.administrator.infraredcontrol;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by Administrator on 2016/6/13.
 */
public class LoadingWin extends AppCompatActivity implements View.OnClickListener {
    private Button id_load_btn;
    private EditText id_disp_pwd_edit, id_pwd_edit;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading_window);
        id_load_btn = (Button) findViewById(R.id.id_load_btn);
        id_load_btn.setOnClickListener(this);

        id_disp_pwd_edit = (EditText) findViewById(R.id.id_disp_pwd_edit);
        id_disp_pwd_edit.setOnClickListener(this);
        id_pwd_edit = (EditText) findViewById(R.id.id_pwd_edit);
    }

    private boolean isDispPwd = true;
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.id_load_btn:
                startActivity(new Intent(LoadingWin.this, Main1Activity.class));
                finish();
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
