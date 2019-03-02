package com.example.githubjobs.ui.base

import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.example.githubjobs.BR

abstract class BaseViewHolder<T : Any, B : ViewDataBinding>(private val binding: B) :
    RecyclerView.ViewHolder(binding.root) {

    protected lateinit var item: T

    open fun bind(lifecycleOwner: LifecycleOwner, item: T) {
        this.item = item
        binding.lifecycleOwner = lifecycleOwner
        binding.setVariable(BR.item, item)
        binding.executePendingBindings()
    }

}