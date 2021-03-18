package com.frame.appframe.adapter

import android.app.Activity
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.frame.appframe.R
import com.frame.main.adapter.BaseDataRvAdapter
import com.frame.main.databinding.ItemTestBinding

class TestDataAdapter : BaseDataRvAdapter<String>() {
    override fun onBindViewHolder(
        holder: BaseViewHolder,
        position: Int,
        viewType: Int,
        item: String
    ) {
        holder
    }

    override fun settingDataBinding(parent: ViewGroup, viewType: Int): ViewDataBinding {
        return getDataBindingView(R.layout.item_test,parent)
    }
}