package com.frame.main.utils

import com.frame.main.bean.ResultData
import kotlinx.coroutines.*
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class CoroutineManager {
    private val jod = SupervisorJob()
    private val uiScope = CoroutineScope(Dispatchers.Main+jod)

    /**
     * 运行在UI线程的协程
     */
    fun doUiLaunch(block:suspend CoroutineScope.() -> Unit,exceptionHandler: CoroutineExceptionHandler?=null): Job {
        return uiScope.launch(Dispatchers.Main+(exceptionHandler?:CoroutineExceptionHandler{_,e->
            e.printStackTrace()
        })){
            block
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

}