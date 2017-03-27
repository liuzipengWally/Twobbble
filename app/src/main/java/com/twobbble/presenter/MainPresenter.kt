package com.twobbble.presenter

import com.twobbble.biz.api.IUserBiz
import com.twobbble.biz.impl.UserBiz
import com.twobbble.entity.Token
import com.twobbble.entity.User
import com.twobbble.tools.NetSubscriber
import com.twobbble.tools.log
import com.twobbble.view.api.IMainView
import org.jetbrains.annotations.NotNull
import retrofit2.http.Query

/**
 * Created by liuzipeng on 2017/3/5.
 */
class MainPresenter(val mMainView: IMainView) : BasePresenter() {
    private var mUserBiz: IUserBiz? = null

    init {
        mUserBiz = UserBiz()
    }

    fun getToken(oauthCode: String) {
        val subscriber = mUserBiz?.getToken(oauthCode, object : NetSubscriber<Token>(mMainView) {
            override fun onFailed(msg: String) {
                mMainView.getTokenFailed(msg)
            }

            override fun onNext(t: Token?) {
                mMainView.getTokenSuccess(t)
            }
        })

        mSubscription?.add(subscriber)
    }

    fun getMyInfo(@NotNull access_token: String) {
        val subscriber = mUserBiz?.getMyInfo(access_token, object : NetSubscriber<User>() {
            override fun onFailed(msg: String) {
                log(msg)
            }

            override fun onNext(t: User?) {
                mMainView.getUserSuccess(t)
            }
        })

        mSubscription?.add(subscriber)
    }
}