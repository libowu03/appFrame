package com.base.net_lib

import android.util.Log
import com.base.net_lib.interceptor.NetInterceptor
import com.base.net_lib.request.NetRequest
import okhttp3.*
import java.io.IOException
import java.net.URL
import java.util.concurrent.TimeUnit

/**
 * 网络请求
 */
object NetHttp {
    private var mOkHttpClient: OkHttpClient? = null

    init {
        mOkHttpClient = OkHttpClient().newBuilder()
            //.cache(Cache (Applications.context().cacheDir, 10240 * 1024))
            .connectTimeout(10, TimeUnit.SECONDS)
            .addInterceptor(NetInterceptor())
            //.cache(cache)
            .readTimeout(10, TimeUnit.SECONDS)
            .build();
    }

    /**
     * 同步请求网络数据
     * @param url 请求地址
     */
    fun <T> get(url: String) : NetRequest<T> {
        return NetRequest(url, mOkHttpClient)
    }

    /**
     * 配置请求参数
     * @param url 请求地址
     */
    private fun configGetReuqest(url: String): Request {
        val url = URL("http")
        return Request.Builder()
            .url(url)
            .build()
    }

}