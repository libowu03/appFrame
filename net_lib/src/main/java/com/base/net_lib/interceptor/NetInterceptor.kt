package com.base.net_lib.interceptor

import android.text.TextUtils
import android.util.Log
import com.base.net_lib.log.NetLog
import okhttp3.Interceptor
import okhttp3.Response

/**
 * 拦截器，用于拦截访问的请求，并通过日志形式输出在控制台
 */
class NetInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)
        val header = request.headers
        val url = request.url.toUrl()
        val headerStringBuffer = StringBuffer("\n")
        header.forEach(){
            headerStringBuffer.append(it.first+":"+it.second+"\n")
        }
        //Log.e("日志","请求头：${headerStringBuffer.toString()}")
        NetLog.e("日志","请求头：${headerStringBuffer.toString()}")
        NetLog.e("日志","请求地址：${url}")
        //Log.e("日志", "请求地址：\n$url")
        return response.newBuilder().build()

    }

}