package com.twobbble.tools

import android.app.ActivityManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.location.LocationManager
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.net.Uri
import android.os.Build
import android.telephony.TelephonyManager
import android.text.TextUtils
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.KeyCharacterMap
import android.view.KeyEvent
import android.view.View
import android.view.ViewConfiguration
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import com.google.gson.internal.bind.util.ISO8601Utils
import com.twobbble.application.App
import org.jetbrains.anko.displayMetrics


import java.io.ByteArrayOutputStream
import java.io.File
import java.math.BigDecimal
import java.text.DecimalFormat
import java.text.ParseException
import java.text.ParsePosition
import java.text.SimpleDateFormat
import kotlin.concurrent.thread


/**
 * Created by liuzipeng on 16/7/8.
 */
object Utils {
    /**
     * 判断网络可不可用

     * @return true为可用
     */
    fun isNetworkAvailable(context: Context): Boolean {
        val cm: ConnectivityManager? = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val info = cm?.activeNetworkInfo
        if (info != null) {
            return info.isAvailable && info.isConnected
        }
        return false
    }

    /**
     * sp转px

     * @param sp
     * *
     * @param metrics
     * *
     * @return
     */
    fun sp2px(sp: Int): Float {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp.toFloat(), App.instance.displayMetrics)
    }


    /**
     * dp转px
     * @param dp
     * *
     * @param metrics
     * *
     * @return
     */
    fun dp2px(dp: Int): Float {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp.toFloat(), App.instance.displayMetrics)
    }

    fun dp2px(dp: Float): Float {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, App.instance.displayMetrics)
    }

    /**
     * 替换电话号码中段4位为·

     * @param phoneNumber
     * *
     * @return
     */
    fun encryptionPhoneNumber(phoneNumber: String): StringBuffer {
        val buffer = StringBuffer()
        for (i in 0..phoneNumber.length - 1) {
            var num = phoneNumber[i]
            if (i in 3..6) {
                num = '·'
            }
            buffer.append(num)
        }

        return buffer
    }

    /**
     * 判断某个服务是否正在运行的方法

     * @param mContext
     * *
     * @param serviceName 是包名+服务的类名（例如:net.loonggg.testbackstage.TestService）
     * *
     * @return true代表正在运行，false代表服务没有正在运行
     */
    fun isServiceWork(mContext: Context, serviceName: String): Boolean {
        val myAM = mContext
                .getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val myList = myAM.getRunningServices(Integer.MAX_VALUE)
        if (myList.size <= 0) {
            return false
        }
        val isWorked: Boolean? = myList.any { serviceName == it.service.className }
        return isWorked!!
    }

    /**
     * 激活receiver  用于对付安全软件的自启控制

     * @param context 上下文
     * *
     * @param name    要激活的receiver的名字--要带包名例:com.test.TimeReceiver
     */
    fun decide(context: Context, name: String) {
        val pm = context.packageManager
        val mComponentName = ComponentName(context, name)
        if (pm.getComponentEnabledSetting(mComponentName) != 1) {
            pm.setComponentEnabledSetting(mComponentName, PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                    PackageManager.DONT_KILL_APP)
        }
    }

    /**
     * 获取版本号

     * @param context
     * *
     * @return
     */
    fun getVersion(context: Context): String {
        try {
            val pi = context.packageManager.getPackageInfo(context.packageName, 0)
            return pi.versionName
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
            return "版本未知"
        }

    }

    /**
     * 获取版本代号

     * @param context
     * *
     * @return
     */
    fun getVersionCode(context: Context): Int {
        try {
            val pi = context.packageManager.getPackageInfo(context.packageName, 0)
            return pi.versionCode
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
            return 0
        }
    }

    //Android获取一个用于打开APK文件的intent
    fun openApk(context: Context, file: File) {
        val intent = Intent()
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.action = Intent.ACTION_VIEW
        val uri = Uri.fromFile(file)
        intent.setDataAndType(uri, "application/vnd.android.package-archive")
        context.startActivity(intent)
    }

    /**
     * 判断手机GPS是否可用

     * @param context
     * *
     * @return
     */
    fun isGpsEnable(context: Context): Boolean {
        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val gps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        val network = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
        if (gps || network) {
            return true
        }
        return false
    }

    /**
     * 隐藏软键盘

     * @param editText
     */
    fun hideKeyboard(editText: EditText) {
        val imm = editText
                .context.getSystemService(
                Context.INPUT_METHOD_SERVICE) as InputMethodManager
        if (imm.isActive) {
            imm.hideSoftInputFromWindow(
                    editText.applicationWindowToken, 0)
        }
    }

    /**
     * 获取imei

     * @param context
     * *
     * @return
     */
    fun getDeviceImei(context: Context): String {
        val telephonyManager = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        return telephonyManager.deviceId
    }

    /**
     * 获取手机号码

     * @param context
     * *
     * @return
     */
    fun getPhoneNumber(context: Context): String {
        val telephonyManager = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        return telephonyManager.line1Number.substring(3, 14)
    }

    /**
     * 判断该系统是否在5.0以上 是否支持Material Design

     * @return
     */
    fun supportMaterial(): Boolean {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            return false
        }

        return true
    }

    /**
     * 将16进制转为2进制

     * @param hexString
     * *
     * @return
     */
    fun convertHexToBinary2(hexString: String): String {
        val l = java.lang.Long.parseLong(hexString, 16)
        val binaryString = java.lang.Long.toBinaryString(l)
        val shouldBinaryLen = hexString.length * 4
        val addZero = StringBuffer()
        val addZeroNum = shouldBinaryLen - binaryString.length
        for (i in 1..addZeroNum) {
            addZero.append("0")
        }
        return addZero.toString() + binaryString
    }

    /**
     * 获取一个View的宽度

     * @param view
     * *
     * @return
     */
    fun getViewWidth(view: View): Int {
        view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED))
        return view.measuredWidth
    }

    /**
     * 获取一个View的高度

     * @param view
     * *
     * @return
     */
    fun getViewHeight(view: View): Int {
        view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED))
        return view.measuredHeight
    }

    /**
     * 保留一位小数

     * @param num
     * *
     * @return
     */
    fun keepOneDecimal(num: Double): String {
        val formater = DecimalFormat()
        formater.maximumFractionDigits = 1
        return formater.format(num)
    }

    /**
     * 格式化文件大小单位

     * @param size
     * *
     * @return
     */
    fun formatFileSize(size: Double): String {
        val kiloByte = size / 1024
        if (kiloByte < 1) {
            return "${size}Byte"
        }

        val megaByte = kiloByte / 1024
        if (megaByte < 1) {
            val result1 = BigDecimal(kiloByte.toString())
            return "${result1.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString()}KB"
        }

        val gigaByte = megaByte / 1024
        if (gigaByte < 1) {
            val result2 = BigDecimal(gigaByte.toString())
            return "${result2.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString()}MB"
        }

        val teraBytes = gigaByte / 1024
        if (teraBytes < 1) {
            val result3 = BigDecimal(teraBytes.toString())
            return "${result3.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString()}GB"
        }
        val result4 = BigDecimal(teraBytes)
        return "${result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString()}TB"
    }

    /**
     * 删除指定目录下文件及目录

     * @param deleteThisPath
     * *
     * @param filePath
     * *
     * @return
     */
    fun deleteFolderFile(filePath: String, deleteThisPath: Boolean) {
        Thread {
            if (!TextUtils.isEmpty(filePath)) {
                try {
                    val file = File(filePath)
                    if (file.isDirectory) {// 如果下面还有文件
                        val files = file.listFiles()
                        for (i in files.indices) {
                            deleteFolderFile(files[i].absolutePath, true)
                        }
                    }
                    if (deleteThisPath) {
                        if (!file.isDirectory) {// 如果是文件，删除
                            file.delete()
                        } else {// 目录
                            if (file.listFiles().isEmpty()) {// 目录下没有文件或者目录，删除
                                file.delete()
                            }
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }.start()
    }

    /**
     * 判断当前进程是否是主进程

     * @param context
     * *
     * @return
     */
    fun inMainProcess(context: Context): Boolean {
        val packageName = context.packageName
        val processName = Utils.getProcessName(context)
        return packageName == processName
    }

    /**
     * 获取当前进程名

     * @param context
     * *
     * @return 进程名
     */
    fun getProcessName(context: Context): String? {
        var processName: String? = null

        // ActivityManager
        val am = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager

        while (true) {
            for (info in am.runningAppProcesses) {
                if (info.pid == android.os.Process.myPid()) {
                    processName = info.processName
                    break
                }
            }

            // go home
            if (!TextUtils.isEmpty(processName)) {
                return processName
            }

            // take a rest and again
            try {
                Thread.sleep(100L)
            } catch (ex: InterruptedException) {
                ex.printStackTrace()
            }
        }
    }

    //将bitmap转换为byte[]格式
    fun bmpToByteArray(bitmap: Bitmap, needRecycle: Boolean): ByteArray {
        val output = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, output)
        if (needRecycle) {
            bitmap.recycle()
        }
        val result = output.toByteArray()
        try {
            output.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return result
    }

    fun hasNavigationBar(context: Context): Boolean {
        //通过判断设备是否有返回键、菜单键(不是虚拟键,是手机屏幕外的按键)来确定是否有navigation bar
        val hasMenuKey = ViewConfiguration.get(context)
                .hasPermanentMenuKey()
        val hasBackKey = KeyCharacterMap
                .deviceHasKey(KeyEvent.KEYCODE_BACK)

        return !hasMenuKey && !hasBackKey
    }

    fun formatDateUseCh(ms: Long): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd")
        return dateFormat.format(ms)
    }

    fun formatTimeUseCh(ms: Long): String {
        val dateFormat = SimpleDateFormat("HH:mm:ss")
        return dateFormat.format(ms)
    }

    fun parseISO8601(date: String): Long {
        return ISO8601Utils.parse(date, ParsePosition(0)).time
    }
}
