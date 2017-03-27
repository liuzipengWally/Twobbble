package com.twobbble.view.fragment

import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceFragment
import android.preference.PreferenceScreen
import com.tencent.bugly.beta.Beta
import com.twobbble.R
import com.twobbble.tools.RxHelper
import com.twobbble.tools.Utils
import com.twobbble.tools.toast
import com.twobbble.view.activity.LicenseActivity
import rx.Observable
import java.io.File

/**
 * Created by liuzipeng on 2017/3/16.
 */
class SettingsFragment : PreferenceFragment() {
    val KEY_VERSION = "version"
    val KEY_CLEAR_CACHE = "clearCache"
    val KEY_LICENSE = "license"

    private var mVersionItem: PreferenceScreen? = null
    private var mClearItem: PreferenceScreen? = null
    private var mLicenseItem: PreferenceScreen? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addPreferencesFromResource(R.xml.preference)
        init()
    }

    private fun init() {
        mLicenseItem = findPreference(KEY_LICENSE) as PreferenceScreen
        mVersionItem = findPreference(KEY_VERSION) as PreferenceScreen
        mVersionItem?.summary = Utils.getVersion(activity)
        mClearItem = findPreference(KEY_CLEAR_CACHE) as PreferenceScreen
        Observable.create<String> { subscribe ->
            subscribe.onNext(Utils.formatFileSize(getFolderSize(activity.externalCacheDir).toDouble()))
            subscribe.onCompleted()
        }.compose(RxHelper.singleModeThreadNormal())
                .subscribe({ size ->
                    mClearItem?.summary = size
                }, Throwable::printStackTrace)
    }

    // 获取文件大小
    //Context.getExternalFilesDir() --> SDCard/Android/data/你的应用的包名/files/ 目录，一般放一些长时间保存的数据
    //Context.getExternalCacheDir() --> SDCard/Android/data/你的应用包名/cache/目录，一般存放临时缓存数据
    fun getFolderSize(file: File): Long {
        var size: Long = 0
        try {
            val fileList = file.listFiles()
            for (i in fileList.indices) {
                // 如果下面还有文件
                if (fileList[i].isDirectory) {
                    size += getFolderSize(fileList[i])
                } else {
                    size += fileList[i].length()
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return size
    }

    override fun onStart() {
        super.onStart()
        bindEvent()
    }

    private fun bindEvent() {
        mVersionItem?.setOnPreferenceClickListener {
            Beta.checkUpgrade(true, false)
            true
        }

        mClearItem?.setOnPreferenceClickListener {
            Utils.deleteFolderFile(activity.externalCacheDir.toString(), true)
            mClearItem?.summary = "0MB"
            toast(R.string.clear_success)
            true
        }

        mLicenseItem?.setOnPreferenceClickListener {
            startActivity(Intent(activity, LicenseActivity::class.java))
            true
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}