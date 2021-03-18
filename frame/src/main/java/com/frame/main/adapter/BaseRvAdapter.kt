package com.frame.main.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

/**
 * recyclerview适配器基类，该基类不支持dataBinding
 */
abstract class BaseRvAdapter<D> :
    RecyclerView.Adapter<BaseRvAdapter.BaseViewHolder>() {
    private val dataList: MutableList<D> = mutableListOf()

    var onItemClickCallback: (itemView: View, itemData: D, viewType: Int, position: Int) -> Unit =
        { itemView, itemData, viewType, position -> }
    var onItemLongClickCallback: (itemView: View, itemData: D, viewType: Int, position: Int) -> Unit =
        { itemView, itemData, viewType, position -> }

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

    abstract fun onBindViewHolder(holder: BaseViewHolder, position: Int, viewType: Int, item: D)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val binding = bindView(viewType, parent)
        return BaseViewHolder(binding, viewType)
    }

    abstract fun bindView(viewType: Int, parent: ViewGroup): ViewBinding

    /**
     * 根据位置删除数据
     * @param position 需要删除的位点
     * @param isNotificationAll 是否通知全部数据更新
     */
    fun removeItemForPosition(position: Int, isNotificationAll: Boolean = true): Int {
        if (position > dataList.size - 1) {
            return -1
        }
        dataList.removeAt(position)
        if (isNotificationAll) {
            notifyDataSetChanged()
        } else {
            notifyItemRemoved(position)
        }
        return 1
    }

    /**
     * 根据对象删除数据
     * @param item 删除对象
     * @param isNotificationAll 是否通知全部数据更新
     */
    fun removeItemForObject(item: D?, isNotificationAll: Boolean = true): Int {
        item?.let {
            val removeIndex = dataList.indexOf(item)
            if (removeIndex != -1) {
                dataList.remove(item)
                if (isNotificationAll) {
                    notifyDataSetChanged()
                } else {
                    notifyItemRemoved(removeIndex)
                }
                return 1
            }
            return -1
        } ?: let {
            return -1
        }
    }

    /**
     * 添加对象，position为插入位置，如果为-1，直接在集合末尾添加
     * @param item 插入对象
     * @param position 插入位置
     */
    fun addItem(item: D?, position: Int = -1, isNotificationAll: Boolean = false): Int {
        item?.let {
            if (position != -1 && position > 0 && position < dataList.size) {
                dataList.add(position, item)
                notifyItemInserted(position)
            } else {
                dataList.add(item)
                notifyItemInserted(dataList.size)
            }
            return 1
        } ?: let {
            return -1
        }

    }

    /**
     * 只添加对象，不更新adapter，position为插入位置，如果为-1，直接在集合末尾添加
     * @param item 插入对象
     * @param position 插入位置
     */
    fun onlyAddItem(item: D?, position: Int = -1): Int {
        item?.let {
            if (position != -1 && position > 0 && position < dataList.size) {
                dataList.add(position, item)
            } else {
                dataList.add(item)
            }
            return 1
        } ?: let {
            return -1
        }

    }

    fun clear() {
        dataList.clear()
        notifyDataSetChanged()
    }

    /**
     * 如果适配器数据为空，则添加数据列表
     * @param itedataList 添加的数据源
     * @param position 添加位置
     * @param isNotificationAll 是否通知整个列表更新
     */
    fun addItemIfEmpty(
        itedataList: MutableList<D>?,
        position: Int = -1,
        isNotificationAll: Boolean = false
    ) {
        if (dataList.isEmpty()) {
            itedataList?.let {
                addItedataList(itedataList, position, isNotificationAll)
            }
        }
    }

    /**
     * 添加对象集合
     * @param itedataList 对象集合
     * @param position 插入位置
     * @param isNotificationAll 是否全部更新，否的话只更新插入区域的数据
     */
    fun addItedataList(
        itedataList: MutableList<D>?,
        position: Int = -1,
        isNotificationAll: Boolean = false
    ) {
        itedataList?.let {
            if (position > dataList.size - 1 || position == -1) {
                dataList.addAll(itedataList)
                if (isNotificationAll) {
                    notifyDataSetChanged()
                } else {
                    notifyItemRangeChanged(position, itedataList.size)
                }
            } else {
                dataList.addAll(position, itedataList)
                if (isNotificationAll) {
                    notifyDataSetChanged()
                } else {
                    notifyItemRangeChanged(position, itedataList.size)
                }
            }
        }
    }

    /**
     * 只添加对象集合，不通知更新
     * @param itedataList 对象集合
     * @param position 插入位置
     * @param isNotificationAll 是否全部更新，否的话只更新插入区域的数据
     */
    fun onlyAddItedataList(itedataList: MutableList<D>?, position: Int = -1) {
        itedataList?.let {
            if (position > dataList.size - 1 || position == -1) {
                dataList.addAll(itedataList)
            } else {
                dataList.addAll(position, itedataList)
            }
        }
    }

    /**
     * 更新某条数据
     * @param item 需要更新的对象
     * @param position 需要更新的位置
     */
    fun updateItem(item: D?, position: Int): Int {
        item?.let {
            if (position > dataList.size) {
                return -1
            }
            dataList[position] = item
            notifyItemChanged(position)
            return 1
        } ?: let {
            return -1
        }
    }


}