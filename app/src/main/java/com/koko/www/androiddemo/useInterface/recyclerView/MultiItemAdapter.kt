package com.koko.www.androiddemo.useInterface.recyclerView

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.koko.www.androiddemo.R
import kotlinx.android.synthetic.main.layout_my_text_view_blue.view.*
import kotlinx.android.synthetic.main.layout_my_text_view_pink.view.*
import kotlinx.android.synthetic.main.layout_my_text_view_yellow.view.*

class MultiItemAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
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


    /***
     * Enum class for recyclerView Cell type,这个只是个标识符具体position怎么对应布局在
     * getItemViewType()重载方法中
     */
    enum class CellType(viewType: Int) {
        Pink(0),
        Yellow(1),
        Blue(2),
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            CellType.Blue.ordinal -> BlueViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.layout_my_text_view_blue, parent, false))
            CellType.Pink.ordinal -> PinkViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.layout_my_text_view_pink, parent, false))
            else -> YellowViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.layout_my_text_view_yellow, parent, false))
        }
    }

    override fun getItemCount(): Int {
        return mDataSet.size
    }

    /***
     * This method will return cell type base on position
     */
    override fun getItemViewType(position: Int): Int {

        return when {
            position == mDataSet.size - 1 -> CellType.Yellow.ordinal
            position % 2 == 0 -> CellType.Blue.ordinal
            else -> CellType.Pink.ordinal
        }
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            CellType.Pink.ordinal -> {
                val headerViewHolder = holder as PinkViewHolder
                headerViewHolder.bindView(mDataSet[position])
            }
            CellType.Blue.ordinal -> {
                val headerViewHolder = holder as BlueViewHolder
                headerViewHolder.bindView(mDataSet[position])
            }
            CellType.Yellow.ordinal -> {
                val headerViewHolder = holder as YellowViewHolder
                headerViewHolder.bindView(mDataSet[position])
            }
        }
    }


    class PinkViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindView(string: String) {
            itemView.info_text_pink.text = string
        }
    }

    class BlueViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindView(string: String) {
            itemView.info_text_blue.text = string

        }
    }

    class YellowViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindView(string: String) {
            itemView.info_text_yellow.text = string
        }
    }
}