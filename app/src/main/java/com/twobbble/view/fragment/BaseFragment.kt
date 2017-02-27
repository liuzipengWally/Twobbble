package com.twobbble.view.fragment

import android.app.Fragment
import android.os.Bundle
import com.twobbble.tools.QuickSimpleIO

/**
 * Created by liuzipeng on 2017/2/22.
 */
open class BaseFragment : Fragment() {
    var mSimpleIo: QuickSimpleIO? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mSimpleIo = QuickSimpleIO.instance
    }
}