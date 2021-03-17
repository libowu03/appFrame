package com.frame.main.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding
import com.frame.main.BR
import com.frame.main.BaseFragmentConfig
import java.lang.reflect.ParameterizedType

abstract class BaseFragment<T : ViewBinding> : Fragment() {
    lateinit var viewBinding: ViewBinding
    private val mConfig = BaseFragmentConfig()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val type = javaClass.genericSuperclass as ParameterizedType
        val aClass = type.actualTypeArguments[0] as Class<*>
        val method = aClass.getDeclaredMethod(
            "inflate",
            LayoutInflater::class.java,
            ViewGroup::class.java,
            Boolean::class.java
        )
        viewBinding = method.invoke(null, layoutInflater, container, false) as T
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        config(mConfig)
        initConfig()
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

    abstract fun initData()

    abstract fun initListener()

    abstract fun initView()

    open fun initAdapter() {

    }

    open fun viewModelSetting(): ViewModel? {
        return null
    }

    private fun initConfig() {

    }

    open fun config(config: BaseFragmentConfig) {

    }


}