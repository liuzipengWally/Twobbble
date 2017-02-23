package com.twobbble.biz.impl

import com.twobbble.biz.api.IShotsBiz
import com.twobbble.biz.assist.NetService
import com.twobbble.biz.assist.RetrofitFactory
import com.twobbble.entity.ShotList
import com.twobbble.tools.NetSubscriber
import com.twobbble.tools.RxHelper
import org.jetbrains.annotations.NotNull
import rx.Subscription

/**
 * Created by liuzipeng on 2017/2/22.
 */
class ShotsBiz : IShotsBiz {
    private var mNetService: NetService? = null

    init {
        mNetService = RetrofitFactory.getInstance().getService()
    }

    override fun getShots(@NotNull access_token: String, list: String?, timeframe: String?, sort: String?, subscriber: NetSubscriber<List<ShotList>>): Subscription {
        mNetService?.getShots(access_token, list, timeframe, sort)?.
                compose(RxHelper.listModeThread())?.
                subscribe(subscriber)

        return subscriber
    }
}