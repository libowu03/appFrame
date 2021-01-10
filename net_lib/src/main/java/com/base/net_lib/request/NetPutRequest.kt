package com.base.net_lib.request

import com.base.net_lib.callback.BaseNetPutlInterface
import com.base.net_lib.constants.NetConstants
import com.base.net_lib.log.L
import com.google.gson.Gson
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.lang.Exception

/**
 * put请求
 */
class NetPutRequest<T>(url: String, okHttpClient: OkHttpClient?) :
    BaseNetRequest<T>(url, okHttpClient), BaseNetPutlInterface<T> {
    private val mForm = FormBody.Builder()
    private val mMultipartBody = MultipartBody.Builder()

    /**
     * 提交表单
     * @param key 键
     * @param value 值
     */
    override fun form(key: String, value: String?): NetPutRequest<T> {
        value?.let {
            mForm.add(key, value)
        }
        return this
    }

    /**
     * 上传json，如果是string类型，直接上传，如果是其他实体类 ,先转换成string类型的json
     * @param value 需要上传的json，可以是string，也可以是其他实体
     */
    override fun json(value: Any?, uploadType: String) : NetPutRequest<T>{
        val jsonType: MediaType? = uploadType.toMediaTypeOrNull()
        var jsonStr: String? = null
        try {
            //如果不是string类型的，先转换成string类型的数据
            if (value is String) {
                jsonStr = value
            } else {
                val gson = Gson()
                jsonStr = gson.toJson(value)
            }
        } catch (e: Exception) {
            L.e(NetConstants.LogTag.NET_HTTP_ERROR, e.localizedMessage)
        }
        jsonStr?.let {
            mRequest?.post(it.toRequestBody(jsonType))
        }
        return this
    }

    /**
     * 上传文件
     * @param path 上传文件所在地址
     */
    override fun file(path: String?, uploadType: String): NetPutRequest<T> {
        val file = File(path)
        if (!file.exists()) {
            L.w(NetConstants.LogTag.NET_HTTP_WARN, "“${path}”文件不存在，请确认")
            return this
        }
        val fileBody = file.asRequestBody(uploadType.toMediaTypeOrNull())
        mMultipartBody.addFormDataPart("file", file.name, fileBody)
        return this
    }

    /**
     * 上传混合表单
     */
    override fun multipartBody(body: MultipartBody?): NetPutRequest<T> {
        body?.let {
            mMultipartBody.addPart(body)
        }
        return this
    }


    override fun getRequest(): Request? {
        mUrl = mHttpParameter.configParameter(mUrl)
        mHeaderParameter.configHeaderParameter(mRequest)
        mRequest?.url(mUrl)
        mRequest?.put(mForm.build())
        mRequest?.put(mMultipartBody.build())
        return super.getRequest()
    }
}