package com.twobbble.tools

import android.content.Context
import android.graphics.drawable.Animatable
import android.net.Uri
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.drawee.controller.BaseControllerListener
import com.facebook.drawee.drawable.ScalingUtils
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder
import com.facebook.drawee.view.SimpleDraweeView
import com.facebook.imagepipeline.image.ImageInfo
import com.facebook.imagepipeline.request.ImageRequest
import com.facebook.imagepipeline.request.ImageRequestBuilder
import com.twobbble.R
import com.twobbble.application.App
import me.relex.photodraweeview.PhotoDraweeView
import jp.wasabeef.glide.transformations.CropCircleTransformation
import jp.wasabeef.glide.transformations.BlurTransformation
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop


/**
 * Created by liuzipeng on 2017/2/22.
 */
object ImageLoad {
    fun frescoLoadCircle(drawees: SimpleDraweeView, url: String) {
        drawees.setImageURI(url)
    }

    fun frescoLoadNormal(drawees: SimpleDraweeView, progressBar: ProgressBar, urlNormal: String, urlLow: String?, playAnimation: Boolean = false) {
        val builder = GenericDraweeHierarchyBuilder(App.instance.resources)
        val hierarchy = builder
                .setPlaceholderImage(R.drawable.img_default)
                .setFailureImage(R.mipmap.img_load_failed)
                .setRetryImage(R.mipmap.img_load_failed)
//                    .setActualImageScaleType(ScalingUtils.ScaleType.CENTER_CROP)
                .build()
        drawees.hierarchy = hierarchy

        val request = ImageRequestBuilder.newBuilderWithSource(Uri.parse(urlNormal))
                .setProgressiveRenderingEnabled(true)
                .build()

        val controller = Fresco.newDraweeControllerBuilder()
                .setAutoPlayAnimations(playAnimation)
                .setUri(Uri.parse(urlNormal))
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

    fun frescoLoadMini(drawees: SimpleDraweeView, urlLow: String?, playAnimation: Boolean = false) {
        val builder = GenericDraweeHierarchyBuilder(App.instance.resources)
        val hierarchy = builder
                .setPlaceholderImage(R.drawable.img_default)
//                    .setActualImageScaleType(ScalingUtils.ScaleType.CENTER_CROP)
                .build()
        drawees.hierarchy = hierarchy

        val request = ImageRequestBuilder.newBuilderWithSource(Uri.parse(urlLow))
                .setProgressiveRenderingEnabled(true)
                .build()

        val controller = Fresco.newDraweeControllerBuilder()
                .setAutoPlayAnimations(playAnimation)
                .setTapToRetryEnabled(true)
                .setImageRequest(request)
                .setOldController(drawees.controller)
                .build()
        drawees.controller = controller
    }

    fun frescoLoadZoom(drawees: PhotoDraweeView, progressBar: ProgressBar, urlNormal: String, urlLow: String?, playAnimation: Boolean = false) {
        val builder = GenericDraweeHierarchyBuilder(App.instance.resources)
        val hierarchy = builder
                .setPlaceholderImage(R.drawable.img_default)
                .setFailureImage(R.mipmap.img_load_failed)
                .setRetryImage(R.mipmap.img_load_failed)
                .setActualImageScaleType(ScalingUtils.ScaleType.FIT_CENTER)
                .build()
        drawees.hierarchy = hierarchy

        val request = ImageRequestBuilder.newBuilderWithSource(Uri.parse(urlNormal))
                .setProgressiveRenderingEnabled(true)
                .build()

        val controller = Fresco.newDraweeControllerBuilder()
                .setAutoPlayAnimations(playAnimation)
                .setUri(Uri.parse(urlNormal))
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
                        if (imageInfo == null || drawees == null) {
                            return;
                        }
                        drawees.update(imageInfo.width, imageInfo.height);
                    }
                })
                .setOldController(drawees.controller)
                .build()
        drawees.controller = controller
    }

    fun loadBlurImgFromLocal(context: Context, imageView: ImageView, resId: Int) {
        Glide.with(context).load(resId)
                .bitmapTransform(BlurTransformation(context, 50), CenterCrop(context))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .crossFade()
                .into(imageView)
    }
}