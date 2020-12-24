package edu.yujie.socketex

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import edu.yujie.socketex.databinding.ItemChatBinding

class ChatListAdapter : ListAdapter<ChatBean, ChatListAdapter.VH>(
    object : DiffUtil.ItemCallback<ChatBean>() {
        override fun areItemsTheSame(oldItem: ChatBean, newItem: ChatBean): Boolean {
            return oldItem.id == newItem.id && oldItem.msg == newItem.msg && oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: ChatBean, newItem: ChatBean): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }

    }
) {
    inner class VH(private val binding: ItemChatBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(chatBean: ChatBean) = binding.apply {
            this.chatBean = chatBean
            executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ItemChatBinding>(inflater, R.layout.item_chat, parent, false)
        return VH(binding)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(getItem(position))
    }
}