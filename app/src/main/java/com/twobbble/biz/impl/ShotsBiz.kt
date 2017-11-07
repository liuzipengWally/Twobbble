package com.twobbble.biz.impl

import com.twobbble.biz.api.IShotsBiz
import com.twobbble.entity.Shot
import com.twobbble.tools.NetObserver
import com.twobbble.tools.RxHelper
import org.jetbrains.annotations.NotNull

/**
 * Created by liuzipeng on 2017/2/22.
 */
class ShotsBiz : IShotsBiz, BaseBiz() {
    override fun getShots(@NotNull access_token: String,
                          list: String?,
                          timeframe: String?,
                          sort: String?,
                          page: Int?,
                          observer: NetObserver<MutableList<Shot>>) {
        getNetService().getShots(access_token, list, timeframe, sort, page).
                compose(RxHelper.listModeThread()).
                subscribe(observer)
    }
}