package com.lsh.packagelibrary;

import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Base64;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.just.agentweb.AbsAgentWebSettings;
import com.just.agentweb.AgentWeb;
import com.just.agentweb.AgentWebSettingsImpl;
import com.just.agentweb.DefaultWebClient;
import com.just.agentweb.IAgentWebSettings;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;
import com.yanzhenjie.permission.Setting;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.BitmapCallback;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;

import static android.content.pm.ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;

public class WebTwoActivity extends AppCompatActivity {


    private LinearLayout mView;
    private AgentWeb mAgentWeb;
    private String mUrl, mSkipurl;
    private String referer;
    private SpUtils mSpUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Theme_AppCompat_Light_NoActionBar);
        setRequestedOrientation(SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_two);
        mView = (LinearLayout) findViewById(R.id.rl_parent);
        mSpUtils = new SpUtils(this);
        mUrl = getIntent().getStringExtra("aaurl");
        mSkipurl = getIntent().getStringExtra("skipurls");
        referer = getIntent().getStringExtra("referer");
        initaadfd();
    }

    private void initaadfd() {
        mAgentWeb = AgentWeb.with(this)
                .setAgentWebParent(mView, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT))
                .useDefaultIndicator(-1, 3)
                .setAgentWebWebSettings(getSettings())
                .setWebViewClient(mWebViewClient)
                .setWebChromeClient(mWebChromeClient)
                .setSecurityType(AgentWeb.SecurityType.STRICT_CHECK)
                .setMainFrameErrorView(R.layout.agentweb_error_page, -1)
                .setOpenOtherPageWays(DefaultWebClient.OpenOtherPageWays.ASK)
                .interceptUnkownUrl()
                .createAgentWeb()
                .ready()
                .go(mUrl);
        WebView cordWebView = mAgentWeb.getWebCreator().getWebView();
        cordWebView.getSettings().setUserAgentString(cordWebView.getSettings().getUserAgentString() + "LXWebApp");
        cordWebView.setOnLongClickListener(sadasdas);

    }

    private IAgentWebSettings getSettings() {
        AbsAgentWebSettings instance = AgentWebSettingsImpl.getInstance();
//        instance.getWebSettings().setUserAgentString(mAgentWeb.getWebCreator().getWebView().getSettings().getUserAgentString() + "LXWebApp");
//        instance.getWebSettings().setUserAgentString(instance.getWebSettings()
//                .getUserAgentString()
//                .concat("LXWebApp"));
        return instance;
    }


    @Override
    protected void onPause() {
        mAgentWeb.getWebLifeCycle().onPause();
        super.onPause();

    }

    @Override
    protected void onResume() {
        mAgentWeb.getWebLifeCycle().onResume();
        super.onResume();
    }

    @Override
    public void onDestroy() {
        mAgentWeb.getWebLifeCycle().onDestroy();
        super.onDestroy();
    }


    private WebViewClient mWebViewClient = new WebViewClient() {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            //do you  work
        }

        @Override
        public boolean shouldOverrideUrlLoading(final WebView view, String url) {
            try {


                if (!TextUtils.isEmpty(mSkipurl)) {
                    String[] strings = mSkipurl.split("&");
                    for (String s : strings) {
                        if (url.contains(s)) {
                            Intent intent = new Intent();
                            intent.setAction(Intent.ACTION_VIEW);
                            intent.setData(Uri.parse(url));
                            startActivity(intent);
                            return true;
                        }
                    }
                }


                if (url.contains("platformapi/startapp")) {
                    startAlipayActivity(url);
                    // android  6.0 两种方式获取intent都可以跳转支付宝成功,7.1测试不成功
                } else if ((Build.VERSION.SDK_INT > Build.VERSION_CODES.M)
                        && (url.contains("platformapi") && url.contains("startapp"))) {
                    startAlipayActivity(url);
                } else if (url.contains("weixin://wap/pay?") || url.contains("mqqapi://forward/url?") || url.contains("mapi.alipay.com")) {
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(url));
                    startActivity(intent);
                    return true;

                } else {
                    if (!TextUtils.isEmpty(referer)) {
                        Map<String, String> exta = new HashMap<>();
                        exta.put("Referer", referer);
                        view.loadUrl(url, exta);
                    } else {
                        view.loadUrl(url);
                    }
                }
            } catch (Exception e) {
                return true;
            }
            return true;
        }
    };


    // 调起支付宝并跳转到指定页面
    private void startAlipayActivity(String url) {
        Intent intent;
        try {
            intent = Intent.parseUri(url,
                    Intent.URI_INTENT_SCHEME);
            intent.addCategory(Intent.CATEGORY_BROWSABLE);
            intent.setComponent(null);
            startActivity(intent);
            finish();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private WebChromeClient mWebChromeClient = new WebChromeClient() {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            //do you work
        }
    };

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (mAgentWeb.handleKeyEvent(keyCode, event)) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    private View.OnLongClickListener sadasdas = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
//            MainActivityPermissionsDispatcher.downloadWithPermissionCheck(OfficalMainActivity.this,"");

            final WebView.HitTestResult hitTestResult = mAgentWeb.getWebCreator().getWebView().getHitTestResult();
            // 如果是图片类型或者是带有图片链接的类型
            if (hitTestResult.getType() == android.webkit.WebView.HitTestResult.IMAGE_TYPE ||
                    hitTestResult.getType() == android.webkit.WebView.HitTestResult.SRC_IMAGE_ANCHOR_TYPE) {
                // 弹出保存图片的对话框
                AlertDialog.Builder builder = new AlertDialog.Builder(WebTwoActivity.this);
                builder.setTitle("提示");
                builder.setMessage("保存图片到本地");
                builder.setPositiveButton("确认", new android.content.DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        // 获取屏幕
                        View dView = getWindow().getDecorView();
                        dView.setDrawingCacheEnabled(true);
                        dView.buildDrawingCache();
                        Bitmap bmp = dView.getDrawingCache();

                        if (null == bmp) {
                            Toast.makeText(WebTwoActivity.this, "保存失败,请手动截屏", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        ApplyPermission2(bmp);
                    }
                });


                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    // 自动dismiss
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
            return true;

        }
    };

    public Bitmap base64ToBitmap(String string) {
        Bitmap bitmap = null;
        try {
            byte[] bitmapArray = Base64.decode(string.split(",")[1], Base64.DEFAULT);
            bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0, bitmapArray.length);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    private void ApplyPermission2(final Bitmap bitmap) {

        AndPermission.with(WebTwoActivity.this)
                .runtime()
                .permission(Permission.WRITE_EXTERNAL_STORAGE)
                .onGranted(new Action<List<String>>() {
                    @Override
                    public void onAction(List<String> data) {
                        MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, "图片", "description");
                        try {
                            SavePic.saveImageToGallery(WebTwoActivity.this, bitmap);
                        } catch (Exception e) {
                            Toast.makeText(WebTwoActivity.this, "保存失败,请手动截屏", Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                            return;
                        }
                        ShowJumpOtherApp();

                    }
                })
                .onDenied(new Action<List<String>>() {
                    @Override
                    public void onAction(List<String> data) {
                        if (AndPermission.hasAlwaysDeniedPermission(WebTwoActivity.this, data)) {

                            new AlertDialog.Builder(WebTwoActivity.this).setTitle("申请权限").setMessage("保存图片需要给予保存权限请选择存储权限同意")
                                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {

                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            AndPermission.with(WebTwoActivity.this)
                                                    .runtime()
                                                    .setting()
                                                    .onComeback(new Setting.Action() {
                                                        @Override
                                                        public void onAction() {
                                                            // 用户从设置回来了。
                                                            ApplyPermission2(bitmap);
                                                        }
                                                    })
                                                    .start();

                                        }
                                    }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            }).show();
                        }
                    }
                })
                .start();

    }

    private void ApplyPermission(final String url) {
        AndPermission.with(WebTwoActivity.this)
                .runtime()
                .permission(Permission.WRITE_EXTERNAL_STORAGE)
//                .rationale(mRationale)
                .onGranted(new Action<List<String>>() {
                    @Override
                    public void onAction(List<String> data) {
                        if (url.endsWith(".png") || url.endsWith(".jpg") || url.endsWith(".jpeg") || url.endsWith(".gif") || url.startsWith("http")) {

                            OkHttpUtils.get().url(url).build().execute(new BitmapCallback() {
                                @Override
                                public void onError(Call call, Exception e, int id) {

                                }

                                @Override
                                public void onResponse(Bitmap response, int id) {
                                    MediaStore.Images.Media.insertImage(getContentResolver(), response, "图片", "description");
                                    try {
                                        SavePic.saveImageToGallery(WebTwoActivity.this, response);
                                    } catch (Exception e) {
                                        Toast.makeText(WebTwoActivity.this, "保存失败", Toast.LENGTH_SHORT).show();
                                        e.printStackTrace();
                                        return;
                                    }
//                                    Toast.makeText(WebActivity.this, "保存成功", Toast.LENGTH_SHORT).show();
                                    ShowJumpOtherApp();
                                }
                            });
                        } else {
                            Bitmap bitmap = base64ToBitmap(url);
                            if (bitmap == null) return;
                            MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, "图片", "description");
                            try {
                                SavePic.saveImageToGallery(WebTwoActivity.this, bitmap);
                            } catch (Exception e) {
                                Toast.makeText(WebTwoActivity.this, "保存失败", Toast.LENGTH_SHORT).show();
                                e.printStackTrace();
                                return;
                            }
//                            Toast.makeText(WebActivity.this, "保存成功", Toast.LENGTH_SHORT).show();
                            ShowJumpOtherApp();
                        }
                    }
                })
                .onDenied(new Action<List<String>>() {
                    @Override
                    public void onAction(List<String> data) {
                        if (AndPermission.hasAlwaysDeniedPermission(WebTwoActivity.this, data)) {

                            new AlertDialog.Builder(WebTwoActivity.this).setTitle("申请权限").setMessage("保存图片需要给予保存权限请选择存储权限同意")
                                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {

                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            AndPermission.with(WebTwoActivity.this)
                                                    .runtime()
                                                    .setting()
                                                    .onComeback(new Setting.Action() {
                                                        @Override
                                                        public void onAction() {
                                                            // 用户从设置回来了。
                                                            ApplyPermission(url);
                                                        }
                                                    })
                                                    .start();

                                        }
                                    }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            }).show();

                        }
                    }
                })
                .start();
    }

    public void ShowJumpOtherApp() {
        new AlertDialog.Builder(WebTwoActivity.this).setTitle("扫码支付").setMessage("请打开微信支付宝或QQ-->扫码-->点击相册-->选取二维码进行扫码支付")
                .setPositiveButton("打开微信", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        int wechatcount = mSpUtils.getInt("wechatcount", 0);
                        if (wechatcount <= 2) {
                            View view2 = View.inflate(WebTwoActivity.this, R.layout.wechat, null);
                            new AlertDialog.Builder(WebTwoActivity.this).setTitle("温馨提示").setView(view2)
                                    .setPositiveButton("我知道啦", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            try {
                                                Intent intent = new Intent(Intent.ACTION_MAIN);
                                                ComponentName cmp = new ComponentName("com.tencent.mm", "com.tencent.mm.ui.LauncherUI");
                                                intent.putExtra("LauncherUI.From.Scaner.Shortcut", true);
                                                intent.addCategory(Intent.CATEGORY_LAUNCHER);
                                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                                intent.setComponent(cmp);
                                                startActivity(intent);
                                            } catch (ActivityNotFoundException e) {
                                                Toast.makeText(WebTwoActivity.this, "检查到您手机没有安装微信，请安装后使用该功能", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    }).show();

                        } else {
                            try {
                                Intent intent = new Intent(Intent.ACTION_MAIN);
                                ComponentName cmp = new ComponentName("com.tencent.mm", "com.tencent.mm.ui.LauncherUI");
                                intent.putExtra("LauncherUI.From.Scaner.Shortcut", true);
                                intent.addCategory(Intent.CATEGORY_LAUNCHER);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.setComponent(cmp);
                                startActivity(intent);
                            } catch (ActivityNotFoundException e) {
                                Toast.makeText(WebTwoActivity.this, "检查到您手机没有安装微信，请安装后使用该功能", Toast.LENGTH_SHORT).show();
                            }
                        }

                        mSpUtils.putInt("wechatcount", ++wechatcount);

                    }

                }).setNegativeButton("打开支付宝", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int zhicount = mSpUtils.getInt("zhicount", 0);
                if (zhicount <= 2) {

                    View view2 = View.inflate(WebTwoActivity.this, R.layout.zhi, null);
                    new AlertDialog.Builder(WebTwoActivity.this).setTitle("温馨提示").setView(view2)
                            .setPositiveButton("我知道啦", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    try {
                                        Uri uri = Uri.parse("alipayqr://platformapi/startapp?saId=10000007");
                                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                                        startActivity(intent);
                                    } catch (Exception e) {
                                        Toast.makeText(WebTwoActivity.this, "检查到您手机没有安装支付宝，请安装后使用该功能", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }).show();


                } else {
                    try {
                        Uri uri = Uri.parse("alipayqr://platformapi/startapp?saId=10000007");
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        startActivity(intent);
                    } catch (Exception e) {
                        Toast.makeText(WebTwoActivity.this, "检查到您手机没有安装支付宝，请安装后使用该功能", Toast.LENGTH_SHORT).show();
                    }
                }

                mSpUtils.putInt("zhicount", ++zhicount);
            }
        }).setNeutralButton("打开QQ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    Intent intent = WebTwoActivity.this.getPackageManager().getLaunchIntentForPackage("com.tencent.mobileqq");
                    WebTwoActivity.this.startActivity(intent);
                } catch (Exception e) {
                    Toast.makeText(WebTwoActivity.this, "检查到您手机没有安装QQ，请安装后使用该功能", Toast.LENGTH_SHORT).show();
                }

            }
        }).show();

    }
}
