package com.twobbble.biz.impl

import com.twobbble.biz.api.IUserBiz
import com.twobbble.biz.assist.RetrofitFactory
import com.twobbble.entity.NullResponse
import com.twobbble.entity.Shot
import com.twobbble.entity.Token
import com.twobbble.entity.User
import com.twobbble.tools.NetObserver
import com.twobbble.tools.RxHelper
import org.jetbrains.annotations.NotNull

/**
 * Created by liuzipeng on 2017/3/5.
 */
class UserBiz : BaseBiz(), IUserBiz {
    override fun checkIfFollowingUser(id: Long, access_token: String, netObserver: NetObserver<NullResponse>) {
        getNetService().checkIfFollowingUser(id, access_token)
                .compose(RxHelper.singleModeThread())
                .subscribe(netObserver)
    }

    override fun followUser(id: Long, access_token: String, netObserver: NetObserver<NullResponse>) {
        getNetService().followUser(id, access_token)
                .compose(RxHelper.singleModeThread())
                .subscribe(netObserver)
    }

    override fun unFollowUser(id: Long, access_token: String, netObserver: NetObserver<NullResponse>) {
        getNetService().unFollowUser(id, access_token)
                .compose(RxHelper.singleModeThread())
                .subscribe(netObserver)
    }

    override fun getUserShot(user: String, id: String?, access_token: String, page: Int?, netObserver: NetObserver<MutableList<Shot>>) {
        getNetService().getUserShot(user, id, access_token, page)
                .compose(RxHelper.listModeThread())
                .subscribe(netObserver)
    }

    override fun getMyInfo(@NotNull access_token: String, netObserver: NetObserver<User>) {
        getNetService().getMyInfo(access_token)
                .compose(RxHelper.singleModeThread())
                .subscribe(netObserver)
    }

    override fun getToken(oauthCode: String, netObserver: NetObserver<Token>) {
        RetrofitFactory.instance()
                .createWebsiteRetrofit()
                .getToken(oauthCode = oauthCode)
                .compose(RxHelper.singleModeThread())
                .subscribe(netObserver)
    }
}