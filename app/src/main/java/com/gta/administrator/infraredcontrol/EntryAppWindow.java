package com.gta.administrator.infraredcontrol;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;

//import net.danlew.android.joda.JodaTimeAndroid;

/**
 * Created by Administrator on 2016/6/13.
 */
public class EntryAppWindow extends AppCompatActivity {
    private Button register_btn;
    private Button load_btn;
    private static final String tag = "EntryAppWindow";
    private int winWidth = 0;
    private int start = 0;
//    private Bitmap bitmap;
    private ImageView background_img;
//    private App myApplication;
//    private Handler handler = new Handler(){
//        @Override
//        public void handleMessage(Message msg) {
//            background_img.setBackground(new BitmapDrawable((Bitmap)msg.obj));
//            super.handleMessage(msg);
//        }
//    };
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        myApplication = (App) getApplication();
        setContentView(R.layout.entry_app_window);

//        JodaTimeAndroid.init(this);

        background_img = (ImageView) findViewById(R.id.background_img);
        register_btn = (Button) findViewById(R.id.register_btn);
        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EntryAppWindow.this, RegisterIdentificationWindow.class));
            }
        });
        load_btn = (Button) findViewById(R.id.load_btn);
        load_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EntryAppWindow.this, LoadingWin.class));
//                finish();
            }
        });

	//已经弃用
//        WindowManager manager = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
//        winWidth = getWindowManager().getDefaultDisplay().getWidth();
        DisplayMetrics dm = getResources().getDisplayMetrics();
        winWidth = dm.widthPixels;  // 得到屏幕宽度像素
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(getResources(), R.mipmap.entry_window_bg, options);
        int width = options.outWidth;
//        int width = BitmapFactory.decodeResource(getResources(), R.drawable.login_bg).getWidth()/2;
        Log.i(tag, "width:" + width);
        Log.i(tag, "winwidth:" + winWidth);
        Object localObject = background_img.getLayoutParams();
        ((ViewGroup.LayoutParams) localObject).width = width;
        background_img.setLayoutParams((ViewGroup.LayoutParams) localObject);
        localObject = new TranslateAnimation(0F, winWidth - width, 0F, 0f);
        ((TranslateAnimation) localObject).setDuration(20000l);
        ((TranslateAnimation) localObject).setFillAfter(true);
        background_img.setAnimation((TranslateAnimation) localObject);
        ((TranslateAnimation) localObject).startNow();
	// 已经弃用
//        WindowManager manager = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
//        winWidth = manager.getDefaultDisplay().getWidth();
//        Log.i(tag, manager.getDefaultDisplay().getHeight() + "");
//        Log.i(tag, bitmap.getWidth() + "");
//        Log.i(tag, bitmap.getHeight() + "");
//        final Timer timer = new Timer();
//        timer.schedule(new TimerTask() {
//            @Override
//            public void run() {
////                bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.loadimg);
//                Resources res = getResources();
//                BitmapDrawable bmpDraw = (BitmapDrawable) res.getDrawable(R.drawable.login_bg);
//                bitmap = bmpDraw.getBitmap();
//                Message message = handler.obtainMessage();
//                message.obj = bitmap.createBitmap(bitmap, start, 0, width, bitmap.getHeight());
//                handler.sendMessage(message);
//                start += 1;
//                width += 1;
//                if (width >= bitmap.getWidth()) {
//                    timer.cancel();
//                }
//            }
//        }, 0, 100);

    }

    @Override
    protected void onResume() {
        super.onResume();
        DisplayMetrics dm = getResources().getDisplayMetrics();
        winWidth = dm.widthPixels;  // 得到屏幕宽度像素
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(getResources(), R.mipmap.entry_window_bg, options);
        int width = options.outWidth;
        Log.i(tag, "width:" + width);
        Log.i(tag, "winwidth:" + winWidth);
        Object localObject = background_img.getLayoutParams();
        ((ViewGroup.LayoutParams) localObject).width = width;
        background_img.setLayoutParams((ViewGroup.LayoutParams) localObject);
        localObject = new TranslateAnimation(0F, winWidth - width, 0F, 0f);
        ((TranslateAnimation) localObject).setDuration(10000l);
        ((TranslateAnimation) localObject).setFillAfter(true);
        background_img.setAnimation((TranslateAnimation) localObject);
        ((TranslateAnimation) localObject).startNow();
    }




}
