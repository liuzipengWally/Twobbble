package com.twobbble.view.api

import com.twobbble.entity.ShotList
import org.jetbrains.annotations.NotNull

/**
 * Created by liuzipeng on 2017/2/22.
 */
interface IShotsView : IBaseView {
    /**
     * 获取shots成功
     * @param shotList shot列表
     */
    fun getShotSuccess(shotList: List<ShotList>?)

    /**
     * 获取shots失败
     * @param msg 失败原因
     */
    fun getShotFailed(@NotNull msg: String)
}