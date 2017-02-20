package com.twobbble.view.fragment

import android.app.Fragment
import android.app.FragmentManager
import android.content.Context
import android.os.Bundle
import android.support.design.widget.CoordinatorLayout
import android.support.design.widget.TabLayout
import android.support.v13.app.FragmentPagerAdapter
import android.support.v13.app.FragmentStatePagerAdapter
import android.support.v4.view.ViewPager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.twobbble.R
import com.twobbble.event.OpenDrawerEvent
import com.twobbble.tools.log

import kotlinx.android.synthetic.main.fragment_home.*
import org.greenrobot.eventbus.EventBus

/**
 * Created by liuzipeng on 2017/2/17.
 */
class HomeFragment : BaseFragment() {
    private var mContext: Context? = null
    private var mRecentFragment: RecentFragment? = null
    private var mPopularFragment: PopularFragment? = null
    private var mFollowingFragment: FollowingFragment? = null
    var mFragments: List<Fragment>? = null
    val TITLE_RECENT = "RECENT"
    val TITLE_POPULAR = "POPULAR"
    val TITLE_FOLLOWING = "FOLLOWING"
    var mTitles: List<String>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        initView()
        return LayoutInflater.from(mContext).inflate(R.layout.fragment_home, null)
    }

    private fun initView() {
        mRecentFragment = RecentFragment()
        mPopularFragment = PopularFragment()
        mFollowingFragment = FollowingFragment()
        mFragments = arrayListOf(mPopularFragment!!, mRecentFragment!!)
        mTitles = arrayListOf(TITLE_POPULAR, TITLE_RECENT)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        mContext = context
    }

    override fun onStart() {
        super.onStart()
        loadPager()
        bindEvent()
    }

    override fun onResume() {
        super.onResume()
    }

    private fun loadPager() {
        mViewPager?.adapter = PagerAdapter(childFragmentManager)
        mTabLayout?.setViewPager(mViewPager)
    }

    private fun bindEvent() {
        Toolbar.setNavigationOnClickListener {
            EventBus.getDefault().post(OpenDrawerEvent())
        }
    }

    //加inner标签才能访问外部对象
    inner class PagerAdapter(fm: FragmentManager?) : FragmentStatePagerAdapter(fm) {
        override fun getPageTitle(position: Int): CharSequence {
            return mTitles!![position]
        }

        override fun getItem(position: Int): Fragment = mFragments!![position]

        override fun getCount(): Int = mFragments?.size!!
    }
}