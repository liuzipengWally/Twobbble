package com.twobbble.view.fragment

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.twobbble.R
import com.twobbble.application.App
import com.twobbble.entity.Bucket
import com.twobbble.event.HideBucketEvent
import com.twobbble.event.OpenDrawerEvent
import com.twobbble.presenter.MyBucketsPresenter
import com.twobbble.tools.Utils
import com.twobbble.tools.showErrorImg
import com.twobbble.tools.singleData
import com.twobbble.tools.toast
import com.twobbble.view.activity.BucketShotsActivity
import com.twobbble.view.adapter.MyBucketsAdapter
import com.twobbble.view.api.IMyBucketsView
import com.twobbble.view.customview.ItemSwipeRemoveCallback
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
    private var mDelPosition: Int = 0
    private var mType: String? = null
    private var mShotId: Long = 0

    companion object {
        val TYPE = "type"
        val SHOT_ID = "id"
        val LOCK_BUCKET = "lock"
        val ADD_SHOT = "add"

        fun newInstance(type: String? = null, shotId: Long = 0): BucketsFragment {
            val fragment = BucketsFragment()
            val args = Bundle()
            args.putString(TYPE, type)
            args.putLong(SHOT_ID, shotId)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onBackPressed() {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            mType = arguments.getString(TYPE)
            mShotId = arguments.getLong(SHOT_ID, 0)
        }
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
        if (Utils.hasNavigationBar(activity) && mType != ADD_SHOT) {
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

        if (mType == ADD_SHOT) {
            Toolbar.setNavigationIcon(R.drawable.ic_arrow_back_light_24dp)
            Toolbar.setTitle(R.string.into_the_bucket)
            mStatusView.visibility = View.VISIBLE
        }
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
            when (mType) {
                ADD_SHOT -> EventBus.getDefault().post(HideBucketEvent())
                LOCK_BUCKET -> EventBus.getDefault().post(OpenDrawerEvent())
            }
        }

        mAddBtn.setOnClickListener {
            addBucket()
        }

        mRefresh.setOnRefreshListener {
            getBuckets()
        }

        val itemTouchHelper = ItemTouchHelper(ItemSwipeRemoveCallback { delPosition, view ->
            mDelBucket = mBuckets!![delPosition]
            mDelPosition = delPosition
            mAdapter?.deleteItem(delPosition)
            mDialogManager?.showOptionDialog(
                    resources.getString(R.string.delete_bucket),
                    resources.getString(R.string.whether_to_delete_bucket),
                    confirmText = resources.getString(R.string.delete),
                    onConfirm = {
                        mPresenter?.deleteBucket(singleData.token!!, mDelBucket?.id!!)
                    }, onCancel = {
                mAdapter?.addItem(delPosition, mDelBucket!!)
            })
        })
        itemTouchHelper.attachToRecyclerView(mRecyclerView)
    }

    fun addBucket() {
        if (singleData.isLogin()) {
            mDialogManager?.showEditBucketDialog { name, description ->
                mPresenter?.createBucket(singleData.token!!, name, description)
            }
        } else {
            toast(R.string.not_logged)
        }
    }

    override fun getBucketsSuccess(buckets: MutableList<Bucket>?) {
        if (buckets != null) {
            mBuckets = buckets
            mRecyclerView?.visibility = View.VISIBLE
            mRecyclerView?.adapter = getAdapter(mBuckets!!)
        } else {
            mRecyclerView?.visibility = View.GONE
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
        mRefresh?.isRefreshing = true
    }

    override fun hideProgress() {
        mRefresh?.isRefreshing = false
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
            itemClick(position, buckets)
        }, { position ->
            mDialogManager?.showEditBucketDialog(mBuckets!![position].name!!, mBuckets!![position].description, resources.getString(R.string.modify_bucket)) { name, description ->
                mPresenter?.modifyBucket(singleData.token!!, mBuckets!![position].id, name, description, position)
            }
        })
        return mAdapter!!
    }

    private fun itemClick(position: Int, buckets: MutableList<Bucket>) {
        when (mType) {
            LOCK_BUCKET -> {
                val intent = Intent(activity, BucketShotsActivity::class.java)
                intent.putExtra(BucketShotsActivity.KEY_ID, buckets[position].id)
                intent.putExtra(BucketShotsActivity.KEY_TITLE, buckets[position].name)
                startActivity(intent)
            }
            ADD_SHOT -> mPresenter?.addShot2Bucket(id = buckets[position].id, shotId = mShotId)
        }
    }

    override fun createBucketFailed(msg: String) {
        toast(msg)
    }

    override fun deleteBucketSuccess() {
        toast(R.string.delete_success)
    }

    override fun deleteBucketFailed(msg: String) {
        toast("${resources.getString(R.string.delete_failed)} :$msg")
        mAdapter?.addItem(mDelPosition, mDelBucket!!)
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

    override fun addShotSuccess() {
        toast(resources.getString(R.string.add_success))
        EventBus.getDefault().post(HideBucketEvent())
    }

    override fun addShotFailed(msg: String) {
        toast("${resources.getString(R.string.add_failed)} :$msg")
    }
}