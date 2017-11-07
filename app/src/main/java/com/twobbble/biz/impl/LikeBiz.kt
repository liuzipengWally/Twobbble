package com.twobbble.biz.impl

import com.twobbble.biz.api.ILikeBiz
import com.twobbble.entity.Like
import com.twobbble.tools.NetObserver
import com.twobbble.tools.RxHelper

/**
 * Created by liuzipeng on 2017/3/7.
 */
class LikeBiz : ILikeBiz, BaseBiz() {
    override fun getMyLike(access_token: String, page: Int?, observer: NetObserver<MutableList<Like>>) {
        getNetService().getMyLikes(access_token, page)
                .compose(RxHelper.listModeThread())
                .subscribe(observer)
    }
}