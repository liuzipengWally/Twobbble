package com.twobbble.presenter

import com.twobbble.biz.api.ILikeBiz
import com.twobbble.biz.impl.LikeBiz
import com.twobbble.entity.Like
import com.twobbble.entity.Shot
import com.twobbble.tools.NetSubscriber
import com.twobbble.view.api.ILikeView
import org.jetbrains.annotations.NotNull

/**
 * Created by liuzipeng on 2017/3/7.
 */
class LikePresenter(val mLikeView: ILikeView) : BasePresenter() {
    private var mLikeBiz: ILikeBiz? = null

    init {
        mLikeBiz = LikeBiz()
    }

    fun getMyLike(@NotNull access_token: String, page: Int?, isLoadMore: Boolean) {
        val subscribe = mLikeBiz?.getMyLike(access_token, page
                , object : NetSubscriber<MutableList<Like>>(mLikeView) {
            override fun onFailed(msg: String) {
                mLikeView.getLikeFailed(msg, isLoadMore)
            }

            override fun onNext(t: MutableList<Like>?) {
                mLikeView.getLikeSuccess(t, isLoadMore)
            }
        })

        mSubscription?.add(subscribe)
    }
}