package com.twobbble.presenter

import com.twobbble.biz.api.IUserBiz
import com.twobbble.biz.impl.UserBiz
import com.twobbble.entity.NullResponse
import com.twobbble.entity.Shot
import com.twobbble.tools.Constant
import com.twobbble.tools.NetSubscriber
import com.twobbble.tools.log
import com.twobbble.tools.singleData
import com.twobbble.view.api.IUserView

/**
 * Created by liuzipeng on 2017/3/15.
 */
class UserPresenter(val mUserView: IUserView) : BasePresenter() {
    private val mUserBiz: IUserBiz by lazy {
        UserBiz()
    }

    fun getUserShot(user: String,
                    id: String? = null,
                    token: String = if (singleData.isLogin()) singleData.token!! else Constant.ACCESS_TOKEN,
                    page: Int,
                    isLoadMore: Boolean) {

        val subscribe = mUserBiz.getUserShot(user, id, token, page, object : NetSubscriber<MutableList<Shot>>(mUserView) {
            override fun onNext(t: MutableList<Shot>?) {
                mUserView.getShotSuccess(t, isLoadMore)
            }

            override fun onFailed(msg: String) {
                mUserView.getShotFailed(msg, isLoadMore)
            }
        })

        mSubscription.add(subscribe)
    }

    fun checkIfFollowingUser(id: Long) {
        val subscribe = mUserBiz.checkIfFollowingUser(id, singleData.token!!, object : NetSubscriber<NullResponse>() {
            override fun onNext(t: NullResponse?) {
                mUserView.following()
            }

            override fun onFailed(msg: String) {
                log(msg)
                mUserView.notFollowing()
            }
        })

        mSubscription.add(subscribe)
    }

    fun followUser(id: Long) {
        val subscribe = mUserBiz.followUser(id, singleData.token!!, object : NetSubscriber<NullResponse>() {
            override fun onCompleted() {
                super.onCompleted()
                mUserView.hideProgressDialog()
            }

            override fun onStart() {
                mUserView.showProgressDialog()
                super.onStart()
            }

            override fun onNext(t: NullResponse?) {
                mUserView.followUserSuccess()
            }

            override fun onFailed(msg: String) {
                mUserView.hideProgressDialog()
                mUserView.followUserFailed(msg)
            }
        })

        mSubscription.add(subscribe)
    }

    fun unFollowUser(id: Long) {
        val subscribe = mUserBiz.unFollowUser(id, singleData.token!!, object : NetSubscriber<NullResponse>() {
            override fun onCompleted() {
                super.onCompleted()
                mUserView.hideProgressDialog()
            }

            override fun onStart() {
                mUserView.showProgressDialog()
                super.onStart()
            }

            override fun onNext(t: NullResponse?) {
                mUserView.unFollowUserSuccess()
            }

            override fun onFailed(msg: String) {
                mUserView.hideProgressDialog()
                mUserView.unFollowUserFailed(msg)
            }
        })

        mSubscription.add(subscribe)
    }
}