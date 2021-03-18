package com.frame.appframe

import android.graphics.Color
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import com.frame.appframe.databinding.ActivityMainBinding
import com.frame.appframe.viewModel.TestModel
import com.frame.main.BaseActivityConfig
import com.frame.main.activity.BaseActivity

class MainActivity : BaseActivity<ActivityMainBinding>() {
    private val testModel: TestModel by viewModels()

    override fun initData() {
        viewBinding.vTvTestGet.setOnClickListener {
            //测试get请求
            //testModel.requestTest()
            val dialog = TestDialog()
            dialog.show(supportFragmentManager,"")
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

    override fun config(baseActivityConfig: BaseActivityConfig) {
        baseActivityConfig.isHiddenStatusBar = true
    }

}