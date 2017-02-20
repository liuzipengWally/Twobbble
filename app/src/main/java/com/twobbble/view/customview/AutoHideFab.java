package com.twobbble.view.customview;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.util.AttributeSet;
import android.view.animation.OvershootInterpolator;

/**
 * 自定义的FloatingActionButton，封装了两个显示和隐藏的动画。
 */
public class AutoHideFab extends FloatingActionButton {
    private boolean mStatus = true;

    public AutoHideFab(Context context) {
        this(context, null);
    }

    public AutoHideFab(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AutoHideFab(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void show() {
        if (!mStatus) {
            ObjectAnimator scaleX = ObjectAnimator.ofFloat(this, "scaleX", 0f, 1f);
            ObjectAnimator scaleY = ObjectAnimator.ofFloat(this, "scaleY", 0f, 1f);
            ObjectAnimator alpha = ObjectAnimator.ofFloat(this, "alpha", 0f, 1f);

            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.playTogether(scaleX, scaleY, alpha);
            animatorSet.setDuration(300);
            animatorSet.setInterpolator(new OvershootInterpolator());

            animatorSet.start();

            mStatus = true;
        }
    }

    public void hide() {
        if (mStatus) {
            ObjectAnimator scaleX = ObjectAnimator.ofFloat(this, "scaleX", 1f, 0f);
            ObjectAnimator scaleY = ObjectAnimator.ofFloat(this, "scaleY", 1f, 0f);
            ObjectAnimator alpha = ObjectAnimator.ofFloat(this, "alpha", 1f, 0f);

            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.playTogether(scaleX, scaleY, alpha);
            animatorSet.setDuration(300);
            animatorSet.setInterpolator(new OvershootInterpolator());

            animatorSet.start();

            mStatus = false;
        }
    }
}
