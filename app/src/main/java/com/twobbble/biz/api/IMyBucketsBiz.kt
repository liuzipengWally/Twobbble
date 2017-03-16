package com.twobbble.biz.api

import com.twobbble.entity.Bucket
import com.twobbble.entity.Like
import com.twobbble.entity.Shot
import com.twobbble.tools.NetSubscriber
import org.jetbrains.annotations.NotNull
import retrofit2.http.Field
import retrofit2.http.Path
import rx.Subscription

/**
 * Created by liuzipeng on 2017/3/9.
 */
interface IMyBucketsBiz {
    /**
     * 获取当前用户的Bucket列表
     */
    fun getMyBuckets(@NotNull access_token: String,
                     page: Int?,
                     subscriber: NetSubscriber<MutableList<Bucket>>): Subscription

    /**
     * 创建一个bucket
     */
    fun createBucket(@NotNull access_token: String,
                     @NotNull name: String,
                     description: String?,
                     subscriber: NetSubscriber<Bucket>): Subscription

    /**
     * 删除一个bucket
     */
    fun deleteBucket(@NotNull access_token: String,
                     @NotNull id: Long,
                     subscriber: NetSubscriber<Bucket>): Subscription

    /**
     * 修改一个bucket
     */
    fun modifyBucket(@NotNull access_token: String,
                     @NotNull id: Long,
                     @NotNull name: String,
                     description: String?,
                     subscriber: NetSubscriber<Bucket>): Subscription

    /**
     * 添加一个shot到一个bucket
     */
    fun addShot2Bucket(@NotNull id: Long,
                       @NotNull access_token: String,
                       @NotNull shot_id: Long?,
                       subscriber: NetSubscriber<Shot>): Subscription
}