package com.base.net_lib.bean

/**
 * @param createDate 缓存保存时间
 * @param cacheLife 缓存有效期
 * @param cacheContent 缓存内容
 */
data class Cache (val createDate:Long,val cacheLife:Long,val cacheContent:String?)