package com.twobbble.biz.impl

import com.twobbble.biz.api.IUserBiz
import com.twobbble.biz.assist.RetrofitFactory
import com.twobbble.entity.Token
import com.twobbble.entity.User
import com.twobbble.tools.NetSubscriber
import com.twobbble.tools.RxHelper
import org.jetbrains.annotations.NotNull
import retrofit2.http.Query
import rx.Subscription

/**
 * Created by liuzipeng on 2017/3/5.
 */
class UserBiz : BaseBiz(), IUserBiz {
    override fun getMyInfo(@NotNull access_token: String, netSubscriber: NetSubscriber<User>): Subscription {
        mNetService?.getMyInfo(access_token)
                ?.compose(RxHelper.singleModeThread())
                ?.subscribe(netSubscriber)
        return netSubscriber
    }

    override fun getToken(oauthCode: String, netSubscriber: NetSubscriber<Token>): Subscription {
        RetrofitFactory.getInstance()
                .createWebsiteRetrofit()
                .getToken(oauthCode = oauthCode)
                .compose(RxHelper.singleModeThread())
                .subscribe(netSubscriber)

        return netSubscriber
    }
}