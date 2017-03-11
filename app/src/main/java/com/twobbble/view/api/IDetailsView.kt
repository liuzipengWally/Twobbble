package com.twobbble.view.api

import com.twobbble.entity.Comment

/**
 * Created by liuzipeng on 2017/3/1.
 */
interface IDetailsView : IBaseView {
    /**
     * 获取评论列表成功
     */
    fun getCommentsSuccess(Comments: MutableList<Comment>?)

    /**
     * 获取评论列表失败
     */
    fun getCommentsFailed(msg: String)

    fun showSendProgress()

    fun hideSendProgress()

    /**
     * 添加评论失败
     */
    fun addCommentFailed(msg: String)

    /**
     * 添加评论成功
     */
    fun addCommentSuccess(comment: Comment?)

    /**
     * 喜欢shot成功
     */
    fun likeShotSuccess()

    /**
     * 喜欢shot失败
     */
    fun likeShotFailed(msg: String)

    /**
     * 这个shot是喜欢状态
     */
    fun checkIfLikeSuccess()

    /**
     * 这个shot为不喜欢状态
     */
    fun checkIfLikeFailed()

    /**
     * 取消shot的喜欢成功
     */
    fun unLikeShotSuccess()

    /**
     * 取消shot的喜欢失败
     */
    fun unLikeShotFailed(msg: String)
}