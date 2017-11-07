package com.twobbble.presenter

import com.twobbble.biz.api.IMyBucketsBiz
import com.twobbble.biz.impl.MyBucketsBiz
import com.twobbble.tools.NetObserver
import com.twobbble.tools.singleData
import com.twobbble.view.api.IMyBucketsView
import org.jetbrains.annotations.NotNull

/**
 * Created by liuzipeng on 2017/3/9.
 */
class MyBucketsPresenter(val mMyBucketsView: IMyBucketsView) : BasePresenter() {
    private val mMyBucketsBiz: IMyBucketsBiz by lazy {
        MyBucketsBiz()
    }

    fun getMyBuckets(@NotNull token: String, page: Int? = null) {
        mMyBucketsBiz.getMyBuckets(token, page, NetObserver({
            mDisposables.add(it)
        }, {
            mMyBucketsView.getBucketsSuccess(it)
        }, {
            mMyBucketsView.getBucketsFailed(it)
        }, mMyBucketsView))
    }

    fun createBucket(@NotNull token: String, @NotNull name: String, description: String?) {
        mMyBucketsBiz.createBucket(token, name, description, NetObserver({
            mMyBucketsView.showProgressDialog()
            mDisposables.add(it)
        }, {
            mMyBucketsView.hideProgressDialog()
            mMyBucketsView.createBucketSuccess(it)
        }, {
            mMyBucketsView.hideProgressDialog()
            mMyBucketsView.createBucketFailed(it)
        }))
    }

    fun deleteBucket(@NotNull token: String, @NotNull id: Long) {
        mMyBucketsBiz.deleteBucket(token, id, NetObserver({
            mMyBucketsView.showProgressDialog()
            mDisposables.add(it)
        }, {
            mMyBucketsView.hideProgressDialog()
            mMyBucketsView.deleteBucketSuccess()
        }, {
            mMyBucketsView.hideProgressDialog()
            mMyBucketsView.deleteBucketFailed(it)
        }))
    }

    fun modifyBucket(@NotNull token: String, @NotNull id: Long, @NotNull name: String, description: String?, position: Int) {
        mMyBucketsBiz.modifyBucket(token, id, name, description, NetObserver({
            mMyBucketsView.showProgressDialog()
            mDisposables.add(it)
        }, {
            mMyBucketsView.hideProgressDialog()
            mMyBucketsView.modifyBucketSuccess(it, position)
        }, {
            mMyBucketsView.hideProgressDialog()
            mMyBucketsView.modifyBucketFailed(it)
        }))
    }

    fun addShot2Bucket(@NotNull id: Long, @NotNull token: String = singleData.token!!, @NotNull shotId: Long) {
        mMyBucketsBiz.addShot2Bucket(id, token, shotId, NetObserver({
            mMyBucketsView.showProgressDialog()
            mDisposables.add(it)
        }, {
            mMyBucketsView.hideProgressDialog()
            mMyBucketsView.addShotSuccess()
        }, {
            mMyBucketsView.hideProgressDialog()
            mMyBucketsView.addShotFailed(it)
        }))
    }
}