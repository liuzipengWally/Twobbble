package com.twobbble.tools

import android.app.Activity
import android.app.Fragment
import android.content.Context
import android.content.Intent
import android.os.Build
import android.speech.RecognizerIntent
import android.support.design.widget.Snackbar
import android.support.v4.app.ActivityCompat.startActivityForResult
import android.util.Log
import android.util.TypedValue
import android.view.*
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.twobbble.R
import com.twobbble.application.App
import org.jetbrains.anko.displayMetrics
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

fun Any.log(msg: String) {
    Log.d(this.javaClass.simpleName, msg)
}

fun Any.toast(msg: String, length: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(App.instance, msg, length).show()
}

fun Any.toast(msg: Int, length: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(App.instance, msg, length).show()
}

/**
 * 显示错误图片
 */
fun Fragment.showErrorImg(@NotNull viewGroup: ViewGroup, @NotNull msgResId: Int = R.string.no_shot, imgResID: Int = R.mipmap.img_empty_shots) {
    viewGroup.visibility = View.VISIBLE
    viewGroup.find<ImageView>(R.id.mErrorImg).setImageResource(imgResID)
    viewGroup.find<TextView>(R.id.mErrorText).setText(msgResId)
}

/**
 * 显示错误图片
 */
fun Fragment.showErrorImg(@NotNull viewGroup: ViewGroup, @NotNull msg: String, imgResID: Int = R.mipmap.img_empty_shots) {
    viewGroup.visibility = View.VISIBLE
    viewGroup.find<ImageView>(R.id.mErrorImg).setImageResource(imgResID)
    viewGroup.find<TextView>(R.id.mErrorText).text = msg
}

/**
 * 显示错误图片
 */
fun Activity.showErrorImg(@NotNull viewGroup: ViewGroup, @NotNull msgResId: Int = R.string.no_shot, imgResID: Int = R.mipmap.img_empty_shots) {
    viewGroup.visibility = View.VISIBLE
    viewGroup.find<ImageView>(R.id.mErrorImg).setImageResource(imgResID)
    viewGroup.find<TextView>(R.id.mErrorText).setText(msgResId)
}

/**
 * 显示错误图片
 */
fun Activity.showErrorImg(@NotNull viewGroup: ViewGroup, @NotNull msg: String, imgResID: Int = R.mipmap.img_empty_shots) {
    viewGroup.visibility = View.VISIBLE
    viewGroup.find<ImageView>(R.id.mErrorImg).setImageResource(imgResID)
    viewGroup.find<TextView>(R.id.mErrorText).text = msg
}

/**
 * 隐藏错误图片
 */
fun Fragment.hideErrorImg(@NotNull viewGroup: ViewGroup) {
    viewGroup.visibility = View.GONE
}

/**
 * 隐藏错误图片
 */
fun Activity.hideErrorImg(@NotNull viewGroup: ViewGroup) {
    viewGroup.visibility = View.GONE
}

inline fun Fragment.startSpeak() {
    //通过Intent传递语音识别的模式
    val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
    //语言模式和自由形式的语音识别
    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
    //提示语音开始
    intent.putExtra(RecognizerIntent.EXTRA_PROMPT, resources.getString(R.string.start_speak))
    //开始执行我们的Intent、语音识别
    startActivityForResult(intent, Constant.VOICE_CODE)
}

inline fun Activity.startSpeak() {
    //通过Intent传递语音识别的模式
    val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
    //语言模式和自由形式的语音识别
    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
    //提示语音开始
    intent.putExtra(RecognizerIntent.EXTRA_PROMPT, resources.getString(R.string.start_speak))
    //开始执行我们的Intent、语音识别
    startActivityForResult(intent, Constant.VOICE_CODE)
}

inline fun Context.hasNavigationBar(block: () -> Unit) {
    //通过判断设备是否有返回键、菜单键(不是虚拟键,是手机屏幕外的按键)来确定是否有navigation bar
    val hasMenuKey = ViewConfiguration.get(this)
            .hasPermanentMenuKey()
    val hasBackKey = KeyCharacterMap
            .deviceHasKey(KeyEvent.KEYCODE_BACK)

    if (!hasMenuKey && !hasBackKey) {
        block()
    }
}

inline fun Any.supportL(block: () -> Unit) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        block()
    }
}

/**
 * sp2px
 */
inline fun Int.sp2px(): Float = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, this.toFloat(), App.instance.displayMetrics)

/**
 * dp2px
 */
inline fun Int.dp2px(): Float = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, this.toFloat(), App.instance.displayMetrics)

/**
 * dp2px
 */
inline fun Float.dp2px(): Float = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, this, App.instance.displayMetrics)

/**
 * 当前网络是否可用
 */
inline fun Any.netAvailable(block: (a: Boolean) -> Unit) {
    if (Utils.isNetworkAvailable(App.instance)) block(true) else block(false)
}

/**
 * 获取字符串
 */
inline fun  Context.obtainString(resId: Int): String = this.resources.getString(resId)

/**
 * 获取颜色
 */
inline fun Context.obtainColor(resId: Int): Int = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
    this.resources.getColor(resId, null)
} else {
    this.resources.getColor(resId)
}