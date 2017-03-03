package com.twobbble.view.fragment

import android.app.Fragment
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import com.twobbble.R
import com.twobbble.application.App
import com.twobbble.entity.Shot
import com.twobbble.event.OpenDrawerEvent
import com.twobbble.presenter.service.ShotsPresenter
import com.twobbble.tools.*
import com.twobbble.view.activity.DetailsActivity
import com.twobbble.view.adapter.ItemShotAdapter
import com.twobbble.view.adapter.ToolbarSpinnerAdapter
import com.twobbble.view.api.IShotsView
import kotlinx.android.synthetic.main.error_layout.*

import kotlinx.android.synthetic.main.fragment_explore.*
import kotlinx.android.synthetic.main.list_shot.*
import org.greenrobot.eventbus.EventBus

/**
 * Created by liuzipeng on 2017/2/17.
 */
class ExploreFragment : BaseFragment(), IShotsView {
    private var mPresenter: ShotsPresenter? = null
    private var mSort: String? = null
    private var mSortList: String? = null
    private var mTimeFrame: String? = null
    private var mPage: Int = 1
    private var mShots: MutableList<Shot>? = null
    private var mListAdapter: ItemShotAdapter? = null
    private var isLoading: Boolean = false
    private val mSorts = listOf(null, Parameters.COMMENTS, Parameters.RECENT, Parameters.VIEWS)
    private val mList = listOf(null, Parameters.ANIMATED, Parameters.ATTACHMENTS,
            Parameters.DEBUTS, Parameters.DEBUTS, Parameters.PLAYOFFS, Parameters.REBOUNDS, Parameters.TEAMS)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return LayoutInflater.from(activity).inflate(R.layout.fragment_explore, null)
    }

    private fun init() {
        mPresenter = ShotsPresenter(this)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()  //init放在onCreate中是因为  lazyLoad比onCreateView先走，所以懒加载的操作会因为很多对象还没初始化而不走
        initView()
        bindEvent()
        getShots(false)
    }

    private fun initView() {
        Toolbar.inflateMenu(R.menu.expolre_menu)
        mRefresh.setColorSchemeResources(R.color.google_red, R.color.google_yellow, R.color.google_green, R.color.google_blue)
        val layoutManager = LinearLayoutManager(App.instance, LinearLayoutManager.VERTICAL, false)
        mRecyclerView.layoutManager = layoutManager
        mRecyclerView.itemAnimator = DefaultItemAnimator()
        mSortSpinner.adapter = ToolbarSpinnerAdapter(activity, resources.getStringArray(R.array.sorts))
        mSortListSpinner.adapter = ToolbarSpinnerAdapter(activity, resources.getStringArray(R.array.sort_list))
        mSortSpinner.setSelection(0, true)
        mSortListSpinner.setSelection(0, true)
    }

    fun getShots(isLoadMore: Boolean = false) {
        isLoading = true
        val token = mSimpleIo?.getString(Constant.KEY_TOKEN)
        if (token == null) {
            mPresenter?.getShots(sort = mSort,
                    list = mSortList,
                    timeframe = mTimeFrame,
                    page = mPage,
                    isLoadMore = isLoadMore)
        } else {
            mPresenter?.getShots(access_token = token,
                    sort = mSort,
                    list = mSortList,
                    timeframe = mTimeFrame,
                    page = mPage,
                    isLoadMore = isLoadMore)
        }
    }

    override fun onStart() {
        super.onStart()
    }

    private fun bindEvent() {
        Toolbar.setNavigationOnClickListener {
            EventBus.getDefault().post(OpenDrawerEvent())
        }

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
                if (mShots?.isNotEmpty()!! && position == mShots?.size!!) {
                    if (!isLoading) {
                        mPage += 1
                        getShots(true)
                    }
                }
            }
        })

        mSortSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {}

            override fun onItemSelected(p0: AdapterView<*>?, view: View?, position: Int, p3: Long) {
                mSort = mSorts[position]
                mListAdapter = null
                getShots(false)
            }
        }

        mSortListSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {}

            override fun onItemSelected(p0: AdapterView<*>?, view: View?, position: Int, p3: Long) {
                mListAdapter = null
                mSortList = mList[position]
                getShots(false)
            }
        }

        Toolbar.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.mNow -> timeFrameUpdate(null)
                R.id.mWeek -> timeFrameUpdate(Parameters.WEEK)
                R.id.mMonth -> timeFrameUpdate(Parameters.MONTH)
                R.id.mYear -> timeFrameUpdate(Parameters.YEAR)
                R.id.mAllTime -> timeFrameUpdate(Parameters.EVER)
            }
            true
        }
    }

    /**
     * 更新时间线
     * @param timeFrame 时间线，默认为null，代表Now，其它的值在Parameters这个单利对象中保存
     */
    private fun timeFrameUpdate(timeFrame: String?) {
        mListAdapter = null
        mTimeFrame = timeFrame
        getShots(false)
    }

    override fun showProgress() {
        if (mListAdapter == null) mRefresh.isRefreshing = true
    }

    override fun hideProgress() {
        mRefresh.isRefreshing = false
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
                if (mListAdapter == null)
                    showErrorImg(mErrorLayout)
            } else {
                mListAdapter?.loadError {
                    getShots(true)
                }
            }
        }
    }

    private fun mountList(shots: MutableList<Shot>) {
        mShots = shots
        mListAdapter = ItemShotAdapter(mShots!!, { view, position ->
            EventBus.getDefault().postSticky(mShots!![position])
            startActivity(Intent(activity, DetailsActivity::class.java))
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
}