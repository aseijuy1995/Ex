package edu.yujie.socketex.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jakewharton.rxrelay3.PublishRelay
import edu.yujie.socketex.bean.Media
import edu.yujie.socketex.databinding.ItemMediaBinding

class MediaListAdapter : ListAdapter<Media, MediaListAdapter.VH>(
    object : DiffUtil.ItemCallback<Media>() {
        override fun areItemsTheSame(oldItem: Media, newItem: Media): Boolean = oldItem.hashCode() == newItem.hashCode()

        override fun areContentsTheSame(oldItem: Media, newItem: Media): Boolean = oldItem.hashCode() == newItem.hashCode()
    }
) {

    val itemClickRelay = PublishRelay.create<Media>()

    val itemSelectedRelay = PublishRelay.create<Media>()

    inner class VH(val binding: ItemMediaBinding, val context: Context) : RecyclerView.ViewHolder(binding.root) {
        fun bind(media: Media) = binding.apply {
            this.media = media
            executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemMediaBinding.inflate(inflater, parent, false)
        return VH(binding, parent.context)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val media = getItem(position)
        holder.bind(media)
        holder.itemView.setOnClickListener {
            itemClickRelay.accept(media)
        }
        holder.binding.chkSelect.setOnCheckedChangeListener { _, b ->
            media.isSelect = b
            itemSelectedRelay.accept(media)
        }
    }
}