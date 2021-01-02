package com.base.net_lib.utils

object UrlUtils {

    fun addUrlParameter(url:String,parameterName:String,parameter:String):String{
        var urlResult = StringBuffer(url)
        if (urlResult.indexOf("?")==-1){
            urlResult.append("?${parameterName}=${parameter}")
        }else{
            urlResult.append("&${parameterName}=${parameter}")
        }
        return urlResult.toString()
    }

}