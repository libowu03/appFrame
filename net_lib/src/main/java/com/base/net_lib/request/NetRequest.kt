package com.base.net_lib.request

import com.base.net_lib.callback.JsonCallback
import com.base.net_lib.log.HttpLog
import com.base.net_lib.parameter.HeaderParameter
import com.base.net_lib.parameter.HttpParameter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.*
import java.io.IOException
import java.lang.reflect.ParameterizedType
import java.util.concurrent.ConcurrentHashMap

/**
 * 设置请求参数以及执行请求的地方
 */
class NetRequest<T> : BaseNetRequest<T>{
    private var mRequest: Request.Builder? = null
    private var mUrl: String = ""
    private var mHttpParameter: HttpParameter = HttpParameter()
    private var mHeaderParameter: HeaderParameter = HeaderParameter()
    private var mOkHttpClient: OkHttpClient? = null

    constructor(url: String, okHttpClient: OkHttpClient?) {
        mUrl = url
        mOkHttpClient = okHttpClient
        mRequest = configGetRequest()
    }

    constructor(url: String, okHttpClient: OkHttpClient?, callback: (result: T) -> Unit) {
        mUrl = url
        mOkHttpClient = okHttpClient
        mRequest = configGetRequest()
    }

    init {
        mRequest = configGetRequest()
    }

    /**
     * 设置请求参数
     * @param key 参数的key
     * @param value 请求参数的值
     */
    fun put(key: String, value: Any): NetRequest<T> {
        mHttpParameter.put(key, value)
        return this
    }

    /**
     * 设置请求参数
     * @param parameter 请求参数
     */
    fun put(parameter: HttpParameter): NetRequest<T> {
        mHttpParameter.put(parameter)
        return this
    }

    /**
     * 通过map添加请求参数
     * @param parameterMap 请求参数
     */
    fun put(parameterMap: ConcurrentHashMap<String, Any>): NetRequest<T> {
        mHttpParameter.put(parameterMap)
        return this
    }

    /**
     * 添加头部信息
     * @param key 头部请求参数的key
     * @param value 头部请求参数的值
     */
    fun header(key: String, value: Any): NetRequest<T> {
        mHeaderParameter.put(key, value)
        return this
    }

    /**
     * 添加头部参数
     * @param header 头部请求参数
     */
    fun header(header: HeaderParameter): NetRequest<T> {
        mHeaderParameter.put(header)
        return this
    }

    /**
     * 添加头部参数
     * @param header 头部请求参数
     */
    fun header(headerMap: ConcurrentHashMap<String, Any>): NetRequest<T> {
        mHeaderParameter.put(headerMap)
        return this
    }

    /**
     * 异步请求
     */
    fun execute(/*callback: (result: T?) -> Unit*/callback:JsonCallback<T>) {
        val request = getRequest()
        request?.let {
            mOkHttpClient?.newCall(it)?.enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    HttpLog.response(call, e)
                }

                override fun onResponse(call: Call, response: Response) {
                    val reader = response.body?.string()
                    try {
                        var data: T? = null
                        val gson = Gson()
                        //val genType = callback::class.java.genericInterfaces[0]
                        val type = this@NetRequest::class.java.genericSuperclass
                        //data = gson.fromJson(reader, type)
                        //callback.invoke(data)
                        HttpLog.response(call.request(), response, reader ?: "")
                    } catch (e: Exception) {
                        HttpLog.response(call.request(), response, reader ?: "", e)
                    }
                }
            })
        }
    }

    /**
     * 同步请求
     */
    fun enqueue():String?{
        val request = getRequest()
        request?.let {
            return mOkHttpClient?.newCall(it)?.execute()?.body?.string()?:""
        }?:let {
            return null
        }
    }

    /**
     * 配置请求参数
     * @param url 请求地址
     */
    private fun configGetRequest(): Request.Builder {
        return Request.Builder()

    }

    /**
     * 获取request
     */
    private fun getRequest():Request?{
        mUrl = mHttpParameter.configParameter(mUrl)
        mHeaderParameter.configHeaderParameter(mRequest)
        mRequest?.url(mUrl)
        return mRequest?.url(mUrl)?.build()
    }
}