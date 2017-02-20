package com.twobbble.view.customview;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by liuzipeng on 16/7/8.
 */

public class ViewPagerNoTouch extends ViewPager {

    public ViewPagerNoTouch(Context context) {
        this(context, null);
    }

    public ViewPagerNoTouch(Context context, AttributeSet attrs) {
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
