package com.twobbble.view.activity

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import com.twobbble.R
import com.twobbble.application.App
import com.twobbble.entity.Shot
import com.twobbble.presenter.BucketShotsPresenter
import com.twobbble.tools.hideErrorImg
import com.twobbble.tools.showErrorImg
import com.twobbble.tools.toast
import com.twobbble.view.adapter.ItemShotAdapter
import com.twobbble.view.api.IBucketShotsView
import com.twobbble.view.customview.ItemSwipeRemoveCallback
import kotlinx.android.synthetic.main.activity_bucket_shots.*
import kotlinx.android.synthetic.main.error_layout.*
import kotlinx.android.synthetic.main.list.*
import org.greenrobot.eventbus.EventBus
import org.jetbrains.anko.doAsync

class BucketShotsActivity : BaseActivity(), IBucketShotsView {
    private var mId: Long = 0
    private var mTitle: String? = null
    private val mPresenter: BucketShotsPresenter by lazy {
        BucketShotsPresenter(this)
    }
    private var isLoading: Boolean = false
    private var mPage: Int = 1
    lateinit private var mShots: MutableList<Shot>
    private var mListAdapter: ItemShotAdapter? = null
    private var mDelPosition: Int = 0
    private lateinit var mDelShot: Shot

    companion object {
        val KEY_ID = "id"
        val KEY_TITLE = "title"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bucket_shots)
        mId = intent.getLongExtra(KEY_ID, 0)
        mTitle = intent.getStringExtra(KEY_TITLE)
        init()
        getShots()
    }

    private fun init() {
        Toolbar.title = mTitle
        mRefresh.setColorSchemeResources(R.color.google_red, R.color.google_yellow, R.color.google_green, R.color.google_blue)
        val layoutManager = LinearLayoutManager(App.instance, LinearLayoutManager.VERTICAL, false)
        mRecyclerView.layoutManager = layoutManager
        mRecyclerView.itemAnimator = DefaultItemAnimator()
    }

    fun getShots(isLoadMore: Boolean = false) {
        isLoading = true
        mPresenter.getBucketShots(id = mId, page = mPage, isLoadMore = isLoadMore)
    }

    override fun onStart() {
        super.onStart()
        bindEvent()
    }

    private fun bindEvent() {
        Toolbar.setNavigationOnClickListener { finish() }

        mRefresh.setOnRefreshListener {
            mPage = 1
            getShots(false)
        }

        mErrorLayout.setOnClickListener {
            hideErrorImg(mErrorLayout)
            getShots(false)
        }

        mRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                val linearManager = recyclerView?.layoutManager as LinearLayoutManager
                val position = linearManager.findLastVisibleItemPosition()
                if (mShots.isNotEmpty() && position == mShots.size) {
                    if (!isLoading) {
                        mPage += 1
                        getShots(true)
                    }
                }
            }
        })

        val itemTouchHelper = ItemTouchHelper(ItemSwipeRemoveCallback { delPosition, _ ->
            mDelShot = mShots[delPosition]
            mListAdapter?.deleteItem(delPosition)
            mDelPosition = delPosition
            mDialogManager.showOptionDialog(
                    resources.getString(R.string.delete_shot),
                    resources.getString(R.string.whether_to_delete_shot_from_bucket),
                    confirmText = resources.getString(R.string.delete),
                    onConfirm = {
                        mPresenter.removeShotFromBucket(id = mId, shot_id = mDelShot.id)
                    }, onCancel = {
                mListAdapter?.addItem(delPosition, mDelShot)
                mRecyclerView.scrollToPosition(delPosition)
            })
        })
        itemTouchHelper.attachToRecyclerView(mRecyclerView)
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.unSubscriber()
    }

    override fun showProgress() {
        if (mListAdapter == null) mRefresh.isRefreshing = true
    }

    override fun hideProgress() {
        mRefresh.isRefreshing = false
    }

    override fun showProgressDialog(msg: String?) {
        mDialogManager.showCircleProgressDialog()
    }

    override fun hideProgressDialog() {
        mDialogManager.dismissAll()
    }

    override fun getShotSuccess(shots: MutableList<Shot>?, isLoadMore: Boolean) {
        isLoading = false
        if (shots != null && shots.isNotEmpty()) {
            if (!isLoadMore) {
                mountList(shots)
            } else {
                mListAdapter?.addItems(shots)
            }
        } else {
            if (!isLoadMore) {
                showErrorImg(mErrorLayout, imgResID = R.mipmap.img_empty_buckets)
            } else {
                mListAdapter?.hideProgress()
            }
        }
    }

    private fun mountList(shots: MutableList<Shot>) {
        mShots = shots
        mListAdapter = ItemShotAdapter(mShots, {
            EventBus.getDefault().postSticky(mShots[it])
            startActivity(Intent(this, DetailsActivity::class.java),
                    ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
        }, {
            EventBus.getDefault().postSticky(shots[it].user)
            startActivity(Intent(applicationContext, UserActivity::class.java))
        })
        mRecyclerView.adapter = mListAdapter
    }

    override fun getShotFailed(msg: String, isLoadMore: Boolean) {
        isLoading = false
        toast(msg)
        if (!isLoadMore) {
            if (mListAdapter == null)
                showErrorImg(mErrorLayout, msg, R.mipmap.img_network_error_2)
        } else {
            mListAdapter?.loadError {
                getShots(true)
            }
        }
    }

    override fun removeShotSuccess() {
        toast(R.string.delete_success)
    }

    override fun removeShotFailed(msg: String) {
        toast("${resources.getString(R.string.delete_success)}:$msg")
        mListAdapter?.addItem(mDelPosition, mDelShot)
        mRecyclerView.scrollToPosition(mDelPosition)
    }
}
