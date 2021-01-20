package edu.yujie.socketex.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jakewharton.rxbinding4.view.clicks
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

    inner class VH(val binding: ItemMediaBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(media: Media) = binding.apply {
            this.media = media
            executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemMediaBinding.inflate(inflater, parent, false)
        return VH(binding)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val media = getItem(position)
        holder.bind(media)
        holder.binding.root
            .clicks()
            .subscribe { itemClickRelay.accept(media) }
    }
}