package com.base.net_lib.request

import com.base.net_lib.bean.Download
import com.base.net_lib.callback.BaseGetInterface
import com.base.net_lib.constants.NetConstants
import com.base.net_lib.constants.NetConstants.DownloadState.STATE_DOING
import com.base.net_lib.constants.NetConstants.DownloadState.STATE_DONE
import com.base.net_lib.constants.NetConstants.DownloadState.STATE_ERROR
import com.base.net_lib.log.L
import com.base.net_lib.parameter.HeaderParameter
import com.base.net_lib.parameter.HttpParameter
import com.base.net_lib.utils.Applications
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Response
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.lang.Exception
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.TimeUnit


/**
 * 实际上get请求的类，也是给外面用的类
 */
class NetGetRequest<T>(url: String, okHttpClient: OkHttpClient?) : BaseNetRequest<T>(url, okHttpClient),BaseGetInterface<T> {

    /**
     * 设置请求参数
     * @param key 参数的key
     * @param value 请求参数的值
     */
    override fun put(key: String, value: Any?): NetGetRequest<T> {
        value?.let {
            mHttpParameter.put(key, value)
        }
        return this
    }

    /**
     * 设置请求参数
     * @param parameter 请求参数
     */
    override fun put(parameter: HttpParameter?): NetGetRequest<T> {
        mHeaderParameter?.let {
            mHttpParameter.put(parameter)
        }
        return this
    }

    /**
     * 通过map添加请求参数
     * @param parameterMap 请求参数
     */
    override fun put(parameterMap: ConcurrentHashMap<String, Any>): NetGetRequest<T> {
        mHttpParameter.put(parameterMap)
        return this
    }

    /**
     * 添加头部信息
     * @param key 头部请求参数的key
     * @param value 头部请求参数的值
     */
    override fun header(key: String, value: Any?): NetGetRequest<T> {
        value?.let {
            mHeaderParameter.put(key, value)
        }
        return this
    }

    /**
     * 添加头部参数
     * @param header 头部请求参数
     */
    override fun header(header: HeaderParameter?): NetGetRequest<T> {
        header?.let {
            mHeaderParameter.put(header)
        }
        return this
    }

    /**
     * 添加头部参数
     * @param header 头部请求参数
     */
    override fun header(headerMap: ConcurrentHashMap<String, Any>): NetGetRequest<T> {
        mHeaderParameter.put(headerMap)
        return this
    }

    /**
     * 下载文件
     * @param fileSavePath 下载文件后保存的地点
     */
    override fun download(fileSavePath: String?, downloadListener: (download: Download) -> Unit){
        val download = Download(0,0,0L,null,null)
        val request = getRequest()
        request?.let {
            mOkHttpClient?.newCall(it)?.enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    download.state = STATE_ERROR
                    download.e = e
                    downloadListener.invoke(download)
                }

                override fun onResponse(call: Call, response: Response) {
                    try{
                        //获取保存文件的名称
                        val fileName = request.url.toUrl().toString().substring(request.url.toUrl().toString().lastIndexOf("/"))
                        //获取保存地址
                        var savePath:String? = if (fileSavePath.isNullOrEmpty()){
                            Applications.context().getExternalFilesDir("Download")?.path+"/"+fileName
                        }else{
                            "$fileSavePath/$fileName"
                        }
                        var file = File(savePath)
                        if (file.exists()){
                            file.delete()
                        }
                        L.i("日志","下载地址：${file.path}")
                        //设置输入输出流
                        val inputStream = response.body?.byteStream()
                        val outputStream = FileOutputStream(file)
                        //获取文件大小
                        val totalSize = response.body?.contentLength()?:0
                        //大小为零时直接结束下载
                        if (totalSize == 0L){
                            downloadListener.invoke(download)
                            outputStream.close()
                            return
                        }
                        //输入流为空时结束下载
                        if (inputStream == null){
                            downloadListener.invoke(download)
                            outputStream.close()
                            return
                        }
                        //设置回调数据
                        download.downloadFileSize = totalSize
                        download.file = file
                        download.state = STATE_DOING
                        val byteArray = ByteArray(1024)
                        var len = 0
                        var progress = 0
                        while (inputStream.read(byteArray).also { len = it } != -1){
                            outputStream.write(byteArray,0,len)
                            progress += len
                            download.progress = progress
                            downloadListener.invoke(download)
                        }
                        download.state = STATE_DONE
                        download.progress = totalSize.toInt()
                        downloadListener.invoke(download)
                        outputStream.flush()
                        outputStream.close()
                    }catch (e:Exception){
                        e.printStackTrace()
                        download.state = STATE_ERROR
                        download.e = e
                        downloadListener.invoke(download)
                    }
                }
            })
        }
    }


    /**
     * 缓存时间
     * @param cacheTime 缓存有效期，单位秒
     */
    override fun cacheTime(cacheTime: Int): NetGetRequest<T> {
        this.mCacheTime = cacheTime
        if (cacheTime == NetConstants.CacheModel.TYPE_ONLY_CACHE) {
            createCacheControllerBuilder().onlyIfCached()
        } else {
            createCacheControllerBuilder().maxStale(cacheTime, TimeUnit.SECONDS)
            createCacheControllerBuilder().maxAge(cacheTime, TimeUnit.SECONDS)
        }
        return this
    }

}