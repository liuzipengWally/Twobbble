package com.twobbble.view.fragment

import android.app.Activity
import android.app.ActivityOptions
import android.app.Fragment
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.view.KeyEvent
import com.twobbble.tools.Constant
import com.twobbble.tools.QuickSimpleIO
import com.twobbble.view.activity.DetailsActivity
import kotlinx.android.synthetic.main.item_shots.*
import kotlinx.android.synthetic.main.search_layout.*

/**
 * Created by liuzipeng on 2017/2/22.
 */
abstract class BaseFragment : Fragment() {
    var isShowSearchBar: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    open fun onBackPressed() {}

    open fun onKeyDown(keyCode: Int, event: KeyEvent?) {

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

    fun startDetailsActivity() {
        startActivity(Intent(activity, DetailsActivity::class.java),
                ActivityOptions.makeSceneTransitionAnimation(activity).toBundle())
    }
}