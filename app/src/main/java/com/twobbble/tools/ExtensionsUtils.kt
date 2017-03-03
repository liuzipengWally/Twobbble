package com.twobbble.tools

import android.app.Activity
import android.app.Fragment
import android.content.Context
import android.support.design.widget.Snackbar
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.twobbble.R
import com.twobbble.application.App
import org.jetbrains.anko.find
import org.jetbrains.annotations.NotNull

/**
 * Created by liuzipeng on 2017/2/15.
 */

val View.ctx: Context
    get() = context

fun Activity.showSnackBar(view: View, msg: String, time: Int = Snackbar.LENGTH_SHORT) {
    Snackbar.make(view, msg, time).show()
}

fun Activity.showSnackBar(view: View, msg: String, time: Int = Snackbar.LENGTH_SHORT, actionMsg: String = "重试", action: (View) -> Unit) {
    Snackbar.make(view, msg, time).setAction(actionMsg, View.OnClickListener { action.invoke(view) }).show()
}

fun Fragment.showSnackBar(view: View, msg: String, time: Int = Snackbar.LENGTH_SHORT) {
    Snackbar.make(view, msg, time).show()
}

fun Fragment.showSnackBar(view: View, msg: String, time: Int = Snackbar.LENGTH_SHORT, actionMsg: String = "重试", action: (View) -> Unit) {
    Snackbar.make(view, msg, time).setAction(actionMsg, View.OnClickListener { action.invoke(view) }).show()
}

fun Any.log(msg: String?) {
    Log.d(this.javaClass.simpleName, msg)
}

fun Any.toast(msg: String?, length: Int? = Toast.LENGTH_SHORT) {
    Toast.makeText(App.instance, msg!!, length!!).show()
}

fun Any.toast(msg: Int?, length: Int? = Toast.LENGTH_SHORT) {
    Toast.makeText(App.instance, msg!!, length!!).show()
}

/**
 * 显示错误图片
 */
fun Fragment.showErrorImg(@NotNull linearLayout: LinearLayout, @NotNull msgResId: Int = R.string.no_shot, imgResID: Int = R.mipmap.img_empty_shots) {
    linearLayout.visibility = View.VISIBLE
    linearLayout.find<ImageView>(R.id.mErrorImg).setImageResource(imgResID)
    linearLayout.find<TextView>(R.id.mErrorText).setText(msgResId)
}

/**
 * 显示错误图片
 */
fun Fragment.showErrorImg(@NotNull linearLayout: LinearLayout, @NotNull msg: String, imgResID: Int = R.mipmap.img_empty_shots) {
    linearLayout.visibility = View.VISIBLE
    linearLayout.find<ImageView>(R.id.mErrorImg).setImageResource(imgResID)
    linearLayout.find<TextView>(R.id.mErrorText).text = msg
}

/**
 * 隐藏错无图片
 */
fun Fragment.hideErrorImg(@NotNull linearLayout: LinearLayout) {
    linearLayout.visibility = View.GONE
}