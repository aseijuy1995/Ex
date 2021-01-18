package edu.yujie.socketex.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import edu.yujie.socketex.bean.ChatImgItem
import edu.yujie.socketex.databinding.ItemChatImgBinding


class ChatImgListAdapter : ListAdapter<ChatImgItem, ChatImgListAdapter.VH>(
    object : DiffUtil.ItemCallback<ChatImgItem>() {
        override fun areItemsTheSame(oldItem: ChatImgItem, newItem: ChatImgItem): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }

        override fun areContentsTheSame(oldItem: ChatImgItem, newItem: ChatImgItem): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }

    }
) {

    class VH(val binding: ItemChatImgBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(chatImgItem: ChatImgItem) = binding.apply {
            this.chatImgBean = chatImgItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemChatImgBinding.inflate(inflater, parent, false)
        return VH(binding)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val chatImgBean = getItem(position)
        holder.bind(chatImgBean)
    }

}