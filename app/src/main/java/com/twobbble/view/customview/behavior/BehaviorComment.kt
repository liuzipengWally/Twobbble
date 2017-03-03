package com.twobbble.view.customview.behavior

import android.content.Context
import android.support.design.widget.CoordinatorLayout
import android.support.design.widget.FloatingActionButton
import android.support.v4.view.ViewCompat
import android.util.AttributeSet
import android.view.View
import com.twobbble.application.App
import com.twobbble.tools.Utils
import com.twobbble.tools.log

/**
 * Created by liuzipeng on 2017/2/28.
 */
class BehaviorComment(context: Context, attrs: AttributeSet) : CoordinatorLayout.Behavior<View>(context, attrs) {
    //判断滑动方向，因为我们只要垂直滑动，所以用nestedScrollAxes去&ViewCompat.SCROLL_AXIS_VERTICAL，如果不为0，就是垂直
    override fun onStartNestedScroll(coordinatorLayout: CoordinatorLayout?, child: View?, directTargetChild: View?, target: View?, nestedScrollAxes: Int): Boolean {
        return nestedScrollAxes and ViewCompat.SCROLL_AXIS_VERTICAL != 0
    }

    //根据滑动距离，显示隐藏。
    override fun onNestedPreScroll(coordinatorLayout: CoordinatorLayout?, child: View?, target: View?, dx: Int, dy: Int, consumed: IntArray?) {
        if (dy >= 2) {
            hideChild(child)
        } else if (dy < -2) {
            showChild(child)
        }
    }

    private fun hideChild(child: View?) {
        child?.animate()?.translationY(child.height + Utils.dp2px(16, App.instance.resources.displayMetrics))?.duration = 200
    }

    private fun showChild(child: View?) {
        child?.animate()?.translationY(0f)?.duration = 200
    }
}