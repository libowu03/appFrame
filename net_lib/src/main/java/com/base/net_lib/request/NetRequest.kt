package com.base.net_lib.request

import com.base.net_lib.callback.BaseNetRequest
import com.base.net_lib.callback.JsonCallback
import com.base.net_lib.log.HttpLog
import com.base.net_lib.parameter.HeaderParameter
import com.base.net_lib.parameter.HttpParameter
import com.google.gson.Gson
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
    override fun put(key: String, value: Any): NetRequest<T> {
        mHttpParameter.put(key, value)
        return this
    }

    /**
     * 设置请求参数
     * @param parameter 请求参数
     */
    override fun put(parameter: HttpParameter): NetRequest<T> {
        mHttpParameter.put(parameter)
        return this
    }

    /**
     * 通过map添加请求参数
     * @param parameterMap 请求参数
     */
    override fun put(parameterMap: ConcurrentHashMap<String, Any>): NetRequest<T> {
        mHttpParameter.put(parameterMap)
        return this
    }

    /**
     * 添加头部信息
     * @param key 头部请求参数的key
     * @param value 头部请求参数的值
     */
    override fun header(key: String, value: Any): NetRequest<T> {
        mHeaderParameter.put(key, value)
        return this
    }

    /**
     * 添加头部参数
     * @param header 头部请求参数
     */
    override fun header(header: HeaderParameter): NetRequest<T> {
        mHeaderParameter.put(header)
        return this
    }

    /**
     * 添加头部参数
     * @param header 头部请求参数
     */
    override fun header(headerMap: ConcurrentHashMap<String, Any>): NetRequest<T> {
        mHeaderParameter.put(headerMap)
        return this
    }

    /**
     * 异步请求
     */
    override fun execute(callback: JsonCallback<T>) {
        val request = getRequest()
        request?.let {
            mOkHttpClient?.newCall(it)?.enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    HttpLog.response(call, e)
                    callback.onError(e.localizedMessage)
                }

                override fun onResponse(call: Call, response: Response) {
                    val reader = response.body?.string()
                    try {
                        var data: T?
                        val gson = Gson()
                        val genType = callback::class.java.genericInterfaces[0]
                        val params = (genType as ParameterizedType).actualTypeArguments[0]
                        val n = params.toString()
                        if (n == "class java.lang.String"){
                            callback.onSuccess(reader as T)
                        }else{
                            data = gson.fromJson(reader, params)
                            callback.onSuccess(data)
                        }
                        HttpLog.response(call.request(), response, reader ?: "")
                    } catch (e: Exception) {
                        callback.onError(e.localizedMessage)
                        HttpLog.response(call.request(), response, reader ?: "", e)
                    }
                }
            })
        }
    }

    /**
     * 同步请求
     */
    override fun enqueue():T?{
        val request = getRequest()
        request?.let {
            var data: T?
            val gson = Gson()
            val genType = javaClass.genericSuperclass
            val params = (genType as ParameterizedType).actualTypeArguments[0]
            val n = params.toString()

            val result = mOkHttpClient?.newCall(it)?.execute()?.body?.string()?:""

            return null
        }?:let {
            return null
        }
    }

    /**
     * 配置请求参数
     * @param url 请求地址
     */
    override fun configGetRequest(): Request.Builder {
        return Request.Builder()

    }

    /**
     * 获取request
     */
    override fun getRequest():Request?{
        mUrl = mHttpParameter.configParameter(mUrl)
        mHeaderParameter.configHeaderParameter(mRequest)
        mRequest?.url(mUrl)
        return mRequest?.url(mUrl)?.build()
    }
}