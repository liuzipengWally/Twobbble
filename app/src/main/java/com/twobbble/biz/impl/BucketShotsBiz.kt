package com.twobbble.biz.impl

import com.twobbble.biz.api.IBucketShotsBiz
import com.twobbble.entity.Shot
import com.twobbble.tools.NetSubscriber
import com.twobbble.tools.RxHelper
import rx.Subscription

/**
 * Created by liuzipeng on 2017/3/12.
 */
class BucketShotsBiz : IBucketShotsBiz, BaseBiz() {
    override fun getBucketShots(id: Long, access_token: String, page: Int?, netSubscriber: NetSubscriber<MutableList<Shot>>): Subscription {
        getNetService().getBucketShots(id, access_token, page)
                .compose(RxHelper.listModeThread())
                .subscribe(netSubscriber)

        return netSubscriber
    }

    override fun removeShotFromBucket(access_token: String, id: Long, shot_id: Long?, netSubscriber: NetSubscriber<Shot>): Subscription {
        getNetService().removeShotFromBucket(id, access_token, shot_id)
                .compose(RxHelper.singleModeThread())
                .subscribe(netSubscriber)

        return netSubscriber
    }
}