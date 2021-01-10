package com.base.net_lib

import android.os.Environment
import android.os.Environment.*
import com.base.net_lib.interceptor.NetInterceptor
import com.base.net_lib.request.*
import com.base.net_lib.utils.Applications
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.File
import java.net.URL
import java.util.concurrent.TimeUnit

/**
 * 网络请求
 */
object NetHttp {
    private var mOkHttpClient: OkHttpClient? = null

    init {
        // 缓存目录

        // 缓存目录
        val file = File(Applications.context().cacheDir.path, "a_cache")
        // 缓存大小
        // 缓存大小
        val cacheSize = 10 * 1024 * 1024
        mOkHttpClient = OkHttpClient().newBuilder()
            //.cache(Cache (Applications.context().cacheDir, 10240 * 1024))
            .connectTimeout(10, TimeUnit.SECONDS)
            .addInterceptor(NetInterceptor())
            .cache(Cache(file, cacheSize.toLong()))
            .readTimeout(10, TimeUnit.SECONDS)
            .build();
    }

    /**
     * get请求
     * @param url 请求地址
     */
    fun <T> get(url: String) : NetGetRequest<T> {
        return NetGetRequest(url, mOkHttpClient)
    }

    /**
     * post请求
     * @param url 请求地址
     */
    fun <T> post(url:String):NetPostRequest<T>{
        return NetPostRequest(url, mOkHttpClient)
    }

    /**
     * put请求
     * @param url 请求地址
     */
    fun <T> put(url:String):NetPutRequest<T>{
        return NetPutRequest(url, mOkHttpClient)
    }

    /**
     * delete请求
     * @param url 请求地址
     */
    fun <T> delete(url:String):NetDeleteRequest<T>{
        return NetDeleteRequest(url, mOkHttpClient)
    }

    /**
     * header请求
     * @param url 请求地址
     */
    fun <T> header(url:String):NetHeadRequest<T>{
        return NetHeadRequest(url, mOkHttpClient)
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