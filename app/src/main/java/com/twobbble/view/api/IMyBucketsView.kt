package com.twobbble.view.api

import com.twobbble.R
import com.twobbble.application.App
import com.twobbble.entity.Bucket
import com.twobbble.entity.Like
import com.twobbble.entity.Shot

/**
 * Created by liuzipeng on 2017/3/7.
 */
interface IMyBucketsView : IBaseView {
    fun getBucketsSuccess(buckets: MutableList<Bucket>?)

    fun getBucketsFailed(msg: String)

    fun createBucketSuccess(bucket: Bucket?)

    fun createBucketFailed(msg: String)

    fun deleteBucketSuccess()

    fun deleteBucketFailed(msg: String)

    fun modifyBucketSuccess(bucket: Bucket?, position: Int)

    fun modifyBucketFailed(msg: String)

    fun addShotSuccess()

    fun addShotFailed(msg: String)
}