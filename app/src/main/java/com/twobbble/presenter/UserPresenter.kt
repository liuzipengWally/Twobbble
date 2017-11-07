package com.twobbble.presenter

import com.twobbble.biz.api.IUserBiz
import com.twobbble.biz.impl.UserBiz
import com.twobbble.entity.NullResponse
import com.twobbble.entity.Shot
import com.twobbble.tools.Constant
import com.twobbble.tools.NetObserver
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

        mUserBiz.getUserShot(user, id, token, page, NetObserver({
            mDisposables.add(it)
        }, {
            mUserView.getShotSuccess(it, isLoadMore)
        }, {
            mUserView.getShotFailed(it, isLoadMore)
        }, mUserView))
    }

    fun checkIfFollowingUser(id: Long) {
        mUserBiz.checkIfFollowingUser(id, singleData.token!!, NetObserver({
            mDisposables.add(it)
        }, {
            mUserView.following()
        }, {
            log(it)
            mUserView.notFollowing()
        }))
    }

    fun followUser(id: Long) {
        mUserBiz.followUser(id, singleData.token!!, NetObserver({
            mUserView.showProgressDialog()
            mDisposables.add(it)
        }, {
            mUserView.hideProgressDialog()
            mUserView.followUserSuccess()
        }, {
            mUserView.hideProgressDialog()
            mUserView.followUserFailed(it)
        }))
    }

    fun unFollowUser(id: Long) {
        mUserBiz.unFollowUser(id, singleData.token!!, NetObserver({
            mUserView.showProgressDialog()
            mDisposables.add(it)
        }, {
            mUserView.hideProgressDialog()
            mUserView.unFollowUserSuccess()
        }, {
            mUserView.hideProgressDialog()
            mUserView.unFollowUserFailed(it)
        }))
    }
}