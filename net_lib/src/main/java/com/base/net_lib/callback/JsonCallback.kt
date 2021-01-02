package com.base.net_lib.callback

interface JsonCallback<T>  {
    fun onSuccess(result:T?)
    fun onError(msg:String)
}