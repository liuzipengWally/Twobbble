package com.twobbble.view.api

import com.twobbble.entity.Token
import com.twobbble.entity.User

/**
 * Created by liuzipeng on 2017/3/5.
 */
interface IMainView : IBaseView {
    fun getTokenSuccess(token: Token?)

    fun getTokenFailed(msg: String)

    fun getUserSuccess(user: User?)
}