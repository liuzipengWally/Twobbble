package com.twobbble.application

import android.app.Application
import com.twobbble.R
import uk.co.chrisjenx.calligraphy.CalligraphyConfig

/**
 * Created by liuzipeng on 2017/2/15.
 */
class App : Application() {
    //将Application 单利化，可供全局调用 Context
    companion object {   //companion为伴随对象   object 为单利对象
        private var instance: Application? = null  //申明一个可为空的instance
        fun getInstace() = instance!! //返回一个不能为空的instance  !!表示这个对象如果不为空就返回，为空就抛出异常
    }

    override fun onCreate() {
        super.onCreate()
        init()
        initFont()
    }

    private fun initFont() {
        CalligraphyConfig.initDefault(CalligraphyConfig.Builder().
                setDefaultFontPath("fonts/yuehei.ttf").setFontAttrId(R.attr.fontPath).build())
    }

    private fun init() {
        instance = this
    }
}