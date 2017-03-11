package com.twobbble.biz.api

import com.twobbble.entity.Token
import com.twobbble.entity.User
import com.twobbble.tools.NetSubscriber
import org.jetbrains.annotations.NotNull
import retrofit2.http.Query
import rx.Subscription

/**
 * Created by liuzipeng on 2017/3/5.
 */
interface IUserBiz {
    /**
     * 登录获取token
     */
    fun getToken(oauthCode: String, netSubscriber: NetSubscriber<Token>): Subscription

    /**
     * 获取登录的用户信息
     *
     * @param access_token 用户登录后获得的token
     */
    fun getMyInfo(@NotNull access_token: String, netSubscriber: NetSubscriber<User>): Subscription
}