package com.twobbble.view.customview

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.support.v7.widget.RecyclerView
import android.view.View
import com.twobbble.R
import com.twobbble.application.App
import com.twobbble.tools.Utils


/**
 * 主页RecyclerView的分隔线
 */
class NormalDivider : RecyclerView.ItemDecoration() {

    private val mDivider: Drawable

    init {
        val typedArray = App.instance.obtainStyledAttributes(attrs) //将数组转化为TypedArray
        mDivider = typedArray.getDrawable(0)  //取出这个Drawable文件
        typedArray.recycle()   //回收TypedArray
    }

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State?) {
        val metrics = parent.context.resources.displayMetrics
        val childCount = parent.childCount
        if (childCount != 0) {
            for (i in 0..childCount - 1) {
                if (i != childCount - 1) {
                    val child = parent.getChildAt(i)
                    val params = child.layoutParams as RecyclerView.LayoutParams
                    val top = child.bottom + params.bottomMargin
                    val bottom = top + mDivider.intrinsicHeight
                    mDivider.alpha = 100
                    mDivider.setBounds(0, top, parent.width, bottom)
                    mDivider.draw(c)
                }
            }
        }
    }

    companion object {
        private val attrs = intArrayOf(android.R.attr.listDivider)//系统自带分割线文件，获取后先保存为数组
    }
}
