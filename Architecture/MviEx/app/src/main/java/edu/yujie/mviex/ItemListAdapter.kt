package edu.yujie.mviex

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import edu.yujie.mviex.bean.ItemBean
import edu.yujie.mviex.databinding.ItemItemBinding

class ItemListAdapter : ListAdapter<ItemBean, ItemListAdapter.VH>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ItemBean>() {
            override fun areItemsTheSame(oldItem: ItemBean, newItem: ItemBean): Boolean {
                return oldItem.hashCode() == newItem.hashCode()
            }

            override fun areContentsTheSame(oldItem: ItemBean, newItem: ItemBean): Boolean {
                return oldItem.hashCode() == newItem.hashCode()
            }

        }
    }

    class VH(val binding: ItemItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ItemBean) = binding.apply {
            this.item = item
            executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ItemItemBinding>(inflater, R.layout.item_item, parent, false)
        return VH(binding)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val item = getItem(position)
        Glide.with(holder.binding.ivView.context)
            .load(item.owner.avatar_url)
            .placeholder(R.drawable.ic_baseline_insert_photo_24_gray)
            .into(holder.binding.ivView)
        holder.bind(item)
    }
}