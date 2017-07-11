package com.twobbble.presenter

import com.twobbble.biz.api.IBucketShotsBiz
import com.twobbble.biz.impl.BucketShotsBiz
import com.twobbble.entity.Shot
import com.twobbble.tools.NetSubscriber
import com.twobbble.tools.RxHelper
import com.twobbble.tools.singleData
import com.twobbble.view.api.IBucketShotsView
import org.jetbrains.annotations.NotNull

/**
 * Created by liuzipeng on 2017/3/12.
 */
class BucketShotsPresenter(val mBucketShotsView: IBucketShotsView) : BasePresenter() {
    val mBucketShotBiz: IBucketShotsBiz by lazy {
        BucketShotsBiz()
    }

    fun getBucketShots(id: Long, access_token: String = singleData.token!!, page: Int?, isLoadMore: Boolean) {
        val subscribe = mBucketShotBiz.getBucketShots(id, access_token, page, object : NetSubscriber<MutableList<Shot>>(mBucketShotsView) {
            override fun onNext(t: MutableList<Shot>?) {
                mBucketShotsView.getShotSuccess(t, isLoadMore)
            }

            override fun onFailed(msg: String) {
                mBucketShotsView.getShotFailed(msg, isLoadMore)
            }
        })

        mSubscription.add(subscribe)
    }

    fun removeShotFromBucket(@NotNull access_token: String = singleData.token!!, @NotNull id: Long, @NotNull shot_id: Long?) {
        val subscribe = mBucketShotBiz.removeShotFromBucket(access_token, id, shot_id, object : NetSubscriber<Shot>() {
            override fun onStart() {
                mBucketShotsView.showProgressDialog()
                super.onStart()
            }

            override fun onCompleted() {
                super.onCompleted()
                mBucketShotsView.hideProgressDialog()
            }

            override fun onFailed(msg: String) {
                mBucketShotsView.hideProgressDialog()
                mBucketShotsView.removeShotFailed(msg)
            }

            override fun onNext(t: Shot?) {
                mBucketShotsView.removeShotSuccess()
            }
        })

        mSubscription.add(subscribe)
    }
}