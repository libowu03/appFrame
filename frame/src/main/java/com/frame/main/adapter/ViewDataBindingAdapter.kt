package com.frame.main.adapter

import android.app.Activity
import android.graphics.Color
import android.text.Html
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import java.lang.Exception

/**
 * 通过本地图片索引id设置图片
 */
@BindingAdapter("imgId")
fun loadImageById(view: ImageView?, imageId: Int) {
    view?.setImageResource(imageId)
}

/**
 * 通过图片链接设置图片
 */
@BindingAdapter("imgUrl")
fun loadImageByUrl(view: ImageView?, url: String) {
    if (view?.context is Activity){
        //ImageLoaderUtils.mmcImageLoaderUrl(view?.context as Activity,url,view, 0)
    }
}

/**
 * 通过boolean改变view的显示状态,设置gone
 */
@BindingAdapter("goneByBool")
fun goneByBool(view: View?, isHide:Boolean){
    if (isHide){
        view?.visibility = View.GONE
    }else{
        view?.visibility = View.VISIBLE
    }
}

/**
 * 通过boolean改变view的显示状态,设置INVISIBLE
 */
@BindingAdapter("visibleByBool")
fun visibleByBool(view:View?,isHide:Boolean){
    if (isHide){
        view?.visibility = View.INVISIBLE
    }else{
        view?.visibility = View.VISIBLE
    }
}

@BindingAdapter(value = ["htmlListToStr","htmlCutSign"],requireAll = true)
fun TextView.htmlListToStr(list:MutableList<String>?,cutSign:String?=" "){
    if (list == null || cutSign == null){
        return
    }
    if (list != null){
        val l = list.iterator()
        val result = StringBuffer("")
        var index = 0
        while (l.hasNext()) {
            val item = l.next().trim()
            //L.i("日志","长度：${item.length}")
            if (item.isNotEmpty() && item != null && !item.endsWith("\n")){
                //L.i("日志","进入长度：${item.length}")

                //最后一行不添加分割符
                if (!l.hasNext()) {
                    result.append(item)
                } else {
                    if (list[index+1].isNotEmpty() && !list[index+1].endsWith("\n")){
                        result.append(item + cutSign)
                    }else{
                        result.append(item)
                    }
                }
            }
            index++
        }
        this.text = Html.fromHtml(result.toString())
    }else{
        this.text = ""
    }
}


/**
 * 将list转换成字符
 * @param listToStringData(list) 数据源
 * @param listCutSign(cutSign) 分割字符
 * 设置时一定要同时设置listToStringData和listCutSign这两个参数，否则无法编译过
 */
@BindingAdapter(value=["listToStringData","listCutSign"],requireAll = true)
fun TextView.listToString(list:MutableList<String>?, cutSign:String?=" "){
    if (list != null){
        val l = list.iterator()
        val result = StringBuffer("")
        var index = 0
        while (l.hasNext()) {
            val item = l.next().trim()
            //L.i("日志","长度：${item.length}")
            if (item.isNotEmpty() && item != null && !item.endsWith("\n")){
                //L.i("日志","进入长度：${item.length}")

                //最后一行不添加分割符
                if (!l.hasNext()) {
                    result.append(item)
                } else {
                    if (list[index+1].isNotEmpty() && !list[index+1].endsWith("\n")){
                        result.append(item + cutSign)
                    }else{
                        result.append(item)
                    }
                }
            }
            index++
        }
        this.text = result.toString()
    }else{
        this.text = ""
    }
}

/**
 * 将文本颜色直接设置为颜色
 */
@BindingAdapter("strToTextColor")
fun TextView.setStrToTextColor(color:String?){
    color?.let {
        try{
            this.setTextColor(Color.parseColor(it))
        }catch (e:Exception){
            e.printStackTrace()
        }
    }
}


/**
 * 将html转换成正常文本
 */
@BindingAdapter("htmlToStr")
fun TextView.htmlToStr(str:String?){
    str?.let {
        this.text = Html.fromHtml(str)
    }
}