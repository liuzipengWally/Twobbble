package com.twobbble.tools

import android.widget.Toast
import com.twobbble.R
import com.twobbble.application.App
import rx.Subscriber


/**
 * Created by liuzipeng on 2017/2/20.
 */
abstract class NetSubscriber<T> : Subscriber<T>() {
    override fun onStart() {
        super.onStart()
        if (!Utils.isNetworkAvailable(App.instance)) {
            onError(RuntimeException(App.instance.resources.getString(R.string.net_disable)))
            unsubscribe()
        }
    }

    override fun onError(t: Throwable?) {
        t?.printStackTrace()
        if (t is NetExceptionHandler.ResponseException) onError(t)
        else onError(NetExceptionHandler.ResponseException(t!!, NetExceptionHandler.ERROR.UNKNOWN))
    }
}