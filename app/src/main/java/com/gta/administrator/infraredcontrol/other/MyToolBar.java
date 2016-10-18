package com.gta.administrator.infraredcontrol.other;

import android.content.Context;
import android.support.v7.widget.TintTypedArray;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.gta.administrator.infraredcontrol.R;

/**
 * Created by yanwen on 16/10/5.
 */
public class MyToolBar extends Toolbar {


    public MyToolBar(Context context) {
        this(context, null);
    }

    public MyToolBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyToolBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        View view = LayoutInflater.from(context).inflate(R.layout.my_tool_bar, null);


        final TintTypedArray a = TintTypedArray.obtainStyledAttributes(getContext(), attrs,
                R.styleable.MyToolBarAttrs, defStyleAttr, 0);

        Button start_btn = (Button) view.findViewById(R.id.toolbar_start_btn);
        start_btn.setText(a.getText(R.styleable.MyToolBarAttrs_startText));

        TextView middle_text = (TextView) view.findViewById(R.id.toolbar_middle_text);
        middle_text.setText(a.getText(R.styleable.MyToolBarAttrs_middleText));

        Button end_btn = (Button) view.findViewById(R.id.toolbar_end_btn);
        end_btn.setText(a.getText(R.styleable.MyToolBarAttrs_endText));

        a.recycle();
        addView(view);

    }
}
