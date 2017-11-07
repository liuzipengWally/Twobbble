package com.twobbble.presenter

import com.twobbble.biz.api.IBucketShotsBiz
import com.twobbble.biz.impl.BucketShotsBiz
import com.twobbble.entity.Shot
import com.twobbble.tools.NetObserver
import com.twobbble.tools.singleData
import com.twobbble.view.api.IBucketShotsView
import org.jetbrains.annotations.NotNull

/**
 * Created by liuzipeng on 2017/3/12.
 */
class BucketShotsPresenter(val mBucketShotsView: IBucketShotsView) : BasePresenter() {
    private val mBucketShotBiz: IBucketShotsBiz by lazy {
        BucketShotsBiz()
    }

    fun getBucketShots(id: Long, access_token: String = singleData.token!!, page: Int?, isLoadMore: Boolean) {
        mBucketShotBiz.getBucketShots(id, access_token, page, NetObserver({
            mDisposables.add(it)
        }, {
            mBucketShotsView.getShotSuccess(it, isLoadMore)
        }, {
            mBucketShotsView.getShotFailed(it, isLoadMore)
        }, mBucketShotsView))
    }

    fun removeShotFromBucket(@NotNull access_token: String = singleData.token!!, @NotNull id: Long, @NotNull shot_id: Long?) {
        mBucketShotBiz.removeShotFromBucket(access_token, id, shot_id, NetObserver({
            mBucketShotsView.showProgressDialog()
            mDisposables.add(it)
        }, {
            mBucketShotsView.hideProgressDialog()
            mBucketShotsView.removeShotSuccess()
        }, {
            mBucketShotsView.hideProgressDialog()
            mBucketShotsView.removeShotFailed(it)
        }))
    }
}