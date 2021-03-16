package tw.north27.coachingapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jakewharton.rxrelay3.ReplayRelay
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

    val notifyClickRelay = ReplayRelay.create<Pair<View, ChatInfo>>()

    val deleteClickRelay = ReplayRelay.create<Pair<View, ChatInfo>>()

    inner class VH(val binding: ItemChatListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(chat: ChatInfo) = binding.apply {
            this.chat = chat
            binding.itemChatListSwipe.flDelete.setOnClickListener {
                deleteClickRelay.accept(it to chat)
            }
            binding.itemChatListSwipe.flNotify.setOnClickListener {
                notifyClickRelay.accept(it to chat)
            }
            executePendingBindings()
            binding.itemChatListSwipe.executePendingBindings()
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