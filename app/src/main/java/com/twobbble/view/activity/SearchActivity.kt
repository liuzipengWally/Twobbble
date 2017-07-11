package com.twobbble.view.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.support.v7.widget.PopupMenu
import android.transition.Fade
import android.transition.Slide
import android.view.View
import android.view.ViewAnimationUtils
import android.view.animation.AccelerateInterpolator
import com.twobbble.R
import com.twobbble.tools.Constant
import com.twobbble.tools.Parameters
import com.twobbble.tools.Utils
import com.twobbble.tools.startSpeak
import com.twobbble.view.adapter.ItemShotAdapter
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.search_layout.*


class SearchActivity : BaseActivity() {
    private var mSort: String? = null
    private var mSortList: String? = null
    private var mTimeFrame: String? = null
    private var mPage: Int = 1
    private var mListAdapter: ItemShotAdapter? = null
    private var isLoading: Boolean = false

    private val mSorts = listOf(null, Parameters.COMMENTS, Parameters.RECENT, Parameters.VIEWS)
    private val mList = listOf(null, Parameters.ANIMATED, Parameters.ATTACHMENTS,
            Parameters.DEBUTS, Parameters.DEBUTS, Parameters.PLAYOFFS, Parameters.REBOUNDS, Parameters.TEAMS)

    companion object {
        val KEY_KEYWORD: String = "keyword"
    }

    private var mKeyword: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        initView()
    }

    private fun initView() {
        mSortBtn.visibility = View.VISIBLE
        mKeyword = intent.getStringExtra(KEY_KEYWORD)
        mSearchEdit.setText(mKeyword)
        mSortSpinner.dropDownVerticalOffset = Utils.dp2px(16).toInt()
        mSortListSpinner.dropDownVerticalOffset = Utils.dp2px(16).toInt()
    }

    override fun onStart() {
        super.onStart()
        bindEvent()
    }

    private fun bindEvent() {
        mBackBtn.setOnClickListener { finish() }

        mVoiceBtn.setOnClickListener { startSpeak() }

        mSortBtn.setOnClickListener {
            val popupMenu = PopupMenu(this, mSortBtn)
            popupMenu.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.mNow -> timeFrameUpdate(null)
                    R.id.mWeek -> timeFrameUpdate(Parameters.WEEK)
                    R.id.mMonth -> timeFrameUpdate(Parameters.MONTH)
                    R.id.mYear -> timeFrameUpdate(Parameters.YEAR)
                    R.id.mAllTime -> timeFrameUpdate(Parameters.EVER)
                }
                true
            }
            val inflater = popupMenu.menuInflater
            inflater.inflate(R.menu.sort_menu, popupMenu.menu)
            popupMenu.show()
        }
    }

    /**
     * 更新时间线
     * @param timeFrame 时间线，默认为null，代表Now，其它的值在Parameters这个单利对象中保存
     */
    private fun timeFrameUpdate(timeFrame: String?) {
        mListAdapter = null
        mTimeFrame = timeFrame
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == Constant.VOICE_CODE && resultCode == Activity.RESULT_OK) {
            val keywords = data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
            keywords?.forEach {
                mSearchEdit.setText(it)
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }
}
