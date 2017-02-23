package com.twobbble.view.fragment


import android.app.Fragment
import android.content.Context
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.twobbble.R
import com.twobbble.application.App
import com.twobbble.entity.ShotList
import com.twobbble.presenter.service.ShotsPresenter
import com.twobbble.tools.Constant
import com.twobbble.tools.log
import com.twobbble.tools.showSnackBar
import com.twobbble.view.adapter.ItemShotAdapter
import com.twobbble.view.api.IShotsView
import kotlinx.android.synthetic.main.fragment_recent.*
import org.jetbrains.annotations.NotNull

/**
 * Created by liuzipeng on 2017/2/17.
 */
class HomeShotsFragment : BaseFragment(), IShotsView {
    private var mContext: Context? = null
    private var mPresenter: ShotsPresenter? = null
    private var mSort: String? = null
    private var mShots: List<ShotList>? = null

    companion object {
        val SORT = "sort"
        val RECENT = "recent"
        fun newInstance(sort: String? = null): HomeShotsFragment {
            val fragment = HomeShotsFragment()
            val args = Bundle()
            args.putString(SORT, sort)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            mSort = arguments.getString(SORT)
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        init()
        return LayoutInflater.from(mContext).inflate(R.layout.fragment_recent, null)
    }

    private fun init() {
        mPresenter = ShotsPresenter(this)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        initView()
    }

    override fun lazyLoad() {
        if (mShots == null) {
            getShots()
        }
    }

    private fun initView() {
        mRefresh.setColorSchemeResources(R.color.google_red, R.color.google_yellow, R.color.google_green, R.color.google_blue)
        val layoutManager = LinearLayoutManager(App.instance, LinearLayoutManager.VERTICAL, false)
        mRecyclerView.layoutManager = layoutManager
    }

    fun getShots() {
        val token = mSimpleIo?.getString(Constant.KEY_TOKEN)
        if (token == null) {
            mPresenter?.getShots(sort = mSort)
        } else {
            mPresenter?.getShots(access_token = token, sort = mSort)
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        mContext = context
    }

    override fun onStart() {
        super.onStart()
        bindEvent()
    }

    private fun bindEvent() {
        mRefresh.setOnRefreshListener {
            getShots()
        }
    }

    override fun showProgress() {
        mRefresh.isRefreshing = true
    }

    override fun hideProgress() {
        mRefresh.isRefreshing = false
    }

    override fun getShotSuccess(shotList: List<ShotList>?) {
        mShots = shotList
        val adapter = ItemShotAdapter(mShots!!)
        mRecyclerView.adapter = adapter
    }

    override fun getShotFailed(msg: String) {
        showSnackBar(mRootLayout, msg)
    }
}