package com.twobbble.view.adapter

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.twobbble.R
import com.twobbble.application.App
import com.twobbble.entity.Bucket
import com.twobbble.tools.Utils
import com.twobbble.tools.hasNavigationBar
import com.twobbble.tools.log
import kotlinx.android.synthetic.main.item_bucket.view.*

/**
 * Created by liuzipeng on 2017/3/9.
 */
class MyBucketsAdapter(val mBuckets: MutableList<Bucket>, val onClick: (Int) -> Unit, val onLongClick: (Int) -> Unit) : RecyclerView.Adapter<MyBucketsAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent?.context).inflate(R.layout.item_bucket, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindMyBuckets(mBuckets[position], position)
        App.instance.hasNavigationBar {
            if (position == mBuckets.size - 1) {
                val item = holder.itemView.mItemBucket
                val params = item.layoutParams as ViewGroup.MarginLayoutParams
                params.bottomMargin = Utils.dp2px(48).toInt()
                item.layoutParams = params
            }
        }
    }

    override fun getItemCount(): Int = mBuckets.size

    fun addItem(position: Int, bucket: Bucket) {
        log("增加$position")
        mBuckets.add(position, bucket)
        notifyItemInserted(position)
    }

    fun deleteItem(position: Int) {
        log("删除$position")
        mBuckets.removeAt(position)
        notifyItemRemoved(position)
    }

    override fun onViewAttachedToWindow(holder: ViewHolder) {
        super.onViewAttachedToWindow(holder)
        addItemAnimation(holder.itemView.mItemBucket)
    }

    private fun addItemAnimation(view: View?) {
        view?.let {
            val translationX = ObjectAnimator.ofFloat(it, "translationX", it.width.toFloat(), 0f)
            translationX.duration = 500
            translationX.start()
        }
    }

    fun addItems(buckets: MutableList<Bucket>) {
        val position = mBuckets.size
        mBuckets.addAll(buckets)
        notifyItemInserted(position)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindMyBuckets(bucket: Bucket, position: Int) {
            with(bucket) {
                itemView.mBucketNameText.text = name
                itemView.mShotCountText.text = "$shots_count shot"
                itemView.mTimeText.text = "Updated ${Utils.formatDateUseCh(Utils.parseISO8601(updated_at!!))}"
            }

            itemView.mItemBucket.setOnClickListener {
                onClick.invoke(position)
            }

            itemView.mItemBucket.setOnLongClickListener {
                onLongClick.invoke(position)
                true
            }
        }
    }
}