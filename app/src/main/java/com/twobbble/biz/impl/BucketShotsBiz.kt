package com.twobbble.biz.impl

import com.twobbble.biz.api.IBucketShotsBiz
import com.twobbble.entity.Shot
import com.twobbble.tools.NetObserver
import com.twobbble.tools.RxHelper

/**
 * Created by liuzipeng on 2017/3/12.
 */
class BucketShotsBiz : IBucketShotsBiz, BaseBiz() {
    override fun getBucketShots(id: Long, access_token: String, page: Int?, netObserver: NetObserver<MutableList<Shot>>) {
        getNetService().getBucketShots(id, access_token, page)
                .compose(RxHelper.listModeThread())
                .subscribe(netObserver)
    }

    override fun removeShotFromBucket(access_token: String, id: Long, shot_id: Long?, netObserver: NetObserver<Shot>) {
        getNetService().removeShotFromBucket(id, access_token, shot_id)
                .compose(RxHelper.singleModeThread())
                .subscribe(netObserver)
    }
}