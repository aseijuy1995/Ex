package edu.yujie.mvvmex

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import edu.yujie.mvvmex.bean.ItemBean
import edu.yujie.mvvmex.databinding.ItemItemBinding

class ItemListAdapter : RecyclerView.Adapter<ItemListAdapter.VH>() {
    private var items: List<ItemBean>? = null

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
        items?.let {
            val item = it[position]
            Glide.with(holder.binding.ivView.context)
                .load(item.owner.avatar_url)
                .placeholder(R.drawable.ic_baseline_insert_photo_24_gray)
                .into(holder.binding.ivView)
            holder.bind(item)
        }
    }

    override fun getItemCount(): Int = items?.size ?: 0

    fun submit(items: List<ItemBean>) {
        this.items = items
        notifyDataSetChanged()
    }
}