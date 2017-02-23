package com.twobbble.presenter

import rx.subscriptions.CompositeSubscription

/**
 * Created by liuzipeng on 2017/2/22.
 */
abstract class BasePresenter {
    var mSubscription: CompositeSubscription? = null

    init {
        mSubscription = CompositeSubscription()
    }

    open fun unSubscriber() {
        mSubscription?.unsubscribe()
    }
}