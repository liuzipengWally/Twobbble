package com.twobbble.biz.impl

import com.twobbble.biz.api.ILikeBiz
import com.twobbble.entity.Like
import com.twobbble.entity.Shot
import com.twobbble.tools.NetSubscriber
import com.twobbble.tools.RxHelper
import rx.Subscription

/**
 * Created by liuzipeng on 2017/3/7.
 */
class LikeBiz : ILikeBiz, BaseBiz() {
    override fun getMyLike(access_token: String, page: Int?, subscriber: NetSubscriber<MutableList<Like>>): Subscription {
        mNetService?.getMyLikes(access_token, page)
                ?.compose(RxHelper.listModeThread())
                ?.subscribe(subscriber)

        return subscriber
    }
}