package com.base.net_lib.request

import android.os.Environment
import com.base.net_lib.callback.BaseGetInterface
import com.base.net_lib.log.L
import com.base.net_lib.utils.Applications
import okhttp3.*


/**
 * 实际上get请求的类，也是给外面用的类
 */
class NetGetRequest<T>(url: String, okHttpClient: OkHttpClient?) : BaseNetRequest<T>(url, okHttpClient),BaseGetInterface<T> {

    override fun download(fileSavePath:String):NetGetRequest<T>{
        val savePath = Applications.context().getExternalFilesDir("Downloads")
        L.i("日志","储存路径：${savePath?.path}")
        return this
    }

}