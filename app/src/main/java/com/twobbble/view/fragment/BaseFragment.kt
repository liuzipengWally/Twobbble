package com.twobbble.view.fragment

import android.app.Fragment
import android.os.Bundle
import com.twobbble.tools.QuickSimpleIO

/**
 * Created by liuzipeng on 2017/2/22.
 */
open class BaseFragment : Fragment() {
    var mSimpleIo: QuickSimpleIO? = null
    var isVisible: Boolean? = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mSimpleIo = QuickSimpleIO.getInstance()
    }

    /**
     * 在这里实现Fragment数据的缓加载.
     * fragment 被设为可见的时候,会调用这个方法

     * @param isVisibleToUser
     */
    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (userVisibleHint) {
            isVisible = true
            onVisible()
        } else {
            isVisible = false
            onInvisible()
        }
    }

    open fun onVisible() {
        lazyLoad()
    }

    open fun lazyLoad() {}

    fun onInvisible() {
        inVisible()
    }

    fun inVisible() {}
}