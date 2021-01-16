package com.frame.appframe.viewModel

import android.util.Log
import com.base.net_lib.NetHttp
import com.base.net_lib.log.L
import com.frame.main.net.NetManager
import com.frame.main.viewModel.BaseViewModel
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineExceptionHandler

class TestModel : BaseViewModel() {
    val a = "你好呀"

    fun requestTest() {
        doUILaunch({
            /*val a = doIOAsyncAndAwait { NetManager.requestFestival<String>() }
            val gson = Gson()
            L.i("日志","结果：${gson.toJson(a)}")*/
            NetHttp.get<String>("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Finews.gtimg.com%2Fnewsapp_bt%2F0%2F11682904034%2F1000.jpg&refer=http%3A%2F%2Finews.gtimg.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1613365883&t=7448f538b0620deae5105f55afcd2e1a.jpg")
                .download(null) {
                    L.i(
                        "日志",
                        "总长度：${it.downloadFileSize},进度：${it.progress / (it.downloadFileSize * 1.0f)},下载状态：${it.state}"
                    )
                }

        }, CoroutineExceptionHandler() { _, e ->
            Log.e("日志", "错误ii：${e.message}")
        })
    }

}