package cn.carsbe.cb01.view.customview.wrapper;

import android.view.View;

/**
 * Created by liuzipeng on 16/2/28.
 */
public class WrapperView {
    private View mTarget;

    public WrapperView(View mTarget) {
        this.mTarget = mTarget;
    }

    public int getWidth() {
        return mTarget.getLayoutParams().width;
    }

    public void setWidth(int width) {
        mTarget.getLayoutParams().width = width;
        mTarget.requestLayout();
    }

    public int getHeight() {
        return mTarget.getLayoutParams().height;
    }

    public void setHeight(int height) {
        mTarget.getLayoutParams().height = height;
        mTarget.requestLayout();
    }
}
