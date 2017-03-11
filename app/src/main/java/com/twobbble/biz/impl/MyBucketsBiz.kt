package com.twobbble.biz.impl

import com.twobbble.biz.api.IMyBucketsBiz
import com.twobbble.entity.Bucket
import com.twobbble.tools.NetSubscriber
import com.twobbble.tools.RxHelper
import rx.Subscription

/**
 * Created by liuzipeng on 2017/3/9.
 */
class MyBucketsBiz : BaseBiz(), IMyBucketsBiz {
    override fun modifyBucket(access_token: String, id: Long, name: String, description: String?, subscriber: NetSubscriber<Bucket>): Subscription {
        mNetService?.modifyBucket(id, access_token, name, description)
                ?.compose(RxHelper.singleModeThread())
                ?.subscribe(subscriber)

        return subscriber
    }

    override fun deleteBucket(access_token: String, id: Long, subscriber: NetSubscriber<Bucket>): Subscription {
        mNetService?.deleteBucket(id, access_token)
                ?.compose(RxHelper.singleModeThread())
                ?.subscribe(subscriber)

        return subscriber
    }

    override fun createBucket(access_token: String, name: String, description: String?, subscriber: NetSubscriber<Bucket>): Subscription {
        mNetService?.createBucket(access_token, name, description)
                ?.compose(RxHelper.singleModeThread())
                ?.subscribe(subscriber)

        return subscriber
    }

    override fun getMyBuckets(access_token: String, page: Int?, subscriber: NetSubscriber<MutableList<Bucket>>): Subscription {
        mNetService?.getMyBuckets(access_token, page)
                ?.compose(RxHelper.listModeThread())
                ?.subscribe(subscriber)
        return subscriber
    }
}