package com.base.net_lib.constants

object NetConstants {

    object LogTag{
        const val NET_HTTP_ERROR = "NetHttpError"
        const val NET_HTTP_WARN = "NetHttpWarn"
    }

    object NetType{
        const val NET_TYPE_JSON = "application/json; charset=utf-8"
        const val NET_TYPE_FILE = "text/x-markdown; charset=utf-8"
    }

    object Code{
        //请求成功
        const val NET_NORMAL = 200
    }

    object Cache{
        const val CACHE_KEY = "netHttpCache"
    }

    object CacheModel{
        //无缓存
        const val TYPE_NONE = 0
        //缓存一小时
        const val TYPE_ONE_HOUR = 1000*60*60
    }

}