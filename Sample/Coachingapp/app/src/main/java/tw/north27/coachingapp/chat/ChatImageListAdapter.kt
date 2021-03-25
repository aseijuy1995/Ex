package tw.north27.coachingapp.chat

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jakewharton.rxrelay3.BehaviorRelay
import com.jakewharton.rxrelay3.PublishRelay
import tw.north27.coachingapp.databinding.ItemChatImageBinding
import tw.north27.coachingapp.model.result.ChatImage

class ChatImageListAdapter : ListAdapter<ChatImage, ChatImageListAdapter.VH>(
    object : DiffUtil.ItemCallback<ChatImage>() {
        override fun areItemsTheSame(oldItem: ChatImage, newItem: ChatImage): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ChatImage, newItem: ChatImage): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }
) {
    val itemClickRelay = PublishRelay.create<Pair<View, ChatImage>>()

    inner class VH(val binding: ItemChatImageBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(chatImage: ChatImage) = binding.apply {
            this.chatImage = chatImage
            itemView.setOnClickListener { itemClickRelay.accept(it to chatImage) }
            executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val binding = ItemChatImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VH(binding)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(getItem(position))
    }

}