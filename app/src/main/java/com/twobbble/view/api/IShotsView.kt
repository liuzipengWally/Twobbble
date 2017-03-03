package com.twobbble.view.api

import com.twobbble.entity.Shot
import org.jetbrains.annotations.NotNull

/**
 * Created by liuzipeng on 2017/2/22.
 */
interface IShotsView : IBaseView {
    /**
     * 获取shots成功
     * @param shots shot列表
     * @param isLoadMore 是否是上拉加载
     */
    fun getShotSuccess(shots: MutableList<Shot>?, isLoadMore: Boolean)

    /**
     * 获取shots失败
     * @param msg 失败原因
     * @param isLoadMore 是否是上拉加载
     */
    fun getShotFailed(@NotNull msg: String, isLoadMore: Boolean)
}