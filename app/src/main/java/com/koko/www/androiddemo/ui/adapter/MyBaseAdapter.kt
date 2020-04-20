package com.koko.www.androiddemo.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import com.koko.www.androiddemo.db.Animal
import android.widget.TextView
import com.koko.www.androiddemo.R


/**
 * 数据类型比较复杂，需要个性化定制布局，则可以采用BaseAdapter适配器进行数据适配
 */
class MyBaseAdapter(var context: Context, var data: List<Animal>) : BaseAdapter() {

    @SuppressLint("InflateParams")
    override fun getView(p0: Int, convertView: View?, viewGroup: ViewGroup?): View {
        val animal: Animal = getItem(p0) as Animal
        val view: View
        val viewHolder: ViewHolder
        if (convertView == null) {
            view = LayoutInflater.from(context).inflate(R.layout.simple_list_item_text_image, null)
            viewHolder = ViewHolder()
            viewHolder.animalImage = view.findViewById<View>(R.id.img) as ImageView
            viewHolder.animalName = view.findViewById<View>(R.id.tv) as TextView
            view.tag = viewHolder
        } else {
            view = convertView
            viewHolder = view.tag as ViewHolder
        }
        viewHolder.animalName!!.text = animal.animal
        viewHolder.animalImage!!.setImageResource(animal.imgId)
        return view
    }

    override fun getItem(p0: Int): Any {
        return data[p0]
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getCount(): Int {
        return data.size
    }

    //创建ViewHolder类
    internal inner class ViewHolder {
        var animalImage: ImageView? = null
        var animalName: TextView? = null
    }
}