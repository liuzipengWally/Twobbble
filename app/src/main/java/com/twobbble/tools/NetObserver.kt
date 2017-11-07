package com.twobbble.tools

import com.twobbble.R
import com.twobbble.application.App
import com.twobbble.view.api.IBaseView
import io.reactivex.Observer
import io.reactivex.disposables.Disposable


/**
 * Created by liuzipeng on 2017/2/20.
 */
class NetObserver<T>(val onStart: (Disposable) -> Unit,
                     private val onResult: (T) -> Unit,
                     private val onFailure: (String) -> Unit,
                     private val baseView: IBaseView? = null) : Observer<T> {


    override fun onError(e: Throwable) {
        e.printStackTrace()
        baseView?.hideProgress()
        if (e is NetExceptionHandler.ResponseException) {
            onFailure(e.message.toString())
        } else {
            onFailure("可能遇到了未知的错误，请重试")
        }
    }

    override fun onComplete() {
        baseView?.hideProgress()
    }

    override fun onNext(t: T) {
        onResult(t)
    }

    override fun onSubscribe(d: Disposable) {
        netAvailable {
            if (!it) {
                onFailure(App.instance.resources.getString(R.string.net_disable))
                baseView?.hideProgress()
                d.dispose()
            } else {
                baseView?.showProgress()
            }
        }

        onStart(d)
    }
}