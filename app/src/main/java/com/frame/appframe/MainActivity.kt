package com.frame.appframe

import androidx.activity.viewModels
import com.frame.appframe.viewModel.TestModel
import com.frame.main.Config
import com.frame.main.activity.BaseActivity

class MainActivity : BaseActivity() {
    private val testModel:TestModel by viewModels()

    override fun initData() {
        testModel.requestTest()
    }

    override fun initAdapter() {

    }

    override fun initListener() {

    }

    override fun initView() {

    }

    override fun config(config: Config) {
        config.enableDataBing = true
        config.viewModel = testModel
    }

    override fun setLayout(): Int {
        return R.layout.activity_main
    }


}