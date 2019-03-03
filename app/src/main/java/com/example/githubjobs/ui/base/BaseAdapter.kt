package com.example.githubjobs.ui.base

import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter

abstract class BaseAdapter<T : Any>(
    private val lifecycleOwner: LifecycleOwner,
    diffCallback: DiffUtil.ItemCallback<T>
) :
    ListAdapter<T, BaseViewHolder<T, *>>(diffCallback) {

    private var onItemClick: ((item: T) -> Unit)? = null

    fun setOnItemClickListener(listener: (item: T) -> Unit) {
        this.onItemClick = listener
    }

    override fun onBindViewHolder(holder: BaseViewHolder<T, *>, position: Int) {
        holder.bind(lifecycleOwner, getItem(position), onItemClick)
    }

}