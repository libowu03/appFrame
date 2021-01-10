package com.base.net_lib.utils

import android.R.attr.key
import android.app.Application
import androidx.datastore.core.DataStore
import androidx.datastore.createDataStore
import com.base.net_lib.constants.NetConstants.Cache.CACHE_KEY
import java.util.prefs.Preferences


object CacheUtils {

    /**
     * 将url装换成hash值
     */
    fun urlToHashName(value:String):Int{
        val arraySize = 11113 // 数组大小一般取质数
        var hashCode = 0
        for (i in value.indices) { // 从字符串的左边开始计算
            val letterValue: Int = value[i].toInt() - 96 // 将获取到的字符串转换成数字，比如a的码值是97，则97-96=1
            // 就代表a的值，同理b=2；
            hashCode = ((hashCode shl 5) + letterValue) % arraySize // 防止编码溢出，对每步结果都进行取模运算
        }
        return hashCode
    }

    fun <T> readCache(url:String):T?{
        val sp = Applications.context().getSharedPreferences(CACHE_KEY,Application.MODE_PRIVATE)
        val cache = sp.getString(urlToHashName(url).toString(),null)

        return null
    }

}