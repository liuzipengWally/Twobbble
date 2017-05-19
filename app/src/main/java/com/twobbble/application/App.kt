package com.twobbble.application

import android.app.Application
import android.os.Environment
import com.facebook.drawee.backends.pipeline.Fresco
import com.liulishuo.filedownloader.FileDownloader
import com.tencent.bugly.Bugly
import com.tencent.bugly.beta.Beta
import com.twobbble.R
import com.twobbble.tools.Constant
import com.twobbble.tools.delegates.NotNullSingleValueVar
import com.twobbble.view.activity.MainActivity
import uk.co.chrisjenx.calligraphy.CalligraphyConfig


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
        Thread {
            Fresco.initialize(this)
        }.start()
        initBugLy()
    }

    fun initBugLy() {
        Beta.initDelay = 3000
        Beta.largeIconId = R.mipmap.ic_launcher
        Beta.canShowUpgradeActs.add(MainActivity::class.java)
        Beta.smallIconId = R.drawable.ic_update_black_24dp
        Beta.upgradeDialogLayoutId = R.layout.upgrade_dialog
        Beta.storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
        Bugly.init(applicationContext, Constant.BUGLY_ID, true)
    }
}