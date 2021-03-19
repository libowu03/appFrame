package com.frame.main.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

/**
 * recyclerview适配器基类，该基类支持dataBinding
 * 如果不需要dataBinding，请使用com.frame.main.adapter.BaseRvAdapter
 */
abstract class BaseDataRvAdapter<D> : BaseRecyclerViewAdapter<D,BaseDataRvAdapter.BaseViewHolder>() {

    class BaseViewHolder(var item: ViewDataBinding, var viewType: Int) :
        RecyclerView.ViewHolder(item.root) {
    }

    abstract fun onBindViewHolder(holder: BaseViewHolder, position: Int, viewType: Int, item: D?)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseDataRvAdapter.BaseViewHolder {
        context = parent.context
        val dataBinding = settingDataBinding(parent, viewType)
        return BaseViewHolder(dataBinding, viewType)
    }

    abstract fun settingDataBinding(parent: ViewGroup, viewType: Int): ViewDataBinding

    override fun onBindViewHolder(holder: BaseDataRvAdapter.BaseViewHolder, position: Int) {
        holder.itemView.setOnClickListener {
            onItemClickCallback.invoke(
                holder.itemView,
                dataList[position],
                holder.viewType,
                position
            )
        }
        holder.itemView.setOnLongClickListener {
            onItemLongClickCallback.invoke(
                holder.itemView,
                dataList[position],
                holder.viewType,
                position
            )
            return@setOnLongClickListener true
        }
        onBindViewHolder(holder, position, holder.viewType, dataList[position])
    }

    fun getDataBindingView(layoutId: Int, parent: ViewGroup): ViewDataBinding {
        return DataBindingUtil.inflate(LayoutInflater.from(context), layoutId, parent, false)
    }
}