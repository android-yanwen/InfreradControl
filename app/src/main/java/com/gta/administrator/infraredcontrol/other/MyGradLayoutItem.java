package com.gta.administrator.infraredcontrol.other;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gta.administrator.infraredcontrol.R;

/**
 * Created by yanwen on 16/10/1.
 */
public class MyGradLayoutItem extends LinearLayout {
    TextView textView;
    ImageView imageView;
    View view;

    public MyGradLayoutItem(Context context) {
        this(context, null);
    }

    public MyGradLayoutItem(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyGradLayoutItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);


        view = LayoutInflater.from(context).inflate(R.layout.my_gradlayout_item, null, false);



        final TypedArray a = context.obtainStyledAttributes(
                attrs, R.styleable.MyGradLayoutItem, defStyleAttr, 0);

        int res = a.getResourceId(R.styleable.MyGradLayoutItem_gradlayoutSrc, 0);
        imageView = (ImageView)view.findViewById(R.id.item_img);
        imageView.setImageResource(res);

        String text = a.getString(R.styleable.MyGradLayoutItem_gradlayoutText);
        textView = (TextView) view.findViewById(R.id.item_text);
        textView.setText(text);

        a.recycle();
        addView(view);
    }
/*
    public MyGradLayoutItem(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);



    }*/

    public void setTextText(String textText) {
        textView.setText(textText);
    }

    public void setImageViewIcon(int res) {
        imageView.setImageResource(res);
    }




}
