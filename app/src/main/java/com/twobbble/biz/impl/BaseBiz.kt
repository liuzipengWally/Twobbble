package com.twobbble.biz.impl

import com.twobbble.biz.assist.NetService
import com.twobbble.biz.assist.RetrofitFactory

/**
 * Created by liuzipeng on 2017/3/1.
 */
open class BaseBiz {
    fun getNetService(): NetService = RetrofitFactory.instance().getService()
}