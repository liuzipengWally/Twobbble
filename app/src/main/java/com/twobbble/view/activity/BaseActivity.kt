package com.twobbble.view.activity

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import com.twobbble.tools.QuickSimpleIO
import com.twobbble.view.dialog.DialogManager
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper

open class BaseActivity : AppCompatActivity() {
    var screenWidth: Int = 0
    var screenHeight: Int = 0
    var mSimpleIo: QuickSimpleIO? = null
    var mDialogManager: DialogManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mSimpleIo = QuickSimpleIO.instance
        mDialogManager = DialogManager(this)
        getScreenPixel()
    }

    private fun getScreenPixel() {
        val metrics = DisplayMetrics()
        val manager = windowManager
        manager.defaultDisplay.getMetrics(metrics)
        screenWidth = metrics.widthPixels
        screenHeight = metrics.heightPixels
    }

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
    }
}
