package com.base.net_lib.callback

/**
 * get请求基础接口
 */
interface BaseGetInterface<T> {
    fun download(savePath:String = ""):BaseGetInterface<T>
}