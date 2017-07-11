package com.twobbble.presenter

import com.twobbble.R
import com.twobbble.application.App
import com.twobbble.biz.api.IDetailsBiz
import com.twobbble.biz.impl.DetailsBiz
import com.twobbble.entity.Comment
import com.twobbble.entity.LikeShotResponse
import com.twobbble.tools.NetSubscriber
import com.twobbble.tools.log
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
        val subscribe = mDetailsBiz.getComments(id, token, page, object : NetSubscriber<MutableList<Comment>>(mDetailsView) {
            override fun onFailed(msg: String) {
                mDetailsView.getCommentsFailed(msg)
            }

            override fun onNext(t: MutableList<Comment>?) {
                mDetailsView.getCommentsSuccess(t)
            }
        })

        mSubscription.add(subscribe)
    }

    fun likeShot(@NotNull id: Long, @NotNull token: String) {
        val subscribe = mDetailsBiz.likeShot(id, token, object : NetSubscriber<LikeShotResponse>() {
            override fun onFailed(msg: String) {
                mDetailsView.likeShotFailed("${App.instance.resources.getString(R.string.like_failed)}:$msg")
            }

            override fun onNext(t: LikeShotResponse?) {
                if (t != null) mDetailsView.likeShotSuccess() else mDetailsView.likeShotFailed(App.instance.resources.getString(R.string.like_failed))
            }
        })

        mSubscription.add(subscribe)
    }

    fun checkIfLikeShot(@NotNull id: Long, @NotNull token: String) {
        val subscribe = mDetailsBiz.checkIfLikeShot(id, token, object : NetSubscriber<LikeShotResponse>() {
            override fun onFailed(msg: String) {
                log(msg)
                mDetailsView.checkIfLikeFailed()
            }

            override fun onNext(t: LikeShotResponse?) {
                if (t != null) mDetailsView.checkIfLikeSuccess() else mDetailsView.checkIfLikeFailed()
            }
        })

        mSubscription.add(subscribe)
    }

    fun unlikeShot(@NotNull id: Long, @NotNull token: String) {
        val subscribe = mDetailsBiz.unlikeShot(id, token, object : NetSubscriber<LikeShotResponse>() {
            override fun onFailed(msg: String) {
                mDetailsView.unLikeShotFailed("${App.instance.resources.getString(R.string.unlike_failed)}:$msg")
            }

            override fun onNext(t: LikeShotResponse?) {
                mDetailsView.unLikeShotSuccess()
            }
        })

        mSubscription.add(subscribe)
    }

    fun createComment(@NotNull id: Long, @NotNull token: String, @NotNull body: String) {
        val subscribe = mDetailsBiz.createComment(id, token, body, object : NetSubscriber<Comment>() {
            override fun onStart() {
                super.onStart()
                mDetailsView.showSendProgress()
            }

            override fun onCompleted() {
                super.onCompleted()
                mDetailsView.hideSendProgress()
            }

            override fun onFailed(msg: String) {
                mDetailsView.hideSendProgress()
                mDetailsView.addCommentFailed("${App.instance.resources.getString(R.string.account_not_player)}:$msg")
            }

            override fun onNext(t: Comment?) {
                mDetailsView.addCommentSuccess(t)
            }
        })

        mSubscription.add(subscribe)
    }
}