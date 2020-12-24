package edu.yujie.socketex

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import edu.yujie.socketex.databinding.ItemInfoBinding

class InfoListAdapter : ListAdapter<String, InfoListAdapter.VH>(
    object : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }
) {

    inner class VH(private val binding: ItemInfoBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(str: String?) = binding.apply {
            msg = str
            executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ItemInfoBinding>(inflater, R.layout.item_info, parent, false)
        return VH(binding)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(getItem(position))
    }
}