package com.twobbble.tools

import android.content.Context
import android.graphics.drawable.Animatable
import android.net.Uri
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.drawee.controller.BaseControllerListener
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder
import com.facebook.drawee.view.SimpleDraweeView
import com.facebook.imagepipeline.image.ImageInfo
import com.facebook.imagepipeline.request.ImageRequest
import com.facebook.imagepipeline.request.ImageRequestBuilder
import com.twobbble.R
import com.twobbble.application.App
import com.twobbble.view.customview.CustomProgressBar
import jp.wasabeef.glide.transformations.CropCircleTransformation


/**
 * Created by liuzipeng on 2017/2/22.
 */
class ImageLoad {
    companion object {
        fun glideLoadNormal(context: Context, imageView: ImageView, url: String) {
            Glide.with(context).load(url).thumbnail(0.1f).placeholder(R.drawable.img_default).
                    error(R.mipmap.img_network_error_2).diskCacheStrategy(DiskCacheStrategy.ALL)
                    .crossFade().centerCrop().into(imageView)
        }

        fun glideLoadGif(context: Context, imageView: ImageView, url: String) {
            Glide.with(context).load(url).asGif().thumbnail(0.1f).placeholder(R.drawable.img_default).
                    error(R.mipmap.img_network_error_2).diskCacheStrategy(DiskCacheStrategy.ALL)
                    .crossFade().centerCrop().into(imageView)
        }

        fun glideLoadCircle(context: Context, imageView: ImageView, url: String) {
            Glide.with(context).load(url).placeholder(R.mipmap.ic_user_placeholder).diskCacheStrategy(DiskCacheStrategy.ALL)
                    .crossFade().bitmapTransform(CropCircleTransformation(context)).into(imageView)
        }

        fun frescoLoadCircle(drawees: SimpleDraweeView, url: String) {
            drawees.setImageURI(url)
        }

        fun frescoLoadNormal(drawees: SimpleDraweeView, progressBar: ProgressBar, urlNormal: String, urlLow: String?) {
            val builder = GenericDraweeHierarchyBuilder(App.instance.resources)
            val hierarchy = builder
                    .setPlaceholderImage(R.drawable.img_default)
                    .setFailureImage(R.mipmap.img_load_failed)
                    .setRetryImage(R.mipmap.img_load_failed)
//                    .setProgressBarImage(CustomProgressBar(progressBar))
                    .build()
            drawees.hierarchy = hierarchy

            val request = ImageRequestBuilder.newBuilderWithSource(Uri.parse(urlNormal))
                    .setProgressiveRenderingEnabled(true)
                    .build()

            val controller = Fresco.newDraweeControllerBuilder()
                    .setLowResImageRequest(ImageRequest.fromUri(urlLow))
                    .setTapToRetryEnabled(true)
                    .setImageRequest(request)
                    .setControllerListener(object : BaseControllerListener<ImageInfo>() {
                        override fun onFailure(id: String?, throwable: Throwable?) {
                            super.onFailure(id, throwable)
                            progressBar.visibility = View.GONE
                        }

                        override fun onFinalImageSet(id: String?, imageInfo: ImageInfo?, animatable: Animatable?) {
                            super.onFinalImageSet(id, imageInfo, animatable)
                            progressBar.visibility = View.GONE
                        }
                    })
                    .setOldController(drawees.controller)
                    .build()
            drawees.controller = controller
        }
    }
}