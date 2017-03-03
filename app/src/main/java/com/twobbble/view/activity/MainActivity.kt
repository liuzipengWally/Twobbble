package com.twobbble.view.activity

import android.app.Fragment
import android.app.FragmentTransaction
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.util.Log
import android.view.MenuItem
import com.twobbble.R
import com.twobbble.event.OpenDrawerEvent
import com.twobbble.view.fragment.*
import kotlinx.android.synthetic.main.activity_main.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

class MainActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener {
    private var mHomeFragment: HomeFragment? = null
    private var mExploreFragment: ExploreFragment? = null
    private var mBucketsFragment: BucketsFragment? = null
    private var mLikesFragment: LikesFragment? = null
    private var mMyShotFragment: MyShotFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
        if (savedInstanceState == null) initFragment()
        EventBus.getDefault().register(this)
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
        mNavigation.setNavigationItemSelectedListener(this)
    }

    private fun initFragment() {
        mHomeFragment = HomeFragment()
        mExploreFragment = ExploreFragment()
        mBucketsFragment = BucketsFragment()
        mLikesFragment = LikesFragment()
        mMyShotFragment = MyShotFragment()
        addFragment(mHomeFragment)
        mNavigation.setCheckedItem(R.id.mHomeMenu)
    }

    private fun replaceFragment(fragment: Fragment?, addBackStack: Boolean = false) {
        if (fragment != null && !fragment.isVisible) {
            val transaction = fragmentManager.beginTransaction().setCustomAnimations(R.anim.fragment_enter, R.anim.fragment_exit, R.anim.fragment_enter, R.anim.fragment_exit)
            transaction.hide(mBucketsFragment).hide(mExploreFragment).hide(mLikesFragment).hide(mMyShotFragment).hide(mHomeFragment).show(fragment)
            if (addBackStack) transaction.addToBackStack(fragment.javaClass.simpleName).commit() else transaction.commit()
        }
    }

    private fun initView() {
        //绑定抽屉
        val toggle = ActionBarDrawerToggle(this, mDrawerLayout,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        mDrawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        val params = mNavigation.layoutParams
        params.width = screenWidth - screenWidth / 4
        mNavigation.layoutParams = params
    }

    @Subscribe fun DrawerOpen(drawerEvent: OpenDrawerEvent?) {
        mDrawerLayout.openDrawer(GravityCompat.START)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        mDrawerLayout.closeDrawer(GravityCompat.START)
        when (item.itemId) {
            R.id.mHomeMenu -> if (!mHomeFragment!!.isAdded) {
                addFragment(mHomeFragment)
            } else replaceFragment(mHomeFragment)
            R.id.mExploreMenu -> if (!mExploreFragment!!.isAdded) {
                addFragment(mExploreFragment)
            } else replaceFragment(mExploreFragment)
            R.id.mBucketMenu -> if (!mBucketsFragment!!.isAdded) {
                addFragment(mBucketsFragment)
            } else replaceFragment(mBucketsFragment)
            R.id.mLikesMenu -> if (!mLikesFragment!!.isAdded) {
                addFragment(mLikesFragment)
            } else replaceFragment(mLikesFragment)
            R.id.mShotMenu -> if (!mMyShotFragment!!.isAdded) {
                addFragment(mMyShotFragment)
            } else replaceFragment(mMyShotFragment)
            R.id.mSettingsMenu -> {
                startActivity(Intent(this, SettingsActivity::class.java))
                return false
            }
        }
        return true
    }

    fun addFragment(fragment: Fragment?) {
        fragmentManager.beginTransaction().setCustomAnimations(R.anim.fragment_enter,
                R.anim.fragment_exit, R.anim.fragment_enter, R.anim.fragment_exit)
                .hide(mBucketsFragment)
                .hide(mExploreFragment)
                .hide(mLikesFragment)
                .hide(mMyShotFragment)
                .hide(mHomeFragment)
                .add(R.id.mContentLayout, fragment)
                .show(fragment)
                .commit()
    }
}
