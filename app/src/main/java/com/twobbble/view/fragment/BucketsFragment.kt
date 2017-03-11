package com.twobbble.view.fragment

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.twobbble.R
import com.twobbble.application.App
import com.twobbble.entity.Bucket
import com.twobbble.event.OpenDrawerEvent
import com.twobbble.presenter.MyBucketsPresenter
import com.twobbble.tools.*
import com.twobbble.view.adapter.MyBucketsAdapter
import com.twobbble.view.api.IMyBucketsView
import com.twobbble.view.customview.CommentDivider
import com.twobbble.view.customview.ItemTouchHelperCallback
import com.twobbble.view.customview.NormalDivider
import com.twobbble.view.dialog.DialogManager
import kotlinx.android.synthetic.main.error_layout.*
import kotlinx.android.synthetic.main.fragment_buckets.*
import kotlinx.android.synthetic.main.list.*
import org.greenrobot.eventbus.EventBus


/**
 * Created by liuzipeng on 2017/2/17.
 */
class BucketsFragment : BaseFragment(), IMyBucketsView {
    private var mDialogManager: DialogManager? = null
    private var mPresenter: MyBucketsPresenter? = null
    private var mAdapter: MyBucketsAdapter? = null
    private var mBuckets: MutableList<Bucket>? = null
    private var mDelBucket: Bucket? = null

    override fun onBackPressed() {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return LayoutInflater.from(activity).inflate(R.layout.fragment_buckets, null)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        initView()
        getBuckets()
    }

    private fun init() {
        mBuckets = mutableListOf()
    }

    fun getBuckets() {
        if (singleData.isLogin()) {
            mPresenter?.getMyBuckets(
                    singleData.token!!)
        } else {
            showErrorImg(mErrorLayout, R.string.not_logged, R.mipmap.img_empty_buckets)
        }
    }

    private fun initView() {
        if (Utils.hasNavigationBar(activity)) {
            val params = mAddBtn.layoutParams as ViewGroup.MarginLayoutParams
            params.setMargins(0, 0, Utils.dp2px(16, resources.displayMetrics).toInt(), Utils.dp2px(64, resources.displayMetrics).toInt())
        }
        mPresenter = MyBucketsPresenter(this)
        mDialogManager = DialogManager(activity)
        mRefresh.setColorSchemeResources(R.color.google_red, R.color.google_yellow, R.color.google_green, R.color.google_blue)

        val layoutManager = LinearLayoutManager(App.instance, LinearLayoutManager.VERTICAL, false)
        mRecyclerView.layoutManager = layoutManager
        mRecyclerView.itemAnimator = DefaultItemAnimator()
        mRecyclerView.addItemDecoration(NormalDivider())
        val itemTouchHelper = ItemTouchHelper(ItemTouchHelperCallback { delPosition, view ->
            mDelBucket = mBuckets!![delPosition]
            mAdapter?.deleteItem(delPosition)
            mDialogManager?.showOptionDialog(
                    resources.getString(R.string.delete_bucket),
                    resources.getString(R.string.whether_to_delete),
                    confirmText = resources.getString(R.string.delete),
                    onConfirm = {
                        mPresenter?.deleteBucket(singleData.token!!, mDelBucket?.id!!)
                    }, onCancel = {
                mAdapter?.addItem(delPosition, mDelBucket!!)
            })
        })
        itemTouchHelper.attachToRecyclerView(mRecyclerView)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
    }

    override fun onStart() {
        super.onStart()
        bindEvent()
    }

    override fun onDestroy() {
        super.onDestroy()
        mDialogManager?.dismissAll()
        mPresenter?.unSubscriber()
    }

    private fun bindEvent() {
        Toolbar.setNavigationOnClickListener {
            EventBus.getDefault().post(OpenDrawerEvent())
        }

        mAddBtn.setOnClickListener {
            if (singleData.isLogin()) {
                mDialogManager?.showEditBucketDialog { name, description ->
                    mPresenter?.createBucket(singleData.token!!, name, description)
                }
            } else {
                toast(R.string.not_logged)
            }
        }

        mRefresh.setOnRefreshListener {
            getBuckets()
        }
    }

    override fun getBucketsSuccess(buckets: MutableList<Bucket>?) {
        if (buckets != null) {
            mBuckets = buckets
            mRecyclerView.visibility = View.VISIBLE
            mRecyclerView.adapter = getAdapter(mBuckets!!)
        } else {
            mRecyclerView.visibility = View.GONE
            showErrorImg(mErrorLayout, R.string.no_bucket, R.mipmap.img_empty_buckets)
        }
    }

    override fun getBucketsFailed(msg: String) {
        toast(msg)
        if (mAdapter == null) {
            showErrorImg(mErrorLayout, msg, R.mipmap.img_empty_buckets)
        }
    }

    override fun showProgress() {
        mRefresh.isRefreshing = true
    }

    override fun hideProgress() {
        mRefresh.isRefreshing = false
    }

    override fun showProgressDialog(msg: String?) {
        mDialogManager?.showCircleProgressDialog()
    }

    override fun hideProgressDialog() {
        mDialogManager?.dismissAll()
    }

    override fun createBucketSuccess(bucket: Bucket?) {
        if (bucket != null) {
            if (mAdapter != null) {
                mAdapter?.addItem(0, bucket)
                mRecyclerView.scrollToPosition(0)
            } else {
                mBuckets?.add(bucket)
                mRecyclerView.adapter = getAdapter(mBuckets!!)
            }
        }
    }

    private fun getAdapter(buckets: MutableList<Bucket>): MyBucketsAdapter {
        mAdapter = MyBucketsAdapter(buckets, { position ->

        }, { position ->
            mDialogManager?.showEditBucketDialog(mBuckets!![position].name!!, mBuckets!![position].description, resources.getString(R.string.modify_bucket)) { name, description ->
                mPresenter?.modifyBucket(singleData.token!!, mBuckets!![position].id, name, description, position)
            }
        })
        return mAdapter!!
    }

    override fun createBucketFailed(msg: String) {
        toast(msg)
    }

    override fun deleteBucketSuccess() {
        toast(R.string.delete_success)
    }

    override fun deleteBucketFailed(msg: String) {
        toast("${resources.getString(R.string.delete_failed)} :$msg")
    }

    override fun modifyBucketSuccess(bucket: Bucket?, position: Int) {
        mBuckets!![position].name = bucket?.name
        mBuckets!![position].description = bucket?.description
        mBuckets!![position].updated_at = bucket?.updated_at
        mAdapter?.notifyDataSetChanged()
        toast(R.string.modify_success)
    }

    override fun modifyBucketFailed(msg: String) {
        toast("${resources.getString(R.string.modify_failed)} :$msg")
    }
}