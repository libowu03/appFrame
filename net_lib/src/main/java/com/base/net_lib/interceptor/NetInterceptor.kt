package com.base.net_lib.interceptor

import android.text.TextUtils
import com.base.net_lib.log.HttpLog
import com.base.net_lib.log.L
import okhttp3.Interceptor
import okhttp3.Response

/**
 * 拦截器，用于拦截访问的请求，并通过日志形式输出在控制台
 */
class NetInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val header = request.headers
        HttpLog.request(request,header)
        val response = chain.proceed(request)
        //response?.body?.close()
        //Log.e("日志", "请求地址：\n$url")
        return response.newBuilder().build()
    }



}