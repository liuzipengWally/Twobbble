package com.twobbble.presenter

import com.twobbble.biz.api.ILikeBiz
import com.twobbble.biz.impl.LikeBiz
import com.twobbble.entity.Like
import com.twobbble.tools.NetObserver
import com.twobbble.view.api.ILikeView
import org.jetbrains.annotations.NotNull

/**
 * Created by liuzipeng on 2017/3/7.
 */
class LikePresenter(val mLikeView: ILikeView) : BasePresenter() {
    private val mLikeBiz: ILikeBiz by lazy {
        LikeBiz()
    }

    fun getMyLike(@NotNull access_token: String, page: Int?, isLoadMore: Boolean) {
        mLikeBiz.getMyLike(access_token, page, NetObserver({
            mDisposables.add(it)
        }, {
            mLikeView.getLikeSuccess(it, isLoadMore)
        }, {
            mLikeView.getLikeFailed(it, isLoadMore)
        }, mLikeView))
    }
}