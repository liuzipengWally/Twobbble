package com.twobbble.tools

import android.content.Context
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
        fun loadNormal(context: Context, imageView: ImageView, url: String) {
            Glide.with(context).load(url).thumbnail(0.1f).placeholder(R.drawable.img_default).
                    error(R.mipmap.img_network_error_2).diskCacheStrategy(DiskCacheStrategy.ALL)
                    .crossFade().centerCrop().into(imageView)
        }

        fun loadGif(context: Context, imageView: ImageView, url: String) {
            Glide.with(context).load(url).asGif().thumbnail(0.1f).placeholder(R.drawable.img_default).
                    error(R.mipmap.img_network_error_2).diskCacheStrategy(DiskCacheStrategy.ALL)
                    .crossFade().centerCrop().into(imageView)
        }

        fun loadCircle(context: Context, imageView: ImageView, url: String) {
            Glide.with(context).load(url).placeholder(R.mipmap.ic_user_placeholder).diskCacheStrategy(DiskCacheStrategy.ALL)
                    .crossFade().bitmapTransform(CropCircleTransformation(App.instance)).into(imageView)
        }
    }
}