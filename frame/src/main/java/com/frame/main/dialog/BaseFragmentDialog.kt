package com.frame.main.dialog

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.viewbinding.ViewBinding
import com.frame.main.R
import com.frame.main.callback.FragmentDialogStatusListener
import java.lang.reflect.ParameterizedType

abstract class BaseFragmentDialog<T : ViewBinding> : DialogFragment() {
    lateinit var viewBinding: T
    var statusListener: FragmentDialogStatusListener? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        initView()
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_FRAME, R.style.DialogStyle)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
        initListener()
    }

    abstract fun initListener()

    abstract fun initData()

    open fun initAdapter() {

    }

    /**
     * 设置自定义弹窗高度
     */
    open fun getDialogHeight(): Int {
        return ViewGroup.LayoutParams.WRAP_CONTENT
    }

    /**
     * 设置自定义弹窗宽度
     */
    open fun getDialogWidth(): Int {
        return ViewGroup.LayoutParams.MATCH_PARENT
    }

    /**
     * 设置一些弹窗的基本样式,如果需要自定义，可以重写此方法
     */
    open protected fun initView() {
        dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        if (setBackGroundDrawable() != 0) {
            dialog?.window?.setBackgroundDrawableResource(setBackGroundDrawable())
        }
        //设置padding
        dialog?.window?.decorView?.setPadding(0, 0, 0, 0)
        //透明背景
        //dialog?.window?.setBackgroundDrawableResource(R.color.transparent)
        val lp = dialog?.window?.attributes
        when (setDialogShowGravity()) {
            DialogGravity.CENTER -> {
                lp?.gravity = Gravity.CENTER
            }
            DialogGravity.TOP -> {
                lp?.gravity = Gravity.TOP
            }
            DialogGravity.BOTTOM -> {
                lp?.gravity = Gravity.BOTTOM
            }
            DialogGravity.START -> {
                lp?.gravity = Gravity.START
            }
            DialogGravity.END -> {
                lp?.gravity = Gravity.END
            }
        }
        when {
            getDialogHeight() == ViewGroup.LayoutParams.WRAP_CONTENT -> {
                lp?.height = ViewGroup.LayoutParams.WRAP_CONTENT
            }
            getDialogHeight() == ViewGroup.LayoutParams.MATCH_PARENT -> {
                lp?.height = ViewGroup.LayoutParams.MATCH_PARENT
            }
            else -> {
                lp?.height = getDialogHeight()
            }
        }

        when {
            getDialogWidth() == ViewGroup.LayoutParams.WRAP_CONTENT -> {
                lp?.width = ViewGroup.LayoutParams.WRAP_CONTENT
            }
            getDialogWidth() == ViewGroup.LayoutParams.MATCH_PARENT -> {
                lp?.width = ViewGroup.LayoutParams.MATCH_PARENT
            }
            else -> {
                lp?.width = getDialogWidth()

            }
        }

        dialog?.window?.attributes = lp
    }

    override fun show(manager: FragmentManager, tag: String?) {
        try {
            super.show(manager, tag)
            statusListener?.onShow(manager, tag, true)
        } catch (e: Exception) {
            statusListener?.onShow(manager, tag, false, e)
        }
    }

    override fun dismiss() {
        try {
            super.dismiss()
            statusListener?.onDismiss(false)
        } catch (e: Exception) {
            statusListener?.onDismiss(false, e)
        }
    }

    open fun setBackGroundDrawable(): Int {
        return 0
    }

    open fun setDialogShowGravity(): DialogGravity {
        return DialogGravity.CENTER
    }

    override fun onCancel(dialog: DialogInterface) {
        try {
            super.onCancel(dialog)
            statusListener?.onCancel(true)
        } catch (e: Exception) {
            statusListener?.onCancel(false, e)
        }
    }

    enum class DialogGravity {
        BOTTOM, TOP, CENTER, START, END
    }
}