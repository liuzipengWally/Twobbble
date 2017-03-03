package com.twobbble.view.api

import com.twobbble.entity.Comment

/**
 * Created by liuzipeng on 2017/3/1.
 */
interface IDetailsView : IBaseView {
    fun getCommentsSuccess(Comments: MutableList<Comment>?)

    fun getCommentsFailed(msg: String)

    fun addCommentFailed(msg: String)

    fun addCommentSuccess(Comments: MutableList<Comment>)
}