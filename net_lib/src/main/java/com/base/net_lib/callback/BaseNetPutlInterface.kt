package com.base.net_lib.callback

import com.base.net_lib.constants.NetConstants
import com.base.net_lib.request.NetPostRequest
import com.base.net_lib.request.NetPutRequest
import okhttp3.MultipartBody

interface BaseNetPutlInterface<T> {
    /**
     * 提交表单
     */
    fun form(key:String,value:String?): NetPutRequest<T>

    /**
     * 上传json文件，如果是string类型，直接上传，如果是其他实体类 ,先转换成string类型的json
     * @param value 需要上传的json，可以是string，也可以是其他实体
     */
    fun json(value:Any?,uploadType: String = NetConstants.NetType.NET_TYPE_JSON): NetPutRequest<T>

    /**
     * 上传文件
     * @param path 文件地址
     * @param uploadType 上传时标记的类型
     */
    fun file(path:String?,uploadType:String= NetConstants.NetType.NET_TYPE_FILE): NetPutRequest<T>

    /**
     * 上传混合表单
     */
    fun multipartBody(body: MultipartBody?): NetPutRequest<T>
}