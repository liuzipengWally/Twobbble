package com.twobbble.biz.impl

import com.twobbble.biz.api.IDetailsBiz
import com.twobbble.biz.assist.NetService
import com.twobbble.biz.assist.RetrofitFactory
import com.twobbble.entity.Comment
import com.twobbble.tools.NetSubscriber
import com.twobbble.tools.RxHelper
import com.twobbble.tools.log
import rx.Subscription

/**
 * Created by liuzipeng on 2017/3/1.
 */
class DetailsBiz : IDetailsBiz, BaseBiz() {
    override fun getComments(id: Long, access_token: String, page: Int?, subscriber: NetSubscriber<MutableList<Comment>>): Subscription {
        log("$id $access_token $page")
        mNetService?.getComments(id, access_token, page)?.
                compose(RxHelper.listModeThread())?.
                subscribe(subscriber)
        return subscriber
    }
}