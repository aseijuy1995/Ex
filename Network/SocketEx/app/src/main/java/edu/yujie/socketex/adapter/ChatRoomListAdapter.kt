package edu.yujie.socketex.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.jakewharton.rxbinding4.view.clicks
import edu.yujie.socketex.ChatBean
import edu.yujie.socketex.R
import edu.yujie.socketex.databinding.ItemChatOneselfBinding
import edu.yujie.socketex.databinding.ItemChatOtherBinding
import edu.yujie.socketex.vm.ChatRoomViewModel

const val ONESELF = -1
const val OTHER = 0

class ChatListAdapter(val viewModel: ChatRoomViewModel) : ListAdapter<ChatBean, ChatListAdapter.VH>(
    object : DiffUtil.ItemCallback<ChatBean>() {
        override fun areItemsTheSame(oldItem: ChatBean, newItem: ChatBean): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }

        override fun areContentsTheSame(oldItem: ChatBean, newItem: ChatBean): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }
) {

    abstract inner class VH(binding: ViewBinding) : RecyclerView.ViewHolder(binding.root) {
        abstract fun bind(chatBean: ChatBean): Any
    }

    inner class OneSelfVH(private val binding: ItemChatOneselfBinding, val context: Context) : VH(binding) {
        override fun bind(chatBean: ChatBean) = binding.apply {
            chatBean.imgBytes?.let {
                rvImg.adapter = ChatImgListAdapter().apply {
                    submitList(it)
                }
            }
            this.chatBean = chatBean
            executePendingBindings()
        }
    }

    inner class OtherVH(val binding: ItemChatOtherBinding, val context: Context) : VH(binding) {
        override fun bind(chatBean: ChatBean) = binding.apply {
            chatBean.imgBytes?.let {
                rvImg.adapter = ChatImgListAdapter().apply {
                    submitList(it)
                }
            }
            ivRecorder.clicks().subscribe {
                println("clicks=clicks=clicks=clicks=clicks")
                if (viewModel.mediaPlayerState.value!!) {
//                        chatBean.recorderBytes?.let { viewModel.startPlayer(it) }
                    viewModel.mMediaPlayerState.postValue(false)
                    binding.ivRecorder.setImageResource(R.drawable.ic_baseline_play_circle_24_white)
                } else {
//                        viewModel.stopPlayer()
                    viewModel.mMediaPlayerState.postValue(true)
                    binding.ivRecorder.setImageResource(R.drawable.ic_baseline_stop_circle_24_white)

                }
            }
//            viewModel.mediaPlayerState.observe(){
//
//            }
            this.chatBean = chatBean
            executePendingBindings()
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            ONESELF -> {
                val binding = DataBindingUtil.inflate<ItemChatOneselfBinding>(inflater, R.layout.item_chat_oneself, parent, false)
                OneSelfVH(binding, parent.context)
            }
            OTHER -> {
                val binding = DataBindingUtil.inflate<ItemChatOtherBinding>(inflater, R.layout.item_chat_other, parent, false)
                OtherVH(binding, parent.context)
            }
            else -> {
                val binding = DataBindingUtil.inflate<ItemChatOtherBinding>(inflater, R.layout.item_chat_other, parent, false)
                OtherVH(binding, parent.context)
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