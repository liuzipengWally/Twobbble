package com.twobbble.presenter

import com.twobbble.biz.api.IUserBiz
import com.twobbble.biz.impl.UserBiz
import com.twobbble.entity.Token
import com.twobbble.entity.User
import com.twobbble.tools.NetObserver
import com.twobbble.tools.log
import com.twobbble.view.api.IMainView
import org.jetbrains.annotations.NotNull

/**
 * Created by liuzipeng on 2017/3/5.
 */
class MainPresenter(val mMainView: IMainView) : BasePresenter() {
    private val mUserBiz: IUserBiz by lazy {
        UserBiz()
    }

    fun getToken(oauthCode: String) {
        mUserBiz.getToken(oauthCode, NetObserver({
            mDisposables.add(it)
        }, {
            mMainView.getTokenSuccess(it)
        }, {
            mMainView.getTokenFailed(it)
        }, mMainView))
    }

    fun getMyInfo(@NotNull access_token: String) {
        mUserBiz.getMyInfo(access_token, NetObserver({
            mDisposables.add(it)
        }, {
            mMainView.getUserSuccess(it)
        }, {
            log(it)
        }))
    }
}