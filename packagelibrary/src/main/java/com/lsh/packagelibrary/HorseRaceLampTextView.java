package com.lsh.packagelibrary;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Author:lsh
 * Version: 1.0
 * Description:
 * Date: 2018/6/18
 */
public class HorseRaceLampTextView extends TextView {
    public HorseRaceLampTextView(Context context) {
        super(context);
    }

    public HorseRaceLampTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HorseRaceLampTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean isFocused() {
        return true;
    }
}
