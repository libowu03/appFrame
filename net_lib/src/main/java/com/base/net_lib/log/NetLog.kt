package com.base.net_lib.log

import java.lang.Exception

object Log {
    var debug = false

    fun i(tag: String, msg: String?) {
        if (!debug){
            return
        }
        info()
    }

    /**
     * i级别的提醒
     */
    private fun info() {

    }

    private fun getLogTag(){
        val stringBuffer = StringBuffer()
        try{
            val stack =Thread.currentThread().stackTrace

        }catch (e:Exception){
            e.printStackTrace()
        }
    }



}