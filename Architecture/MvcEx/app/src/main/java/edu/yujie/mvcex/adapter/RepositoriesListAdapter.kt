package edu.yujie.mvcex.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import edu.yujie.mvcex.databinding.ItemRepositoriesBinding
import edu.yujie.mvcex.model.ItemXXXX

class RepositoriesListAdapter : IListAdapter<ItemXXXX, RepositoriesListAdapter.VH>(
    object : DiffUtil.ItemCallback<ItemXXXX>() {
        override fun areItemsTheSame(oldItem: ItemXXXX, newItem: ItemXXXX): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }

        override fun areContentsTheSame(oldItem: ItemXXXX, newItem: ItemXXXX): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }
) {

    class VH(val binding: ItemRepositoriesBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(itemXXXX: ItemXXXX) = binding.apply {
            this.itemXXXX = itemXXXX
            executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemRepositoriesBinding.inflate(inflater, parent, false)
        return VH(binding)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val itemXXXX = getItem(position)
        holder.bind(itemXXXX)
    }
}