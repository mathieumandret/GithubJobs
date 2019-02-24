package com.example.githubjobs.ui.base

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class BaseViewHolder<T>(private val root: View) : RecyclerView.ViewHolder(root) {

    abstract fun bind(item: T)

}