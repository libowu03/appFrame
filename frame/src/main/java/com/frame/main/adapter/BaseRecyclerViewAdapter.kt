package com.frame.main.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

/**
 * recyclerview最基础的adapter，其他几个基类适配器都继承自这个适配器
 * 非依赖内的适配器请不要继承自该类
 */
open class BaseRecyclerViewAdapter<D,H:RecyclerView.ViewHolder> : RecyclerView.Adapter<H>(){
    var context: Context? = null
    var onItemClickCallback: (itemView: View, itemData: D, viewType: Int, position: Int) -> Unit =
        { itemView, itemData, viewType, position -> }
    var onItemLongClickCallback: (itemView: View, itemData: D, viewType: Int, position: Int) -> Unit =
        { itemView, itemData, viewType, position -> }
    protected val dataList: MutableList<D> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): H {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: H, position: Int) {

    }

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
                addItemdataList(itedataList, position, isNotificationAll)
            }
        }
    }

    /**
     * 添加对象集合
     * @param itedataList 对象集合
     * @param position 插入位置
     * @param isNotificationAll 是否全部更新，否的话只更新插入区域的数据
     */
    fun addItemdataList(
        itemDataList: MutableList<D>?,
        position: Int = -1,
        isNotificationAll: Boolean = false
    ) {
        itemDataList?.let {
            if (position > dataList.size - 1 || position == -1) {
                dataList.addAll(itemDataList)
                if (isNotificationAll) {
                    notifyDataSetChanged()
                } else {
                    notifyItemRangeChanged(position, itemDataList.size)
                }
            } else {
                dataList.addAll(position, itemDataList)
                if (isNotificationAll) {
                    notifyDataSetChanged()
                } else {
                    notifyItemRangeChanged(position, itemDataList.size)
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
    fun onlyAddItemDataList(itedataList: MutableList<D>?, position: Int = -1) {
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