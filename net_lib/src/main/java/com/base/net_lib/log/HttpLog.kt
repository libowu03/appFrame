package com.base.net_lib.log

import android.text.TextUtils
import okhttp3.Call
import okhttp3.Headers
import okhttp3.Request
import okhttp3.Response
import java.io.BufferedInputStream
import java.io.BufferedReader
import java.io.Reader
import java.lang.Exception

/**
 * 网络请求的日志输出
 */
object HttpLog {
    private val TAG = "NetHttp"

    /**
     * 获取请求头数据
     * @param header 头部数据
     */
    private fun header(header: Headers): String {
        val headerStringBuffer = StringBuffer("\n")
        header.forEach() {
            headerStringBuffer.append(it.first + ":" + it.second + "\n")
        }
        if (!TextUtils.isEmpty(headerStringBuffer.toString().replace("\n", ""))) {
            return headerStringBuffer.toString()
        } else {
            return ""
        }
    }

    /**
     * @param request 请求体
     * @param header 请求头参数
     * @param enableLog 是否输出日志
     */
    fun request(request: Request, header: Headers,enableLog:Boolean = true): String {
        val url = request.url.toUrl()
        val resultString = StringBuffer()
        val header = header(header)
        resultString.append("\n请求地址：${url}")
        if (header.isNotEmpty()){
            resultString.append("请 求 头：${header}")
        }
        if (enableLog){
            L.i(TAG,resultString.toString())
        }
        return resultString.toString()
    }

    /**
     * @param request 请求体
     * @param response 返回数据
     * @param enableLog 是否输出日志
     */
    fun response(request: Request, response: Response, result: String, enableLog:Boolean = true):String{
        val header = request.headers
        val url = request.url.toUrl()
        val resultBuffer = StringBuffer()
        var headerStr = header(header)
        if(headerStr.isNotEmpty()){
            headerStr = "请 求 头：\n${header(header)}\n"
        }else{
            headerStr = "请 求 头：\n"
        }
        if (result.isNotEmpty()) {
            resultBuffer.append("\n")
                .append("请求状态：${response.code}\n")
                .append("请求地址：${url}\n")
                .append(headerStr)
                .append("请求结果：\n${result}")
            if (enableLog){
                L.i(TAG, "${resultBuffer.toString()}")
            }
        }
        return resultBuffer.toString()
    }

    /**
     * @param request 请求体
     * @param response 返回数据
     * @param enableLog 是否输出日志
     */
    fun response(request: Request, response: Response, result: String, e:Exception){
        val header = request.headers
        val url = request.url.toUrl()
        val resultBuffer = StringBuffer()
        var headerStr = header(header)
        if(headerStr.isNotEmpty()){
            headerStr = "请 求 头：\n${header(header)}\n"
        }else{
            headerStr = "请 求 头：\n"
        }
        if (result.isNotEmpty()) {
            resultBuffer.append("\n")
                .append("请求状态：${response.code}\n")
                .append("解析失败：${e.localizedMessage}\n")
                .append("请求地址：${url}\n")
                .append(headerStr)
                .append("请求结果：\n${result}")
            L.i(TAG, "${resultBuffer.toString()}")
        }
    }

    /**
     * @param call 请求参数
     * @param e 错误信息
     */
    fun response(call: Call,e:Exception){
        val request = call.request()
        val header = request.headers
        val requestStr = request(request,header,false)
        val resultString = StringBuffer(requestStr)
        resultString.append("\n失败原因：${e.localizedMessage}")
        resultString.append("\n请求状态：失败")
        L.i(TAG,resultString.toString())
    }

}