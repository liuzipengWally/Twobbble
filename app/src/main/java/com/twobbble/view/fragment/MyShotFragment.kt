package com.twobbble.view.fragment

import android.app.Fragment
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.twobbble.R
import com.twobbble.application.App
import com.twobbble.event.OpenDrawerEvent
import kotlinx.android.synthetic.main.fragment_my_shot.*
import org.greenrobot.eventbus.EventBus

/**
 * Created by liuzipeng on 2017/2/17.
 */
class MyShotFragment : BaseFragment() {
    override fun onBackPressed() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return LayoutInflater.from(activity).inflate(R.layout.fragment_my_shot, null)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
    }

    override fun onStart() {
        super.onStart()
        bindEvent()
    }

    private fun bindEvent() {
        Toolbar.setNavigationOnClickListener {
            EventBus.getDefault().post(OpenDrawerEvent())
        }
    }
}