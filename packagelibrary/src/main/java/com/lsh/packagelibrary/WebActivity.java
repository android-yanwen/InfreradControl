//package com.lsh.packagelibrary;
//
//import android.annotation.SuppressLint;
//import android.annotation.TargetApi;
//import android.app.Activity;
//import android.content.ActivityNotFoundException;
//import android.content.ComponentName;
//import android.content.ContextWrapper;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.content.pm.ActivityInfo;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.net.Uri;
//import android.net.http.SslError;
//import android.os.Build;
//import android.os.Bundle;
//import android.provider.MediaStore;
//import android.support.annotation.Nullable;
//import android.support.v7.app.AlertDialog;
//import android.text.TextUtils;
//import android.util.Base64;
//import android.view.View;
//import android.view.ViewGroup;
//import android.webkit.CookieManager;
//import android.webkit.SslErrorHandler;
//import android.webkit.WebChromeClient;
//import android.webkit.WebResourceRequest;
//import android.webkit.WebResourceResponse;
//import android.webkit.WebSettings;
//import android.webkit.WebView;
//import android.widget.FrameLayout;
//import android.widget.ProgressBar;
//import android.widget.Toast;
//
//import com.yanzhenjie.permission.Action;
//import com.yanzhenjie.permission.AndPermission;
//import com.yanzhenjie.permission.Permission;
//import com.yanzhenjie.permission.Setting;
//import com.zhy.http.okhttp.OkHttpUtils;
//import com.zhy.http.okhttp.callback.BitmapCallback;
//
//import org.apache.cordova.CordovaActivity;
//import org.apache.cordova.CordovaChromeClient;
//import org.apache.cordova.CordovaInterface;
//import org.apache.cordova.CordovaPlugin;
//import org.apache.cordova.CordovaWebView;
//import org.apache.cordova.CordovaWebViewClient;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//
//import okhttp3.Call;
//
//import static android.content.pm.ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
//
///**
// * Author:lsh
// * Version: 1.0
// * Description:
// * Date: 2018/4/26
// */
//
//public class WebActivity extends CordovaActivity {
//    private CordovaWebView cordWebView;
//    private ProgressBar pg1;
//    private String mSkipurl;
//    private String referer;
//    private SpUtils mSpUtils;
//
//    //    Theme.AppCompat.NoActionBar
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        setTheme(R.style.Theme_AppCompat_Light_NoActionBar);
//        setRequestedOrientation(SCREEN_ORIENTATION_PORTRAIT);
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_web_clad);
//        cordWebView = (CordovaWebView) findViewById(R.id.cordovaview_llac);
//        pg1 = (ProgressBar) findViewById(R.id.progressBar1);
//        mSpUtils = new SpUtils(this);
//        initCachePath();
//
//        initCordWebView();
//        String url = getIntent().getStringExtra("aaurl");
//        mSkipurl = getIntent().getStringExtra("skipurls");
//        referer = getIntent().getStringExtra("referer");
//        cordWebView.loadUrl(url);
//    }
//
//
//    //初始化cordovaWebView
//    @SuppressLint("NewApi")
//    public void initCordWebView() {
//
//        cordWebView.setWebViewClient(new CordovaWebViewClient(new CordovaContext(this), cordWebView) {
//            @Override
//            public void onPageStarted(WebView view, String url, Bitmap favicon) {
//                super.onPageStarted(view, url, favicon);
//            }
//
//            @Override
//            public void onPageFinished(WebView view, String url) {
//                super.onPageFinished(view, url);
//            }
//
//            //...WebView报错时
//            @Override
//            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
////                hideWaitingPage();
//            }
//
//            @Override
//            public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
//                return super.shouldInterceptRequest(view, url);
//            }
//
//            // API 21 以上用shouldInterceptRequest(WebView view, WebResourceRequest request)
//            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
//            @Override
//            public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
//
//                return super.shouldInterceptRequest(view, request);
//            }
//
//            @Override
//            public boolean shouldOverrideUrlLoading(final WebView view, String url) {
//                try {
//
//
//                    if (!TextUtils.isEmpty(mSkipurl)) {
//                        String[] strings = mSkipurl.split("&");
//                        for (String s : strings) {
//                            if (url.contains(s)) {
//                                Intent intent = new Intent();
//                                intent.setAction(Intent.ACTION_VIEW);
//                                intent.setData(Uri.parse(url));
//                                startActivity(intent);
//                                return true;
//                            }
//                        }
//                    }
//
//
//                    if (url.contains("platformapi/startapp")) {
//                        startAlipayActivity(url);
//                        // android  6.0 两种方式获取intent都可以跳转支付宝成功,7.1测试不成功
//                    } else if ((Build.VERSION.SDK_INT > Build.VERSION_CODES.M)
//                            && (url.contains("platformapi") && url.contains("startapp"))) {
//                        startAlipayActivity(url);
//                    } else if (url.contains("weixin://wap/pay?") || url.contains("mqqapi://forward/url?") || url.contains("mapi.alipay.com")) {
//                        Intent intent = new Intent();
//                        intent.setAction(Intent.ACTION_VIEW);
//                        intent.setData(Uri.parse(url));
//                        startActivity(intent);
//                        return true;
//
//                    } else {
//                        if (!TextUtils.isEmpty(referer)) {
//                            Map<String, String> exta = new HashMap<>();
//                            exta.put("Referer", referer);
//                            cordWebView.loadUrl(url, exta);
//                        } else {
//                            cordWebView.loadUrl(url);
//                        }
//                    }
//                } catch (Exception e) {
//                    return true;
//                }
//                return true;
//            }
//
//            @Override
//            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
//                //此方法是为了处理在5.0以上Htts的问题，必须加上
//                handler.proceed();
//            }
//        });
//
//        cordWebView.setWebChromeClient(new CordovaChromeClient(new CordovaContext(this), cordWebView) {
//
//            @Override
//            public void onReceivedTitle(WebView view, String title) {
//                super.onReceivedTitle(view, title);
////                Log.w("TempActivity", title);
////                if ("网页无法打开".equals(title)) {
////                    cordWebView.reload();
////                }
//            }
//
//            @Override
//            public void onProgressChanged(WebView view, int newProgress) {
//                super.onProgressChanged(view, newProgress);
//                if (newProgress == 100) {
//                    pg1.setVisibility(View.GONE);//加载完网页进度条消失
//                } else {
//                    pg1.setVisibility(View.VISIBLE);//开始加载网页时显示进度条
//                    pg1.setProgress(newProgress);//设置进度值
//                }
//            }
//        });
//
//        //获取缓存文件夹
//        // 设置 缓存模式
//        cordWebView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
//        //设置缓存路径
//        cordWebView.getSettings().setAppCachePath(cachePath);
//        // 开启 DOM storage API 功能
//        cordWebView.getSettings().setDomStorageEnabled(true);
//        //开启 database storage API 功能
//        cordWebView.getSettings().setDatabaseEnabled(true);
//        //开启缓存
//        cordWebView.getSettings().setAppCacheEnabled(true);
//        cordWebView.getSettings().setJavaScriptEnabled(true);
//        cordWebView.getSettings().setBuiltInZoomControls(false);
//        cordWebView.getSettings().setSupportZoom(true);
//        cordWebView.getSettings().setSaveFormData(true);
//        cordWebView.getSettings().setDatabasePath(filePath);
//        cordWebView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
//        if (Build.VERSION.SDK_INT >= 21) {
//            cordWebView.getSettings().setMixedContentMode(0);
//            CookieManager.getInstance().setAcceptThirdPartyCookies(cordWebView, true);
//        }
//        //设置允许访问文件
//        cordWebView.getSettings().setAllowFileAccess(true);
//        //支持自动加载图片
//        cordWebView.getSettings().setLoadsImagesAutomatically(true);
//        cordWebView.getSettings().setUserAgentString(cordWebView.getSettings().getUserAgentString() + "LXWebApp");
//
//        cordWebView.getSettings().setLoadWithOverviewMode(true);
//        cordWebView.getSettings().setMediaPlaybackRequiresUserGesture(false);
//        cordWebView.getSettings().setUseWideViewPort(true);
//        cordWebView.setOnLongClickListener(sadasdas);
//
//    }
//
//
//    // 调起支付宝并跳转到指定页面
//    private void startAlipayActivity(String url) {
//        Intent intent;
//        try {
//            intent = Intent.parseUri(url,
//                    Intent.URI_INTENT_SCHEME);
//            intent.addCategory(Intent.CATEGORY_BROWSABLE);
//            intent.setComponent(null);
//            startActivity(intent);
//            finish();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Override
//    public void onDestroy() {
//        cordWebView.handleDestroy();
//        cordWebView.destroy();
//        if (cordWebView.pluginManager != null) {
//            cordWebView.pluginManager.onDestroy();
//        }
//
//        super.onDestroy();
//    }
//
//    public void loadUrl(String url) {
//        cordWebView.loadUrl(url);
//    }
//
//    private String cachePath;
//    private String filePath;
//
//    //    初始化缓存文件夹
//    public void initCachePath() {
//        cachePath = FileUtils.getCacheDirectory(this, "").getAbsolutePath();
//        filePath = FileUtils.getCacheDirectory(this, "images").getAbsolutePath();
//    }
//
//    private class CordovaContext extends ContextWrapper implements CordovaInterface {
//
//        Activity activity;
//        protected final ExecutorService threadPool = Executors.newCachedThreadPool();
//
//        public CordovaContext(Activity activity) {
//            super(activity.getBaseContext());
//            this.activity = activity;
//        }
//
//        public void startActivityForResult(CordovaPlugin command, Intent intent, int requestCode) {
//            //activity.startActivityForResult(command, intent, requestCode);
//        }
//
//        public void setActivityResultCallback(CordovaPlugin plugin) {
//            //activity.setActivityResultCallback(plugin);
//        }
//
//        public Activity getActivity() {
//            return activity;
//        }
//
//        public Object onMessage(String id, Object data) {
//            return null;
//        }
//
//        public ExecutorService getThreadPool() {
//            return threadPool;
//        }
//    }
//
//
//    private View.OnLongClickListener sadasdas = new View.OnLongClickListener() {
//        @Override
//        public boolean onLongClick(View v) {
////            MainActivityPermissionsDispatcher.downloadWithPermissionCheck(OfficalMainActivity.this,"");
//
//            final WebView.HitTestResult hitTestResult = cordWebView.getHitTestResult();
//            // 如果是图片类型或者是带有图片链接的类型
//            if (hitTestResult.getType() == android.webkit.WebView.HitTestResult.IMAGE_TYPE ||
//                    hitTestResult.getType() == android.webkit.WebView.HitTestResult.SRC_IMAGE_ANCHOR_TYPE) {
//                // 弹出保存图片的对话框
//                AlertDialog.Builder builder = new AlertDialog.Builder(WebActivity.this);
//                builder.setTitle("提示");
//                builder.setMessage("保存图片到本地");
//                builder.setPositiveButton("确认", new android.content.DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        final String url = hitTestResult.getExtra();
//                        // 下载图片到本地
//                        if (TextUtils.isEmpty(url)) {
//                            return;
//                        }
//                        ApplyPermission(url);
//                    }
//                });
//
//
//                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
//                    // 自动dismiss
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//
//                    }
//                });
//                AlertDialog dialog = builder.create();
//                dialog.show();
//            }
//            return true;
//
//        }
//    };
//
//    public Bitmap base64ToBitmap(String string) {
//        Bitmap bitmap = null;
//        try {
//            byte[] bitmapArray = Base64.decode(string.split(",")[1], Base64.DEFAULT);
//            bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0, bitmapArray.length);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return bitmap;
//    }
//
//    private void ApplyPermission(final String url) {
//        AndPermission.with(WebActivity.this)
//                .runtime()
//                .permission(Permission.WRITE_EXTERNAL_STORAGE)
////                .rationale(mRationale)
//                .onGranted(new Action<List<String>>() {
//                    @Override
//                    public void onAction(List<String> data) {
//                        if (url.endsWith(".png") || url.endsWith(".jpg") || url.endsWith(".jpeg") || url.endsWith(".gif") || url.startsWith("http")) {
//
//                            OkHttpUtils.get().url(url).build().execute(new BitmapCallback() {
//                                @Override
//                                public void onError(Call call, Exception e, int id) {
//
//                                }
//
//                                @Override
//                                public void onResponse(Bitmap response, int id) {
//                                    MediaStore.Images.Media.insertImage(getContentResolver(), response, "图片", "description");
//                                    try {
//                                        SavePic.saveImageToGallery(WebActivity.this, response);
//                                    } catch (Exception e) {
//                                        Toast.makeText(WebActivity.this, "保存失败", Toast.LENGTH_SHORT).show();
//                                        e.printStackTrace();
//                                        return;
//                                    }
////                                    Toast.makeText(WebActivity.this, "保存成功", Toast.LENGTH_SHORT).show();
//                                    ShowJumpOtherApp();
//                                }
//                            });
//                        } else {
//                            Bitmap bitmap = base64ToBitmap(url);
//                            if (bitmap == null) return;
//                            MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, "图片", "description");
//                            try {
//                                SavePic.saveImageToGallery(WebActivity.this, bitmap);
//                            } catch (Exception e) {
//                                Toast.makeText(WebActivity.this, "保存失败", Toast.LENGTH_SHORT).show();
//                                e.printStackTrace();
//                                return;
//                            }
////                            Toast.makeText(WebActivity.this, "保存成功", Toast.LENGTH_SHORT).show();
//                            ShowJumpOtherApp();
//                        }
//                    }
//                })
//                .onDenied(new Action<List<String>>() {
//                    @Override
//                    public void onAction(List<String> data) {
//                        if (AndPermission.hasAlwaysDeniedPermission(WebActivity.this, data)) {
//
//                            new AlertDialog.Builder(WebActivity.this).setTitle("申请权限").setMessage("保存图片需要给予保存权限请选择存储权限同意")
//                                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
//
//                                        @Override
//                                        public void onClick(DialogInterface dialog, int which) {
//                                            AndPermission.with(WebActivity.this)
//                                                    .runtime()
//                                                    .setting()
//                                                    .onComeback(new Setting.Action() {
//                                                        @Override
//                                                        public void onAction() {
//                                                            // 用户从设置回来了。
//                                                            ApplyPermission(url);
//                                                        }
//                                                    })
//                                                    .start();
//
//                                        }
//                                    }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialog, int which) {
//                                }
//                            }).show();
//
//                        }
//                    }
//                })
//                .start();
//    }
//
//
//    public void ShowJumpOtherApp() {
//        new AlertDialog.Builder(WebActivity.this).setTitle("扫码支付").setMessage("请打开微信支付宝或QQ-->扫码-->点击相册-->选取二维码进行扫码支付")
//                .setPositiveButton("打开微信", new DialogInterface.OnClickListener() {
//
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//
//                        int wechatcount = mSpUtils.getInt("wechatcount", 0);
//                        if (wechatcount <= 2) {
//                            View view2 = View.inflate(WebActivity.this, R.layout.wechat, null);
//                            new AlertDialog.Builder(WebActivity.this).setTitle("温馨提示").setView(view2)
//                                    .setPositiveButton("我知道啦", new DialogInterface.OnClickListener() {
//                                        @Override
//                                        public void onClick(DialogInterface dialog, int which) {
//                                            try {
//                                                Intent intent = new Intent(Intent.ACTION_MAIN);
//                                                ComponentName cmp = new ComponentName("com.tencent.mm", "com.tencent.mm.ui.LauncherUI");
//                                                intent.putExtra("LauncherUI.From.Scaner.Shortcut", true);
//                                                intent.addCategory(Intent.CATEGORY_LAUNCHER);
//                                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                                                intent.setComponent(cmp);
//                                                startActivity(intent);
//                                            } catch (ActivityNotFoundException e) {
//                                                Toast.makeText(WebActivity.this, "检查到您手机没有安装微信，请安装后使用该功能", Toast.LENGTH_SHORT).show();
//                                            }
//                                        }
//                                    }).show();
//
//                        } else {
//                            try {
//                                Intent intent = new Intent(Intent.ACTION_MAIN);
//                                ComponentName cmp = new ComponentName("com.tencent.mm", "com.tencent.mm.ui.LauncherUI");
//                                intent.putExtra("LauncherUI.From.Scaner.Shortcut", true);
//                                intent.addCategory(Intent.CATEGORY_LAUNCHER);
//                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                                intent.setComponent(cmp);
//                                startActivity(intent);
//                            } catch (ActivityNotFoundException e) {
//                                Toast.makeText(WebActivity.this, "检查到您手机没有安装微信，请安装后使用该功能", Toast.LENGTH_SHORT).show();
//                            }
//                        }
//
//                        mSpUtils.putInt("wechatcount", ++wechatcount);
//
//                    }
//
//                }).setNegativeButton("打开支付宝", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                int zhicount = mSpUtils.getInt("zhicount", 0);
//                if (zhicount <= 2) {
//
//                    View view2 = View.inflate(WebActivity.this, R.layout.zhi, null);
//                    new AlertDialog.Builder(WebActivity.this).setTitle("温馨提示").setView(view2)
//                            .setPositiveButton("我知道啦", new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialog, int which) {
//                                    try {
//                                        Uri uri = Uri.parse("alipayqr://platformapi/startapp?saId=10000007");
//                                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
//                                        startActivity(intent);
//                                    } catch (Exception e) {
//                                        Toast.makeText(WebActivity.this, "检查到您手机没有安装支付宝，请安装后使用该功能", Toast.LENGTH_SHORT).show();
//                                    }
//                                }
//                            }).show();
//
//
//                } else {
//                    try {
//                        Uri uri = Uri.parse("alipayqr://platformapi/startapp?saId=10000007");
//                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
//                        startActivity(intent);
//                    } catch (Exception e) {
//                        Toast.makeText(WebActivity.this, "检查到您手机没有安装支付宝，请安装后使用该功能", Toast.LENGTH_SHORT).show();
//                    }
//                }
//
//                mSpUtils.putInt("zhicount", ++zhicount);
//            }
//        }).setNeutralButton("打开QQ", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                try {
//                    Intent intent = WebActivity.this.getPackageManager().getLaunchIntentForPackage("com.tencent.mobileqq");
//                    WebActivity.this.startActivity(intent);
//                } catch (Exception e) {
//                    Toast.makeText(WebActivity.this, "检查到您手机没有安装QQ，请安装后使用该功能", Toast.LENGTH_SHORT).show();
//                }
//
//            }
//        }).show();
//
//    }
//
//}
