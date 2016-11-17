package com.gta.administrator.infraredcontrol.air_condition;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.gta.administrator.infraredcontrol.ActivityManager;
import com.gta.administrator.infraredcontrol.R;
import com.gta.administrator.infraredcontrol.other.MyGradLayoutItem;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddTelecontrolActivity extends AppCompatActivity {

    @BindView(R.id.air_condition_name)
    MyGradLayoutItem airConditionName;
    @BindView(R.id.last_page_button)
    ImageButton lastPageButton;
    @BindView(R.id.telecontrol_program_no_view)
    TextView telecontrolProgramNoView;
    @BindView(R.id.next_page_button)
    ImageButton nextPageButton;
    @BindView(R.id.power_switch_button)
    ImageButton powerSwitchButton;
    @BindView(R.id.no_button)
    ImageButton noButton;
    @BindView(R.id.yes_button)
    ImageButton yesButton;
    @BindView(R.id.open_prompt_view)
    TextView openPromptView;
    @BindView(R.id.no_button_with_layout)
    FrameLayout noButtonWithLayout;
    @BindView(R.id.yes_button_with_layout)
    FrameLayout yesButtonWithLayout;
    private ActivityManager activityManager;

    private int programNo = 1;//第几套遥控方案
    private int totalProgram = 242;//遥控方案总数

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_telecontrol);
        ButterKnife.bind(this);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.mipmap.yk_ctrl_arrows);

        activityManager = ActivityManager.getInstance(AddTelecontrolActivity.this);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_air_title, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @OnClick({R.id.last_page_button, R.id.next_page_button, R.id.power_switch_button, R.id
            .no_button, R.id.yes_button})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.last_page_button:
                if (--programNo == 0) {
                    programNo = totalProgram;
                }
                telecontrolProgramNoView.setText("第"+programNo+"/"+totalProgram+"套遥控方案\n奥克斯-AUX-1(V3)");
                break;
            case R.id.next_page_button:
                if (++programNo > totalProgram) {
                    programNo = 1;
                }
                telecontrolProgramNoView.setText("第"+programNo+"/"+totalProgram+"套遥控方案\n奥克斯-AUX-1(V3)");
                break;
            case R.id.power_switch_button:
                openPromptView.setText("空调是否打开并且温度调节到26度？");
                showView();
                break;
            case R.id.no_button:
                openPromptView.setText("点击下面开关按钮");
                hideView();
                break;
            case R.id.yes_button:
                activityManager.startActivity(AirConditionControlActivity.class, "格力空调-1");

                break;
        }
    }

    private void showView() {
        yesButtonWithLayout.setVisibility(View.VISIBLE);
        noButtonWithLayout.setVisibility(View.VISIBLE);
    }

    private void hideView() {
        yesButtonWithLayout.setVisibility(View.GONE);
        noButtonWithLayout.setVisibility(View.GONE);
    }


}
