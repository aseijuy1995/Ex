package tw.north27.coachingapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.daimajia.swipe.SwipeLayout
import com.jakewharton.rxrelay3.PublishRelay
import tw.north27.coachingapp.databinding.ItemChatListBinding
import tw.north27.coachingapp.model.result.ChatInfo


class ChatListAdapter : ListAdapter<ChatInfo, ChatListAdapter.VH>(object : DiffUtil.ItemCallback<ChatInfo>() {
    override fun areItemsTheSame(oldItem: ChatInfo, newItem: ChatInfo): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ChatInfo, newItem: ChatInfo): Boolean {
        return oldItem.hashCode() == newItem.hashCode()
    }
}
) {

    val soundClickRelay = PublishRelay.create<Pair<View, ChatInfo>>()

    val deleteClickRelay = PublishRelay.create<Pair<View, ChatInfo>>()

    val itemClickRelay = PublishRelay.create<Pair<View, ChatInfo>>()

    inner class VH(val binding: ItemChatListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(chat: ChatInfo) = binding.apply {
            this.chat = chat
            itemChatListSwipe.flSound.setOnClickListener { soundClickRelay.accept(it to chat) }
            itemChatListSwipe.flDelete.setOnClickListener { deleteClickRelay.accept(it to chat) }
            itemView.setOnClickListener {
                if (swipeLayout.openStatus == SwipeLayout.Status.Open)
                    swipeLayout.close()
                else if (swipeLayout.openStatus == SwipeLayout.Status.Middle)
                else
                    itemClickRelay.accept(it to chat)
            }
            executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val binding = ItemChatListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VH(binding)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val chat = getItem(position)
        holder.bind(chat)
    }

}