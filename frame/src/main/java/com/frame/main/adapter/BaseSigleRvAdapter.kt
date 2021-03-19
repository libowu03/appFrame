package com.frame.main.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

/**
 * recyclerview的基础适配器 不支持viewDataBinding
 * 这个适合列表中只有一个类型显示的情况。如果列表中存在多个布局，且不需要viewDataBinding，请使用BaseRvAdapter
 */
abstract class BaseSigleRvAdapter<T : ViewBinding, D> :
    BaseRecyclerViewAdapter<D,BaseSigleRvAdapter.BaseHolder>() {

    class BaseHolder(var binding: ViewBinding, var viewType: Int) :
        RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseSigleRvAdapter.BaseHolder {
        return BaseHolder(bindView(), viewType)
    }

    abstract fun bindView(): T

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: BaseSigleRvAdapter.BaseHolder, position: Int) {
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

    abstract fun onBindViewHolder(
        holder: BaseHolder,
        position: Int,
        viewType: Int,
        any: D?
    )

}