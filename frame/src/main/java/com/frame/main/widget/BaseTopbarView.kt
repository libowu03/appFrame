package com.frame.main.widget

import android.app.Activity
import android.content.Context
import android.content.res.TypedArray
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import com.frame.main.R
import com.frame.main.databinding.FrameViewTopBarBinding
import com.frame.main.ext.dp2px
import com.frame.main.ext.getStatusBarHeightExt

/**
 * 通用顶部标题栏
 */
class BaseTopbarView : LinearLayout {
    private var mNeedStatusBarHeight = true
    private var mOnlyNeedStatusHeight = false
    private var mNeedLeftOneImage = true
    private var mNeedRightOneImage = false
    private var mNeedLeftOneText = false
    private var mNeedRightOneText = false
    private var mTopBarTitle: String? = ""
    private var mLeftOneText: String? = ""
    private var mRightOneText: String? = ""
    private var mLeftDrawable: Drawable? = null
    private var mRightDrawable: Drawable? = null
    private var mTopBarTitleTextColor: Int = Color.BLACK
    private var mTopBarLeftTextColor: Int = Color.BLACK
    private var mTopBarRightTextColor: Int = Color.BLACK
    private var mTopBarTitleTextSize: Float = 0f
    private var mTopBarLeftTextSize: Float = 0f
    private var mTopBarRightTextSize: Float = 0f
    private lateinit var mViewBinding: FrameViewTopBarBinding

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attributeSet: AttributeSet?) : this(context, attributeSet, 0) {

    }

    constructor(context: Context, attributeSet: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attributeSet,
        defStyleAttr
    ) {
        val attr: TypedArray =
            context.obtainStyledAttributes(attributeSet, R.styleable.BaseTopbarView)
        mNeedStatusBarHeight = attr.getBoolean(R.styleable.BaseTopbarView_needStatusBarHeight, true)
        mOnlyNeedStatusHeight =
            attr.getBoolean(R.styleable.BaseTopbarView_onlyNeedStatusHeight, false)
        mNeedLeftOneImage = attr.getBoolean(R.styleable.BaseTopbarView_needLeftOneImage, true)
        mNeedLeftOneText = attr.getBoolean(R.styleable.BaseTopbarView_needLeftOneText, false)
        mNeedRightOneText = attr.getBoolean(R.styleable.BaseTopbarView_needRightOneText, false)
        mNeedRightOneImage = attr.getBoolean(R.styleable.BaseTopbarView_needRightOneImage, false)
        mTopBarTitle = attr.getString(R.styleable.BaseTopbarView_topBarTitle)
        mLeftOneText = attr.getString(R.styleable.BaseTopbarView_leftText)
        mRightOneText = attr.getString(R.styleable.BaseTopbarView_rightText)
        mRightDrawable = attr.getDrawable(R.styleable.BaseTopbarView_rightOneImage)
        mLeftDrawable = attr.getDrawable(R.styleable.BaseTopbarView_leftOneImage)
        mTopBarTitleTextColor = attr.getColor(R.styleable.BaseTopbarView_topBarTitleTextColor,Color.BLACK)
        mTopBarLeftTextColor = attr.getColor(R.styleable.BaseTopbarView_topBarLeftTextColor,Color.BLACK)
        mTopBarRightTextColor = attr.getColor(R.styleable.BaseTopbarView_topBarRightTextColor,Color.BLACK)
        mTopBarTitleTextSize = attr.getDimension(R.styleable.BaseTopbarView_topBarTitleTextSize, dp2px(15f).toFloat())
        mTopBarLeftTextSize = attr.getDimension(R.styleable.BaseTopbarView_topBarLeftTextSize, dp2px(15f).toFloat())
        mTopBarRightTextSize = attr.getDimension(R.styleable.BaseTopbarView_topBarRightTextSize, dp2px(15f).toFloat())
        attr.recycle()

        mViewBinding = FrameViewTopBarBinding.inflate(LayoutInflater.from(context), this, true)

        initView()
        initListener()
    }

    private fun initListener() {
        mViewBinding.vImageLeftOne.setOnClickListener {
            if (context is Activity) {
                (context as Activity).finish()
            }
        }
    }

    /**
     * 设置左边第一个图片按钮点击事件
     */
    fun setLeftOneImageClickListener(click: () -> Unit) {
        mViewBinding.vImageLeftOne.setOnClickListener {
            click.invoke()
        }
    }


    /**
     * 设置左边第一个图片按钮点击事件
     */
    fun setRightOneImageClickListener(click: () -> Unit) {
        mViewBinding.vImageRightOne.setOnClickListener {
            click.invoke()
        }
    }


    private fun initView() {
        //是否需要状态栏高度
        if (mNeedStatusBarHeight) {
            mViewBinding.vStatusBar.visibility = View.VISIBLE
            //设置状态栏高度
            mViewBinding.vStatusBar.layoutParams.height = getStatusBarHeightExt()
        } else {
            mViewBinding.vStatusBar.visibility = View.INVISIBLE
        }
        //如果只需要状态栏高度，则其他内容隐藏
        if (mOnlyNeedStatusHeight) {
            mViewBinding.vLlContentBox.visibility = View.INVISIBLE
            mViewBinding.vStatusBar.visibility = View.VISIBLE
        }
        //判断是否需要左边和右边第一个图片
        if (mNeedLeftOneImage) {
            mViewBinding.vImageLeftOne.visibility = View.VISIBLE
        } else {
            mViewBinding.vImageLeftOne.visibility = View.INVISIBLE
        }

        if (mNeedLeftOneText) {
            mViewBinding.vTvLeftOne.visibility = View.VISIBLE
        } else {
            mViewBinding.vTvLeftOne.visibility = View.INVISIBLE
        }

        if (mNeedRightOneImage) {
            mViewBinding.vImageRightOne.visibility = View.VISIBLE
        } else {
            mViewBinding.vImageRightOne.visibility = View.INVISIBLE

        }

        if (mNeedRightOneText) {
            mViewBinding.vTvRightOne.visibility = View.VISIBLE
        } else {
            mViewBinding.vTvRightOne.visibility = View.INVISIBLE
        }

        if (mLeftDrawable != null) {
            mViewBinding.vImageLeftOne.setImageDrawable(mLeftDrawable)
        }
        if (mRightDrawable != null) {
            mViewBinding.vImageRightOne.setImageDrawable(mRightDrawable)
        }

        mViewBinding.vTvLeftOne.setTextSize(TypedValue.COMPLEX_UNIT_PX,mTopBarLeftTextSize)
        mViewBinding.vTvRightOne.setTextSize(TypedValue.COMPLEX_UNIT_PX,mTopBarRightTextSize)
        mViewBinding.vTvCenter.setTextSize(TypedValue.COMPLEX_UNIT_PX,mTopBarTitleTextSize)
        mViewBinding.vTvLeftOne.setTextColor(mTopBarLeftTextColor)
        mViewBinding.vTvRightOne.setTextColor(mTopBarRightTextColor)
        mViewBinding.vTvCenter.setTextColor(mTopBarTitleTextColor)
        mViewBinding.vTvCenter.text = mTopBarTitle
        mViewBinding.vTvLeftOne.text = mLeftOneText
        mViewBinding.vTvRightOne.text = mRightOneText
    }


}