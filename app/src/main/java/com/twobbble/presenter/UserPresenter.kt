package com.twobbble.presenter

import com.twobbble.biz.api.IUserBiz
import com.twobbble.biz.impl.UserBiz
import com.twobbble.entity.Shot
import com.twobbble.tools.Constant
import com.twobbble.tools.NetSubscriber
import com.twobbble.tools.singleData
import com.twobbble.view.api.IUserView

/**
 * Created by liuzipeng on 2017/3/15.
 */
class UserPresenter(val mUserView: IUserView) : BasePresenter() {
    private var mUserBiz: IUserBiz? = null

    init {
        mUserBiz = UserBiz()
    }

    fun getUserShot(user: String,
                    id: String? = null,
                    token: String = if (singleData.isLogin()) singleData.token!! else Constant.ACCESS_TOKEN,
                    page: Int,
                    isLoadMore: Boolean) {

        mUserBiz?.getUserShot(user, id, token, page, object : NetSubscriber<MutableList<Shot>>(mUserView) {
            override fun onNext(t: MutableList<Shot>?) {
                mUserView.getShotSuccess(t, isLoadMore)
            }

            override fun onFailed(msg: String) {
                mUserView.getShotFailed(msg, isLoadMore)
            }
        })
    }
}