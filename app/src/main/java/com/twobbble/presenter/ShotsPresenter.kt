package com.twobbble.presenter.service

import com.twobbble.biz.api.IShotsBiz
import com.twobbble.biz.impl.ShotsBiz
import com.twobbble.entity.Shot
import com.twobbble.presenter.BasePresenter
import com.twobbble.tools.Constant
import com.twobbble.tools.NetSubscriber
import com.twobbble.view.api.IShotsView

/**
 * Created by liuzipeng on 2017/2/22.
 */
class ShotsPresenter(val mShotsView: IShotsView) : BasePresenter() {
    private val mShotsBiz: IShotsBiz by lazy {
        ShotsBiz()
    }

    fun getShots(access_token: String = Constant.ACCESS_TOKEN, list: String?, timeframe: String?, sort: String?, page: Int?, isLoadMore: Boolean) {
        val subscriber = mShotsBiz.getShots(access_token, list, timeframe, sort, page, object : NetSubscriber<MutableList<Shot>>(mShotsView) {
            override fun onFailed(msg: String) {
                mShotsView.getShotFailed(msg, isLoadMore)
            }

            override fun onNext(t: MutableList<Shot>?) {
                mShotsView.getShotSuccess(t, isLoadMore)
            }
        })

        mSubscription.add(subscriber)
    }
}