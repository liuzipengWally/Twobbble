package com.twobbble.view.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.twobbble.R
import com.twobbble.application.App
import com.twobbble.entity.ShotList
import com.twobbble.tools.ImageLoad
import com.twobbble.tools.log
import kotlinx.android.synthetic.main.item_card_bottom.view.*
import kotlinx.android.synthetic.main.item_card_head.view.*
import kotlinx.android.synthetic.main.item_shots.view.*

/**
 * Created by liuzipeng on 2017/2/22.
 */
class ItemShotAdapter(val mShotList: List<ShotList>) : RecyclerView.Adapter<ItemShotAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent?.context).inflate(R.layout.item_shots, parent, false))
    }

    override fun getItemCount(): Int = mShotList.size

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        holder?.bindShots(mShotList[position])
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindShots(shotList: ShotList) {
            with(shotList) {
                ImageLoad.loadCircle(itemView.mAvatarImg, shotList.user?.avatar_url.toString())
                ImageLoad.loadNormal(itemView.mContentImg, shotList.images?.normal.toString())
                itemView.mTitleText.text = shotList.title
                itemView.mAuthorText.text = shotList.user?.name
                itemView.mLikeCountText.text = shotList.likes_count.toString()
                itemView.mCommentCountText.text = shotList.comments_count.toString()
                itemView.mViewsCountText.text = shotList.views_count.toString()
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