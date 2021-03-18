package com.frame.appframe.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.ViewParent
import com.frame.main.adapter.BaseAdapter
import com.frame.main.databinding.ItemTestBinding

class TestAdapter : BaseAdapter<ItemTestBinding, String>() {

    override fun bindView(viewType: Int, parent: ViewGroup): ItemTestBinding {
        return ItemTestBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    }

    override fun onBindViewHolder(
        holder: BaseViewHolder<ItemTestBinding>,
        position: Int,
        viewType: Int,
        item: String
    ) {

    }

}