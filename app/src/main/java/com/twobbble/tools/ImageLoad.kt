package com.twobbble.tools

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.twobbble.R
import com.twobbble.application.App
import jp.wasabeef.glide.transformations.CropCircleTransformation

/**
 * Created by liuzipeng on 2017/2/22.
 */
class ImageLoad {
    companion object {
        fun loadNormal(imageView: ImageView, url: String) {
            Glide.with(App.instance).load(url).placeholder(R.drawable.img_default).diskCacheStrategy(DiskCacheStrategy.ALL)
                    .crossFade().centerCrop().into(imageView)
        }

        fun loadCircle(imageView: ImageView, url: String) {
            Glide.with(App.instance).load(url).placeholder(R.mipmap.ic_user_placeholder).diskCacheStrategy(DiskCacheStrategy.ALL)
                    .crossFade().bitmapTransform(CropCircleTransformation(App.instance)).into(imageView)
        }
    }
}