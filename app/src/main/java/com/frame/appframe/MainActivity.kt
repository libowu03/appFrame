package com.frame.appframe

import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import com.base.net_lib.log.L
import com.frame.appframe.adapter.TestAdapter
import com.frame.appframe.databinding.ActivityMainBinding
import com.frame.appframe.viewModel.TestModel
import com.frame.main.BaseActivityConfig
import com.frame.main.activity.BaseActivity
import com.frame.main.ext.sendToActivity
import com.frame.main.widget.EmptyAndLoadView

class MainActivity : BaseActivity<ActivityMainBinding>() {
    private val testModel: TestModel by viewModels()
    private val mAdapter = TestAdapter()

    override fun initData() {
        val list = mutableListOf<String>()
        for (item in 0..50) {
            list.add("")
        }
        mAdapter.addItedataList(list)
        mAdapter.onItemClickCallback = { itemView, itemData, viewType, position ->
            sendToActivity(TestTwoActivity::class.java).put("test", "你好呀").put("test2", 10).go()
        }
        viewBinding.vRvContent.adapter = mAdapter

        viewBinding.vEmpty.onLoading()
        viewBinding.vEmpty.onCenterImageClick {
            when (it) {
                EmptyAndLoadView.EmptyAndLoadingType.TYPE_FAIL -> {
                    L.i("日志", "错误点击")
                }
                EmptyAndLoadView.EmptyAndLoadingType.TYPE_SUCCESS -> {
                    L.i("日志", "成功点击")
                }
                EmptyAndLoadView.EmptyAndLoadingType.TYPE_LOADING -> {
                    L.i("日志", "加载中点击")
                }
                EmptyAndLoadView.EmptyAndLoadingType.TYPE_EMPTY -> {
                    L.i("日志", "空点击")
                }
            }
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