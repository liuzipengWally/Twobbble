package com.twobbble.biz.impl

import com.twobbble.biz.api.IDetailsBiz
import com.twobbble.entity.Comment
import com.twobbble.entity.LikeShotResponse
import com.twobbble.tools.NetObserver
import com.twobbble.tools.RxHelper

/**
 * Created by liuzipeng on 2017/3/1.
 */
class DetailsBiz : IDetailsBiz, BaseBiz() {
    override fun createComment(id: Long, access_token: String, body: String, observer: NetObserver<Comment>) {
        getNetService().createComment(id, access_token, body)
                .compose(RxHelper.singleModeThread())
                .subscribe(observer)
    }

    override fun unlikeShot(id: Long, access_token: String, observer: NetObserver<LikeShotResponse>) {
        getNetService().unlikeShot(id, access_token)
                .compose(RxHelper.singleModeThread())
                .subscribe(observer)
    }

    override fun checkIfLikeShot(id: Long, access_token: String, observer: NetObserver<LikeShotResponse>) {
        getNetService().checkIfLikeShot(id, access_token)
                .compose(RxHelper.singleModeThread())
                .subscribe(observer)
    }

    override fun likeShot(id: Long, access_token: String, observer: NetObserver<LikeShotResponse>) {
        getNetService().likeShot(id, access_token)
                .compose(RxHelper.singleModeThread())
                .subscribe(observer)
    }

    override fun getComments(id: Long, access_token: String, page: Int?, observer: NetObserver<MutableList<Comment>>) {
        getNetService().getComments(id, access_token, page).
                compose(RxHelper.listModeThread()).
                subscribe(observer)
    }
}