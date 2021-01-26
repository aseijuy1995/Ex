package edu.yujie.socketex.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jakewharton.rxrelay3.PublishRelay
import edu.yujie.socketex.bean.ChatImg
import edu.yujie.socketex.databinding.ItemChatImgBinding


class ChatImgListAdapter : ListAdapter<ChatImg, ChatImgListAdapter.VH>(
    object : DiffUtil.ItemCallback<ChatImg>() {
        override fun areItemsTheSame(oldItem: ChatImg, newItem: ChatImg): Boolean = oldItem.hashCode() == newItem.hashCode()

        override fun areContentsTheSame(oldItem: ChatImg, newItem: ChatImg): Boolean = oldItem.hashCode() == newItem.hashCode()
    }
) {
    val itemImgClickRelay = PublishRelay.create<ChatImg>()

    inner class VH(val binding: ItemChatImgBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(chatImg: ChatImg) = binding.apply {
            ivItem.setOnClickListener { itemImgClickRelay.accept(chatImg) }
            this.chatImg = chatImg
            executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemChatImgBinding.inflate(inflater, parent, false)
        return VH(binding)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(getItem(position))
    }

}