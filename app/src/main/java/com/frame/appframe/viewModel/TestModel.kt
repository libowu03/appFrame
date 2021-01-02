package com.frame.appframe.viewModel

import android.util.Log
import com.base.net_lib.NetHttp
import com.base.net_lib.callback.JsonCallback
import com.base.net_lib.impl.User
import com.base.net_lib.log.L
import com.frame.main.viewModel.BaseViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.delay

class TestModel : BaseViewModel() {
    val a = "你好呀"

    fun requestTest() {
        doUILaunch({
            val a = doIOAsyncAndAwait {
                NetHttp.get<String>("http://114.116.149.238:8080/getFestival").execute(object:JsonCallback<String>{
                    override fun onSuccess(result: String?) {

                    }

                    override fun onError(msg: String) {

                    }

                })
            }
        }, CoroutineExceptionHandler() { _, e ->
            Log.e("日志", "错误ii：${e.message}")
        })
    }

}