package edu.yujie.socketex.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import edu.yujie.socketex.bean.ChatImgBean
import edu.yujie.socketex.databinding.ItemChatImgBinding


class ChatImgListAdapter : ListAdapter<ChatImgBean, ChatImgListAdapter.VH>(
    object : DiffUtil.ItemCallback<ChatImgBean>() {
        override fun areItemsTheSame(oldItem: ChatImgBean, newItem: ChatImgBean): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }

        override fun areContentsTheSame(oldItem: ChatImgBean, newItem: ChatImgBean): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }

    }
) {

    class VH(val binding: ItemChatImgBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(chatImgBean: ChatImgBean) = binding.apply {
            this.chatImgBean = chatImgBean
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