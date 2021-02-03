package edu.yujie.socketex.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.jakewharton.rxbinding4.view.clicks
import com.jakewharton.rxrelay3.PublishRelay
import edu.yujie.socketex.R
import edu.yujie.socketex.bean.ChatImg
import edu.yujie.socketex.bean.ChatItem
import edu.yujie.socketex.bean.ChatSender
import edu.yujie.socketex.databinding.ItemChatOtherBinding
import edu.yujie.socketex.databinding.ItemChatOwnerBinding

class ChatListAdapter : ListAdapter<ChatItem, ChatListAdapter.VH>(
    object : DiffUtil.ItemCallback<ChatItem>() {
        override fun areItemsTheSame(oldItem: ChatItem, newItem: ChatItem): Boolean = oldItem.hashCode() == newItem.hashCode()

        override fun areContentsTheSame(oldItem: ChatItem, newItem: ChatItem): Boolean = oldItem.hashCode() == newItem.hashCode()
    }
) {

    val itemImgClickRelay = PublishRelay.create<ChatImg>()//image click

    val itemRecordingClickRelay = PublishRelay.create<Pair<Boolean, ChatItem>>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            ChatSender.OWNER.value -> {
                val binding = DataBindingUtil.inflate<ItemChatOwnerBinding>(inflater, R.layout.item_chat_owner, parent, false)
                OwnerVH(binding, parent.context)
            }
//            ChatSender.OTHER.value -> {
            else -> {
                val binding = DataBindingUtil.inflate<ItemChatOtherBinding>(inflater, R.layout.item_chat_other, parent, false)
                OtherVH(binding, parent.context)
            }
        }
    }

    override fun getItemViewType(position: Int): Int = getItem(position).sender.value

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(getItem(position))
    }

    abstract inner class VH(binding: ViewBinding) : RecyclerView.ViewHolder(binding.root) {
        abstract fun bind(chatItem: ChatItem): Any
    }

    inner class OwnerVH(private val binding: ItemChatOwnerBinding, val context: Context) : VH(binding) {
        override fun bind(chatItem: ChatItem) = binding.apply {
            //image click
            chatItem.imgListMsg?.let { initImageView(binding, it) }

            chatItem.videoListMsg?.let {
                rvVideo.adapter = ChatVideoListAdapter().apply { submitList(it) }
            }
            //recording check
            viewRecorder.chkRecorder.setOnCheckedChangeListener { _, isChecked ->
                itemRecordingClickRelay.accept(Pair(isChecked, chatItem))
            }
            this.chatItem = chatItem
            executePendingBindings()
        }

        private fun initImageView(binding: ItemChatOwnerBinding, imgListMsg: List<ChatImg>) {
            (binding.rvImg.layoutManager as GridLayoutManager).spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    println("imgListMsg.size:${imgListMsg.size}")
                    return if (imgListMsg.size % 2 == 1) if (imgListMsg.size - 1 == position) 1 else 2 else 2
                }
            }

            binding.rvImg.adapter = ChatImgListAdapter().apply {
                itemImgClickRelay.subscribe { this@ChatListAdapter.itemImgClickRelay.accept(it) }
//                imgListMsg.forEach {
                submitList(imgListMsg)
                notifyItemInserted(imgListMsg.size - 1)
                binding.rvImg.postDelayed({ binding.rvImg.smoothScrollToPosition(imgListMsg.size - 1) }, 500)
//                }
            }

        }
    }

    inner class OtherVH(val binding: ItemChatOtherBinding, val context: Context) : VH(binding) {
        override fun bind(chatItem: ChatItem) = binding.apply {
            chatItem.imgListMsg?.let {
                rvImg.adapter = ChatImgListAdapter().apply {
                    submitList(it)
                }
            }
            ivRecorder.clicks().subscribe {
//                if (viewModel.mediaPlayerState.value!!) {
////                        chatBean.recorderBytes?.let { viewModel.startPlayer(it) }
//                    viewModel.mMediaPlayerState.postValue(false)
//                    binding.ivRecorder.setImageResource(R.drawable.ic_baseline_play_circle_24_white)
//                } else {
////                        viewModel.stopPlayer()
//                    viewModel.mMediaPlayerState.postValue(true)
//                    binding.ivRecorder.setImageResource(R.drawable.ic_baseline_stop_circle_24_white)
//
//                }
            }
//            viewModel.mediaPlayerState.observe(){
//
//            }
            this.chatItem = chatItem
            executePendingBindings()
        }

    }
}