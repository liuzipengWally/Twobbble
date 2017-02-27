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
import com.twobbble.application.App
import com.twobbble.event.OpenDrawerEvent
import com.twobbble.tools.log

import kotlinx.android.synthetic.main.fragment_home.*
import org.greenrobot.eventbus.EventBus

/**
 * Created by liuzipeng on 2017/2/17.
 */
class HomeFragment : BaseFragment() {
    private var mRecentFragment: HomeShotsFragment? = null
    private var mPopularFragment: HomeShotsFragment? = null
    private var mFollowingFragment: FollowingFragment? = null
    var mFragments: List<Fragment>? = null
    val TITLE_RECENT = "RECENT"
    val TITLE_POPULAR = "POPULAR"
    val TITLE_FOLLOWING = "FOLLOWING"
    var mTitles: List<String>? = null
    var mPagerAdapter: PagerAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        init()
        return LayoutInflater.from(activity).inflate(R.layout.fragment_home, null)
    }

    private fun init() {
        mRecentFragment = HomeShotsFragment.newInstance(HomeShotsFragment.RECENT)
        mPopularFragment = HomeShotsFragment.newInstance()
        mFollowingFragment = FollowingFragment()
        mFragments = arrayListOf(mPopularFragment!!, mRecentFragment!!)
        mTitles = arrayListOf(TITLE_POPULAR, TITLE_RECENT)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        initView()
        loadPager()
    }

    private fun initView() {
        Toolbar.inflateMenu(R.menu.search_menu)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
    }

    override fun onStart() {
        super.onStart()
        bindEvent()
    }

    override fun onResume() {
        super.onResume()
    }

    private fun loadPager() {
        mPagerAdapter = PagerAdapter(childFragmentManager)
        mViewPager?.adapter = mPagerAdapter
        mTabLayout?.setViewPager(mViewPager)
    }

    private fun bindEvent() {
        Toolbar.setNavigationOnClickListener {
            EventBus.getDefault().post(OpenDrawerEvent())
        }

        Toolbar.setOnMenuItemClickListener { menu ->
            when (menu.itemId) {
                R.id.mSearch -> {
                }
            }
            true
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