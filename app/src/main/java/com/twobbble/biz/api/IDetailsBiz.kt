package com.twobbble.biz.api

import com.twobbble.entity.Shot
import com.twobbble.entity.Comment
import com.twobbble.tools.NetSubscriber
import org.jetbrains.annotations.NotNull
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
}