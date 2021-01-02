package com.frame.appframe.viewModel

import android.util.Log
import com.base.net_lib.NetHttp
import com.frame.main.viewModel.BaseViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.delay

class TestModel : BaseViewModel() {
    val a = "你好呀"

    fun requestTest() {
        doUILaunch({
            val a = doIOAsyncAndAwait {
                NetHttp.get<String>("https://www.baidu.com/s").put("ie", "UTF-8")
                    .put("wd", "url添加参数")
                    .header("haha","ff")
                    .header("cccc","ff").execute {

                }
            }
        }, CoroutineExceptionHandler() { _, e ->
            Log.e("日志", "错误ii：${e.message}")
        })
    }

}