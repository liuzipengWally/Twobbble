package com.twobbble.tools

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager

import com.twobbble.application.App

/**
 * 该类是处理SharedPreferences的工具类
 */
class QuickSimpleIO private constructor() {
    private var mPreferences: SharedPreferences? = null
    private var mEditor: SharedPreferences.Editor? = null

    init {
        createSimpleIo()
    }

    fun createSimpleIo() {
        if (mPreferences == null) {
            mPreferences = PreferenceManager.getDefaultSharedPreferences(App.instance)
            mEditor = mPreferences!!.edit()
        }
    }

    fun setString(key: String, value: String): Boolean {
        mEditor!!.putString(key, value)
        return mEditor!!.commit()
    }

    fun getString(key: String): String? {
        return mPreferences?.getString(key, null)
    }

    fun setBoolean(key: String, value: Boolean): Boolean {
        mEditor!!.putBoolean(key, value)
        return mEditor!!.commit()
    }

    fun getBoolean(key: String): Boolean? {
        return mPreferences!!.getBoolean(key, false)
    }

    fun setInt(key: String, value: Int): Boolean {
        mEditor!!.putInt(key, value)
        return mEditor!!.commit()
    }

    fun getInt(key: String): Int? {
        return mPreferences!!.getInt(key, 0)
    }

    fun setFloat(key: String, value: Float?): Boolean {
        mEditor!!.putFloat(key, value!!)
        return mEditor!!.commit()
    }

    fun getFloat(key: String): Float? {
        return mPreferences!!.getFloat(key, 0f)
    }

    fun setLong(key: String, value: Long): Boolean {
        mEditor!!.putLong(key, value)
        return mEditor!!.commit()
    }

    fun getLong(key: String): Long? {
        return mPreferences!!.getLong(key, 0L)
    }

    fun clear() {
        mEditor!!.clear()
    }

    fun remove(key: String): Boolean {
        mEditor!!.remove(key)
        return mEditor!!.commit()
    }

    companion object {
        private var mInstance: QuickSimpleIO? = null

        val instance: QuickSimpleIO
            get() {
                if (mInstance == null) {
                    synchronized(QuickSimpleIO::class.java) {
                        if (mInstance == null) {
                            mInstance = QuickSimpleIO()
                        }
                    }
                }

                return mInstance!!
            }
    }
}
