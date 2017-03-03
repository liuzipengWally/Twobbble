package com.twobbble.tools

import android.Manifest
import com.liulishuo.filedownloader.BaseDownloadTask
import com.liulishuo.filedownloader.FileDownloadListener
import android.R.attr.path
import com.liulishuo.filedownloader.FileDownloader
import com.tbruyelle.rxpermissions.RxPermissions
import com.twobbble.application.App
import rx.functions.Action1
import java.io.File


/**
 * Created by liuzipeng on 2017/3/1.
 */
class DownloadUtils {
    companion object {
        fun DownloadImg(url: String, path: String) {
            RxPermissions.getInstance(App.instance).request(Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE).
                    subscribe({ aBoolean ->
                        if (aBoolean) {
                            download(url, path)
                        }
                    }, Throwable::printStackTrace)
        }

        private fun download(url: String, path: String) {
            log(path)
            FileDownloader.getImpl().create(url)
                    .setPath(path)
                    .setListener(object : FileDownloadListener() {
                        override fun pending(task: BaseDownloadTask, soFarBytes: Int, totalBytes: Int) {}

                        override fun connected(task: BaseDownloadTask?, etag: String?, isContinue: Boolean, soFarBytes: Int, totalBytes: Int) {}

                        override fun progress(task: BaseDownloadTask, soFarBytes: Int, totalBytes: Int) {

                        }

                        override fun blockComplete(task: BaseDownloadTask?) {}

                        override fun retry(task: BaseDownloadTask?, ex: Throwable?, retryingTimes: Int, soFarBytes: Int) {}

                        override fun completed(task: BaseDownloadTask) {
                            toast("下载完成")
                        }

                        override fun paused(task: BaseDownloadTask, soFarBytes: Int, totalBytes: Int) {}

                        override fun error(task: BaseDownloadTask, e: Throwable) {
                            toast("下载失败")
                        }

                        override fun warn(task: BaseDownloadTask) {}
                    }).start()
        }
    }
}