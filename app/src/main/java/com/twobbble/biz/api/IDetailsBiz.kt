package com.twobbble.biz.api

import com.twobbble.entity.Shot
import com.twobbble.entity.Comment
import com.twobbble.entity.LikeShotResponse
import com.twobbble.tools.NetSubscriber
import org.jetbrains.annotations.NotNull
import retrofit2.http.Field
import retrofit2.http.Path
import retrofit2.http.Query
import rx.Subscription

/**
 * Created by liuzipeng on 2017/3/1.
 */
interface IDetailsBiz {
    /**
     * 获取一个shot下的评论列表
     *
     * @param id 这条shot的ID
     * @param access_token token  默认值为公用token
     * @param page 获取哪一页  默认为空，评论列表应为dribbble评论偏少的缘故，不做上拉加载
     *
     * return MutableList<Comment>
     */
    fun getComments(@NotNull id: Long,
                    @NotNull access_token: String,
                    page: Int?,
                    subscriber: NetSubscriber<MutableList<Comment>>): Subscription

    /**
     * 喜欢一个shot
     */
    fun likeShot(@NotNull id: Long,
                 @NotNull access_token: String,
                 subscriber: NetSubscriber<LikeShotResponse>): Subscription

    /**
     * 检查这个shot是否已经被喜欢
     */
    fun checkIfLikeShot(@NotNull id: Long,
                        @NotNull access_token: String,
                        subscriber: NetSubscriber<LikeShotResponse>): Subscription

    /**
     * 删除一个like的喜欢
     */
    fun unlikeShot(@NotNull id: Long,
                   @NotNull access_token: String,
                   subscriber: NetSubscriber<LikeShotResponse>): Subscription

    /**
     * 创建一条评论
     */
    fun createComment(@NotNull id: Long,
                      @NotNull access_token: String,
                      @NotNull body: String,
                      subscriber: NetSubscriber<Comment>): Subscription
}