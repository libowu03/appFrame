package com.base.net_lib.callback

import com.base.net_lib.bean.Download
import java.io.File

/**
 * get请求基础接口
 */
interface BaseGetInterface<T> {
    /**
     * @param fileSavePath 文件下载地址
     * @param status 下载状态
     * @param msg 输出错误信息或其他信息
     * @param downloadListener 下载进度监听
     */
    fun download(fileSavePath:String?,downloadListener:(download:Download)->Unit)
}