package com.twobbble.presenter

import com.twobbble.R
import com.twobbble.application.App
import com.twobbble.biz.api.IDetailsBiz
import com.twobbble.biz.impl.DetailsBiz
import com.twobbble.entity.Comment
import com.twobbble.entity.LikeShotResponse
import com.twobbble.tools.NetObserver
import com.twobbble.tools.log
import com.twobbble.tools.obtainString
import com.twobbble.view.api.IDetailsView
import org.jetbrains.annotations.NotNull

/**
 * Created by liuzipeng on 2017/3/1.
 */
class DetailsPresenter(val mDetailsView: IDetailsView) : BasePresenter() {
    private val mDetailsBiz: IDetailsBiz by lazy {
        DetailsBiz()
    }

    fun getComments(@NotNull id: Long, @NotNull token: String, page: Int?) {
        mDetailsBiz.getComments(id, token, page, NetObserver({
            mDisposables.add(it)
        }, {
            mDetailsView.getCommentsSuccess(it)
        }, {
            mDetailsView.getCommentsFailed(it)
        }, mDetailsView))
    }

    fun likeShot(@NotNull id: Long, @NotNull token: String) {
        mDetailsBiz.likeShot(id, token, NetObserver({
            mDisposables.add(it)
        }, {
            if (it != null) mDetailsView.likeShotSuccess() else
                mDetailsView.likeShotFailed(App.instance.obtainString(R.string.like_failed))
        }, {
            mDetailsView.likeShotFailed("${App.instance.obtainString(R.string.like_failed)}:$it")
        }))
    }

    fun checkIfLikeShot(@NotNull id: Long, @NotNull token: String) {
        mDetailsBiz.checkIfLikeShot(id, token, NetObserver({
            mDisposables.add(it)
        }, {
            if (it != null) mDetailsView.checkIfLikeSuccess() else
                mDetailsView.checkIfLikeFailed()
        }, {
            mDetailsView.checkIfLikeFailed()
        }))
    }

    fun unlikeShot(@NotNull id: Long, @NotNull token: String) {
        mDetailsBiz.unlikeShot(id, token, NetObserver({
            mDisposables.add(it)
        }, {
            mDetailsView.unLikeShotSuccess()
        }, {
            mDetailsView.unLikeShotFailed("${App.instance.obtainString(R.string.unlike_failed)}:$it")
        }))
    }

    fun createComment(@NotNull id: Long, @NotNull token: String, @NotNull body: String) {
        mDetailsBiz.createComment(id, token, body, NetObserver({
            mDetailsView.showSendProgress()
            mDisposables.add(it)
        }, {
            mDetailsView.hideSendProgress()
            mDetailsView.addCommentSuccess(it)
        }, {
            mDetailsView.hideSendProgress()
            mDetailsView.addCommentFailed("${App.instance.obtainString(R.string.account_not_player)}:$it")
        }))
    }
}