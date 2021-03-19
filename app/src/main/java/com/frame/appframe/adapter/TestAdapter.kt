package com.frame.appframe.adapter

import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import com.frame.appframe.R
import com.frame.main.adapter.BaseDataRvAdapter
import com.frame.main.databinding.ItemTestBinding

/**
 *
 */
class TestAdapter : BaseDataRvAdapter<String>() {
    override fun settingDataBinding(parent: ViewGroup, viewType: Int): ViewDataBinding {
        return getDataBindingView(R.layout.item_test,parent)
    }

    override fun onBindViewHolder(
        holder: BaseViewHolder,
        position: Int,
        viewType: Int,
        item: String?
    ) {
        (holder.item as ItemTestBinding).apply {
            bean = item
        }
    }

}