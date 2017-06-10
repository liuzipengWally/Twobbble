package com.twobbble.view.activity

import android.animation.Animator
import android.app.ActivityOptions
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.BottomSheetBehavior
import android.support.design.widget.BottomSheetDialog
import android.support.design.widget.BottomSheetDialogFragment
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewAnimationUtils
import android.view.animation.AccelerateInterpolator
import android.view.animation.OvershootInterpolator
import com.jakewharton.rxbinding.view.RxView
import com.jakewharton.rxbinding.widget.RxTextView
import com.twobbble.R
import com.twobbble.entity.Comment
import com.twobbble.entity.Shot
import com.twobbble.event.HideBucketEvent
import com.twobbble.presenter.DetailsPresenter
import com.twobbble.tools.*
import com.twobbble.view.adapter.CommentAdapter
import com.twobbble.view.api.IDetailsView
import com.twobbble.view.fragment.BucketsFragment
import kotlinx.android.synthetic.main.activity_details.*
import kotlinx.android.synthetic.main.comment_layout.*
import kotlinx.android.synthetic.main.count_info_layout.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.io.File
import java.util.concurrent.TimeUnit

class DetailsActivity : BaseActivity(), IDetailsView {
    private var mPresenter: DetailsPresenter? = null
    private var mId: Long = 0
    private var mAdapter: CommentAdapter? = null
    private var mComments: MutableList<Comment>? = null
    private var mShot: Shot? = null
    private var mLiked: Boolean = false
    private var mBucketFragment: BucketsFragment? = null
    private var bucketIsShowing = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        init()
        initView()
        EventBus.getDefault().register(this)
    }

    private fun init() {
        mPresenter = DetailsPresenter(this)
    }

    private fun initView() {
        if (singleData.isLogin()) {
            mCommentEdit.isFocusableInTouchMode = true
            mCommentEdit.isFocusable = true
        }
        toolbar.inflateMenu(R.menu.details_menu)
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        mRecycler.layoutManager = layoutManager
        mRecycler.itemAnimator = DefaultItemAnimator()
    }

    override fun onBackPressed() {
        if (bucketIsShowing) {
            hideBuckets()
        } else {
            mFavoriteBtn.visibility = View.GONE
            super.onBackPressed()
        }
    }

    override fun onStart() {
        super.onStart()
        bindEvent()
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
        mPresenter?.unSubscriber()
    }

    private fun bindEvent() {
        toolbar.setNavigationOnClickListener { onBackPressed() }

        mContentImg.setOnClickListener {
            val intent = Intent(this, ImageFullActivity::class.java)
            intent.putExtra(ImageFullActivity.KEY_TITLE, mShot?.title)
            val urlNormal: String = mShot?.images?.hidpi ?: mShot?.images?.normal!!
            intent.putExtra(ImageFullActivity.KEY_URL_NORMAL, urlNormal)
            if (mShot?.images?.hidpi != null) {
                intent.putExtra(ImageFullActivity.KEY_URL_LOW, mShot?.images?.normal)
            } else {
                intent.putExtra(ImageFullActivity.KEY_URL_LOW, mShot?.images?.teaser)
            }
            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
        }

        toolbar.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.mDownload -> {
                    val url = mShot?.images?.hidpi ?: mShot?.images?.normal
                    val urls = url?.split(".")
                    DownloadUtils.DownloadImg(url.toString(),
                            "${Constant.IMAGE_DOWNLOAD_PATH}${File.separator}${mShot?.title}.${urls!![urls.size - 1]}")
                }
                R.id.mOpenInBrowser -> openLink(mShot?.html_url!!)
                R.id.mShare -> share()
                R.id.mAddBucket -> showBuckets()
            }
            true
        }

        mFavoriteBtn.setOnClickListener {
            if (singleData.isLogin()) {
                if (!mLiked) {
                    mPresenter?.likeShot(mShot?.id!!, singleData.token!!)
                    mLiked = true
                } else {
                    mPresenter?.unlikeShot(mShot?.id!!, singleData.token!!)
                    mLiked = false
                }

                FavoriteAnimation()
            } else showSnackBar(mRootLayout, resources.getString(R.string.not_logged))
        }

        //未登录的时候不允许用户在评论输入框输入内容，并弹出提示
        mCommentEdit.setOnClickListener {
            if (!singleData.isLogin()) {
                showSnackBar(mRootLayout, resources.getString(R.string.not_logged))
            }
        }

        RxTextView.textChanges(mCommentEdit).subscribe({ text ->
            if (text.isNotEmpty()) {
                mSendBtn.setImageResource(R.drawable.ic_send_enable_24dp)
                mSendBtn.isClickable = true
            } else {
                mSendBtn.setImageResource(R.drawable.ic_send_disable_24dp)
                mSendBtn.isClickable = false
            }
        })

        RxView.clicks(mSendBtn).throttleFirst(200, TimeUnit.MILLISECONDS).subscribe {
            Utils.hideKeyboard(mCommentEdit)
            mPresenter?.createComment(mShot!!.id, singleData.token!!, mCommentEdit.text.toString())
        }
    }

    private fun showBuckets() {
        val cx = screenWidth / 2
        val cy = screenHeight / 2
        val dx = Math.max(cx, screenWidth - cx)
        val dy = Math.max(cy, screenHeight - cy)
        val finalRadius = Math.hypot(dx.toDouble(), dy.toDouble()).toFloat()
        val animator = ViewAnimationUtils.createCircularReveal(mAddLayout, cx, cy, 0f, finalRadius)
        animator.interpolator = AccelerateInterpolator()
        animator.duration = 300
        mAddLayout.visibility = View.VISIBLE
        mAddLayout.alpha = 1F
        animator.start()
        bucketIsShowing = true
    }

    private fun hideBuckets() {
        val cx = screenWidth / 2
        val cy = screenHeight / 2
        val dx = Math.max(cx, screenWidth - cx)
        val dy = Math.max(cy, screenHeight - cy)
        val finalRadius = Math.hypot(dx.toDouble(), dy.toDouble()).toFloat()
        val animator = ViewAnimationUtils.createCircularReveal(mAddLayout, cx, cy, finalRadius, 0f)
        animator.interpolator = AccelerateInterpolator()
        animator.duration = 300
        animator.start()
        animator.addListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(p0: Animator?) {}
            override fun onAnimationCancel(p0: Animator?) {}
            override fun onAnimationStart(p0: Animator?) {}
            override fun onAnimationEnd(p0: Animator?) {
                mAddLayout.visibility = View.GONE
                mAddLayout.alpha = 0f
                bucketIsShowing = false
            }
        })
    }

    private fun FavoriteAnimation() {
        mFavoriteBtn.animate()
                .scaleY(2f)
                .setDuration(300)
                .setInterpolator(OvershootInterpolator())
                .setListener(object : Animator.AnimatorListener {
                    override fun onAnimationRepeat(p0: Animator?) {}
                    override fun onAnimationEnd(p0: Animator?) {
                        if (mLiked) {
                            mFavoriteBtn.setImageResource(R.drawable.ic_favorite_black_24dp)
                        } else {
                            mFavoriteBtn.setImageResource(R.drawable.ic_favorite_border_light_24dp)
                        }

                        mFavoriteBtn.animate()
                                .scaleY(1f)
                                .duration = 300
                    }

                    override fun onAnimationCancel(p0: Animator?) {}
                    override fun onAnimationStart(p0: Animator?) {}
                })
    }

    private fun openLink(url: String) {
        val intent = Intent()
        intent.action = Intent.ACTION_VIEW
        intent.data = Uri.parse(url)
        startActivity(intent)
    }

    private fun share() {
        val sendIntent = Intent()
        sendIntent.action = Intent.ACTION_SEND
        sendIntent.type = "text/plain"
        sendIntent.putExtra(Intent.EXTRA_TEXT,
                "我分享了${mShot?.user?.name}的作品《${mShot?.title}》\n ${mShot?.html_url}\n来自@${resources.getString(R.string.app_name)}")
        sendIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(Intent.createChooser(sendIntent, resources.getString(R.string.share)))
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    fun obtainShot(shot: Shot) {
        mId = shot.id
        mShot = shot
        if (singleData.isLogin()) {
            mPresenter?.checkIfLikeShot(shot.id, singleData.token.toString())
        }
        mountData(shot)
        getComments()
        ImageLoad.frescoLoadCircle(mCommentAvatarImg, singleData.avatar.toString())
//        EventBus.getDefault().removeStickyEvent(shot)
        if (mBucketFragment == null) {
            mBucketFragment = BucketsFragment.newInstance(BucketsFragment.ADD_SHOT, mShot!!.id)
            fragmentManager.beginTransaction().add(R.id.mAddLayout, mBucketFragment).commit()
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun hideBucketEvent(hideBucketEvent: HideBucketEvent) {
        hideBuckets()
    }

    private fun getComments() {
        val token = singleData.token ?: Constant.ACCESS_TOKEN
        mPresenter?.getComments(mId, token, null)
    }

    private fun mountData(shot: Shot) {
        val urlNormal: String = shot.images?.hidpi ?: shot.images?.normal!!
        ImageLoad.frescoLoadNormal(mContentImg, mProgress,
                urlNormal,
                shot.images?.teaser.toString(), true)

        mComments = mutableListOf(Comment())
        mAdapter = CommentAdapter(shot, mComments!!,
                userClick = { _, i ->
                    EventBus.getDefault().postSticky(mComments!![i].user)
                    startActivity(Intent(this, UserActivity::class.java))
                }, likeClick = { _, _ ->
            //TODO 评论中的喜欢点击事件
        }, authorClick = {
            EventBus.getDefault().postSticky(shot.user)
            startActivity(Intent(this, UserActivity::class.java))
        }, commentHintClick = {
            // 评论中加载状态提示的点击事件
            mAdapter?.hideCommentHint()
            getComments()
        }, countClick = {
            //TODO 数据统计栏的各个按钮的点击事件
        }, tagClick = {
            //TODO 标签的点击事件
        })

        mRecycler.adapter = mAdapter
    }

    override fun onResume() {
        super.onResume()
    }

    override fun showProgress() {
        mAdapter?.showProgress()
    }

    override fun hideProgress() {
        mAdapter?.hideProgress()
    }

    override fun getCommentsSuccess(Comments: MutableList<Comment>?) {
        if (Comments != null && Comments.isNotEmpty()) {
            mAdapter?.addItems(Comments)
        } else {
            mAdapter?.showCommentHint(R.string.no_comment)
        }
    }

    override fun addCommentSuccess(comment: Comment?) {
        if (comment != null) {
            mAdapter?.addItem(mComments?.size!!, comment)
        }
    }

    override fun showSendProgress() {
        mSendBtn.animate().scaleX(0f).scaleY(0f).duration = 100
        mSendProgress.animate().scaleX(1f).scaleY(1f).duration = 200
    }

    override fun hideSendProgress() {
        mSendProgress.animate().scaleX(0f).scaleY(0f).duration = 100
        mSendBtn.animate().scaleX(1f).scaleY(1f).duration = 200
    }

    override fun getCommentsFailed(msg: String) {
        showSnackBar(mRootLayout, msg)
        mAdapter?.showCommentHint(R.string.click_retry)
    }

    override fun addCommentFailed(msg: String) {
        showSnackBar(mRootLayout, msg)
    }

    override fun likeShotSuccess() {
        showSnackBar(mRootLayout, resources.getString(R.string.like_success))
        mLikeCountText.text = "${mLikeCountText.text.toString().toInt() + 1}"
    }

    override fun likeShotFailed(msg: String) {
        showSnackBar(mRootLayout, msg)
    }

    override fun checkIfLikeSuccess() {
        mLiked = true
        mFavoriteBtn.setImageResource(R.drawable.ic_favorite_black_24dp)
//        showFab()
    }

    override fun checkIfLikeFailed() {
        mFavoriteBtn.setImageResource(R.drawable.ic_favorite_border_light_24dp)
//        showFab()
    }

    override fun unLikeShotSuccess() {
        showSnackBar(mRootLayout, resources.getString(R.string.unlike_success))
        mLikeCountText.text = "${mLikeCountText.text.toString().toInt() - 1}"
    }

    override fun unLikeShotFailed(msg: String) {
        showSnackBar(mRootLayout, msg)
    }

    private fun showFab() {
        mFavoriteBtn.visibility = View.VISIBLE
        mFavoriteBtn.animate()
                .scaleX(1f)
                .scaleY(1f)
                .setDuration(300).interpolator = OvershootInterpolator()
    }
}
