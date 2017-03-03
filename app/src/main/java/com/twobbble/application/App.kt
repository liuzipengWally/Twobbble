package com.twobbble.application

import android.app.Application
import com.facebook.drawee.backends.pipeline.Fresco
import com.liulishuo.filedownloader.FileDownloader
import com.twobbble.R
import com.twobbble.tools.delegates.NotNullSingleValueVar
import uk.co.chrisjenx.calligraphy.CalligraphyConfig
import kotlin.concurrent.thread


/**
 * Created by liuzipeng on 2017/2/15.
 */
class App : Application() {
    //将Application 单利化，可供全局调用 Context
    companion object {
        var instance: App by NotNullSingleValueVar.DelegatesExt.notNullSingleValue()
    }

    override fun onCreate() {
        super.onCreate()
        init()
//        initFont()
    }

    private fun initFont() {
        CalligraphyConfig.initDefault(CalligraphyConfig.Builder().
                setDefaultFontPath("fonts/yuehei.ttf").setFontAttrId(R.attr.fontPath).build())
    }

    private fun init() {
        instance = this
        FileDownloader.init(applicationContext)
        thread {
            Fresco.initialize(this)
        }.start()
    }
}