package com.twobbble.tools;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.twobbble.application.App;

/**
 * 该类是处理SharedPreferences的工具类
 */
public class QuickSimpleIO {
    private SharedPreferences mPreferences;
    private SharedPreferences.Editor mEditor;
    private static QuickSimpleIO mInstance;

    private QuickSimpleIO() {
        createSimpleIo();
    }

    public static QuickSimpleIO getInstance() {
        if (mInstance == null) {
            synchronized (QuickSimpleIO.class) {
                if (mInstance == null) {
                    mInstance = new QuickSimpleIO();
                }
            }
        }

        return mInstance;
    }

    public void createSimpleIo() {
        if (mPreferences == null) {
            mPreferences = PreferenceManager.getDefaultSharedPreferences(App.Companion.getInstance());
            mEditor = mPreferences.edit();
        }
    }

    public boolean setString(String key, String value) {
        mEditor.putString(key, value);
        return mEditor.commit();
    }

    public String getString(String key) {
        return mPreferences.getString(key, null);
    }

    public boolean setBoolean(String key, boolean value) {
        mEditor.putBoolean(key, value);
        return mEditor.commit();
    }

    public boolean getBoolean(String key) {
        return mPreferences.getBoolean(key, false);
    }

    public boolean setInt(String key, int value) {
        mEditor.putInt(key, value);
        return mEditor.commit();
    }

    public int getInt(String key) {
        return mPreferences.getInt(key, 0);
    }

    public boolean setFloat(String key, Float value) {
        mEditor.putFloat(key, value);
        return mEditor.commit();
    }

    public float getFloat(String key) {
        return mPreferences.getFloat(key, 0);
    }

    public boolean setLong(String key, long value) {
        mEditor.putLong(key, value);
        return mEditor.commit();
    }

    public long getLong(String key) {
        return mPreferences.getLong(key, 0l);
    }

    public void clear() {
        mEditor.clear();
    }

    public boolean remove(String key) {
        mEditor.remove(key);
        return mEditor.commit();
    }
}
