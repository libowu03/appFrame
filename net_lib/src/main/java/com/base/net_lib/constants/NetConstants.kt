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

        //无网络
        const val NET_NO_NET = 0
    }

    object Cache{
        const val CACHE_KEY = "netHttpCache"
    }

    object CacheModel{
        //无缓存
        const val TYPE_NONE = 0
        //缓存一小时
        const val TYPE_ONE_HOUR = 60*60
        //缓存一天
        const val TYPE_ONE_DAY = TYPE_ONE_HOUR*24
        //一个月（这里一个月用30天计算）
        const val TYPE_ONE_MONTH = TYPE_ONE_DAY * 30
        //一年
        const val TYPE_ONE_YEAR = TYPE_ONE_MONTH * 12
        //只使用缓存
        const val TYPE_ONLY_CACHE = -1
    }

}