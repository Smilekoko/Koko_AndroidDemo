package com.koko.www.androiddemo.ui.adapter

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import com.koko.www.androiddemo.R
import com.koko.www.androiddemo.ui.adapter.ListAdapter.ViewHolder

/**
 * Created by 17962 on 2017/11/29.
 */

class ListAdapter : RecyclerView.Adapter<ViewHolder>() {

    private val mDataSet = arrayOf("android",
            "Unity3d", "手绘", "UR4", "c++", "java", "android",
            "Unity3d", "手绘", "UR4", "c++", "java", "android",
            "Unity3d", "手绘", "UR4", "c++", "java", "android",
            "Unity3d", "手绘", "UR4", "c++", "java", "android",
            "Unity3d", "手绘", "UR4", "c++", "java", "android",
            "Unity3d", "手绘", "UR4", "c++", "java", "android",
            "Unity3d", "手绘", "UR4", "c++", "java", "android",
            "Unity3d", "手绘", "UR4", "c++", "java", "android",
            "Unity3d", "手绘", "UR4", "c++", "java", "android",
            "Unity3d", "手绘", "UR4", "c++", "java", "android",
            "Unity3d", "手绘", "UR4", "c++", "java", "android", "Unity3d", "手绘", "UR4", "c++", "java")


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
                .inflate(R.layout.layout_my_text_view_pink, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.mTextView.text = mDataSet[position]

    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var mTextView: TextView = view.findViewById(R.id.info_text_pink)
    }


    override fun getItemCount(): Int {
        return mDataSet.size
    }
}
