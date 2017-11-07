package com.twobbble.presenter

import io.reactivex.disposables.CompositeDisposable

/**
 * Created by liuzipeng on 2017/2/22.
 */
abstract class BasePresenter {
    val mDisposables by lazy {
        CompositeDisposable()
    }

    open fun unSubscriber() {
        if (mDisposables.isDisposed) {
            mDisposables.isDisposed
        }
    }
}