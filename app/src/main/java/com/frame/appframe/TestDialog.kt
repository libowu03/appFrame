package com.frame.appframe

import android.util.Log
import com.frame.appframe.databinding.DialogTestBinding
import com.frame.main.dialog.BaseFragmentDialog

/**
 * 测试弹窗
 */
class TestDialog : BaseFragmentDialog<DialogTestBinding>() {

    override fun initListener() {
        viewBinding.apply {
            vLlBox.setOnClickListener {
                Log.i("日志","点击了")
            }
        }
    }

    override fun initData() {

    }
}