package com.twobbble.view.activity

import android.app.Fragment
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.view.KeyEvent
import android.view.MenuItem
import android.view.View
import com.google.gson.Gson
import com.twobbble.R
import com.twobbble.entity.Token
import com.twobbble.entity.User
import com.twobbble.event.OpenDrawerEvent
import com.twobbble.presenter.MainPresenter
import com.twobbble.tools.*
import com.twobbble.view.api.IMainView
import com.twobbble.view.fragment.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.nav_header.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe


class MainActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener, IMainView {
    private var mHomeFragment: HomeFragment? = null
    private var mExploreFragment: ExploreFragment? = null
    private var mBucketsFragment: BucketsFragment? = null
    private var mLikesFragment: LikesFragment? = null
    //    private var mMyShotFragment: MyShotFragment? = null
    private val mPresenter: MainPresenter by lazy {
        MainPresenter(this)
    }
    private var mUser: User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
        if (savedInstanceState == null) initFragment()
        EventBus.getDefault().register(this)
        checkAuthCallback(intent)
    }

    override fun onStart() {
        super.onStart()
        bindEvent()
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        //将按键的事件给fragment传递一份
        mHomeFragment?.onKeyDown(keyCode, event)
        mExploreFragment?.onKeyDown(keyCode, event)
        mBucketsFragment?.onKeyDown(keyCode, event)
        mLikesFragment?.onKeyDown(keyCode, event)
//        mMyShotFragment?.onKeyDown(keyCode, event)
        return super.onKeyDown(keyCode, event)
    }

    override fun onBackPressed() {
        //给fragment传递返回事件
        if (mHomeFragment?.isVisible!! && mHomeFragment?.isShowSearchBar!!) {
            mHomeFragment?.onBackPressed()
            return
        }

        if (mExploreFragment?.isVisible!! && mExploreFragment?.isShowSearchBar!!) {
            mExploreFragment?.onBackPressed()
            return
        }

        if (mBucketsFragment?.isVisible!! && mBucketsFragment?.isShowSearchBar!!) {
            mBucketsFragment?.onBackPressed()
            return
        }

        if (mLikesFragment?.isVisible!! && mLikesFragment?.isShowSearchBar!!) {
            mLikesFragment?.onBackPressed()
            return
        }

//        if (mMyShotFragment?.isVisible!! && mMyShotFragment?.isShowSearchBar!!) {
//            mMyShotFragment?.onBackPressed()
//            return
//        }

        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

    private fun bindEvent() {
        mNavigation.setNavigationItemSelectedListener(this)

        mDrawerLayout.addDrawerListener(object : DrawerLayout.DrawerListener {
            override fun onDrawerStateChanged(newState: Int) {}
            override fun onDrawerSlide(drawerView: View?, slideOffset: Float) {}
            override fun onDrawerClosed(drawerView: View?) {}
            override fun onDrawerOpened(drawerView: View?) {
                if (singleData.isLogin() && mLogoutBtn.visibility == View.GONE) {
                    mLogoutBtn.visibility = View.VISIBLE
                    mLogoutBtn.setOnClickListener {
                        mDialogManager.showOptionDialog(
                                resources.getString(R.string.logout_hint_title),
                                resources.getString(R.string.logout_hint), onConfirm = {
                            logout()
                        }, onCancel = {})
                    }
                }

                if (!singleData.avatar.isNullOrBlank()) {
                    ImageLoad.frescoLoadCircle(mUserAvatar, singleData.avatar.toString())
                    mNameText.text = singleData.username
                }

                mUserHead?.setOnClickListener {
                    if (!singleData.isLogin()) {
                        login()
                    } else {
                        EventBus.getDefault().postSticky(mUser)
                        startActivity(Intent(applicationContext, UserActivity::class.java))
                    }
                }
            }
        })
    }

    private fun logout() {
        QuickSimpleIO.remove(Constant.KEY_TOKEN)
        QuickSimpleIO.remove(Constant.KEY_USER)
        singleData.username = null
        singleData.avatar = null
        singleData.token = null
        mDrawerLayout.closeDrawer(GravityCompat.START)
        mLogoutBtn.visibility = View.GONE
        ImageLoad.frescoLoadCircle(mUserAvatar, "")
        mNameText.setText(R.string.click_to_login)
    }

    /**
     * 用浏览器打开验证连接，通过我们的CLIENT_ID去获取一个code码用来获取用户的token
     */
    private fun login() {
        mDrawerLayout.closeDrawer(GravityCompat.START)
        val intent = Intent("android.intent.action.VIEW", Uri.parse(Constant.OAUTH_URL))
        startActivity(intent)
    }

    private fun initFragment() {
        mHomeFragment = HomeFragment()
        mExploreFragment = ExploreFragment()
        mBucketsFragment = BucketsFragment.newInstance(BucketsFragment.LOCK_BUCKET)
        mLikesFragment = LikesFragment()
//        mMyShotFragment = MyShotFragment()
        addFragment(mHomeFragment)
        mNavigation.setCheckedItem(R.id.mHomeMenu)
    }

    private fun replaceFragment(fragment: Fragment?, addBackStack: Boolean = false) {
        if (fragment != null && !fragment.isVisible) {
            val transaction = fragmentManager
                    .beginTransaction()
                    .setCustomAnimations(R.anim.fragment_enter, R.anim.fragment_exit, R.anim.fragment_enter, R.anim.fragment_exit)
                    .hide(mBucketsFragment)
                    .hide(mExploreFragment)
                    .hide(mLikesFragment)
//                    .hide(mMyShotFragment)
                    .hide(mHomeFragment)
                    .show(fragment)
            if (addBackStack) transaction.addToBackStack(fragment.javaClass.simpleName).commit() else transaction.commit()
        }
    }

    private fun init() {
        //绑定抽屉
        val toggle = ActionBarDrawerToggle(this, mDrawerLayout,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        mDrawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        val params = mNavigation.layoutParams
        params.width = screenWidth - screenWidth / 4
        mNavigation.layoutParams = params
        singleData.token = QuickSimpleIO.getString(Constant.KEY_TOKEN)
        mUser = Gson().fromJson(QuickSimpleIO.getString(Constant.KEY_USER), User::class.java)
        singleData.avatar = mUser?.avatar_url
        singleData.username = mUser?.name
        initUser()
    }

    private fun initUser() {
        //每次打开应用要检查是否已经登录，如果已登录就要检查头像是否存在，如果在已登录的情况下无头像url，那么我们需要去获取一次用户信息
        if (singleData.isLogin() && singleData.avatar.isNullOrBlank()) {
            mPresenter.getMyInfo(singleData.token!!)
        }
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
//            R.id.mShotMenu -> if (!mMyShotFragment!!.isAdded) {
//                addFragment(mMyShotFragment)
//            } else replaceFragment(mMyShotFragment)
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
//                .hide(mMyShotFragment)
                .hide(mHomeFragment)
                .add(R.id.mContentLayout, fragment)
                .show(fragment)
                .commit()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        //onActivityResult获取到的数据传给fragment一份
        mHomeFragment?.onActivityResult(requestCode, resultCode, data)
        mExploreFragment?.onActivityResult(requestCode, resultCode, data)
        mBucketsFragment?.onActivityResult(requestCode, resultCode, data)
        mLikesFragment?.onActivityResult(requestCode, resultCode, data)
//        mMyShotFragment?.onActivityResult(requestCode, resultCode, data)
    }

    override fun onNewIntent(intent: Intent?) {
        checkAuthCallback(intent)
    }

    private fun checkAuthCallback(paramIntent: Intent?) {
        if (paramIntent != null && paramIntent.data != null && paramIntent.data.authority.isNotEmpty() && "towbbble.app" == paramIntent.data.authority) {
            mPresenter.getToken(paramIntent.data.getQueryParameter("code"))
            return
        }
    }

    override fun getTokenSuccess(token: Token?) {
        if (token != null) {
            QuickSimpleIO.setString(Constant.KEY_TOKEN, token.access_token)
            singleData.token = token.access_token
            toast(resources.getString(R.string.login_success))
            initUser()
        } else {
            toast(resources.getString(R.string.login_failed))
        }
    }

    override fun getTokenFailed(msg: String) {
        toast("${resources.getString(R.string.login_failed)}:$msg")
    }

    override fun showProgress() {
        mDialogManager.showCircleProgressDialog()
    }

    override fun hideProgress() {
        mDialogManager.dismissAll()
    }

    override fun getUserSuccess(user: User?) {
        if (user != null) {
//            mSimpleIo?.setString(Constant.KEY_AVATAR, user.avatar_url!!)
//            mSimpleIo?.setString(Constant.KEY_NAME, user.name!!)
            singleData.avatar = user.avatar_url
            singleData.username = user.name
            mNameText.text = user.name
            ImageLoad.frescoLoadCircle(mUserAvatar, singleData.avatar.toString())
            val userJson = Gson().toJson(user)
            QuickSimpleIO.setString(Constant.KEY_USER, userJson)
        }
    }
}
