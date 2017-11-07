package com.twobbble.biz.api

import com.twobbble.entity.Bucket
import com.twobbble.entity.Shot
import com.twobbble.tools.NetObserver
import org.jetbrains.annotations.NotNull

/**
 * Created by liuzipeng on 2017/3/9.
 */
interface IMyBucketsBiz {
    /**
     * 获取当前用户的Bucket列表
     */
    fun getMyBuckets(@NotNull access_token: String,
                     page: Int?,
                     observer: NetObserver<MutableList<Bucket>>)

    /**
     * 创建一个bucket
     */
    fun createBucket(@NotNull access_token: String,
                     @NotNull name: String,
                     description: String?,
                     observer: NetObserver<Bucket>)

    /**
     * 删除一个bucket
     */
    fun deleteBucket(@NotNull access_token: String,
                     @NotNull id: Long,
                     observer: NetObserver<Bucket>)

    /**
     * 修改一个bucket
     */
    fun modifyBucket(@NotNull access_token: String,
                     @NotNull id: Long,
                     @NotNull name: String,
                     description: String?,
                     observer: NetObserver<Bucket>)

    /**
     * 添加一个shot到一个bucket
     */
    fun addShot2Bucket(@NotNull id: Long,
                       @NotNull access_token: String,
                       @NotNull shot_id: Long?,
                       observer: NetObserver<Shot>)
}