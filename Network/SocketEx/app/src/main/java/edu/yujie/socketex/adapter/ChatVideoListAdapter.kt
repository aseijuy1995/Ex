package edu.yujie.socketex.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import edu.yujie.socketex.bean.ChatVideo
import edu.yujie.socketex.databinding.ItemChatVideoBinding


class ChatVideoListAdapter : ListAdapter<ChatVideo, ChatVideoListAdapter.VH>(
    object : DiffUtil.ItemCallback<ChatVideo>() {
        override fun areItemsTheSame(old: ChatVideo, aNew: ChatVideo): Boolean {
            return old.hashCode() == aNew.hashCode()
        }

        override fun areContentsTheSame(old: ChatVideo, aNew: ChatVideo): Boolean {
            return old.hashCode() == aNew.hashCode()
        }
    }
) {

    class VH(val binding: ItemChatVideoBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(chatVideo: ChatVideo) = binding.apply {
            this.chatVideo = chatVideo
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemChatVideoBinding.inflate(inflater, parent, false)
        return VH(binding)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(getItem(position))
    }

}