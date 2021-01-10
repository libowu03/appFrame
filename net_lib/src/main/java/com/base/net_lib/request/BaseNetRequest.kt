package com.base.net_lib.request

import com.base.net_lib.callback.BaseNetRequestInte
import com.base.net_lib.callback.JsonCallback
import com.base.net_lib.constants.NetConstants.CacheModel.TYPE_NONE
import com.base.net_lib.log.HttpLog
import com.base.net_lib.parameter.HeaderParameter
import com.base.net_lib.parameter.HttpParameter
import com.google.gson.Gson
import okhttp3.*
import java.io.IOException
import java.lang.reflect.ParameterizedType
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.TimeUnit

/**
 * 基础请求，功能相当于get请求，不过对外一般使用NetGetRequest方法进行请求，而不是使用该类进行请求
 */
open class BaseNetRequest<T> : BaseNetRequestInte<T> {
    protected var mRequest: Request.Builder? = null
    protected var mUrl: String = ""
    protected var mHttpParameter: HttpParameter = HttpParameter()
    protected var mHeaderParameter: HeaderParameter = HeaderParameter()
    protected var mOkHttpClient: OkHttpClient? = null
    protected var mCacheTime:Long = 0L
    protected var mCacheControlBuilder:CacheControl.Builder?=null

    constructor(url: String, okHttpClient: OkHttpClient?) {
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
    override fun put(key: String, value: Any?): BaseNetRequest<T> {
        value?.let {
            mHttpParameter.put(key, value)
        }
        return this
    }

    /**
     * 设置请求参数
     * @param parameter 请求参数
     */
    override fun put(parameter: HttpParameter?): BaseNetRequest<T> {
        mHeaderParameter?.let {
            mHttpParameter.put(parameter)
        }
        return this
    }

    /**
     * 通过map添加请求参数
     * @param parameterMap 请求参数
     */
    override fun put(parameterMap: ConcurrentHashMap<String, Any>): BaseNetRequest<T> {
        mHttpParameter.put(parameterMap)
        return this
    }

    /**
     * 添加头部信息
     * @param key 头部请求参数的key
     * @param value 头部请求参数的值
     */
    override fun header(key: String, value: Any?): BaseNetRequest<T> {
        value?.let {
            mHeaderParameter.put(key, value)
        }
        return this
    }

    /**
     * 添加头部参数
     * @param header 头部请求参数
     */
    override fun header(header: HeaderParameter?): BaseNetRequest<T> {
        header?.let {
            mHeaderParameter.put(header)
        }
        return this
    }

    /**
     * 添加头部参数
     * @param header 头部请求参数
     */
    override fun header(headerMap: ConcurrentHashMap<String, Any>): BaseNetRequest<T> {
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
                        if (n == "class java.lang.String") {
                            callback.onSuccess(reader as T)
                        } else {
                            data = gson.fromJson(reader, params)
                            callback.onSuccess(data)
                        }
                        //HttpLog.response(call.request(), response, reader ?: "")
                    } catch (e: Exception) {
                        callback.onError(e.localizedMessage)
                        //HttpLog.response(call.request(), response, reader ?: "", e)
                    }
                }
            })
        }
    }

    /**
     * 同步请求
     */
    override fun enqueue(): Response? {
        val request = getRequest()
        request?.let {
            //读取缓存，如果存在缓存，且缓存在有效期内，直接返回即可
            return mOkHttpClient?.newCall(it)?.execute()
        } ?: let {
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
    override fun getRequest(): Request? {
        mUrl = mHttpParameter.configParameter(mUrl)
        mHeaderParameter.configHeaderParameter(mRequest)
        mRequest?.url(mUrl)
        return mRequest?.url(mUrl)?.build()
    }

    /**
     * 缓存时间
     */
    override fun cacheTime(cacheTime: Long): BaseNetRequest<T> {
        this.mCacheTime = cacheTime
        mCacheControlBuilder?.maxAge(TYPE_NONE, TimeUnit.MILLISECONDS)
        mCacheControlBuilder?.maxAge(TYPE_NONE, TimeUnit.MILLISECONDS)
        return this
    }

    override fun createCacheControllerBuilder(): CacheControl.Builder {
        if (mCacheControlBuilder == null){
            mCacheControlBuilder = CacheControl.Builder()
        }
        return mCacheControlBuilder!!
    }
}