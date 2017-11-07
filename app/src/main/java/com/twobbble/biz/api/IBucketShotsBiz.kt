package com.twobbble.biz.api

import com.twobbble.entity.Shot
import com.twobbble.tools.NetObserver
import org.jetbrains.annotations.NotNull

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
                       netObserver: NetObserver<MutableList<Shot>>)

    /**
     * 从一个bucket中删除一个shot
     */
    fun removeShotFromBucket(@NotNull access_token: String,
                             @NotNull id: Long,
                             @NotNull shot_id: Long?,
                             netObserver: NetObserver<Shot>)
}