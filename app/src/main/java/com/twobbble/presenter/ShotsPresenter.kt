package com.twobbble.presenter.service

import com.twobbble.biz.api.IShotsBiz
import com.twobbble.biz.impl.ShotsBiz
import com.twobbble.entity.ShotList
import com.twobbble.presenter.BasePresenter
import com.twobbble.tools.Constant
import com.twobbble.tools.NetSubscriber
import com.twobbble.view.api.IShotsView
import org.jetbrains.annotations.NotNull
import rx.subscriptions.CompositeSubscription

/**
 * Created by liuzipeng on 2017/2/22.
 */
class ShotsPresenter(shotsView: IShotsView) : BasePresenter() {
    private var mShotsBiz: IShotsBiz? = null
    private var mShotsView: IShotsView? = null

    init {
        mShotsBiz = ShotsBiz()
        mShotsView = shotsView
    }

    fun getShots(access_token: String = Constant.ACCESS_TOKEN, list: String? = null, timeframe: String? = null, sort: String? = null) {
        val subscriber = mShotsBiz?.getShots(access_token, list, timeframe, sort, object : NetSubscriber<List<ShotList>>() {
            override fun onStart() {
                super.onStart()
                mShotsView?.showProgress()
            }

            override fun onNext(t: List<ShotList>?) {
                mShotsView?.getShotSuccess(t)
            }

            override fun onError(t: Throwable?) {
                t?.printStackTrace()
                mShotsView?.getShotFailed(t?.message.toString())
            }

            override fun onCompleted() {
                mShotsView?.hideProgress()
            }
        })

        mSubscription?.add(subscriber)
    }
}