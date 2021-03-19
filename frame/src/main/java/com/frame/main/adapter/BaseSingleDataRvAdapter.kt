package com.frame.main.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

/**
 * recyclerview的基础适配器 支持viewDataBinding，如果layout中不支持viewDataBinding，会报错
 * 这个适合列表中只有一个类型显示的情况。如果列表中存在多个布局，且需要支持ViewDataBinding，请使用BaseDataRvAdapter
 * 如果不需要ViewDataBinding，且需要支持多布局的，请使用BaseRvAdapter
 */
abstract class BaseSingleDataRvAdapter<T : ViewDataBinding, D> :
    BaseRecyclerViewAdapter<D, BaseSingleDataRvAdapter.BaseHolder<T>>() {

    class BaseHolder<T : ViewDataBinding>(var dataBinding: T, var viewType: Int) :
        RecyclerView.ViewHolder(dataBinding.root) {
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseSingleDataRvAdapter.BaseHolder<T> {
        context = parent.context
        val view = LayoutInflater.from(context).inflate(setLayoutId(), parent, false)
        val dataBinding = DataBindingUtil.bind<T>(view)
        return BaseHolder(dataBinding!!, viewType)
    }

    abstract fun onBindViewHolder(
        holder: BaseHolder<T>,
        position: Int,
        viewType: Int,
        item: D,
        dataBinding: T
    )

    abstract fun setLayoutId(): Int

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: BaseSingleDataRvAdapter.BaseHolder<T>, position: Int) {
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
        onBindViewHolder(holder, position, holder.viewType, dataList[position], holder.dataBinding)
    }
}