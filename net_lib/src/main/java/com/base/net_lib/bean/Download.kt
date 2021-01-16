package com.base.net_lib.bean

import java.io.File
import java.lang.Exception

data class Download(var progress:Int,var state:Int,var downloadFileSize:Long,var file:File?=null,var e:Exception?=null)