package com.lsh.packagelibrary;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lsh.XXRecyclerview.CommonRecyclerAdapter;
import com.lsh.XXRecyclerview.CommonViewHolder;
import com.lsh.XXRecyclerview.XXRecycleView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

import static android.content.pm.ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;

public class MarqueeActivity extends AppCompatActivity {

    private XXRecycleView xxl;
    private CommonRecyclerAdapter<String> adapter;
    private  MarqueeBean result = null;
    private TextView tv_titile2;
    private TextView tv_titile1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_AppCompat_Light_NoActionBar);
        setRequestedOrientation(SCREEN_ORIENTATION_PORTRAIT);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(R.layout.activity_marquee);
        xxl = (XXRecycleView) findViewById(R.id.xxl);
        tv_titile1 = (TextView) findViewById(R.id.tv_title1);
        tv_titile2 = (TextView) findViewById(R.id.tv_title2);
        initXXl();
        get_data(1);
    }

    private void get_data(int page_index) {
        OkHttpUtils
                .post()
                .url("http://sz2.llcheng888.com/switch/api/marquee_data")
                .addParams("page_index", page_index+"")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.w("MarqueeActivity", "e:" + e);
                        xxl.stopLoad();
                        Toast.makeText(MarqueeActivity.this, "网络错误", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            result = new Gson().fromJson(response, MarqueeBean.class);
                            handResult();
                        }catch (Exception e){
                            Toast.makeText(MarqueeActivity.this, "无数据", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void handResult() {
        xxl.stopLoad();
        xxl.setCanLoad(result.isNext());
        xxl.setLoadMoreEnabled(result.isNext());
        if (null != result.getData()) {
            adapter.addAll(result.getData());
        }
        tv_titile1.setText(result.getTitle1());
        tv_titile2.setText(result.getTitle2());
    }

    private void initXXl() {
        adapter = new CommonRecyclerAdapter<String>(this, null, R.layout.marquee_layout) {

            @Override
            public void convert(CommonViewHolder helper, String s, int i, boolean b) {
                String[] split = s.split("&");
                String s1 = split[0];
                int kuan = s1.indexOf("款")+1;
                int yuan = s1.indexOf("元");
                helper.setText(R.id.tv_money,s1.substring(kuan,yuan));
                helper.setText(R.id.tv_left,s1.substring(0,kuan));
                helper.setText(R.id.tv_right,split[1]);
            }
        };
        xxl.setAdapter(adapter);
        xxl.setLoadMoreEnabled(true);

        xxl.setOnLoadMoreListener(new XXRecycleView.OnLoadMoreListener() {
            @Override
            public void onLoad() {
                if (result != null)
                get_data(result.getPage()+1);
            }

            @Override
            public void loadEnd() {

            }
        });
    }
}
