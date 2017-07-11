package com.twobbble.view.fragment

import android.app.ActivityOptions
import android.app.Fragment
import android.app.FragmentManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v13.app.FragmentStatePagerAdapter
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.twobbble.R
import com.twobbble.event.OpenDrawerEvent
import com.twobbble.tools.log
import com.twobbble.tools.startSpeak
import com.twobbble.tools.toast
import com.twobbble.view.activity.SearchActivity
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.search_layout.*
import org.greenrobot.eventbus.EventBus

/**
 * Created by liuzipeng on 2017/2/17.
 */
class HomeFragment : BaseFragment() {
    val TITLE_RECENT = "RECENT"
    val TITLE_POPULAR = "POPULAR"
    val TITLE_FOLLOWING = "FOLLOWING"

    private val mRecentFragment: ShotsFragment by lazy {
        ShotsFragment.newInstance(ShotsFragment.RECENT)
    }
    private val mPopularFragment: ShotsFragment by lazy {
        ShotsFragment.newInstance()
    }
    private val mFollowingFragment: FollowingFragment by lazy {
        FollowingFragment()
    }
    private lateinit var mFragments: MutableList<Fragment>
    private val mTitles: MutableList<String> by lazy {
        mutableListOf(TITLE_POPULAR, TITLE_RECENT)
    }
    private var mPagerAdapter: PagerAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        init()
        return LayoutInflater.from(activity).inflate(R.layout.fragment_home, null)
    }

    override fun onBackPressed() {
        hideSearchView()
    }

    private fun init() {
        mFragments = arrayListOf(mPopularFragment, mRecentFragment)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        initView()
        loadPager()
    }

    private fun initView() {
//        Toolbar.inflateMenu(R.menu.search_menu)
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
        mViewPager.adapter = mPagerAdapter
        mTabLayout.setViewPager(mViewPager)
    }

    private fun bindEvent() {
        Toolbar.setNavigationOnClickListener {
            EventBus.getDefault().post(OpenDrawerEvent())
        }

        Toolbar.setOnMenuItemClickListener { menu ->
            when (menu.itemId) {
                R.id.mSearch -> mSearchLayout.showSearchView(Toolbar.width, {
                    isShowSearchBar = true
                })
            }
            true
        }

        mBackBtn.setOnClickListener {
            hideSearchView()
        }

        mSearchBtn.setOnClickListener {
            search()
        }

        mVoiceBtn.setOnClickListener { startSpeak() }

        mSearchEdit.setOnKeyListener { _, keycode, keyEvent ->
            if (keyEvent.action == KeyEvent.ACTION_DOWN) {//判断是否为点按下去触发的事件，如果不写，会导致该案件的事件被执行两次
                when (keycode) {
                    KeyEvent.KEYCODE_ENTER -> search()
                }
            }
            false
        }
    }

    private fun hideSearchView() {
        mSearchLayout.hideSearchView { isShowSearchBar = false }
    }

    private fun search() {
        if (mSearchEdit.text != null && mSearchEdit.text.toString() != "") {
            val intent = Intent(activity, SearchActivity::class.java)
            intent.putExtra(SearchActivity.KEY_KEYWORD, mSearchEdit.text.toString())
            startActivity(intent, ActivityOptions.
                    makeSceneTransitionAnimation(activity, mSearchLayout, "searchBar").toBundle())
        }
    }

    //加inner标签才能访问外部对象
    inner class PagerAdapter(fm: FragmentManager?) : FragmentStatePagerAdapter(fm) {
        override fun getPageTitle(position: Int): CharSequence {
            return mTitles[position]
        }

        override fun getItem(position: Int): Fragment = mFragments[position]

        override fun getCount(): Int = mFragments.size
    }
}