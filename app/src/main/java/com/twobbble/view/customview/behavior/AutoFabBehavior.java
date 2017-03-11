package com.twobbble.view.customview.behavior;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.OvershootInterpolator;


/**
 * 让我们自定义的FloatingActionButton（AutoHideFab）响应CoordinatorLayout
 * 与RecyclerView协同工作的类
 */
public class AutoFabBehavior extends CoordinatorLayout.Behavior<View> {
    private FloatingActionButton mAutoHideFab;
    private boolean mStatus;

    public AutoFabBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    //判断滑动方向，因为我们只要垂直滑动，所以用nestedScrollAxes去&ViewCompat.SCROLL_AXIS_VERTICAL，如果不为0，就是垂直
    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, View child, View directTargetChild, View target, int nestedScrollAxes) {
        return (nestedScrollAxes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0;
    }

    //根据滑动距离，显示隐藏。
    @Override
    public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, View child, View target, int dx, int dy, int[] consumed) {
        if (mAutoHideFab == null) {
            mAutoHideFab = (FloatingActionButton) child;
        }

        if (dy > 10) {
            hide();
        } else if (dy < -10) {
            show();
        }
    }

    public void show() {
        if (!mStatus) {
            ObjectAnimator scaleX = ObjectAnimator.ofFloat(mAutoHideFab, "scaleX", 0f, 1f);
            ObjectAnimator scaleY = ObjectAnimator.ofFloat(mAutoHideFab, "scaleY", 0f, 1f);
            ObjectAnimator alpha = ObjectAnimator.ofFloat(mAutoHideFab, "alpha", 0f, 1f);

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
            ObjectAnimator scaleX = ObjectAnimator.ofFloat(mAutoHideFab, "scaleX", 1f, 0f);
            ObjectAnimator scaleY = ObjectAnimator.ofFloat(mAutoHideFab, "scaleY", 1f, 0f);
            ObjectAnimator alpha = ObjectAnimator.ofFloat(mAutoHideFab, "alpha", 1f, 0f);

            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.playTogether(scaleX, scaleY, alpha);
            animatorSet.setDuration(300);
            animatorSet.setInterpolator(new OvershootInterpolator());

            animatorSet.start();

            mStatus = false;
        }
    }
}
