package com.twobbble.view.fragment

import android.app.Fragment
import android.content.Context
import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.twobbble.R
import com.twobbble.application.App
import com.twobbble.entity.Like
import com.twobbble.entity.Shot
import com.twobbble.event.OpenDrawerEvent
import com.twobbble.presenter.LikePresenter
import com.twobbble.presenter.service.ShotsPresenter
import com.twobbble.tools.*
import com.twobbble.view.adapter.ItemShotAdapter
import com.twobbble.view.adapter.LikesAdapter
import com.twobbble.view.api.ILikeView
import kotlinx.android.synthetic.main.error_layout.*
import kotlinx.android.synthetic.main.fragment_likes.*
import kotlinx.android.synthetic.main.list.*
import org.greenrobot.eventbus.EventBus

/**
 * Created by liuzipeng on 2017/2/17.
 */
class LikesFragment : BaseFragment(), ILikeView {
    private var mPresenter: LikePresenter? = null
    private var mPage: Int = 1
    private var mLikes: MutableList<Like>? = null
    private var mListAdapter: LikesAdapter? = null
    private var isLoading: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return LayoutInflater.from(activity).inflate(R.layout.fragment_likes, null)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        init()  //init放在onCreate中是因为  lazyLoad比onCreateView先走，所以懒加载的操作会因为很多对象还没初始化而不走
        initView()
        geLikes(false)
    }

    private fun init() {
        mPresenter = LikePresenter(this)
    }

    override fun onStart() {
        super.onStart()
        bindEvent()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mPresenter?.unSubscriber()
    }

    private fun initView() {
        mRefresh.setColorSchemeResources(R.color.google_red, R.color.google_yellow, R.color.google_green, R.color.google_blue)
        val layoutManager = LinearLayoutManager(App.instance, LinearLayoutManager.VERTICAL, false)
        mRecyclerView.layoutManager = layoutManager
        mRecyclerView.itemAnimator = DefaultItemAnimator()
    }

    fun geLikes(isLoadMore: Boolean = false) {
        if (singleData.isLogin()) {
            isLoading = true
            mPresenter?.getMyLike(
                    singleData.token!!,
                    page = mPage,
                    isLoadMore = isLoadMore)
        } else {
            showErrorImg(mErrorLayout, R.string.not_logged, R.mipmap.img_empty_likes)
        }
    }

    private fun bindEvent() {
        Toolbar.setNavigationOnClickListener {
            EventBus.getDefault().post(OpenDrawerEvent())
        }

        mRefresh.setOnRefreshListener {
            mPage = 1
            geLikes(false)
        }

        mErrorLayout.setOnClickListener {
            hideErrorImg(mErrorLayout)
            geLikes(false)
        }

        mRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                val linearManager = recyclerView?.layoutManager as LinearLayoutManager
                val position = linearManager.findLastVisibleItemPosition()
                if (mLikes?.isNotEmpty()!! && position == mLikes?.size!!) {
                    if (!isLoading) {
                        mPage += 1
                        geLikes(true)
                    }
                }
            }
        })
    }


    override fun getLikeSuccess(shots: MutableList<Like>?, isLoadMore: Boolean) {
        isLoading = false
        if (shots != null && shots.isNotEmpty()) {
            if (!isLoadMore) {
                mountList(shots)
            } else {
                mListAdapter?.addItems(shots)
            }
        } else {
            if (!isLoadMore) {
                showErrorImg(mErrorLayout, R.string.no_likes, R.mipmap.img_empty_likes)
            } else {
                mListAdapter?.hideProgress()
            }
        }
    }

    private fun mountList(likes: MutableList<Like>) {
        mLikes = likes
        mListAdapter = LikesAdapter(mLikes!!, { view, position ->
            EventBus.getDefault().postSticky(mLikes!![position].shot)
            startDetailsActivity()
        })
        mRecyclerView.adapter = mListAdapter
    }

    override fun getLikeFailed(msg: String, isLoadMore: Boolean) {
        isLoading = false
        toast(msg)
        if (!isLoadMore) {
            if (mListAdapter == null)
                showErrorImg(mErrorLayout, msg, R.mipmap.img_empty_likes)
        } else {
            mListAdapter?.loadError {
                geLikes(true)
            }
        }
    }

    override fun showProgress() {
        if (mListAdapter == null) mRefresh.isRefreshing = true
    }

    override fun hideProgress() {
        mRefresh.isRefreshing = false
    }

    override fun onBackPressed() {

    }
}