package com.frame.appframe

import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import com.frame.appframe.databinding.ActivityMainBinding
import com.frame.appframe.viewModel.TestModel
import com.frame.main.activity.BaseActivity

class MainActivity : BaseActivity<ActivityMainBinding>() {
    private val testModel:TestModel by viewModels()

    override fun initData() {
        viewBinding.vTvTestGet.setOnClickListener {
            //测试get请求
            testModel.requestTest()
        }
    }

    override fun viewModelSetting(): ViewModel? {
        return testModel
    }

    override fun initAdapter() {

    }

    override fun initListener() {

    }

    override fun initView() {

    }

}