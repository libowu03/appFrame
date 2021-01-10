package com.frame.main.net

import android.util.Log
import com.base.net_lib.NetHttp
import com.base.net_lib.log.L
import com.base.net_lib.parameter.HeaderParameter
import com.base.net_lib.parameter.HttpParameter
import com.frame.main.bean.ResultData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.Exception
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

object NetManager {

    /**
     * 获取get请求
     */
    suspend inline fun <reified T> getHttp(
        url: String,
        parameter: HttpParameter? = null,
        header: HeaderParameter? = null
    ) = suspendCoroutine<ResultData<T>> {
        val response = NetHttp.get<String>(url).put(parameter).header(header).enqueue()
        try {
            val type = object : TypeToken<T>() {}.type
            //如果需要的类型是string类型的，直接输出
            if (type.toString().equals("class java.lang.String")){
                it.resume(ResultData<T>(response?.body?.string() as T))
            }else{
                it.resume(ResultData(Gson().fromJson(response?.body?.charStream(), type)))
            }
        } catch (e: Exception) {
            e.printStackTrace()
            it.resume(ResultData<T>(null,response?.code?:-1,"",e.localizedMessage))
        }
    }

    suspend inline fun <reified T> requestFestival(): ResultData<T> {
        return getHttp("http://114.116.149.238:8080/getFestival")
    }
}