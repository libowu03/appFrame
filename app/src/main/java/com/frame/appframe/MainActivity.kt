package com.frame.appframe

import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import com.frame.appframe.adapter.TestAdapter
import com.frame.appframe.databinding.ActivityMainBinding
import com.frame.appframe.viewModel.TestModel
import com.frame.main.BaseActivityConfig
import com.frame.main.activity.BaseActivity

class MainActivity : BaseActivity<ActivityMainBinding>() {
    private val testModel: TestModel by viewModels()
    private val mAdapter = TestAdapter()

    override fun initData() {
        /*viewBinding.vTvTestGet.setOnClickListener {
            //测试get请求
            testModel.requestTest()
            *//*val dialog = TestDialog()
            dialog.show(supportFragmentManager,"")*//*
        }*/
        val list = mutableListOf<String>()
        for (item in 0..50){
            list.add("")
        }
        mAdapter.addItedataList(list)
        mAdapter.onItemLongClickCallback = {itemView, itemData, viewType,position ->
            Log.e("日志","点击了${position}")
        }
        viewBinding.vRvContent.adapter = mAdapter
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