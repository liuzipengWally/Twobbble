package com.twobbble.biz.impl

import com.twobbble.biz.api.IShotsBiz
import com.twobbble.biz.assist.NetService
import com.twobbble.biz.assist.RetrofitFactory
import com.twobbble.entity.Shot
import com.twobbble.tools.NetSubscriber
import com.twobbble.tools.RxHelper
import org.jetbrains.annotations.NotNull
import rx.Subscription

/**
 * Created by liuzipeng on 2017/2/22.
 */
class ShotsBiz : IShotsBiz, BaseBiz() {
    override fun getShots(@NotNull access_token: String,
                          list: String?,
                          timeframe: String?,
                          sort: String?,
                          page: Int?,
                          subscriber: NetSubscriber<MutableList<Shot>>): Subscription {
        mNetService?.getShots(access_token, list, timeframe, sort, page)?.
                compose(RxHelper.listModeThread())?.
                subscribe(subscriber)

        return subscriber
    }
}