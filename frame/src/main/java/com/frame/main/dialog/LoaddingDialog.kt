package com.frame.main.dialog

import android.animation.ObjectAnimator
import android.view.animation.Animation.INFINITE
import android.view.animation.LinearInterpolator
import com.frame.main.databinding.BaseDialogLoadingBinding

class LoaddingDialog : BaseFragmentDialog<BaseDialogLoadingBinding>() {
    private var animal:ObjectAnimator?=null

    override fun initListener() {

    }

    override fun initData() {
        //开始执行旋转动画
        animal = ObjectAnimator.ofFloat(viewBinding.vImageLoading,"rotation",0f,360f)
        animal?.duration = 1000
        animal?.repeatMode = INFINITE
        animal?.repeatCount = INFINITE
        animal?.interpolator = LinearInterpolator()
        animal?.start()
    }

    override fun dismiss() {
        super.dismiss()
        animal?.cancel()
    }


}