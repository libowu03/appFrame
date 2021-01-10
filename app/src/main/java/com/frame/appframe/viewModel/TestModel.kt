
package com.frame.appframe.viewModel

import android.util.Log
import com.base.net_lib.log.L
import com.frame.main.net.NetManager
import com.frame.main.viewModel.BaseViewModel
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineExceptionHandler

class TestModel : BaseViewModel() {
    val a = "你好呀"

    fun requestTest() {
        doUILaunch({
            val a = doIOAsyncAndAwait { NetManager.requestFestival<String>() }
            val gson = Gson()
            L.i("日志","结果：${gson.toJson(a)}")
        }, CoroutineExceptionHandler() { _, e ->
            Log.e("日志", "错误ii：${e.message}")
        })
    }

}