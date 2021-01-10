package com.base.net_lib.request

import com.base.net_lib.callback.BaseGetInterface
import com.base.net_lib.callback.BaseNetHeadInterface
import okhttp3.OkHttpClient
import okhttp3.Request

/**
 * head请求
 */
class NetHeadRequest<T>(url: String, okHttpClient: OkHttpClient?) :
    BaseNetRequest<T>(url, okHttpClient),
    BaseNetHeadInterface<T> {

    override fun getRequest(): Request? {
        mRequest?.head()
        return super.getRequest()
    }
}