package com.twobbble.biz.api

import com.twobbble.entity.Shot
import com.twobbble.tools.NetSubscriber
import org.jetbrains.annotations.NotNull
import retrofit2.http.Path
import retrofit2.http.Query
import rx.Subscription

/**
 * Created by liuzipeng on 2017/3/12.
 */
interface IBucketShotsBiz {
    /**
     * 获取一个bucket中的shot列表
     */
    fun getBucketShots(@NotNull id: Long,
                       @NotNull access_token: String,
                       page: Int?,
                       netSubscriber: NetSubscriber<MutableList<Shot>>): Subscription

    /**
     * 从一个bucket中删除一个shot
     */
    fun removeShotFromBucket(@NotNull access_token: String,
                             @NotNull id: Long,
                             @NotNull shot_id: Long?,
                             netSubscriber: NetSubscriber<Shot>): Subscription
}