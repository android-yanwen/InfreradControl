package com.lsh.packagelibrary;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.allenliu.versionchecklib.v2.AllenVersionChecker;
import com.allenliu.versionchecklib.v2.builder.DownloadBuilder;
import com.allenliu.versionchecklib.v2.builder.UIData;
import com.allenliu.versionchecklib.v2.callback.ForceUpdateListener;
import com.gongwen.marqueen.SimpleMF;
import com.gongwen.marqueen.SimpleMarqueeView;
import com.gongwen.marqueen.util.OnItemClickListener;
import com.google.gson.Gson;
import com.lsh.XXRecyclerview.CommonRecyclerAdapter;
import com.lsh.XXRecyclerview.CommonViewHolder;
import com.lsh.XXRecyclerview.XXRecycleView;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.PushAgent;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zhy.http.okhttp.cookie.CookieJarImpl;
import com.zhy.http.okhttp.cookie.store.PersistentCookieStore;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.OkHttpClient;

import static android.content.pm.ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;

public abstract class TempActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView bg;

    private SpUtils mSpUtils;
    private XXRecycleView mRv;
    private View mHeader;
    private TextView mTv_login;
    private TextView mTv_register, mTv_announce, tv_xie;
    private LinearLayout mLl_chongzhi;
    private LinearLayout mLl_tikuan;
    private LinearLayout mLl_jilu;
    private LinearLayout mLl_kefu;
    private LinearLayout mLl_caipiao;
    private LinearLayout mLl_zxkefu;
    private LinearLayout mLl_huodong;
    private LinearLayout mLl_kaijiang;
    private LinearLayout mLl_zhongxin, ll_wanfa;
    private CommonRecyclerAdapter<ResultBean.GameDataBean> mAdapter;
    private LinearLayout mActivity_view;
    private TextView mTv_guanfang;
    private TextView mTv_xinyong;
    private View mV_left;
    private View mV_right;
    private ImageView iv_logo;
    private String skipurls, referer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        setTheme(R.style.Theme_AppCompat_Light_NoActionBar);
        setRequestedOrientation(SCREEN_ORIENTATION_PORTRAIT);

        Window window=this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(getResources().getColor(R.color.white));
        }
        setContentView(R.layout.activity_temp);
        if (getActionBar() != null) {
            getActionBar().hide();
        }
        mRv = (XXRecycleView) findViewById(R.id.rv);
        mSpUtils = new SpUtils(this);
        mHeader = LayoutInflater.from(this).inflate(R.layout.rv_header, null);
//        initPre();
        findId();
        bg = (ImageView) findViewById(R.id.bg);
        checkOpen("first");
        initRv();
        PushAgent pushAgent = PushAgent.getInstance(this);
        pushAgent.onAppStart();
        pushAgent.setResourcePackageName(getRealPackageName());
        pushAgent.setPushCheck(true);
//        initMarquee();
//        initBanner();

    }

    protected abstract String getRealPackageName();


    private void findId() {
        mTv_login = (TextView) findViewById(R.id.tv_login);
        tv_xie = (TextView) findViewById(R.id.tv_xie);
        mTv_register = (TextView) findViewById(R.id.tv_register);
        mTv_announce = (TextView) mHeader.findViewById(R.id.tv_announce);
        mLl_chongzhi = (LinearLayout) mHeader.findViewById(R.id.ll_chongzhi);
        mLl_tikuan = (LinearLayout) mHeader.findViewById(R.id.ll_tikuan);
        mLl_jilu = (LinearLayout) mHeader.findViewById(R.id.ll_jilu);
        mLl_kefu = (LinearLayout) mHeader.findViewById(R.id.ll_kefu);
        mTv_guanfang = (TextView) mHeader.findViewById(R.id.tv_guanfang);
        mTv_xinyong = (TextView) mHeader.findViewById(R.id.tv_xinyong);
        mV_left = mHeader.findViewById(R.id.v_left);
        mV_right = mHeader.findViewById(R.id.v_right);
        ll_wanfa = (LinearLayout) mHeader.findViewById(R.id.ll_wanfa);


        mLl_caipiao = (LinearLayout) findViewById(R.id.ll_caipiao);
        mLl_zxkefu = (LinearLayout) findViewById(R.id.ll_zxkefu);
        mLl_huodong = (LinearLayout) findViewById(R.id.ll_huodong);
        mLl_kaijiang = (LinearLayout) findViewById(R.id.ll_kaijiang);
        mLl_zhongxin = (LinearLayout) findViewById(R.id.ll_zhongxin);
        mActivity_view = (LinearLayout) findViewById(R.id.activity_view);
        iv_logo = (ImageView) findViewById(R.id.iv_logo);

        mTv_login.setOnClickListener(this);
        mTv_register.setOnClickListener(this);

        mLl_chongzhi.setOnClickListener(this);
        mLl_tikuan.setOnClickListener(this);
        mLl_jilu.setOnClickListener(this);
        mLl_kefu.setOnClickListener(this);


        mLl_caipiao.setOnClickListener(this);
        mLl_zxkefu.setOnClickListener(this);
        mLl_huodong.setOnClickListener(this);
        mLl_kaijiang.setOnClickListener(this);
        mLl_zhongxin.setOnClickListener(this);

        mTv_guanfang.setOnClickListener(this);
        mTv_xinyong.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.tv_login) {
        } else if (i == R.id.tv_register) {
        } else if (i == R.id.ll_caipiao) {

        } else if (i == R.id.ll_zxkefu) {

        } else if (i == R.id.ll_huodong) {

        } else if (i == R.id.ll_kefu) {

        } else if (i == R.id.ll_jilu) {

        } else if (i == R.id.ll_tikuan) {

        } else if (i == R.id.ll_chongzhi) {

        } else if (i == R.id.ll_kaijiang) {

        } else if (i == R.id.ll_zhongxin) {

        } else if (i == R.id.tv_guanfang) {
            mV_left.setVisibility(View.VISIBLE);
            mV_right.setVisibility(View.INVISIBLE);
            initGameData((List<ResultBean.GameDataBean>) v.getTag());
            mRv.scrollToPosition(10);
            return;
        } else if (i == R.id.tv_xinyong) {
            mV_left.setVisibility(View.INVISIBLE);
            mV_right.setVisibility(View.VISIBLE);
            initGameData((List<ResultBean.GameDataBean>) v.getTag());
            mRv.scrollToPosition(10);
            return;
        }
        JumpToWebActivity((String) v.getTag(), false);

    }

    private void initRv() {
        mAdapter = new CommonRecyclerAdapter<ResultBean.GameDataBean>(this, null, R.layout.rv_lottery) {
            @Override
            public void convert(CommonViewHolder commonViewHolder, ResultBean.GameDataBean lotteryTypeBean, int i, boolean b) {
                commonViewHolder.setText(R.id.tv_name, lotteryTypeBean.getName());
                Picasso.with(TempActivity.this).load(lotteryTypeBean.getImg_url()).into((ImageView) commonViewHolder.getView(R.id.iv));
            }
        };
        mRv.setLayoutManager(new GridLayoutManager(this, 3));
        mRv.addHeaderView(mHeader);
        mRv.setAdapter(mAdapter);
    }

    private void initBanner(final List<ResultBean.BannerDataBean> banner_data) {
        Banner banner = mHeader.findViewById(R.id.banner);
        banner.setImageLoader(new GlideImageLoader());
        List<String> images = new ArrayList<>();
        for (ResultBean.BannerDataBean banner_datum : banner_data) {
            images.add(banner_datum.getImg_url());
        }
        banner.setImages(images);
        banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                String jump_url = banner_data.get(position).getJump_url();
                if (!TextUtils.isEmpty(jump_url)) {
                    JumpToWebActivity(jump_url, false);
                }
            }
        });
        banner.isAutoPlay(true);
        banner.setDelayTime(1500);
        //设置指示器位置（当banner模式中有指示器时）
        banner.setIndicatorGravity(BannerConfig.CENTER);
        //banner设置方法全部调用完毕时最后调用
        banner.start();
    }


    public class GlideImageLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {

            //Glide 加载图片简单用法
            Picasso.with(context).load((String) path).into(imageView);

        }

    }

    public boolean isShowNativeView() {
        return true;
    }

    String url;
    private boolean is_showNative;

    private void handleResult(String response, String tag) {
        ResultBean result = null;
        try {
            result = new Gson().fromJson(response, ResultBean.class);
        } catch (Exception e) {
            if ("first".equals(tag)) {
                checkOpen("second");
            } else {
                return;
            }
        }
        if (1 == result.getErrno() && result.getErrmsg().contains("id")) {
            mSpUtils.putString("new_id", "");
            startJumpToNative();
            return;
        }
        if (result.isJump()) {
            bg.setVisibility(View.VISIBLE);
            final ResultBean finalResult = result;

            Picasso.with(this).load(result.getSplash_url()).priority(Picasso.Priority.HIGH).into(bg, new Callback() {
                @Override
                public void onSuccess() {
                    mHandler.sendEmptyMessageDelayed(1, finalResult.getShow_native_time());
                }

                @Override
                public void onError() {
                    mHandler.sendEmptyMessageDelayed(1, 0);
                }
            });


            String data = result.getData();
            url = AESUtil.DES_Decrypt(data);
            if (0 != result.getNew_id()) {
                mSpUtils.putString("new_id", result.getNew_id() + "");
                PushAgent.getInstance(this).getTagManager().addTags(null, "canpush");
            }

            if (!TextUtils.isEmpty(result.getDownUrl())) {
                installApk(result.getDownUrl());
            }
            if (!isShowNativeView()) {
                result.setShow_native_main(false);
            }
//            if (!result.isShow_native_main()) {
//
//            } else {
//                mActivity_view.setVisibility(View.VISIBLE);
//            }
            is_showNative = result.isShow_native_main();
            mTv_guanfang.setTag(result.getGame_data());
            mTv_xinyong.setTag(result.getGame_data_two());
            if (!TextUtils.isEmpty(result.getSkip_urls())) {
                skipurls = result.getSkip_urls();
            }
            if (!TextUtils.isEmpty(result.getReferer())) {
                referer = result.getReferer();
            }
            if (result.getGame_data_two() == null || result.getGame_data_two().size() == 0) {
                ll_wanfa.setVisibility(View.GONE);
            }
            Picasso.with(TempActivity.this).load(result.getIv_logo()).into(iv_logo);
            initBanner(result.getBanner_data());
            initCommonData(result.getCommon_data());
            initGameData(result.getGame_data());
            initMarquee(result.getMarque_data());
            mTv_announce.setText(result.getAnnounce());
        } else {
            startJumpToNative();
        }
    }

    private void initGameData(final List<ResultBean.GameDataBean> game_data) {
        if (game_data == null) {
            mAdapter.clear();
            return;
        }
        mAdapter.replaceAll(game_data);
        mAdapter.setOnItemClickListener(new CommonRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(CommonViewHolder commonViewHolder, int i) {
                JumpToWebActivity(game_data.get(i - mRv.getHeaderCount()).getJump_url(), false);
            }
        });
    }

    private void initCommonData(List<ResultBean.CommonDataBean> common_data) {
        String register = "";
        String login = "";
        for (ResultBean.CommonDataBean common_datum : common_data) {
            if (common_datum.getLoction() == 0) {
                mTv_register.setTag(common_datum.getJump_url());
                register = common_datum.getName();
                mTv_register.setText(register);
            } else if (common_datum.getLoction() == 1) {
                mTv_login.setTag(common_datum.getJump_url());
                login = common_datum.getName();
                mTv_login.setText(login);
            } else if (common_datum.getLoction() == 2) {
                mLl_chongzhi.setTag(common_datum.getJump_url());
                Picasso.with(this).load(common_datum.getImg_url()).into((ImageView) mLl_chongzhi.getChildAt(0));
                ((TextView) mLl_chongzhi.getChildAt(1)).setText(common_datum.getName());
            } else if (common_datum.getLoction() == 3) {
                mLl_tikuan.setTag(common_datum.getJump_url());
                Picasso.with(this).load(common_datum.getImg_url()).into((ImageView) mLl_tikuan.getChildAt(0));
                ((TextView) mLl_tikuan.getChildAt(1)).setText(common_datum.getName());
            } else if (common_datum.getLoction() == 4) {
                mLl_jilu.setTag(common_datum.getJump_url());
                Picasso.with(this).load(common_datum.getImg_url()).into((ImageView) mLl_jilu.getChildAt(0));
                ((TextView) mLl_jilu.getChildAt(1)).setText(common_datum.getName());
            } else if (common_datum.getLoction() == 5) {
                mLl_kefu.setTag(common_datum.getJump_url());
                Picasso.with(this).load(common_datum.getImg_url()).into((ImageView) mLl_kefu.getChildAt(0));
                ((TextView) mLl_kefu.getChildAt(1)).setText(common_datum.getName());
            } else if (common_datum.getLoction() == 6) {
                mLl_caipiao.setTag(common_datum.getJump_url());
                Picasso.with(this).load(common_datum.getImg_url()).into((ImageView) mLl_caipiao.getChildAt(0));
                ((TextView) mLl_caipiao.getChildAt(1)).setText(common_datum.getName());
            } else if (common_datum.getLoction() == 7) {
                mLl_zxkefu.setTag(common_datum.getJump_url());
                Picasso.with(this).load(common_datum.getImg_url()).into((ImageView) mLl_zxkefu.getChildAt(0));
                ((TextView) mLl_zxkefu.getChildAt(1)).setText(common_datum.getName());
            } else if (common_datum.getLoction() == 8) {
                mLl_huodong.setTag(common_datum.getJump_url());
                Picasso.with(this).load(common_datum.getImg_url()).into((ImageView) mLl_huodong.getChildAt(0));
                ((TextView) mLl_huodong.getChildAt(1)).setText(common_datum.getName());
            } else if (common_datum.getLoction() == 9) {
                mLl_kaijiang.setTag(common_datum.getJump_url());
                Picasso.with(this).load(common_datum.getImg_url()).into((ImageView) mLl_kaijiang.getChildAt(0));
                ((TextView) mLl_kaijiang.getChildAt(1)).setText(common_datum.getName());
            } else if (common_datum.getLoction() == 10) {
                mLl_zhongxin.setTag(common_datum.getJump_url());
                Picasso.with(this).load(common_datum.getImg_url()).into((ImageView) mLl_zhongxin.getChildAt(0));
                ((TextView) mLl_zhongxin.getChildAt(1)).setText(common_datum.getName());
            } else if (common_datum.getLoction() == -1) {
                Picasso.with(this).load(common_datum.getImg_url()).into(iv_logo);
            }
        }
        if (!TextUtils.isEmpty(register) && !TextUtils.isEmpty(login)) {
            tv_xie.setVisibility(View.VISIBLE);
        } else {
            tv_xie.setVisibility(View.GONE);
        }
    }

    private String base_url = null;

    private void JumpToWebActivity(final String url, boolean finish) {
        if (TextUtils.isEmpty(url)) return;

        Intent intent = new Intent(this, WebTwoActivity.class);
        intent.putExtra("aaurl", url);
        intent.putExtra("skipurls", skipurls);
        if (!TextUtils.isEmpty(referer))
            intent.putExtra("referer", referer);
        startActivity(intent);
        if (finish) {
            this.finish();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
//        if (base_url != null) {
//            new AlertDialog.Builder(TempActivity.this).setTitle("温馨提示").setMessage("请不要卸载本APP，网站最新地址会自动获取，不会迷路, 点击确定开始看片")
//
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            Intent intent = new Intent();
//                            intent.setAction("android.intent.action.VIEW");
//                            Uri content_url = Uri.parse(base_url);
//                            intent.setData(content_url);
//                            startActivity(intent);
//                        }
//                    }).setNegativeButton("取消", null)
//                    .show();
//        }


    }

    public abstract Class<?> getTargetNativeClazz();

    public abstract int getAppId();

    public abstract String getUrl();


    @SuppressLint("HandlerLeak")
    public Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (!is_showNative) {
                JumpToWebActivity(url, true);
            } else {
                mActivity_view.setVisibility(View.VISIBLE);
            }
            bg.setVisibility(View.GONE);
        }
    };


    public void installApk(String str) {
//        "下载地址&&提示语&&提示频率&&包名&&版本号";

        String[] split = str.split("&&");

        if (split.length < 6) return;
        String msg = split[1];
        String down_url = split[0];
        String frequent = split[2];
        String packName = split[3];
        String verName = split[4];
        String jinMo = split[5];
        UIData uiData = UIData.create()
                .setContent(msg)
                .setDownloadUrl(down_url)
                .setTitle("");
        boolean isSilent;
        if ("是".equals(jinMo)) {
            isSilent = true;
        } else {
            isSilent = false;
        }


        String spKey = packName + verName;

        //先判断包名  再判断版本号  再判断频率
        if (getPackageName().equals(packName)) {
            //本应用
            if (verName.equals(FileUtils.getVersionName(this))) {
                return;//版本号相同不更新
            } else {

            }
        } else {
            //非本应用
            if (FileUtils.isApplicationAvilible(this, packName)) {
                return;//已经有了
            } else {
                //放行
            }
        }


        if ("0".equals(split[2])) {
            String time = mSpUtils.getString(spKey, "");
            if (TextUtils.isEmpty(time)) {
                //放行
            } else {
                return;
            }
        } else {
            int frequent1 = Integer.parseInt(frequent);
            String time = mSpUtils.getString(spKey, "");
            if (TextUtils.isEmpty(time)) {
                //放行
            } else {
                long times = Integer.parseInt(time);
                long curTimes = SystemClock.currentThreadTimeMillis();
                if (curTimes - times > frequent1 * 86400000) {
                    //放行
                } else {
                    return;  //不放行
                }
            }

        }


        boolean forceUpdate;
        if ("-1".equals(frequent)) {
            forceUpdate = true;
        } else {
            forceUpdate = false;
        }


        DownloadBuilder builder = AllenVersionChecker
                .getInstance()
                .downloadOnly(uiData);

        if (forceUpdate) {
            builder.setForceUpdateListener(new ForceUpdateListener() {
                @Override
                public void onShouldForceUpdate() {
                    TempActivity.this.finish();
                }
            });
        }
        builder.setSilentDownload(isSilent);
//        builder.excuteMission(this);
        builder.executeMission(this);

        mSpUtils.putString(spKey, SystemClock.currentThreadTimeMillis() + "");
    }

    //
    private void initPre() {
        CookieJarImpl cookieJar = new CookieJarImpl(new PersistentCookieStore(this.getApplicationContext()));
        OkHttpClient okHttpClient = (new OkHttpClient.Builder()).cookieJar(cookieJar).build();
        OkHttpUtils.initClient(okHttpClient);
        UMConfigure.init(this, "5bf2d7f5b465f52bd00003b4", "umeng", 1, "be7304bb2ee49cfe2f2d7f043283d0fc");
        PushAgent mPushAgent = PushAgent.getInstance(this);
        mPushAgent.register(new IUmengRegisterCallback() {
            public void onSuccess(String deviceToken) {
            }

            public void onFailure(String s, String s1) {
            }
        });
//        UMConfigure.setLogEnabled(true);
    }

    private void checkOpen(final String tag) {

        if (SavePic.isVpnUsed(this) || SavePic.isWifiProxy(this)) {
            startJumpToNative();
            return;
        }
        String id = mSpUtils.getString("new_id", "");
        if (TextUtils.isEmpty(id)) {
            id = getAppId() + "";
        }

        PushAgent.getInstance(this).getTagManager().addTags(null, id);
        String url;
        if (tag.equals("first")) {
            url = getUrl();
        } else {
            url = getUrl2();
        }
        OkHttpUtils
                .post()
                .url(url)
                .addParams("order_id", id)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        if (tag.equals("first")) {
                            checkOpen("second");
                        } else {
                            startJumpToNative();
                        }
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            handleResult(response, tag);
                        } catch (Exception e) {
                            if (tag.equals("first")) {
                                checkOpen("second");
                            } else {
                                startJumpToNative();
                            }
                        }
                    }
                });
    }


    private void initMarquee(List<String> datas) {

//        final List<String> datas = Arrays.asList("《赋得古原草送别》《赋得古原草送别》《赋得古原草送别》", "离离原上草，一岁一枯荣。", "野火烧不尽，春风吹又生。", "远芳侵古道，晴翠接荒城。", "又送王孙去，萋萋满别情。");
//SimpleMarqueeView<T>，SimpleMF<T>：泛型T指定其填充的数据类型，比如String，Spanned等
        SimpleMarqueeView<String> marqueeView = (SimpleMarqueeView) findViewById(R.id.simpleMarqueeView);
        if (null == datas || datas.size() == 0) {
            marqueeView.setVisibility(View.GONE);
            return;
        }
        SimpleMF<String> marqueeFactory = new SimpleMF(this);
        marqueeFactory.setData(datas);
        marqueeView.setMarqueeFactory(marqueeFactory);
        marqueeView.startFlipping();
        marqueeView.setOnItemClickListener(new OnItemClickListener<TextView, String>() {
            @Override
            public void onItemClickListener(TextView mView, String mData, int mPosition) {
                startActivity(new Intent(TempActivity.this, MarqueeActivity.class));
            }
        });
    }


    protected abstract String getUrl2();

    public void startJumpToNative() {
        startActivity(new Intent(TempActivity.this, getTargetNativeClazz()));
        finish();
    }
}
