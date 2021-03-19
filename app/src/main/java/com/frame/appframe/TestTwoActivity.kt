package com.frame.appframe

import com.base.net_lib.log.L
import com.frame.appframe.databinding.ActivityTestTwoBinding
import com.frame.main.activity.BaseActivity
import com.frame.main.ext.getValue

/**
 * 测试2界面
 */
class TestTwoActivity : BaseActivity<ActivityTestTwoBinding>() {

    override fun initData() {
        val a = getValue("test2",0)
        val b = getValue("test","")
        L.i("日志","的值为：${a}，b的值为：${b}")
    }

    override fun initAdapter() {

    }

    override fun initListener() {

    }

    override fun initView() {

    }

}