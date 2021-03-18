package com.frame.appframe.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.frame.main.adapter.BaseRvAdapter
import com.frame.main.databinding.ItemTestBinding

class TestAdapter : BaseRvAdapter<String>() {

    override fun onBindViewHolder(
        holder: BaseViewHolder,
        position: Int,
        viewType: Int,
        item: String
    ) {

    }

    override fun bindView(viewType: Int, parent: ViewGroup): ViewBinding {
        return ItemTestBinding.inflate(LayoutInflater.from(parent.context),parent,false)
    }

}