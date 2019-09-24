package com.koko.www.androiddemo.component.paging.pagingWithDb

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.koko.www.androiddemo.R

class CheeseViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.cheese_item, parent, false)) {

    //itemView代表的是parent: ViewGroup，但是要用 RecyclerView.ViewHolder的itemView才不会报错
    private val nameView = itemView.findViewById<TextView>(R.id.name)

    var cheese : Cheese? = null

    fun bindTo(cheese: Cheese?) {
        this.cheese = cheese
        Log.e("koko",cheese.toString())
        nameView.text = cheese?.name
    }
}