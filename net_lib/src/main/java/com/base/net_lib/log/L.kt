package com.base.net_lib.log

import android.annotation.SuppressLint
import android.os.Environment
import android.util.Log
import android.util.Log.*
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

object L {
    private const val TAG = "Logger"
    private val isDebug = true
    private var sCacheable = true

    /**
     * 设置一个总开关，决定是否缓存日志,前提条件是[this.isDebug]为true；
     *
     *
     * 如果[this.sCacheable]为false，即使用户调用了缓存日志的方法，缓存也不生效；
     *
     *
     * 如果[this.sCacheable]为true，那么是否缓存日志，取决于是否调用了缓存日志的方法；
     *
     * @param cacheable
     */
    fun initCacheSwitch(cacheable: Boolean) {
        sCacheable = cacheable
    }

    /**
     * 输出当前日志，并且定位代码行数；
     *
     * @param content
     */
    fun w(content: String) {
        if (isDebug) {
            log(TAG, content, Log.WARN)
        }
    }

    fun w(tag: String, content: String) {
        if (isDebug) {
            log(tag, content, Log.WARN)
        }
    }

    fun e(content: String) {
        if (isDebug) {
            log(TAG, content, Log.ERROR)
        }
    }

    fun e(tag: String, content: String) {
        if (isDebug) {
            log(tag, content, Log.ERROR)
        }
    }

    fun i(content: String) {
        if (isDebug) {
            log(TAG, content, Log.INFO)
        }
    }

    fun i(tag: String, content: String?) {
        if (isDebug) {
            log(tag, content, Log.INFO)
        }
    }

    fun v(content: String) {
        if (isDebug) {
            log(TAG, content, Log.VERBOSE)
        }
    }

    fun v(tag: String, content: String) {
        if (isDebug) {
            log(tag, content, Log.VERBOSE)
        }
    }

    fun d(content: String) {
        if (isDebug) {
            log(TAG, content, Log.DEBUG)
        }
    }

    fun d(tag: String, content: String) {
        if (isDebug) {
            log(tag, content, Log.DEBUG)
        }
    }


    private fun log(
        tag: String,
        content: String?,
        level: Int
    ) {
        logAndCache(tag, content, level)
    }


    private fun logAndCache(
        tag: String,
        content: String?,
        level: Int
    ) {
        logTraceInfo(tag, content, level)
    }

    /**
     * 获取相关调用栈的信息，并且打印相关日志及代码行数；
     *
     *
     *
     *
     * 相关调用栈的信息，按照:类名,方法名,行号等，这样的格式拼接，可以用来定位代码行，
     * 如：
     * at cn.xx.ui.MainActivity.onCreate(MainActivity.java:23) 定位代码行;
     *
     * @param tag
     * @param content
     * @param level    日志级别；
     * @param isLinked 是否输出所有相关调用栈的信息；
     */
    private fun logTraceInfo(
        tag: String,
        content: String?,
        level: Int
    ): String {
        val stes =
            Thread.currentThread().stackTrace
        if (stes == null) {
            Log.w(tag, "logTraceLinkInfo#return#stes == null")
            return ""
        }
        var result: StringBuilder? = null
        val sb = StringBuilder()
        for (i in stes.indices) {
            val ste = stes[i]
            if (ignorable(ste)) {
                continue
            }
            sb.append(ste.className)
                .append(" -> ").append(ste.methodName)
                .append("(").append(ste.fileName)
                .append(":").append(ste.lineNumber)
                .append(")]")
                .append("${tag}:")
                .append(content)
            val info = sb.toString()
            logInfo(TAG, level, info)
            return info
        }
        return result.toString()
    }

    private fun ignorable(ste: StackTraceElement): Boolean {
        return ste.isNativeMethod || ste.className == Thread::class.java.name || ste.className == L::class.java.name
    }

    private fun logInfo(tag: String, level: Int, info: String) {
        when (level) {
            ERROR -> Log.e(tag, info)
            INFO -> Log.i(tag, info)
            VERBOSE -> Log.v(tag, info)
            WARN -> Log.w(tag, info)
            DEBUG -> Log.d(tag, info)
            else -> Log.d(tag, info)
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun cacheLogInfo(info: String) {
        var info = info
        val dataFormat = SimpleDateFormat("yyyy-MM-dd_HH-mm-ss-SSS")
        val savePath: String = Environment.getExternalStorageDirectory()
            .toString() + File.separator.toString() + "ForLog"
        val saveFile = File(savePath)
        if (!saveFile.exists()) {
            saveFile.mkdir()
        }
        val file = File(saveFile, "log.txt")
        info = dataFormat.format(Date()).toString() + "：" + info + "\n\n"
        var fos: FileOutputStream? = null
        try {
            fos = FileOutputStream(file, true)
            val bytes = info.toByteArray()
            fos.write(bytes)
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            if (fos != null) {
                try {
                    fos.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
    }
}