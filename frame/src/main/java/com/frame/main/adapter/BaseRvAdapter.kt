package com.frame.main.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

/**
 * recyclerview适配器基类，该基类不支持dataBinding
 * 如果需要支持dataBinding的适配器，可以使用com.frame.main.adapter.BaseDataRvAdapter
 */
abstract class BaseRvAdapter<D> :
    BaseRecyclerViewAdapter<D,BaseRvAdapter.BaseViewHolder>() {

    class BaseViewHolder(var binding: ViewBinding, var viewType: Int) :
        RecyclerView.ViewHolder(binding.root) {
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
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

    abstract fun onBindViewHolder(holder: BaseViewHolder, position: Int, viewType: Int, item: D?)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val binding = bindView(viewType, parent)
        return BaseViewHolder(binding, viewType)
    }

    abstract fun bindView(viewType: Int, parent: ViewGroup): ViewBinding
}