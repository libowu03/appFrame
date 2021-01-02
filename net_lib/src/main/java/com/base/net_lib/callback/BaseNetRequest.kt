package com.base.net_lib.callback

import com.base.net_lib.parameter.HeaderParameter
import com.base.net_lib.parameter.HttpParameter
import com.base.net_lib.request.NetRequest
import okhttp3.Request
import java.util.concurrent.ConcurrentHashMap

interface BaseNetRequest<T> {
    fun put(key: String, value: Any): NetRequest<T>
    fun put(parameter: HttpParameter): NetRequest<T>
    fun put(parameterMap: ConcurrentHashMap<String, Any>): NetRequest<T>
    fun header(key: String, value: Any): NetRequest<T>
    fun header(header: HeaderParameter): NetRequest<T>
    fun header(headerMap: ConcurrentHashMap<String, Any>): NetRequest<T>
    fun execute(callback: JsonCallback<T>)
    fun enqueue(): T?
    fun configGetRequest(): Request.Builder
    fun getRequest(): Request?
}