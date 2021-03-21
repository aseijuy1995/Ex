package tw.north27.coachingapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.daimajia.swipe.SwipeLayout
import com.jakewharton.rxrelay3.ReplayRelay
import timber.log.Timber
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

    val soundClickRelay = ReplayRelay.create<Pair<View, ChatInfo>>()

    val deleteClickRelay = ReplayRelay.create<Pair<View, ChatInfo>>()

    val itemClickRelay = ReplayRelay.create<Pair<View, ChatInfo>>()

    inner class VH(val binding: ItemChatListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(chat: ChatInfo) = binding.apply {
            this.chat = chat
            itemChatListSwipe.flSound.setOnClickListener { soundClickRelay.accept(it to chat) }
            itemChatListSwipe.flDelete.setOnClickListener { deleteClickRelay.accept(it to chat) }
            itemView.setOnClickListener { itemClickRelay.accept(it to chat) }

            swipeLayout.addSwipeListener(object : SwipeLayout.SwipeListener {
                override fun onStartOpen(layout: SwipeLayout?) {
                }

                override fun onOpen(layout: SwipeLayout?) {
                    chat.isSwipe = true
                }

                override fun onStartClose(layout: SwipeLayout?) {
                }

                override fun onClose(layout: SwipeLayout?) {
                    chat.isSwipe = false
                }

                override fun onUpdate(layout: SwipeLayout?, leftOffset: Int, topOffset: Int) {
                }

                override fun onHandRelease(layout: SwipeLayout?, xvel: Float, yvel: Float) {
                }

            })
//            itemChatListSwipe.executePendingBindings()
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