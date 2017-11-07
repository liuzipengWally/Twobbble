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
import com.twobbble.entity.Shot
import com.twobbble.tools.ImageLoad
import com.twobbble.tools.Utils
import com.twobbble.tools.hasNavigationBar
import kotlinx.android.synthetic.main.item_card_bottom.view.*
import kotlinx.android.synthetic.main.item_card_head.view.*
import kotlinx.android.synthetic.main.item_shots.view.*
import kotlinx.android.synthetic.main.pull_up_load_layout.view.*


/**
 * Created by liuzipeng on 2017/2/22.
 */
class ItemShotAdapter(var mShots: MutableList<Shot>, val itemClick: (Int) -> Unit, val userClick: (Int) -> Unit) : RecyclerView.Adapter<ItemShotAdapter.ViewHolder>() {
    companion object {
        val NORMAL = 0
        val LOAD_MORE = 1
        val CARD_TAP_DURATION: Long = 100
    }

    lateinit private var mLastViewHolder: ViewHolder
    private var mOldPosition = 0

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        if (viewType == NORMAL) {
            return ViewHolder(LayoutInflater.from(parent?.context).inflate(R.layout.item_shots, parent, false))
        } else {
            return ViewHolder(LayoutInflater.from(parent?.context).inflate(R.layout.pull_up_load_layout, parent, false))
        }
    }

    override fun getItemCount(): Int = mShots.size + 1

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (position == mShots.size) {
            App.instance.hasNavigationBar {
                holder.itemView.mNavigationBar.visibility = View.VISIBLE
            }
            mLastViewHolder = holder
        } else {
            holder.bindShots(mShots[position])
            holder.itemView.mItemCard.setOnClickListener {
                itemClick.invoke(position)
            }

            holder.itemView.mHeadLayout.setOnClickListener {
                userClick.invoke(position)
            }
            addCardZAnimation(holder.itemView?.mItemCard)
        }
    }

    override fun onViewAttachedToWindow(holder: ViewHolder) {
        if (holder.layoutPosition > mOldPosition) {
            addItemAnimation(holder.itemView.mItemCard)
            mOldPosition = holder.layoutPosition
        }
    }

    private fun addItemAnimation(mItemCard: CardView?) {
        mItemCard?.let {
            val translationY = ObjectAnimator.ofFloat(it, "translationY", 500f, 0f)
//        val scaleY = ObjectAnimator.ofFloat(mItemCard, "scaleY", 0.5f, 1f)
//        val set = AnimatorSet()
//        set.playTogether(scaleX, scaleY)
            translationY.duration = 500
            translationY.start()
        }
    }

    private fun addCardZAnimation(mItemCard: CardView?) {
        mItemCard?.setOnTouchListener { _, motionEvent ->
            when (motionEvent.action) {
                MotionEvent.ACTION_DOWN -> mItemCard.animate().translationZ(Utils.dp2px(24)).duration = CARD_TAP_DURATION
                MotionEvent.ACTION_UP -> mItemCard.animate().translationZ(0f).duration = CARD_TAP_DURATION
                MotionEvent.ACTION_CANCEL -> mItemCard.animate().translationZ(0f).duration = CARD_TAP_DURATION
            }
            false
        }
    }

    fun hideProgress() {
        mLastViewHolder.itemView.mLoadLayout.visibility = View.GONE
    }

    fun loadError(retryListener: () -> Unit) {
        mLastViewHolder.itemView.mRetryLoadProgress.visibility = View.GONE
        mLastViewHolder.itemView.mReTryText.visibility = View.VISIBLE
        mLastViewHolder.itemView.mLoadLayout.setOnClickListener {
            mLastViewHolder.itemView.mRetryLoadProgress.visibility = View.VISIBLE
            mLastViewHolder.itemView.mReTryText.visibility = View.GONE
            retryListener.invoke()
        }
    }

    fun getSize(): Int = mShots.size

    fun addItems(shots: MutableList<Shot>) {
        val position = mShots.size
        mShots.addAll(shots)
        notifyItemInserted(position)
    }

    fun addItem(position: Int, shot: Shot) {
        mShots.add(position, shot)
        notifyItemInserted(position)
    }

    fun deleteItem(position: Int) {
        mShots.removeAt(position)
        notifyItemRemoved(position)
    }

    override fun getItemViewType(position: Int): Int = if (position == mShots.size) LOAD_MORE else NORMAL

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindShots(shot: Shot) {
            with(shot) {
                ImageLoad.frescoLoadCircle(itemView.mAvatarImg, shot.user?.avatar_url.toString())
                ImageLoad.frescoLoadNormal(itemView.mContentImg, itemView.mImgProgress, shot.images?.normal.toString(), shot.images?.teaser.toString())
                itemView.mTitleText.text = shot.title
                itemView.mAuthorText.text = shot.user?.name
                itemView.mLikeCountText.text = shot.likes_count.toString()
                itemView.mCommentCountText.text = shot.comments_count.toString()
                itemView.mViewsCountText.text = shot.views_count.toString()
                itemView.mImgProgress.visibility = View.VISIBLE
                if (shot.animated) itemView.mGifTag.visibility = View.VISIBLE else itemView.mGifTag.visibility = View.GONE

                if (shot.rebounds_count > 0) {
                    itemView.mReboundLayout.visibility = View.VISIBLE
                    itemView.mReboundCountText.text = shot.rebounds_count.toString()
                } else itemView.mReboundLayout.visibility = View.GONE

                if (shot.attachments_count > 0) {
                    itemView.mAttachmentLayout.visibility = View.VISIBLE
                    itemView.mAttachmentCountText.text = shot.attachments_count.toString()
                } else itemView.mAttachmentLayout.visibility = View.GONE
            }
        }
    }
}