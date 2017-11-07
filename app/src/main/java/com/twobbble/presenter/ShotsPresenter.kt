package com.twobbble.presenter.service

import com.twobbble.biz.api.IShotsBiz
import com.twobbble.biz.impl.ShotsBiz
import com.twobbble.presenter.BasePresenter
import com.twobbble.tools.Constant
import com.twobbble.tools.NetObserver
import com.twobbble.view.api.IShotsView

/**
 * Created by liuzipeng on 2017/2/22.
 */
class ShotsPresenter(private val mShotsView: IShotsView) : BasePresenter() {
    private val mShotsBiz: IShotsBiz by lazy {
        ShotsBiz()
    }

    fun getShots(access_token: String = Constant.ACCESS_TOKEN, list: String?, timeframe: String?, sort: String?, page: Int?, isLoadMore: Boolean) {
        mShotsBiz.getShots(access_token, list, timeframe, sort, page, NetObserver({
            mDisposables.add(it)
        }, {
            mShotsView.getShotSuccess(it, isLoadMore)
        }, {
            mShotsView.getShotFailed(it, isLoadMore)
        }, mShotsView))
    }
}