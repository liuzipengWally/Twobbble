package com.twobbble.presenter.service

import com.twobbble.biz.api.IShotsBiz
import com.twobbble.biz.impl.ShotsBiz
import com.twobbble.entity.ShotList
import com.twobbble.presenter.BasePresenter
import com.twobbble.tools.Constant
import com.twobbble.tools.NetSubscriber
import com.twobbble.tools.log
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

    fun getShots(access_token: String = Constant.ACCESS_TOKEN, list: String?, timeframe: String?, sort: String?, page: Int?, isLoadMore: Boolean) {
        val subscriber = mShotsBiz?.getShots(access_token, list, timeframe, sort, page, object : NetSubscriber<MutableList<ShotList>>(mShotsView) {
            override fun onFailed(msg: String) {
                mShotsView?.getShotFailed(msg, isLoadMore)
            }

            override fun onNext(t: MutableList<ShotList>?) {
                mShotsView?.getShotSuccess(t, isLoadMore)
            }
        })

        mSubscription?.add(subscriber)
    }
}