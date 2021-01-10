package com.base.net_lib.callback

import com.base.net_lib.parameter.HeaderParameter
import com.base.net_lib.parameter.HttpParameter
import com.base.net_lib.request.BaseNetRequest
import okhttp3.CacheControl
import okhttp3.Request
import okhttp3.Response
import java.util.concurrent.ConcurrentHashMap

/**
 * 基础接口，也是get的接口
 */
interface BaseNetRequestInte<T> {
    fun put(key: String, value: Any?): BaseNetRequest<T>
    fun put(parameter: HttpParameter?): BaseNetRequest<T>
    fun put(parameterMap: ConcurrentHashMap<String, Any>): BaseNetRequest<T>
    fun header(key: String, value: Any?): BaseNetRequest<T>
    fun header(header: HeaderParameter?): BaseNetRequest<T>
    fun header(headerMap: ConcurrentHashMap<String, Any>): BaseNetRequest<T>
    fun execute(callback: JsonCallback<T>)
    fun enqueue(): Response?
    fun cacheTime(cacheTime:Int): BaseNetRequest<T>
}