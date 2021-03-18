package com.frame.main

import android.graphics.Color
import androidx.lifecycle.ViewModel

class BaseActivityConfig : BaseConfig(){
    //是否需要隐藏状态栏
    var isHiddenStatusBar:Boolean = Config.STATUS_DARK_TEXT
    //状态栏颜色是白色还是深色
    var isDarkStatusBarText = true
}