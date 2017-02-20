package com.twobbble.tools;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;


/**
 * Created by liuzipeng on 16/7/8.
 */

public class Utils {
    /**
     * 判断网络可不可用
     *
     * @return true为可用
     */
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm == null) {
            return false;
        } else {
            NetworkInfo info = cm.getActiveNetworkInfo();
            if (info != null) {
                return info.isAvailable() && info.isConnected();
            }
        }

        return false;
    }

    /**
     * sp转px
     *
     * @param sp
     * @param metrics
     * @return
     */
    public static float sp2px(int sp, DisplayMetrics metrics) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, metrics);
    }

    /**
     * dp转px
     *
     * @param dp
     * @param metrics
     * @return
     */
    public static float dp2px(int dp, DisplayMetrics metrics) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, metrics);
    }

    public static float dp2px(float dp, DisplayMetrics metrics) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, metrics);
    }

    /**
     * 替换电话号码中段4位为·
     *
     * @param phoneNumber
     * @return
     */
    public static StringBuffer encryptionPhoneNumber(String phoneNumber) {
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < phoneNumber.length(); i++) {
            char num = phoneNumber.charAt(i);
            if (i >= 3 && i < 7) {
                num = '·';
            }
            buffer.append(num);
        }

        return buffer;
    }

    /**
     * 判断某个服务是否正在运行的方法
     *
     * @param mContext
     * @param serviceName 是包名+服务的类名（例如:net.loonggg.testbackstage.TestService）
     * @return true代表正在运行，false代表服务没有正在运行
     */
    public static boolean isServiceWork(Context mContext, String serviceName) {
        Boolean isWorked = false;
        ActivityManager myAM = (ActivityManager) mContext
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> myList = myAM.getRunningServices(Integer.MAX_VALUE);
        if (myList.size() <= 0) {
            return false;
        }
        for (ActivityManager.RunningServiceInfo serviceInfo :
                myList) {
            if (serviceName.equals(serviceInfo.service.getClassName())) {
                isWorked = true;
            }
        }
        return isWorked;
    }

    /**
     * 激活receiver  用于对付安全软件的自启控制
     *
     * @param context 上下文
     * @param name    要激活的receiver的名字--要带包名例:com.test.TimeReceiver
     */
    public static void decide(Context context, String name) {
        PackageManager pm = context.getPackageManager();
        ComponentName mComponentName = new ComponentName(context, name);
        if (pm.getComponentEnabledSetting(mComponentName) != 1) {
            pm.setComponentEnabledSetting(mComponentName, PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                    PackageManager.DONT_KILL_APP);
        }
    }

    /**
     * 获取版本号
     *
     * @param context
     * @return
     */
    public static String getVersion(Context context) {
        try {
            PackageInfo pi = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return pi.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return "版本未知";
        }
    }

    /**
     * 获取版本代号
     *
     * @param context
     * @return
     */
    public static int getVersionCode(Context context) {
        try {
            PackageInfo pi = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return pi.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return 0;
        }
    }

    //Android获取一个用于打开APK文件的intent
    public static void openApk(Context context, File file) {
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Intent.ACTION_VIEW);
        Uri uri = Uri.fromFile(file);
        intent.setDataAndType(uri, "application/vnd.android.package-archive");
        context.startActivity(intent);
    }

    /**
     * 判断手机GPS是否可用
     *
     * @param context
     * @return
     */
    public static boolean isGpsEnable(Context context) {
        LocationManager locationManager
                = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        boolean gps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        boolean network = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        if (gps || network) {
            return true;
        }
        return false;
    }

    /**
     * 隐藏软键盘
     *
     * @param editText
     */
    public static void hideKeyboard(EditText editText) {
        InputMethodManager imm = (InputMethodManager) editText
                .getContext().getSystemService(
                        Context.INPUT_METHOD_SERVICE);
        if (imm.isActive()) {
            imm.hideSoftInputFromWindow(
                    editText.getApplicationWindowToken(), 0);
        }
    }

    /**
     * 获取imei
     *
     * @param context
     * @return
     */
    public static String getDeviceImei(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return telephonyManager.getDeviceId();
    }

    /**
     * 获取手机号码
     *
     * @param context
     * @return
     */
    public static String getPhoneNumber(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return telephonyManager.getLine1Number().substring(3, 14);
    }

    /**
     * 判断该系统是否在5.0以上 是否支持Material Design
     *
     * @return
     */
    public static boolean supportMaterial() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            return false;
        }

        return true;
    }

    /**
     * 将16进制转为2进制
     *
     * @param hexString
     * @return
     */
    public static String convertHexToBinary2(String hexString) {
        long l = Long.parseLong(hexString, 16);
        String binaryString = Long.toBinaryString(l);
        int shouldBinaryLen = hexString.length() * 4;
        StringBuffer addZero = new StringBuffer();
        int addZeroNum = shouldBinaryLen - binaryString.length();
        for (int i = 1; i <= addZeroNum; i++) {
            addZero.append("0");
        }
        return addZero.toString() + binaryString;
    }

    public static String getCouponType(int ctype) {
        switch (ctype) {
            case 0:
                return "折扣券";
            case 1:
                return "抵用券";
            case 2:
                return "礼品券";
            case 3:
                return "现金券";
            default:
                return "未知券";
        }
    }

    /**
     * 获取一个View的宽度
     *
     * @param view
     * @return
     */
    public static int getViewWidth(View view) {
        view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        return view.getMeasuredWidth();
    }

    /**
     * 获取一个View的高度
     *
     * @param view
     * @return
     */
    public static int getViewHeight(View view) {
        view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        return view.getMeasuredHeight();
    }

    /**
     * 保留一位小数
     *
     * @param num
     * @return
     */
    public static String keepOneDecimal(double num) {
        DecimalFormat formater = new DecimalFormat();
        formater.setMaximumFractionDigits(1);
        return formater.format(num);
    }

    /**
     * 格式化文件大小单位
     *
     * @param size
     * @return
     */
    public static String formatFileSize(double size) {
        double kiloByte = size / 1024;
        if (kiloByte < 1) {
            return size + "Byte";
        }

        double megaByte = kiloByte / 1024;
        if (megaByte < 1) {
            BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
            return result1.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "KB";
        }

        double gigaByte = megaByte / 1024;
        if (gigaByte < 1) {
            BigDecimal result2 = new BigDecimal(Double.toString(megaByte));
            return result2.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "MB";
        }

        double teraBytes = gigaByte / 1024;
        if (teraBytes < 1) {
            BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
            return result3.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "GB";
        }
        BigDecimal result4 = new BigDecimal(teraBytes);
        return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString()
                + "TB";
    }

    /**
     * 删除指定目录下文件及目录
     *
     * @param deleteThisPath
     * @param filePath
     * @return
     */
    public static void deleteFolderFile(String filePath, boolean deleteThisPath) {
        if (!TextUtils.isEmpty(filePath)) {
            try {
                File file = new File(filePath);
                if (file.isDirectory()) {// 如果下面还有文件
                    File files[] = file.listFiles();
                    for (int i = 0; i < files.length; i++) {
                        deleteFolderFile(files[i].getAbsolutePath(), true);
                    }
                }
                if (deleteThisPath) {
                    if (!file.isDirectory()) {// 如果是文件，删除
                        file.delete();
                    } else {// 目录
                        if (file.listFiles().length == 0) {// 目录下没有文件或者目录，删除
                            file.delete();
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 判断当前进程是否是主进程
     *
     * @param context
     * @return
     */
    public static boolean inMainProcess(Context context) {
        String packageName = context.getPackageName();
        String processName = Utils.getProcessName(context);
        return packageName.equals(processName);
    }

    /**
     * 获取当前进程名
     *
     * @param context
     * @return 进程名
     */
    public static String getProcessName(Context context) {
        String processName = null;

        // ActivityManager
        ActivityManager am = ((ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE));

        while (true) {
            for (ActivityManager.RunningAppProcessInfo info : am.getRunningAppProcesses()) {
                if (info.pid == android.os.Process.myPid()) {
                    processName = info.processName;
                    break;
                }
            }

            // go home
            if (!TextUtils.isEmpty(processName)) {
                return processName;
            }

            // take a rest and again
            try {
                Thread.sleep(100L);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }

    //将bitmap转换为byte[]格式
    public static byte[] bmpToByteArray(final Bitmap bitmap, final boolean needRecycle) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, output);
        if (needRecycle) {
            bitmap.recycle();
        }
        byte[] result = output.toByteArray();
        try {
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
