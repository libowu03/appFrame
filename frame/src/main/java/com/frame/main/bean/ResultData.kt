package com.frame.main.bean

data class ResultData<T> (val data:T?=null,val code:Int?=200,val msg:String?=null,val developerMsg:String?=null)