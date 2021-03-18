package com.frame.main.callback

import androidx.fragment.app.FragmentManager
import java.lang.Exception

/**
 * 弹窗事件监听
 */
interface FragmentDialogStatusListener {
    /**
     * 显示
     */
    fun onShow(manager: FragmentManager, tag: String?, isSuccess:Boolean, exception: Exception?=null)

    /**
     * 取消
     */
    fun onCancel(isSuccess: Boolean,exception: Exception?=null)

    /**
     * 关闭
     */
    fun onDismiss(isSuccess:Boolean,exception: Exception?=null)
}