package edu.yujie.pagingex.paging3

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import edu.yujie.pagingex.Concert
import edu.yujie.pagingex.R
import edu.yujie.pagingex.databinding.ItemConcertBinding

class ConcertDataAdapter : PagingDataAdapter<Concert, ConcertDataAdapter.VH>(sDiffCallback) {

    companion object {
        private val sDiffCallback = object : DiffUtil.ItemCallback<Concert>() {
            override fun areItemsTheSame(oldItem: Concert, newItem: Concert): Boolean =
                oldItem.id == newItem.id && oldItem.name == newItem.name

            override fun areContentsTheSame(oldItem: Concert, newItem: Concert): Boolean =
                oldItem == newItem

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ItemConcertBinding>(inflater, R.layout.item_concert, parent, false)
        return VH(binding)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val concert = getItem(position)
        holder.bind(concert)
    }

    inner class VH(private val binding: ItemConcertBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(concert: Concert?) =
            binding.apply {
                this.concert = concert
                executePendingBindings()
            }

    }


}