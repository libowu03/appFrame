package com.base.net_lib.parameter

import okhttp3.Request
import java.util.concurrent.ConcurrentHashMap

/**
 * 头部请求参数
 */
class HeaderParameter {
    private val mParameterMap: ConcurrentHashMap<String, Any> = ConcurrentHashMap()

    /**
     * 通过key添加参数
     * @param key 参数的键
     * @param value 键对应的值
     */
    fun put(key:String,value:Any?):HeaderParameter{
        if (key.isEmpty()){
            return this
        }
        value?.let {
            mParameterMap[key] = it
        }
        return this
    }

    /**
     * 通过HeaderParameter对象添加参数
     * @param parameter 参数内容
     */
    fun put(parameter:HeaderParameter?):HeaderParameter{
        parameter?.let {
            mParameterMap.putAll(it.getParameterMap())
        }
        return this
    }

    /**
     * 通过map添加参数
     * @param parameterMap 参数map
     */
    fun put(parameterMap: ConcurrentHashMap<String, Any>?):HeaderParameter{
        parameterMap?.let {
            mParameterMap.putAll(it)
        }
        return this
    }

    /**
     * 删除参数
     * @param key 将要删除的键
     */
    fun remove(key:String):HeaderParameter{
        mParameterMap.remove(key)
        return this
    }

    /**
     * 添加头部参数
     */
    fun configHeaderParameter(requestBuilder:Request.Builder?){
        mParameterMap.forEach(){ item->
            requestBuilder?.header(item.key,item.value.toString())
        }
    }

    /**
     * 清空所有请求参数
     */
    fun clear(){
        mParameterMap.clear()
    }

    private fun getParameterMap(): ConcurrentHashMap<String, Any> {
        return mParameterMap
    }
}