package com.twobbble.view.activity

import android.os.Bundle
import com.twobbble.R
import com.twobbble.view.fragment.SettingsFragment
import kotlinx.android.synthetic.main.fragment_settings.*

class SettingsActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        fragmentManager.beginTransaction().replace(R.id.mContentLayout, SettingsFragment()).commit()
    }

    override fun onStart() {
        super.onStart()
        bindEvent()
    }

    private fun bindEvent() {
        Toolbar.setNavigationOnClickListener { onBackPressed() }
    }
}
