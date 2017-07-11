package com.twobbble.presenter

import rx.subscriptions.CompositeSubscription

/**
 * Created by liuzipeng on 2017/2/22.
 */
abstract class BasePresenter {
    val mSubscription: CompositeSubscription by lazy {
        CompositeSubscription()
    }


    open fun unSubscriber() {
        if (mSubscription.hasSubscriptions()) {
            mSubscription.unsubscribe()
        }
    }
}