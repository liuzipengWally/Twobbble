package com.twobbble.biz.assist

import com.twobbble.entity.Shot
import com.twobbble.entity.Comment
import com.twobbble.tools.Constant
import org.jetbrains.annotations.NotNull
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import rx.Observable

/**
 * Created by liuzipeng on 2017/2/21.
 */
interface NetService {
    /**
     * 获取一个shot列表
     *
     * @param access_token token  默认值为公用token
     * @param list  列表类型，比如带GIF的，团队的  animated ，attachments ，debuts ，playoffs，rebounds，teams
     * @param sort 排序 comments评论最多的，recent 最近的，views查看最多的
     * @param timeframe 时间   week一周，month一个月，year一键，ever无论何时
     * @param page 获取哪一页
     *
     * @return MutableList<Shot>
     */
    @GET("shots") fun getShots(@NotNull @Query("access_token") access_token: String,
                               @Query("list") list: String?,
                               @Query("timeframe") timeframe: String?,
                               @Query("sort") sort: String?,
                               @Query("page") page: Int?): Observable<MutableList<Shot>>

    /**
     * 获取一个shot下的评论列表
     *
     * @param id 这条shot的ID
     * @param access_token token  默认值为公用token
     * @param page 获取哪一页  默认为空，评论列表应为dribbble评论偏少的缘故，不做上拉加载
     *
     * return MutableList<Comment>
     */
    @GET("shots/{id}/comments") fun getComments(@NotNull @Path("id") id: Long,
                                                @NotNull @Query("access_token") access_token: String,
                                                @Query("page") page: Int?,
                                                @Query("per_page") per_page: Int? = 100): Observable<MutableList<Comment>>
}