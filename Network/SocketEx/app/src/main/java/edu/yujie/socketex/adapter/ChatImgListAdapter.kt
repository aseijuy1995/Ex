package edu.yujie.socketex.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import edu.yujie.socketex.bean.ChatImg
import edu.yujie.socketex.databinding.ItemChatImgBinding


class ChatImgListAdapter : ListAdapter<ChatImg, ChatImgListAdapter.VH>(
    object : DiffUtil.ItemCallback<ChatImg>() {
        override fun areItemsTheSame(old: ChatImg, aNew: ChatImg): Boolean {
            return old.hashCode() == aNew.hashCode()
        }

        override fun areContentsTheSame(old: ChatImg, aNew: ChatImg): Boolean {
            return old.hashCode() == aNew.hashCode()
        }

    }
) {

    class VH(val binding: ItemChatImgBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(chatImg: ChatImg) = binding.apply {
            this.chatImgBean = chatImg
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