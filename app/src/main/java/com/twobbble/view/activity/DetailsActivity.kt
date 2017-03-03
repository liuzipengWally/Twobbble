package com.twobbble.view.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.widget.NestedScrollView
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.twobbble.R
import com.twobbble.R.id.mTitleText
import com.twobbble.entity.Shot
import com.twobbble.entity.Comment
import com.twobbble.presenter.DetailsPresenter
import com.twobbble.tools.*
import com.twobbble.view.adapter.CommentAdapter
import com.twobbble.view.api.IDetailsView
import com.twobbble.view.customview.CommentDivider
import com.zhy.view.flowlayout.FlowLayout
import com.zhy.view.flowlayout.TagAdapter
import kotlinx.android.synthetic.main.activity_details.*
import kotlinx.android.synthetic.main.comment_layout.*
import kotlinx.android.synthetic.main.count_info_layout.*
import kotlinx.android.synthetic.main.details_user.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.jetbrains.annotations.NotNull
import java.io.File

@Suppress("DEPRECATION")
class DetailsActivity : BaseActivity(), IDetailsView {
    private var mPresenter: DetailsPresenter? = null
    private var mId: Long? = null
    private var mAdapter: CommentAdapter? = null
    private var mComments: MutableList<Comment>? = null
    private var mShot: Shot? = null

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
        toolbar.inflateMenu(R.menu.details_menu)
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        mRecycler.layoutManager = layoutManager
        mRecycler.itemAnimator = DefaultItemAnimator()
    }

    override fun onStart() {
        super.onStart()
        bindEvent()
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

    private fun bindEvent() {
        toolbar.setNavigationOnClickListener { finish() }

        mContentImg.setOnClickListener {
            val intent = Intent(this, ImageFullActivity::class.java)
            intent.putExtra(ImageFullActivity.KEY_TITLE, mShot?.title)
            val urlNormal: String = mShot?.images?.hidpi ?: mShot?.images?.normal!!
            intent.putExtra(ImageFullActivity.KEY_URL_NORMAL, urlNormal)
            intent.putExtra(ImageFullActivity.KEY_URL_LOW, mShot?.images?.teaser)
            startActivity(intent)
        }

        toolbar.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.mDownload -> {
                    val urls = mShot?.images?.hidpi?.split(".")
                    DownloadUtils.DownloadImg(mShot?.images?.hidpi.toString(),
                            "${Constant.IMAGE_DOWNLOAD_PATH}${File.separator}${mShot?.title}.${urls!![urls.size - 1]}")
                }
                R.id.mOpenInBrowser -> openLink(mShot?.html_url!!)
                R.id.mShare -> share()
                R.id.mAddBucket -> {
                }
            }
            true
        }
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
        sendIntent.putExtra(Intent.EXTRA_TEXT, "我分享了${mShot?.user?.name}的作品《${mShot?.title}》\n ${mShot?.html_url}\n来自@${resources.getString(R.string.app_name)}")
        sendIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(Intent.createChooser(sendIntent, resources.getString(R.string.share)))
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true) fun obtainShot(shot: Shot) {
        mId = shot.id
        mShot = shot
        mountData(shot)
        getComments()
//        EventBus.getDefault().removeStickyEvent(shot)
    }

    private fun getComments() {
        val token = mSimpleIo?.getString(Constant.KEY_TOKEN) ?: Constant.ACCESS_TOKEN
        mPresenter?.getComments(mId!!, token, null)
    }

    private fun mountData(shot: Shot) {
        val urlNormal: String = shot.images?.hidpi ?: shot.images?.normal!!
        ImageLoad.frescoLoadNormal(mContentImg, mProgress,
                urlNormal,
                shot.images?.teaser.toString(), true)

        mComments = mutableListOf(Comment())
        mAdapter = CommentAdapter(shot, mComments!!, userClick = { view, i ->
            //TODO 评论中的用户头像点击事件
        }, likeClick = { view, i ->
            //TODO 评论中的喜欢点击事件
        }, authorClick = {
            //TODO 作者栏点击事件
        }, commentHintClick = {
            // 评论中加载状态提示的点击事件
            mAdapter?.hideCommentHint()
            getComments()
        }, countClick = { type ->
            //TODO 数据统计栏的各个按钮的点击事件
        }, tagClick = { i ->
            //TODO 标签的点击事件
        })

        mRecycler.adapter = mAdapter
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

    override fun addCommentSuccess(Comments: MutableList<Comment>) {

    }

    override fun getCommentsFailed(msg: String) {
        showSnackBar(mRootLayout, msg)
        mAdapter?.showCommentHint(R.string.click_retry)
    }

    override fun addCommentFailed(msg: String) {
        showSnackBar(mRootLayout, msg)
    }
}
