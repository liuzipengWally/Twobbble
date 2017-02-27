package com.twobbble.view.adapter

import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.OvershootInterpolator
import com.twobbble.R
import com.twobbble.application.App
import com.twobbble.entity.ShotList
import com.twobbble.tools.ImageLoad
import com.twobbble.tools.Utils
import com.twobbble.tools.log
import kotlinx.android.synthetic.main.item_card_bottom.view.*
import kotlinx.android.synthetic.main.item_card_head.view.*
import kotlinx.android.synthetic.main.item_shots.view.*
import kotlinx.android.synthetic.main.pull_up_load_layout.view.*


/**
 * Created by liuzipeng on 2017/2/22.
 */
class ItemShotAdapter(var mShotList: MutableList<ShotList>, val listener: (View, Int) -> Unit) : RecyclerView.Adapter<ItemShotAdapter.ViewHolder>() {
    val NORMAL = 0
    val LOAD_MORE = 1
    val CARD_TAP_DURATION: Long = 300
    private var mLastViewHolder: ViewHolder? = null

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        if (viewType == NORMAL) {
            return ViewHolder(LayoutInflater.from(parent?.context).inflate(R.layout.item_shots, parent, false))
        } else {
            return ViewHolder(LayoutInflater.from(parent?.context).inflate(R.layout.pull_up_load_layout, parent, false))
        }
    }

    override fun getItemCount(): Int = mShotList.size + 1

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        if (position == mShotList.size) {
            if (Utils.hasNavigationBar(App.instance)) {
                holder?.itemView?.mNavigationBar?.visibility = View.VISIBLE
            } else {
                holder?.itemView?.mNavigationBar?.visibility = View.GONE
            }
            mLastViewHolder = holder
        } else {
            holder?.bindShots(mShotList[position])
            holder?.itemView?.mItemCard?.setOnClickListener {
                listener.invoke(holder.itemView.mItemCard!!, position)
            }

            addCardZAnimation(holder?.itemView?.mItemCard)
        }
    }

    override fun onViewAttachedToWindow(holder: ViewHolder?) {
        addItemAnimation(holder?.itemView?.mItemCard)
    }

    private fun addItemAnimation(mItemCard: CardView?) {
        val scaleX = ObjectAnimator.ofFloat(mItemCard, "scaleX", 0.5f, 1f)
        val scaleY = ObjectAnimator.ofFloat(mItemCard, "scaleY", 0.5f, 1f)
        val set = AnimatorSet()
        set.playTogether(scaleX, scaleY)
        set.duration = 500
        set.start()
    }

    private fun addCardZAnimation(mItemCard: CardView?) {
        mItemCard?.setOnTouchListener { view, motionEvent ->
            when (motionEvent.action) {
                MotionEvent.ACTION_DOWN -> mItemCard.animate().translationZ(Utils.dp2px(16, App.instance.resources.displayMetrics)).duration = CARD_TAP_DURATION
                MotionEvent.ACTION_UP -> mItemCard.animate().translationZ(0f).duration = CARD_TAP_DURATION
                MotionEvent.ACTION_CANCEL -> mItemCard.animate().translationZ(0f).duration = CARD_TAP_DURATION
            }
            false
        }
    }

    fun loadError(retryListener: () -> Unit) {
        mLastViewHolder?.itemView?.mRetryLoadProgress?.visibility = View.GONE
        mLastViewHolder?.itemView?.mReTryText?.visibility = View.VISIBLE
        mLastViewHolder?.itemView?.mLoadLayout?.setOnClickListener {
            mLastViewHolder?.itemView?.mRetryLoadProgress?.visibility = View.VISIBLE
            mLastViewHolder?.itemView?.mReTryText?.visibility = View.GONE
            retryListener.invoke()
        }
    }

    fun getSize(): Int = mShotList.size

    fun addItems(shots: MutableList<ShotList>) {
        val position = mShotList.size
        mShotList.addAll(shots)
        notifyItemInserted(position)
    }

    override fun getItemViewType(position: Int): Int = if (position == mShotList.size) LOAD_MORE else NORMAL

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindShots(shotList: ShotList) {
            with(shotList) {
                ImageLoad.frescoLoadCircle(itemView.mAvatarImg, shotList.user?.avatar_url.toString())
                ImageLoad.frescoLoadNormal(itemView.mContentImg, itemView.mImgProgress, shotList.images?.normal.toString(), shotList.images?.teaser.toString())
                itemView.mTitleText.text = shotList.title
                itemView.mAuthorText.text = shotList.user?.name
                itemView.mLikeCountText.text = shotList.likes_count.toString()
                itemView.mCommentCountText.text = shotList.comments_count.toString()
                itemView.mViewsCountText.text = shotList.views_count.toString()
                itemView.mImgProgress.visibility = View.VISIBLE
                if (shotList.animated) itemView.mGifTag.visibility = View.VISIBLE else itemView.mGifTag.visibility = View.GONE

                if (shotList.rebounds_count > 0) {
                    itemView.mReboundLayout.visibility = View.VISIBLE
                    itemView.mReboundCountText.text = shotList.rebounds_count.toString()
                } else itemView.mReboundLayout.visibility = View.GONE

                if (shotList.attachments_count > 0) {
                    itemView.mAttachmentLayout.visibility = View.VISIBLE
                    itemView.mAttachmentCountText.text = shotList.attachments_count.toString()
                } else itemView.mAttachmentLayout.visibility = View.GONE
            }
        }
    }
}