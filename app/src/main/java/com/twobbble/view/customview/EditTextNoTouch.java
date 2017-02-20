package com.twobbble.view.customview;

import android.content.Context;
import android.support.design.widget.TextInputLayout;
import android.util.AttributeSet;
import android.view.MotionEvent;


/**
 * Created by liuzipeng on 16/9/9.
 */

public class EditTextNoTouch extends TextInputLayout {
    public EditTextNoTouch(Context context) {
        this(context, null);
    }

    public EditTextNoTouch(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return false;
    }
}
