package edu.yujie.socketex

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import edu.yujie.socketex.databinding.ItemChatOneselfBinding
import edu.yujie.socketex.databinding.ItemChatOtherBinding

const val ONESELF = -1
const val OTHER = 0

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

    abstract inner class VH(binding: ViewBinding) : RecyclerView.ViewHolder(binding.root) {
        abstract fun bind(chatBean: ChatBean): Any
    }

    inner class OneSelfVH(private val binding: ItemChatOneselfBinding) : VH(binding) {
        override fun bind(chatBean: ChatBean) = binding.apply {
            this.chatBean = chatBean
            executePendingBindings()
        }
    }

    inner class OtherVH(private val binding: ItemChatOtherBinding) : VH(binding) {
        override fun bind(chatBean: ChatBean) = binding.apply {
            this.chatBean = chatBean
            executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            ONESELF -> {
                val binding = DataBindingUtil.inflate<ItemChatOneselfBinding>(inflater, R.layout.item_chat_oneself, parent, false)
                OneSelfVH(binding)
            }
            OTHER -> {
                val binding = DataBindingUtil.inflate<ItemChatOtherBinding>(inflater, R.layout.item_chat_other, parent, false)
                OtherVH(binding)
            }
            else -> {
                val binding = DataBindingUtil.inflate<ItemChatOtherBinding>(inflater, R.layout.item_chat_other, parent, false)
                OtherVH(binding)
            }
        }

    }

    override fun getItemViewType(position: Int): Int {
        val chatBean = getItem(position)
        return if (chatBean.isOneSelf) ONESELF else OTHER
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(getItem(position))
    }
}