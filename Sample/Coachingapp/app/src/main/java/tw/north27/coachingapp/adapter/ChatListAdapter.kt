package tw.north27.coachingapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import tw.north27.coachingapp.databinding.ItemChatListBinding
import tw.north27.coachingapp.model.result.ChatInfo


class ChatListAdapter : ListAdapter<ChatInfo, ChatListAdapter.VH>(object : DiffUtil.ItemCallback<ChatInfo>() {
    override fun areItemsTheSame(oldItem: ChatInfo, newItem: ChatInfo): Boolean {
        return oldItem.hashCode() == newItem.hashCode()
    }

    override fun areContentsTheSame(oldItem: ChatInfo, newItem: ChatInfo): Boolean {
        return oldItem.hashCode() == newItem.hashCode()
    }

}) {

    inner class VH(val binding: ItemChatListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(chatInfo: ChatInfo) = binding.apply {
            this.chatInfo = chatInfo
            executePendingBindings()
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val binding = ItemChatListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VH(binding)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(getItem(position))
    }
}