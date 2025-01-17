package com.frame.main.ext

import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.os.Build
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.WindowInsetsController.APPEARANCE_LIGHT_NAVIGATION_BARS
import android.view.WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
import android.view.WindowManager
import android.widget.AutoCompleteTextView
import androidx.fragment.app.Fragment
import com.frame.main.constant.Constants
import com.frame.main.constant.Constants.IntentValue.ARGUMENTS_KEY
import com.frame.main.constant.Constants.IntentValue.INTENT_KEY
import com.frame.main.utils.IntentHelper

fun setStatusBarHidden(activity: Activity, isHidden: Boolean, isDark: Boolean = true) {
    if (isHidden) {
        val window = activity.window
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    // 通知视窗，我们（应用）会处理任何系统视窗（而不是 decor）
                    window.setDecorFitsSystemWindows(false)
                    if (isDark) {
                        window?.insetsController?.setSystemBarsAppearance(
                            0,
                            APPEARANCE_LIGHT_STATUS_BARS
                        )
                    } else {
                        window?.insetsController?.setSystemBarsAppearance(
                            APPEARANCE_LIGHT_STATUS_BARS,
                            APPEARANCE_LIGHT_STATUS_BARS
                        )
                    }
                } else {
                    val option =
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    var vis =
                        window.decorView.systemUiVisibility and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                    if (isDark) {
                        vis = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
                    } else {
                        //非沉浸式
                        vis = View.SYSTEM_UI_FLAG_VISIBLE;
                    }
                    window.decorView.systemUiVisibility = vis or option
                }
            } else {
                val option =
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                window.decorView.systemUiVisibility = option
            }
            window.statusBarColor = Color.TRANSPARENT
        } else {
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        }
    }
}

fun setStatusBarColor(activity: Activity, color: Int) {
    if (color == -1) {
        return
    }
    //setStatusBarHidden(activity, true)
    val parent = activity.findViewById<View>(android.R.id.content) as ViewGroup
    val statusBarView = parent.findViewWithTag<View>("TAG_STATUS_BAR")
    if (statusBarView != null) {
        statusBarView.visibility = View.VISIBLE
        statusBarView.setBackgroundColor(color)
    } else {
        val statusBarView = View(activity)
        statusBarView.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, getStatusBarHeightExt()
        )
        statusBarView.setBackgroundColor(color)
        statusBarView.tag = "TAG_STATUS_BAR"
        parent.addView(statusBarView)
    }
}

/** 状态栏高度 */
fun getStatusBarHeightExt(): Int {
    val resources = Resources.getSystem()
    val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
    return resources.getDimensionPixelSize(resourceId)
}

/**
 * 跳转到activity
 * @param clazz 类
 * @param flag 调整模式
 * @param requestCode 请求码
 */
fun Activity.sendToActivity(
    clazz: Class<*>,
    flag: Int = -1,
    requestCode: Int = -1
): IntentHelper.IntentBuilder {
    return IntentHelper.sendToActivity(this, clazz, flag, requestCode)
}

/**
 * 跳转到activity
 * @param clazz 类
 * @param flag 调整模式
 * @param requestCode 请求码
 */
fun Context.sendToActivity(
    clazz: Class<*>,
    flag: Int = -1,
    requestCode: Int = -1
): IntentHelper.IntentBuilder {
    return IntentHelper.sendToActivity(this, clazz, flag, requestCode)
}

/**
 * 跳转到activity
 * @param clzzName 类名
 * @param flag 调整模式
 * @param requestCode 请求码
 */
fun Activity.sendToActivity(
    clzzName: String,
    flag: Int = -1,
    requestCode: Int = -1
): IntentHelper.IntentBuilder {
    return IntentHelper.sendToActivity(this, clzzName, flag, requestCode)
}

/**
 * 跳转到activity
 * @param clzzName 类名
 * @param flag 调整模式
 * @param requestCode 请求码
 */
fun Context.sendToActivity(
    clzzName: String,
    flag: Int = -1,
    requestCode: Int = -1
): IntentHelper.IntentBuilder {
    return IntentHelper.sendToActivity(this, clzzName, flag, requestCode)
}

fun <T> Activity.getBundleValue(key: String, default: T): T {
    val bundle = intent.getBundleExtra(Constants.IntentValue.INTENT_KEY)
    return IntentHelper.get(bundle, key, default)
}

/**
 * 跳转到activity
 * @param clazz 类
 * @param flag 调整模式
 * @param requestCode 请求码
 */
fun Fragment.sendToActivity(
    context: Context?,
    clazz: Class<*>,
    flag: Int = -1,
    requestCode: Int = -1
): IntentHelper.IntentBuilder {
    return IntentHelper.sendToActivity(context, clazz, flag, requestCode)
}

/**
 * 跳转到activity
 * @param clzzName 类明
 * @param flag 调整模式
 * @param requestCode 请求码
 */
fun Fragment.sendToActivity(
    context: Context?,
    clzzName: String,
    flag: Int = -1,
    requestCode: Int = -1
): IntentHelper.IntentBuilder {
    return IntentHelper.sendToActivity(context, clzzName, flag, requestCode)
}

/**
 * 获取activity
 */
fun <T> Activity.getValue(key: String, value: T): T {
    val bundle = this.intent.getBundleExtra(INTENT_KEY)
    bundle?.let {
        return IntentHelper.get(bundle, key, value)
    } ?: let {
        return value
    }
}

fun <T> Fragment.getValue(key: String, value: T): T? {
    val bundle = this.activity?.intent?.getBundleExtra(INTENT_KEY)
    bundle?.let {
        return IntentHelper.get(bundle, key, value)
    } ?: let {
        return null
    }
}

fun <T> Fragment.getArgumentsValue(key: String, value: T): T? {
    val bundle = this.arguments?.getBundle(ARGUMENTS_KEY)
    bundle?.let {
        return IntentHelper.get(bundle, key, value)
    } ?: let {
        return null
    }
}


//region 像素转换
fun dp2px(dpValue: Float): Int {
    return (0.5f + dpValue * Resources.getSystem().displayMetrics.density).toInt()
}

fun px2dp(px: Float): Int {
    val scale = kotlin.math.max(Resources.getSystem().displayMetrics.density, 1f)
    val dp = 0.5f + px / scale
    return dp.toInt()
}

