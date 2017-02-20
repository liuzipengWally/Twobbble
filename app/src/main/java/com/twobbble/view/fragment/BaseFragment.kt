package com.twobbble.view.fragment

import android.app.Fragment
import android.app.FragmentTransaction
import android.os.Bundle

/**
 * Created by liuzipeng on 2017/2/19.
 */
open class BaseFragment : Fragment() {
//    private val STATE_SAVE_IS_HIDDEN = "STATE_SAVE_IS_HIDDEN"
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        if (savedInstanceState != null) {
//            val isSupportHidden = savedInstanceState.getBoolean(STATE_SAVE_IS_HIDDEN)
//            val transaction = fragmentManager.beginTransaction()
//            if (isSupportHidden) {
//                transaction.hide(this)
//            } else {
//                transaction.show(this)
//            }
//            transaction.commit()
//        }
//    }
//
//    override fun onSaveInstanceState(outState: Bundle?) {
//        outState?.putBoolean(STATE_SAVE_IS_HIDDEN, isHidden)
//    }
}