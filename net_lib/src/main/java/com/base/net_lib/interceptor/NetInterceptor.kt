package com.base.net_lib.interceptor

import android.app.Application
import android.text.TextUtils
import androidx.datastore.core.DataStore
import androidx.datastore.createDataStore
import com.base.net_lib.constants.NetConstants.Cache.CACHE_KEY
import com.base.net_lib.constants.NetConstants.CacheModel.TYPE_NONE
import com.base.net_lib.constants.NetConstants.Code.NET_NORMAL
import com.base.net_lib.log.HttpLog
import com.base.net_lib.log.L
import com.base.net_lib.utils.Applications
import com.base.net_lib.utils.CacheUtils
import okhttp3.Interceptor
import okhttp3.Response
import java.util.prefs.Preferences

/**
 * 拦截器，用于拦截访问的请求，并通过日志形式输出在控制台
 */
class NetInterceptor : Interceptor {
    var cacheModel = TYPE_NONE
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val header = request.headers
        HttpLog.request(request,header)
        val response = chain.proceed(request)
        if (response.cacheResponse != null){
            L.i("日志","存在缓存")
        }
        val body = response.peekBody(1024*1024)
        HttpLog.response(request,response,body.string())
        return response.newBuilder().build()
    }



}