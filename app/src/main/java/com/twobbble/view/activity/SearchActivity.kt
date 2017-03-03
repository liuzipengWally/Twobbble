package com.twobbble.view.activity

import android.app.Activity
import android.support.v7.app.AppCompatActivity
import android.os.Bundle

import com.twobbble.R
import kotlinx.android.synthetic.main.search_layout.*
import android.speech.RecognizerIntent
import android.content.Intent
import android.R.attr.data
import com.twobbble.tools.log


class SearchActivity : AppCompatActivity() {
    val VOICE_CODE = 0X01

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        initView()
    }

    private fun initView() {

    }

    override fun onStart() {
        super.onStart()
        bindEvent()
    }

    private fun bindEvent() {
        mBackBtn.setOnClickListener { finish() }

        mVoiceBtn.setOnClickListener { startSpeak() }
    }

    private fun startSpeak() {
        //通过Intent传递语音识别的模式
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        //语言模式和自由形式的语音识别
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
        //提示语音开始
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, resources.getString(R.string.start_speak))
        //开始执行我们的Intent、语音识别
        startActivityForResult(intent, VOICE_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == VOICE_CODE && resultCode == Activity.RESULT_OK) {
            val matches = data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
            matches?.forEach {
                log(it)
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }
}
