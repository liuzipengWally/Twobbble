package com.twobbble.view.customview

import android.graphics.Canvas
import android.graphics.ColorFilter
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ProgressBar

/**
 * Created by liuzipeng on 2017/2/24.
 */
class CustomProgressBar(val progressBar: ProgressBar) : Drawable() {
    override fun onLevelChange(level: Int): Boolean {
        if (level == 10000) progressBar.visibility = View.GONE else {
            if (progressBar.visibility == View.GONE) progressBar.visibility = View.VISIBLE
            progressBar.progress = level / 100
        }
        return true
    }

    override fun draw(p0: Canvas?) {
    }

    override fun setAlpha(p0: Int) {
    }

    override fun getOpacity(): Int = 100

    override fun setColorFilter(p0: ColorFilter?) {
    }
}