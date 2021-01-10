package com.base.net_lib.request

import com.base.net_lib.callback.BaseGetInterface
import okhttp3.*


/**
 * 实际上get请求的类，也是给外面用的类
 */
class NetGetRequest<T>(url: String, okHttpClient: OkHttpClient?) : BaseNetRequest<T>(url, okHttpClient),BaseGetInterface<T> {

}