package com.twobbble.view.customview

import android.animation.Animator
import android.content.Context
import android.support.v7.widget.CardView
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewAnimationUtils
import android.view.animation.AccelerateInterpolator
import android.widget.EditText
import com.twobbble.tools.Utils
import kotlinx.android.synthetic.main.search_layout.*
import kotlinx.android.synthetic.main.search_layout.view.*
import org.jetbrains.anko.find

/**
 * Created by liuzipeng on 2017/3/4.
 */
class SearchBar(context: Context?, attrs: AttributeSet?) : CardView(context, attrs) {
    val SEARCH_DURATION_ANIM: Long = 300

    fun showSearchView(width: Int, animationEnd: () -> Unit) {
        val cx = width - Utils.dp2px(16, resources.displayMetrics)
        val cy = Utils.dp2px(24, resources.displayMetrics)
        val dx = Math.max(cx, width - cx)
        val dy = Math.max(cy, Utils.dp2px(48, resources.displayMetrics) - cy)
        val finalRadius = Math.hypot(dx.toDouble(), dy.toDouble()).toFloat()
        val animator = ViewAnimationUtils.createCircularReveal(this, cx.toInt(), cy.toInt(), 0f, finalRadius)
        animator.interpolator = AccelerateInterpolator()
        animator.duration = SEARCH_DURATION_ANIM
        visibility = View.VISIBLE
        alpha = 1F
        animator.start()

        animator.addListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(p0: Animator?) {

            }

            override fun onAnimationEnd(p0: Animator?) {
                animationEnd.invoke()
            }

            override fun onAnimationCancel(p0: Animator?) {

            }

            override fun onAnimationStart(p0: Animator?) {

            }
        })
    }

    fun hideSearchView(animationEnd: () -> Unit) {
        val cx = measuredWidth
        val cy = Utils.dp2px(24, resources.displayMetrics)
        val dx = Math.max(cx, measuredWidth - cx)
        val dy = Math.max(cy, Utils.dp2px(48, resources.displayMetrics) - cy)
        val finalRadius = Math.hypot(dx.toDouble(), dy.toDouble()).toFloat()
        val animator = ViewAnimationUtils.createCircularReveal(this, cx, cy.toInt(), finalRadius, 0f)
        animator.interpolator = AccelerateInterpolator()
        animator.duration = SEARCH_DURATION_ANIM
        animator.start()

        animator.addListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(p0: Animator?) {

            }

            override fun onAnimationEnd(p0: Animator?) {
                alpha = 0f
                visibility = View.GONE
                animationEnd.invoke()
            }

            override fun onAnimationCancel(p0: Animator?) {

            }

            override fun onAnimationStart(p0: Animator?) {

            }
        })
    }
}