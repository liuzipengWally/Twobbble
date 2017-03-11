package com.twobbble.view.api

import com.twobbble.entity.Like
import com.twobbble.entity.Shot

/**
 * Created by liuzipeng on 2017/3/7.
 */
interface ILikeView : IBaseView {
    fun getLikeSuccess(likes: MutableList<Like>?, isLoadMore: Boolean)

    fun getLikeFailed(msg: String, isLoadMore: Boolean)
}