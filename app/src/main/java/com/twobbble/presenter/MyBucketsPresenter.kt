package com.twobbble.presenter

import com.twobbble.biz.api.IMyBucketsBiz
import com.twobbble.biz.impl.MyBucketsBiz
import com.twobbble.entity.Bucket
import com.twobbble.tools.NetSubscriber
import com.twobbble.view.api.IMyBucketsView
import org.jetbrains.annotations.NotNull

/**
 * Created by liuzipeng on 2017/3/9.
 */
class MyBucketsPresenter(val mMyBucketsView: IMyBucketsView) : BasePresenter() {
    var mMyBucketsBiz: IMyBucketsBiz? = null

    init {
        mMyBucketsBiz = MyBucketsBiz()
    }

    fun getMyBuckets(@NotNull token: String, page: Int? = null) {
        mMyBucketsBiz?.getMyBuckets(token, page, object : NetSubscriber<MutableList<Bucket>>(mMyBucketsView) {
            override fun onNext(t: MutableList<Bucket>?) {
                mMyBucketsView.getBucketsSuccess(t)
            }

            override fun onFailed(msg: String) {
                mMyBucketsView.getBucketsFailed(msg)
            }
        })
    }

    fun createBucket(@NotNull token: String, @NotNull name: String, description: String?) {
        mMyBucketsBiz?.createBucket(token, name, description, object : NetSubscriber<Bucket>() {
            override fun onStart() {
                mMyBucketsView.showProgressDialog()
                super.onStart()
            }

            override fun onCompleted() {
                super.onCompleted()
                mMyBucketsView.hideProgressDialog()
            }

            override fun onFailed(msg: String) {
                mMyBucketsView.hideProgressDialog()
                mMyBucketsView.createBucketFailed(msg)
            }

            override fun onNext(t: Bucket?) {
                mMyBucketsView.createBucketSuccess(t)
            }
        })
    }

    fun deleteBucket(@NotNull token: String, @NotNull id: Long) {
        mMyBucketsBiz?.deleteBucket(token, id, object : NetSubscriber<Bucket>() {
            override fun onStart() {
                mMyBucketsView.showProgressDialog()
                super.onStart()
            }

            override fun onNext(t: Bucket?) {
                mMyBucketsView.deleteBucketSuccess()
            }

            override fun onCompleted() {
                super.onCompleted()
                mMyBucketsView.hideProgressDialog()
            }


            override fun onFailed(msg: String) {
                mMyBucketsView.hideProgressDialog()
                mMyBucketsView.deleteBucketFailed(msg)
            }
        })
    }

    fun modifyBucket(@NotNull token: String, @NotNull id: Long, @NotNull name: String, description: String?, position: Int) {
        mMyBucketsBiz?.modifyBucket(token, id, name, description, object : NetSubscriber<Bucket>() {
            override fun onStart() {
                mMyBucketsView.showProgressDialog()
                super.onStart()
            }

            override fun onNext(t: Bucket?) {
                mMyBucketsView.modifyBucketSuccess(t, position)
            }

            override fun onCompleted() {
                super.onCompleted()
                mMyBucketsView.hideProgressDialog()
            }

            override fun onFailed(msg: String) {
                mMyBucketsView.hideProgressDialog()
                mMyBucketsView.modifyBucketFailed(msg)
            }
        })
    }
}