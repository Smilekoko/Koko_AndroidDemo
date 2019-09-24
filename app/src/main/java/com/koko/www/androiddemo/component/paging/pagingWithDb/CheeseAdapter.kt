package com.koko.www.androiddemo.component.paging.pagingWithDb

import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil

class CheeseAdapter : PagedListAdapter<Cheese, CheeseViewHolder>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CheeseViewHolder =
            CheeseViewHolder(parent)

    override fun onBindViewHolder(holder: CheeseViewHolder, position: Int) {
        holder.bindTo(getItem(position))
    }


    companion object {
        /**
         * 当新生成的PagedList对比差异，回调定义行为
         */
        private val diffCallback = object : DiffUtil.ItemCallback<Cheese>() {
            override fun areContentsTheSame(oldItem: Cheese, newItem: Cheese): Boolean =
                    oldItem == newItem

            override fun areItemsTheSame(oldItem: Cheese, newItem: Cheese): Boolean =
                    oldItem.id == newItem.id

        }
    }

}
