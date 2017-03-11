package com.twobbble.view.fragment

import android.app.Fragment
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.twobbble.R
import com.twobbble.application.App
import kotlinx.android.synthetic.main.fragment_following.*
import kotlinx.android.synthetic.main.search_layout.*

/**
 * Created by liuzipeng on 2017/2/17.
 */
class FollowingFragment : BaseFragment() {
    override fun onBackPressed() {
        hideSearchView()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return LayoutInflater.from(activity).inflate(R.layout.fragment_following, null)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
    }

    override fun onStart() {
        super.onStart()
        bindEvent()
    }

    private fun bindEvent() {

    }


    private fun hideSearchView() {
        mSearchLayout.hideSearchView { isShowSearchBar = false }
    }
}