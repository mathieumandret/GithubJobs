package com.example.githubjobs.ui.positions.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.DiffUtil
import com.example.githubjobs.R
import com.example.githubjobs.data.local.model.Position
import com.example.githubjobs.databinding.PositionItemBinding
import com.example.githubjobs.ui.base.BaseAdapter
import com.example.githubjobs.ui.base.BaseViewHolder

class PositionsAdapter(lifecycleOwner: LifecycleOwner) : BaseAdapter<Position>(lifecycleOwner, object :
    DiffUtil.ItemCallback<Position>() {

    override fun areItemsTheSame(oldItem: Position, newItem: Position): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Position, newItem: Position): Boolean {
        return oldItem == newItem
    }

}) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<Position, *> {
        val binding: PositionItemBinding =
            DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.position_item, parent, false)
        return PositionViewHolder(binding)
    }

    class PositionViewHolder(binding: PositionItemBinding) :
        BaseViewHolder<Position, PositionItemBinding>(binding)

}