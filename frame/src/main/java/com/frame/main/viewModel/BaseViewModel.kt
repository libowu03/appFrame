package com.frame.main.viewModel

import android.app.Activity
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.base.net_lib.log.L
import com.frame.main.bean.ResultData
import com.frame.main.dialog.LoaddingDialog
import kotlinx.coroutines.*
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

open class BaseViewModel : ViewModel() {
    private val jod = SupervisorJob()
    private val uiScope = CoroutineScope(Dispatchers.Main+jod)
    private val loadingDialog = LoaddingDialog()

    init {
        viewModelScope.launch {
            // Coroutine that will be canceled when the ViewModel is cleared.
            //取消所有协程
            //jod.cancel()
        }
    }

    /**
     * 运行在UI线程的协程
     */
    fun doUILaunch(block: suspend CoroutineScope.() -> Unit, exceptionHandler: CoroutineExceptionHandler? = null): Job {
        return uiScope.launch(Dispatchers.Main + (exceptionHandler ?: CoroutineExceptionHandler { _, e ->
            e.printStackTrace()
        })) {
            block()
        }
    }

    /**
     * 运行在IO线程的协程launch
     */
    suspend fun <T> doIOLaunch(block:suspend CoroutineScope.()-> T) = suspendCoroutine<ResultData<T>>{
        uiScope.launch(Dispatchers.IO){
            try {
                it.resume(ResultData(block()))
            }catch (e:Exception){

            }
        }
    }

    /**
     * 运行在IO线程的协程async
     */
    suspend fun <T> doIOAsync(block: suspend CoroutineScope.() -> T): Deferred<T> {
        return uiScope.async(Dispatchers.IO) { block() }
    }

    /**
     * 运行在IO线程的协程async
     */
    suspend fun <T> doIOAsyncAndAwait(block: suspend CoroutineScope.() -> T): T {
        return uiScope.async(Dispatchers.IO) { block() }.await()
    }

    /**
     * 清理资源
     */
    fun cancel() {
        jod.cancel()
    }

    override fun onCleared() {
        super.onCleared()
        jod.cancel()
    }

    /**
     * 显示弹窗
     */
    fun showLoadingDialog(activity:FragmentActivity?){
        L.i("日志","执行显示")
        activity?.let {
            if (loadingDialog.isAdded){
                return
            }
            loadingDialog.show(activity.supportFragmentManager,activity::class.java.name)
        }
    }

    fun hideLoadingDialog(){
        loadingDialog.dismiss()
    }


}