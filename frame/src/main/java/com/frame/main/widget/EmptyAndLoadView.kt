package com.frame.main.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.databinding.BindingAdapter
import com.frame.main.R
import com.frame.main.databinding.BaseViewEmptyAndLoaddingBinding

/**
 *
 * @author bohu
 * @Date on 2020-11-19
 * @Description 加载提示布局，比如加载失败，加载中，加载数据为空
 */
class EmptyAndLoadView : LinearLayout {
    private var mFirstGone: Boolean = false
    private var mTipMarginTop: Float = -1f
    private var mViewBind: BaseViewEmptyAndLoaddingBinding? = null
    private var mCurrentStatus = EmptyAndLoadingType.TYPE_LOADING
    private var mClickListener: (type: EmptyAndLoadingType) -> Unit = {}
    private var mOnStatusChange:(type: EmptyAndLoadingType) -> Unit = {}

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attr: AttributeSet?) : this(context, attr, 0)

    constructor(context: Context, attr: AttributeSet?, def: Int) : super(context, attr, def) {
        mViewBind =
            BaseViewEmptyAndLoaddingBinding.inflate(LayoutInflater.from(context), this, true)
        if (childCount > 2) {
            throw Exception("内部子view请保持在一个以内")
        }
        val parameters =
            context.theme.obtainStyledAttributes(attr, R.styleable.EmptyAndLoadView, def, 0)
        mFirstGone = parameters.getBoolean(R.styleable.EmptyAndLoadView_tipFirstGone, false)
        mTipMarginTop = parameters.getDimension(R.styleable.EmptyAndLoadView_tipMarginTop, -1f)
        if (mTipMarginTop != -1f) {
            val ll = getChildAt(0).layoutParams as LinearLayout.LayoutParams
            ll.topMargin = mTipMarginTop.toInt()
            getChildAt(0).layoutParams = ll
        }
        if (mFirstGone) {
            getChildAt(0).visibility = View.GONE
        }
        mViewBind?.vImgTip?.setOnClickListener {
            mClickListener.invoke(mCurrentStatus)
        }
    }

    /**
     * 数据加载失败
     */
    fun onLoadFail(
        tip: String = context.getString(R.string.baseLoadFailTip),
        imageResource: Int = R.drawable.base_loadding_fail
    ) {
        showTipView()
        mViewBind?.vTvTipText?.text = tip
        if (imageResource != 0) {
            mViewBind?.vImgTip?.setImageResource(imageResource)
        }
        mCurrentStatus = EmptyAndLoadingType.TYPE_FAIL
        mOnStatusChange.invoke(EmptyAndLoadingType.TYPE_FAIL)
    }

    /**
     * 中间图片点击事件，比如想要点击加载失败的图片后进行重新数据请求，就可以通过该方法设置
     * @param clickListener 点击回调
     */
    fun onCenterImageClick(clickListener: (type: EmptyAndLoadingType) -> Unit) {
        this.mClickListener = clickListener
    }

    /**
     * 数据加载为空
     */
    fun onLoadEmpty(
        tip: String = context.getString(R.string.baseEmpytTip),
        imageResource: Int = R.drawable.base_loadding_empty
    ) {
        showTipView()
        mViewBind?.vTvTipText?.text = tip
        if (imageResource != 0) {
            mViewBind?.vImgTip?.setImageResource(imageResource)
        }
        mCurrentStatus = EmptyAndLoadingType.TYPE_EMPTY
        mOnStatusChange.invoke(EmptyAndLoadingType.TYPE_EMPTY)
    }

    /**
     * 数据正在加载
     */
    fun onLoading(
        tip: String = context.getString(R.string.baseLoadingTip),
        imageResource: Int = R.drawable.base_loadding
    ) {
        showTipView()
        mViewBind?.vTvTipText?.text = tip
        if (imageResource != 0) {
            mViewBind?.vImgTip?.setImageResource(imageResource)
        }
        mCurrentStatus = EmptyAndLoadingType.TYPE_LOADING
        mOnStatusChange.invoke(EmptyAndLoadingType.TYPE_LOADING)
    }

    /**
     * 数据加载正常
     */
    fun onLoadNormal() {
        showContentView()
        mCurrentStatus = EmptyAndLoadingType.TYPE_SUCCESS
        mOnStatusChange.invoke(EmptyAndLoadingType.TYPE_SUCCESS)
    }

    /**
     * 显示警告布局，隐藏加载布局
     */
    private fun showTipView() {
        if (childCount > 1) {
            getChildAt(1).visibility = View.GONE
            getChildAt(0).visibility = View.VISIBLE
        }
    }

    /**
     * 显示内容布局，隐藏警告布局
     */
    private fun showContentView() {
        if (childCount > 1) {
            getChildAt(0).visibility = View.GONE
            getChildAt(1).visibility = View.VISIBLE
        }
    }

    companion object {
        //加载失败
        const val EMPTY_FAIL = -1

        //加载中
        const val EMPTY_LOADING = 0

        //数据为空
        const val EMPTY_NONE = 1

        //正常加载且存在数据
        const val EMPTY_SUCCESS = 2

        /**
         * 添加数据源
         * @param status 显示状态
         */
        @BindingAdapter("changeStatus")
        @JvmStatic
        fun EmptyAndLoadView.changeStatus(status: Int) {
            when (status) {
                EMPTY_FAIL -> this.onLoadFail()
                EMPTY_LOADING -> this.onLoading()
                EMPTY_NONE -> this.onLoadEmpty()
                EMPTY_SUCCESS -> this.onLoadNormal()
            }
        }
    }

    enum class EmptyAndLoadingType {
        TYPE_SUCCESS, TYPE_FAIL, TYPE_EMPTY, TYPE_LOADING
    }
}