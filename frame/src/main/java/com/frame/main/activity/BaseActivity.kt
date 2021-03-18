package com.frame.main.activity

import android.os.Bundle
import android.view.LayoutInflater
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding
import com.frame.main.BR
import com.frame.main.BaseActivityConfig
import com.frame.main.ext.setStatusBarColor
import com.frame.main.ext.setStatusBarHidden
import java.lang.reflect.ParameterizedType

/**
 * activity基类
 */
abstract class BaseActivity<T : ViewBinding> : FragmentActivity() {
    protected val viewBinding: T by lazy {
        //使用反射得到viewbinding的class
        val type = javaClass.genericSuperclass as ParameterizedType
        val aClass = type.actualTypeArguments[0] as Class<*>
        val method = aClass.getDeclaredMethod("inflate", LayoutInflater::class.java)
        method.invoke(null, layoutInflater) as T
    }
    private val mConfig = BaseActivityConfig()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        config(mConfig)
        initConfig()
        setContentView(viewBinding.root)
        val binding = DataBindingUtil.bind<ViewDataBinding>(viewBinding.root)
        binding?.lifecycleOwner = this
        val viewModel = viewModelSetting()
        viewModel?.let {
            binding?.setVariable(BR.data, viewModel)
        }
        initView()
        initListener()
        initAdapter()
        initData()
    }

    open fun viewModelSetting(): ViewModel? {
        return null
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
        setStatusBarHidden(this, mConfig.isHiddenStatusBar,mConfig.isDarkStatusBarText)
    }

    /**
     * 配置基础信息,如果需要打开DataBinding，这里必须设置一个viewModel
     * 在xml中，name必须为content，type必须为一个viewModel
     */
    open fun config(baseActivityConfig: BaseActivityConfig) {

    }

}