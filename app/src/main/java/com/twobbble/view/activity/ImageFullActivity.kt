package com.twobbble.view.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.twobbble.R
import com.twobbble.tools.Constant
import com.twobbble.tools.DownloadUtils
import com.twobbble.tools.ImageLoad
import kotlinx.android.synthetic.main.activity_image_full.*
import java.io.File

class ImageFullActivity : AppCompatActivity() {
    companion object {
        val KEY_URL_NORMAL: String = "key_url_normal"
        val KEY_URL_LOW: String = "KEY_URL_LOW"
        val KEY_TITLE: String = "key_title"
    }

    private var mUrlNormal: String? = null
    private var mUrlLow: String? = null
    private var mTitle: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_full)
        mUrlNormal = intent.getStringExtra(KEY_URL_NORMAL)
        mUrlLow = intent.getStringExtra(KEY_URL_LOW)
        mTitle = intent.getStringExtra(KEY_TITLE)
        initView()
    }

    private fun initView() {
        toolbar.inflateMenu(R.menu.image_full_menu)
        if (mUrlNormal != null)
            ImageLoad.frescoLoadZoom(mImage, mProgress, mUrlNormal.toString(), mUrlLow, true)
    }

    override fun onStart() {
        super.onStart()
        bindEvent()
    }

    private fun bindEvent() {
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        toolbar.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.mDownload -> {
                    val urls = mUrlNormal?.split(".")
                    DownloadUtils.DownloadImg(mUrlNormal.toString(),
                            "${Constant.IMAGE_DOWNLOAD_PATH}${File.separator}$mTitle.${urls!![urls.size - 1]}")
                }
            }
            true
        }
    }
}
