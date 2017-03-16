package com.twobbble.biz.impl

import com.twobbble.biz.api.IDetailsBiz
import com.twobbble.biz.assist.NetService
import com.twobbble.biz.assist.RetrofitFactory
import com.twobbble.entity.Comment
import com.twobbble.entity.LikeShotResponse
import com.twobbble.tools.NetSubscriber
import com.twobbble.tools.RxHelper
import com.twobbble.tools.log
import rx.Subscription

/**
 * Created by liuzipeng on 2017/3/1.
 */
class DetailsBiz : IDetailsBiz, BaseBiz() {
    override fun createComment(id: Long, access_token: String, body: String, subscriber: NetSubscriber<Comment>): Subscription {
        getNetService().createComment(id, access_token, body)
                .compose(RxHelper.singleModeThread())
                .subscribe(subscriber)
        return subscriber
    }

    override fun unlikeShot(id: Long, access_token: String, subscriber: NetSubscriber<LikeShotResponse>): Subscription {
        getNetService().unlikeShot(id, access_token)
                .compose(RxHelper.singleModeThread())
                .subscribe(subscriber)
        return subscriber
    }

    override fun checkIfLikeShot(id: Long, access_token: String, subscriber: NetSubscriber<LikeShotResponse>): Subscription {
        getNetService().checkIfLikeShot(id, access_token)
                .compose(RxHelper.singleModeThread())
                .subscribe(subscriber)

        return subscriber
    }

    override fun likeShot(id: Long, access_token: String, subscriber: NetSubscriber<LikeShotResponse>): Subscription {
        getNetService().likeShot(id, access_token)
                .compose(RxHelper.singleModeThread())
                .subscribe(subscriber)

        return subscriber
    }

    override fun getComments(id: Long, access_token: String, page: Int?, subscriber: NetSubscriber<MutableList<Comment>>): Subscription {
        getNetService().getComments(id, access_token, page).
                compose(RxHelper.listModeThread()).
                subscribe(subscriber)
        return subscriber
    }
}