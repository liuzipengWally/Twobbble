package com.twobbble.tools

import android.app.Activity
import android.app.Fragment
import android.support.design.widget.Snackbar
import android.util.Log
import android.view.View

/**
 * Created by liuzipeng on 2017/2/15.
 */

fun Activity.showSnackbar(view: View, msg: String, time: Int = Snackbar.LENGTH_SHORT) {
    Snackbar.make(view, msg, time).show()
}

fun Activity.showSnackbar(view: View, msg: String, time: Int = Snackbar.LENGTH_SHORT, actionMsg: String = "重试", action: (View) -> Unit) {
    Snackbar.make(view, msg, time).setAction(actionMsg, View.OnClickListener { action.invoke(view) }).show()
}

fun Fragment.showSnackbar(view: View, msg: String, time: Int = Snackbar.LENGTH_SHORT) {
    Snackbar.make(view, msg, time).show()
}

fun Fragment.showSnackbar(view: View, msg: String, time: Int = Snackbar.LENGTH_SHORT, actionMsg: String = "重试", action: (View) -> Unit) {
    Snackbar.make(view, msg, time).setAction(actionMsg, View.OnClickListener { action.invoke(view) }).show()
}

fun Activity.log(msg: String) {
    Log.d(this.javaClass.simpleName, msg)
}

fun Fragment.log(msg: String) {
    Log.d(this.javaClass.simpleName, msg)
}