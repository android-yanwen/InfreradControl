package com.gta.administrator.infraredcontrol_apk.view;

import android.util.Log;


import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;
import android.widget.ScrollView;


import com.gta.administrator.infraredcontrol_apk.R;

import java.util.ArrayList;

public class BallScrollView extends ScrollView
{

    public static final int LIGHT_DEFAULT = 128;

    private static ArrayList<Bitmap> frames;
    private int contentHeight = -1;
    private LinearLayout contentView;
    Handler handler = new Handler()
    {
        public void handleMessage(Message paramAnonymousMessage)
        {
            super.handleMessage(paramAnonymousMessage);
            BallScrollView.this.notifyOnDrawListener(paramAnonymousMessage.arg1, paramAnonymousMessage.arg2, false);
        }
    };
    private boolean initscrool = false;
    private boolean isInit = false;
    private int light = 0;
    private OnTrackballDidScrollListener onDrawListener;
    private Paint paint;
    private Rect rect;
    private int x;
    private int y;

    public BallScrollView(Context paramContext)
    {
        super(paramContext);
    }

    public BallScrollView(Context paramContext, AttributeSet paramAttributeSet)
    {
        super(paramContext, paramAttributeSet);
    }

    public BallScrollView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
    {
        super(paramContext, paramAttributeSet, paramInt);
    }

    private void loadFrames()
    {
        Resources localResources = getContext().getResources();
        frames = new ArrayList();
        frames.add(BitmapFactory.decodeResource(localResources, R.mipmap.trackball0000_black2x));
        frames.add(BitmapFactory.decodeResource(localResources, R.mipmap.trackball0001_black2x));
        frames.add(BitmapFactory.decodeResource(localResources, R.mipmap.trackball0002_black2x));
        frames.add(BitmapFactory.decodeResource(localResources, R.mipmap.trackball0003_black2x));
        frames.add(BitmapFactory.decodeResource(localResources, R.mipmap.trackball0004_black2x));
        frames.add(BitmapFactory.decodeResource(localResources, R.mipmap.trackball0005_black2x));
        frames.add(BitmapFactory.decodeResource(localResources, R.mipmap.trackball0006_black2x));
        frames.add(BitmapFactory.decodeResource(localResources, R.mipmap.trackball0007_black2x));
        frames.add(BitmapFactory.decodeResource(localResources, R.mipmap.trackball0008_black2x));
        frames.add(BitmapFactory.decodeResource(localResources, R.mipmap.trackball0009_black2x));
        frames.add(BitmapFactory.decodeResource(localResources, R.mipmap.trackball0010_black2x));
        frames.add(BitmapFactory.decodeResource(localResources, R.mipmap.trackball0011_black2x));
        frames.add(BitmapFactory.decodeResource(localResources, R.mipmap.trackball0012_black2x));
        frames.add(BitmapFactory.decodeResource(localResources, R.mipmap.trackball0013_black2x));
        frames.add(BitmapFactory.decodeResource(localResources, R.mipmap.trackball0014_black2x));
        frames.add(BitmapFactory.decodeResource(localResources, R.mipmap.trackball0015_black2x));
        frames.add(BitmapFactory.decodeResource(localResources, R.mipmap.trackball0016_black2x));
        frames.add(BitmapFactory.decodeResource(localResources, R.mipmap.trackball0017_black2x));
        frames.add(BitmapFactory.decodeResource(localResources, R.mipmap.trackball0018_black2x));
        frames.add(BitmapFactory.decodeResource(localResources, R.mipmap.trackball0019_black2x));
    }

    private void notifyOnDrawListener(int paramInt1, int paramInt2, boolean paramBoolean)
    {
        if (this.onDrawListener != null)
        {
            long l1 = System.currentTimeMillis();
            this.onDrawListener.notifyTrackBallHasScrolled(paramInt1, paramInt2, paramBoolean);
            long l2 = System.currentTimeMillis();
            Log.i("api_time", "light time = " + (l2 - l1));
        }
    }

    public boolean dispatchTouchEvent(MotionEvent paramMotionEvent)
    {
        int i = (int)paramMotionEvent.getX();
        int j = (int)paramMotionEvent.getY();
        Log.i("MotionEvent", "x1 = " + (i - this.x) + " y1=" + j);
        return super.dispatchTouchEvent(paramMotionEvent);
    }

    public void fling(int paramInt)
    {
        super.fling(paramInt / 4);
    }

    public int getContentHeight()
    {
        if ((this.contentHeight == -1) || (this.contentHeight == 0))
            this.contentHeight = findViewById(R.id.track_ball_content_view).getHeight();
        return this.contentHeight;
    }

    public void initLight(int paramInt)
    {
        this.light = paramInt;
        this.isInit = true;
        this.initscrool = true;
        Log.i("scrollTo", "getContentHeight() = " + getContentHeight() + "  this.getHeight()=" + getHeight());
        int i = paramInt * (getContentHeight() - getHeight()) / 255;
        Log.i("scrollTo", "light = " + paramInt + "  val=" + i + "  x = " + this.x);
        scrollTo(this.x, i);
    }

    protected void onDraw(Canvas paramCanvas)
    {
        super.onDraw(paramCanvas);
        if (frames == null)
            loadFrames();
        if (this.paint == null)
        {
            this.paint = new Paint();
            this.paint.setAlpha(100);
        }
        int i = getScrollY();
        int j = 19 - Math.abs(i % 20);
        Bitmap localBitmap = (Bitmap)frames.get(j);
        this.x = (getWidth() / 2 - localBitmap.getWidth() / 2);
        this.y = (i + (getHeight() / 2 - localBitmap.getHeight() / 2));
        paramCanvas.drawBitmap(localBitmap, this.x, this.y, this.paint);
    }

    protected void onFinishInflate()
    {
        super.onFinishInflate();
        this.contentView = ((LinearLayout)findViewById(R.id.track_ball_content_view));
        this.rect = new Rect();
        this.contentView.getGlobalVisibleRect(this.rect);
    }

    protected void onMeasure(int paramInt1, int paramInt2)
    {
        super.onMeasure(paramInt1, paramInt2);
        if ((getHeight() != 0) && (this.isInit))
        {
            int i = this.light * (getContentHeight() - getHeight()) / 255;
            Log.i("scrollTo", "light = " + this.light + "  val=" + i + "  x = " + this.x);
            scrollTo(this.x, i);
            this.isInit = false;
        }
    }

    protected void onScrollChanged(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
    {
        Log.d("onScrollChanged", "onScrollChanged: " + getContentHeight() + ";" + getHeight());
        int i = paramInt2 * 100 / (getContentHeight() - getHeight());
        int j = paramInt2 * 255 / (getContentHeight() - getHeight());
        if (Math.abs(paramInt4 - paramInt2) > 100)
            super.onScrollChanged(paramInt1, paramInt2, paramInt3, paramInt4);
        Message localMessage = this.handler.obtainMessage();
        localMessage.arg1 = i;
        localMessage.arg2 = j;
        this.handler.sendMessageDelayed(localMessage, 10L);
    }

    public boolean onTouchEvent(MotionEvent paramMotionEvent)
    {
        return super.onTouchEvent(paramMotionEvent);
    }

    public void setIntensityBallListener(OnTrackballDidScrollListener paramOnTrackballDidScrollListener)
    {
        this.onDrawListener = paramOnTrackballDidScrollListener;
    }

    public static abstract interface OnTrackballDidScrollListener
    {
        public abstract void notifyTrackBallHasScrolled(int paramInt1, int paramInt2, boolean
                paramBoolean);
    }
}