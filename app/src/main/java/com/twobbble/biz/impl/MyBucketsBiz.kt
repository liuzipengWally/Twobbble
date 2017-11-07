package com.twobbble.biz.impl

import com.twobbble.biz.api.IMyBucketsBiz
import com.twobbble.entity.Bucket
import com.twobbble.entity.Shot
import com.twobbble.tools.NetObserver
import com.twobbble.tools.RxHelper

/**
 * Created by liuzipeng on 2017/3/9.
 */
class MyBucketsBiz : BaseBiz(), IMyBucketsBiz {
    override fun addShot2Bucket(id: Long, access_token: String, shot_id: Long?, observer: NetObserver<Shot>) {
        getNetService().addShot2Bucket(id, access_token, shot_id)
                .compose(RxHelper.singleModeThread())
                .subscribe(observer)
    }

    override fun modifyBucket(access_token: String, id: Long, name: String, description: String?, observer: NetObserver<Bucket>) {
        getNetService().modifyBucket(id, access_token, name, description)
                .compose(RxHelper.singleModeThread())
                .subscribe(observer)
    }

    override fun deleteBucket(access_token: String, id: Long, observer: NetObserver<Bucket>) {
        getNetService().deleteBucket(id, access_token)
                .compose(RxHelper.singleModeThread())
                .subscribe(observer)
    }

    override fun createBucket(access_token: String, name: String, description: String?, observer: NetObserver<Bucket>) {
        getNetService().createBucket(access_token, name, description)
                .compose(RxHelper.singleModeThread())
                .subscribe(observer)
    }

    override fun getMyBuckets(access_token: String, page: Int?, observer: NetObserver<MutableList<Bucket>>) {
        getNetService().getMyBuckets(access_token, page)
                .compose(RxHelper.listModeThread())
                .subscribe(observer)
    }
}