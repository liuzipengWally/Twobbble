package com.twobbble.biz.api

import com.twobbble.entity.Like
import com.twobbble.tools.NetObserver
import org.jetbrains.annotations.NotNull

/**
 * Created by liuzipeng on 2017/3/7.
 */
interface ILikeBiz {
    /**
     * 获取当前用户的likes列表
     */
    fun getMyLike(@NotNull access_token: String,
                  page: Int?,
                  observer: NetObserver<MutableList<Like>>)
}