package com.base.net_lib.callback

import com.base.net_lib.constants.NetConstants
import com.base.net_lib.constants.NetConstants.NetType.NET_TYPE_JSON
import com.base.net_lib.parameter.HttpParameter
import com.base.net_lib.request.NetPostRequest
import okhttp3.MultipartBody
import java.io.File

/**
 * post的接口
 */
interface BasePostInterface<T> {
    /**
     * 提交表单
     */
    fun form(key:String,value:String?):NetPostRequest<T>

    /**
     * 提交表单
     */
    fun form(http:HttpParameter?):NetPostRequest<T>


    /**
     * 上传json文件，如果是string类型，直接上传，如果是其他实体类 ,先转换成string类型的json
     * @param value 需要上传的json，可以是string，也可以是其他实体
     */
    fun json(value:Any?,uploadType: String = NET_TYPE_JSON):NetPostRequest<T>

    /**
     * 上传文件
     * @param path 文件地址
     * @param uploadType 上传时标记的类型
     */
    fun file(path:String?,uploadType:String= NetConstants.NetType.NET_TYPE_FILE):NetPostRequest<T>

    /**
     * 上传混合表单
     */
    fun multipartBody(body: MultipartBody?):NetPostRequest<T>
}