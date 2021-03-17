package com.base.net_lib.parameter

import com.base.net_lib.utils.UrlUtils
import okhttp3.HttpUrl
import okhttp3.Request
import java.util.concurrent.ConcurrentHashMap

/**
 * url请求参数
 */
class HttpParameter {
    private val mParameterMap: ConcurrentHashMap<String,Any> = ConcurrentHashMap()

    /**
     * 通过key添加参数
     * @param key 参数的键
     * @param value 键对应的值
     */
    fun put(key:String,value:Any?):HttpParameter{
        if (key.isEmpty()){
            return this
        }
        value?.let {
            mParameterMap[key] = it
        }
        return this
    }

    /**
     * 通过HttpParameter对象添加参数
     * @param parameter 参数内容
     */
    fun put(parameter:HttpParameter?):HttpParameter{
        parameter?.let {
            mParameterMap.putAll(it.getParameterMap())
        }
        return this
    }

    /**
     * 通过map添加参数
     * @param parameterMap 参数map
     */
    fun put(parameterMap:ConcurrentHashMap<String,Any>?):HttpParameter{
        parameterMap?.let {
            mParameterMap.putAll(it)
        }
        return this
    }

    /**
     * 删除参数
     * @param key 将要删除的键
     */
    fun remove(key:String):HttpParameter{
        mParameterMap.remove(key)
        return this
    }

    /**
     * 给url添加参数
     */
    fun configParameter(url:String):String{
        var resultUrl = url
        mParameterMap.forEach(){
            resultUrl = UrlUtils.addUrlParameter(resultUrl,it.key,it.value.toString())
        }
        return resultUrl
    }

    /**
     * 清空所有请求参数
     */
    fun clear(){
        mParameterMap.clear()
    }

    fun getParameterMap():ConcurrentHashMap<String,Any>{
        return mParameterMap
    }
}