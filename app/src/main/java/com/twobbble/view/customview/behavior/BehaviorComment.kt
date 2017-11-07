package com.twobbble.view.customview.behavior

import android.content.Context
import android.support.design.widget.CoordinatorLayout
import android.support.design.widget.Snackbar
import android.support.v4.view.ViewCompat
import android.util.AttributeSet
import android.view.View
import com.twobbble.application.App
import com.twobbble.tools.Utils

/**
 * Created by liuzipeng on 2017/2/28.
 */
class BehaviorComment(context: Context, attrs: AttributeSet) : CoordinatorLayout.Behavior<View>(context, attrs) {
    //判断滑动方向，因为我们只要垂直滑动，所以用nestedScrollAxes去&ViewCompat.SCROLL_AXIS_VERTICAL，如果不为0，就是垂直
    override fun onStartNestedScroll(coordinatorLayout: CoordinatorLayout, child: View, directTargetChild: View, target: View, axes: Int, type: Int): Boolean {
        return axes and ViewCompat.SCROLL_AXIS_VERTICAL != 0
    }

    //根据滑动距离，显示隐藏。
    override fun onNestedPreScroll(coordinatorLayout: CoordinatorLayout, child: View, target: View, dx: Int, dy: Int, consumed: IntArray, type: Int) {
        if (dy >= 2) {
            hideChild(child)
        } else if (dy < -2) {
            showChild(child)
        }
    }

    override fun layoutDependsOn(parent: CoordinatorLayout?, child: View?, dependency: View?): Boolean {
        return dependency is Snackbar.SnackbarLayout
    }

    override fun onDependentViewChanged(parent: CoordinatorLayout?, child: View?, dependency: View?): Boolean {
        if (dependency is Snackbar.SnackbarLayout) {
            updateTranslate(dependency, child)
        }
        return false
    }

    private fun updateTranslate(dependency: Snackbar.SnackbarLayout, child: View?) {
        val y = Utils.dp2px(48) - dependency.translationY
        if (dependency.left >= dependency.width) {
            child?.animate()?.translationY(0f)?.duration = 200
        } else {
            child?.translationY = -y
        }
    }

    private fun hideChild(child: View?) {
        child?.animate()?.translationY(child.height + Utils.dp2px(16))?.duration = 200
    }

    private fun showChild(child: View?) {
        child?.animate()?.translationY(0f)?.duration = 200
    }
}