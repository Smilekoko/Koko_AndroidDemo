package com.koko.www.androiddemo.ui.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.koko.www.androiddemo.model.Repo
import com.koko.www.androiddemo.ui.RepoViewHolder

/**
 *ReposAdapter extend ListAdapter
 */
class ReposAdapter : ListAdapter<Repo, RecyclerView.ViewHolder>(REPO_COMPARATOR) {

    /**
     * 提交新的数据时,对比每个item的回调
     */
    companion object {
        private val REPO_COMPARATOR = object : DiffUtil.ItemCallback<Repo>() {
            override fun areItemsTheSame(oldItem: Repo, newItem: Repo): Boolean =
                    oldItem.fullName == newItem.fullName

            override fun areContentsTheSame(oldItem: Repo, newItem: Repo): Boolean =
                    oldItem == newItem
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return RepoViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val repoItem = getItem(position)
        if (repoItem != null) {
            (holder as RepoViewHolder).bind(repoItem)
        }
    }

}
