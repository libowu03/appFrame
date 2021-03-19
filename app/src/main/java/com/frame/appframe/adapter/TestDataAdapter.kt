package com.frame.appframe.adapter

import android.app.Activity
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.frame.appframe.R
import com.frame.main.adapter.BaseDataRvAdapter
import com.frame.main.adapter.BaseSingleDataRvAdapter
import com.frame.main.databinding.ItemTestBinding

class TestDataAdapter : BaseSingleDataRvAdapter<ItemTestBinding, String>() {

    override fun setLayoutId(): Int {
        return R.layout.item_test
    }

    override fun onBindViewHolder(
        holder: BaseHolder<ItemTestBinding>,
        position: Int,
        viewType: Int,
        item: String,
        dataBinding: ItemTestBinding
    ) {
        dataBinding.apply {
            bean = item
        }
    }
}