package com.frame.main.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.frame.main.Config
import com.frame.main.uiExt.setStatusBarColor
import com.frame.main.uiExt.setStatusBarHidden


abstract class BaseActivity : AppCompatActivity() {
    private val mConfig = Config()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        config(mConfig)
        initConfig()
        if (mConfig.enableDataBing){
            val binding = DataBindingUtil.setContentView<ViewDataBinding>(this,setLayout())
            binding.lifecycleOwner = this
            //binding.setVariable(BR.vm,mConfig.viewModel)
        }else{
            setContentView(setLayout())
        }
        initView()
        initListener()
        initAdapter()
        initData()
    }

    /**
     * 初始化网络数据等数据请求
     */
    abstract fun initData()

    /**
     * 适配器的初始化
     */
    abstract fun initAdapter()

    /**
     * 监听器的初始化
     */
    abstract fun initListener()

    /**
     * view的初始化
     */
    abstract fun initView()


    /**
     * 设置基础配置数据
     */
    private fun initConfig() {
        if (mConfig.isHiddenStatusBar){
            setStatusBarHidden(this,mConfig.isHiddenStatusBar)
        }
        if (mConfig.statusBarColor != -1){
            setStatusBarColor(this,mConfig.statusBarColor)
        }
    }

    /**
     * 配置基础信息,如果需要打开DataBinding，这里必须设置一个viewModel
     * 在xml中，name必须为content，type必须为一个viewModel
     */
    open fun config(config: Config){

    }

    /**
     * 设置布局
     */
    abstract fun setLayout():Int
}