package com.base.net_lib.request

import com.base.net_lib.callback.BaseNetDeleteInterface
import com.base.net_lib.callback.BaseNetHeadInterface
import okhttp3.OkHttpClient

/**
 * delete请求
 */
class NetDeleteRequest<T>(url: String, okHttpClient: OkHttpClient?) :
    BaseNetRequest<T>(url, okHttpClient),
    BaseNetDeleteInterface<T> {


}