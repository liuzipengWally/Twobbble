package com.twobbble.presenter

import com.twobbble.biz.api.IDetailsBiz
import com.twobbble.biz.impl.DetailsBiz
import com.twobbble.entity.Comment
import com.twobbble.tools.NetSubscriber
import com.twobbble.view.api.IDetailsView
import org.jetbrains.annotations.NotNull

/**
 * Created by liuzipeng on 2017/3/1.
 */
class DetailsPresenter(val mDetailsView: IDetailsView) : BasePresenter() {
    private var mDetailsBiz: IDetailsBiz? = null

    init {
        mDetailsBiz = DetailsBiz()
    }

    fun getComments(@NotNull id: Long, token: String, page: Int?) {
        val subscribe = mDetailsBiz?.getComments(id, token, page, object : NetSubscriber<MutableList<Comment>>(mDetailsView) {
            override fun onFailed(msg: String) {
                mDetailsView.getCommentsFailed(msg)
            }

            override fun onNext(t: MutableList<Comment>?) {
                mDetailsView.getCommentsSuccess(t)
            }
        })

        mSubscription?.add(subscribe)
    }
}