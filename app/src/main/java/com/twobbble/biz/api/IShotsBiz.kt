package com.twobbble.biz.api

import com.twobbble.entity.Shot
import com.twobbble.tools.NetSubscriber
import org.jetbrains.annotations.NotNull
import rx.Subscriber
import rx.Subscription

/**
 * Created by liuzipeng on 2017/2/22.
 */
interface IShotsBiz {
    /**
     * 获取一个shot列表
     *
     * @param access_token token  默认值为公用token
     * @param list  列表类型，比如带GIF的，团队的  animated ，attachments ，debuts ，playoffs，rebounds，teams
     * @param sort 排序 comments评论最多的，recent 最近的，views查看最多的
     * @param timeframe 时间   week一周，month一个月，year一键，ever无论何时
     *
     * @return List<Shot>
     */
    fun getShots(@NotNull access_token: String,
                 list: String?,
                 timeframe: String?,
                 sort: String?,
                 page: Int?,
                 subscriber: NetSubscriber<MutableList<Shot>>): Subscription
}