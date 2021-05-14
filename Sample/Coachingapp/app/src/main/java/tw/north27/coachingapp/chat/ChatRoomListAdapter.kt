package tw.north27.coachingapp.chat

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.*
import androidx.viewbinding.ViewBinding
import com.bumptech.glide.Glide
import com.jakewharton.rxrelay3.PublishRelay
import com.yujie.prefmodule.dataStore.dataStoreUserPref
import com.yujie.prefmodule.dataStore.getAccount
import io.reactivex.rxjava3.kotlin.subscribeBy
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import timber.log.Timber
import tw.north27.coachingapp.R
import tw.north27.coachingapp.databinding.ItemChatRoomOtherBinding
import tw.north27.coachingapp.databinding.ItemChatRoomOwnerBinding
import tw.north27.coachingapp.model.result.ChatImage
import tw.north27.coachingapp.model.result.ChatInfo
import tw.north27.coachingapp.model.result.ChatType
import tw.north27.coachingapp.model.result.ChatVideo

class ChatRoomListAdapter(
    private val cxt: Context,
    private val owner: LifecycleOwner
) : ListAdapter<ChatInfo, ChatRoomListAdapter.VH>(
    object : DiffUtil.ItemCallback<ChatInfo>() {
        override fun areItemsTheSame(oldItem: ChatInfo, newItem: ChatInfo): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ChatInfo, newItem: ChatInfo): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }
) {

    val imageClickRelay = PublishRelay.create<Pair<View, ChatImage>>()

    val videoClickRelay = PublishRelay.create<Pair<View, ChatVideo>>()

    val itemRecordingClickRelay = PublishRelay.create<Pair<Boolean, ChatInfo>>()

    enum class Sender(val value: Int) {
        OWNER(1), OTHER(2)
    }

    override fun getItemViewType(position: Int): Int {
        val account = runBlocking { cxt.dataStoreUserPref.getAccount().first() }
        val chatAccount = getItem(position).sender.account
        return if (account == chatAccount)
            Sender.OWNER.value
        else
            Sender.OTHER.value
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            Sender.OWNER.value -> {
                val binding = DataBindingUtil.inflate<ItemChatRoomOwnerBinding>(inflater, R.layout.item_chat_room_owner, parent, false)
                OwnerVH(binding, parent.context)
            }
            else -> {
                val binding = DataBindingUtil.inflate<ItemChatRoomOtherBinding>(inflater, R.layout.item_chat_room_other, parent, false)
                OtherVH(binding, parent.context)
            }
        }
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val chat = getItem(position)
        holder.bind(chat)
    }

    abstract inner class VH(binding: ViewBinding) : RecyclerView.ViewHolder(binding.root) {
        abstract fun bind(chat: ChatInfo): Any
    }

    inner class OwnerVH(private val binding: ItemChatRoomOwnerBinding, val context: Context) : VH(binding) {
        override fun bind(chat: ChatInfo) = binding.apply {
            this.chat = chat
            Timber.d("chat.chatType - ${chat.chatType}")
            //image
            if (chat.chatType == ChatType.IMAGE) {
                chat.image?.let {
                    val adapter = ChatImageListAdapter()
                    val layoutManager = object : GridLayoutManager(cxt, 2, LinearLayoutManager.VERTICAL, false) {
                        override fun isLayoutRTL(): Boolean = true
                    }.apply {
                        spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                            override fun getSpanSize(position: Int): Int = imageArrangementFormat(it, position)
                        }
                    }
                    rvImg.apply {
                        this.layoutManager = layoutManager
                        this.adapter = adapter
                        addOnScrollListener(object : RecyclerView.OnScrollListener() {
                            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                                super.onScrollStateChanged(recyclerView, newState)
                                when (newState) {
                                    RecyclerView.SCROLL_STATE_IDLE -> Glide.with(this@apply).resumeRequests()
                                    RecyclerView.SCROLL_STATE_DRAGGING -> Glide.with(this@apply).pauseRequests()
                                }

                            }
                        })
                    }
                    adapter.submitList(it)
                    adapter.itemClickRelay.subscribeBy { imageClickRelay?.accept(it) }
                }
            } else if (chat.chatType == ChatType.VIDEO) {
                //video
                chat.videos?.let {
                    val adapter = ChatVideoListAdapter(cxt, owner).apply { submitList(it) }
                    rvVideo.adapter = adapter
                    adapter.itemClickRelay.subscribeBy { videoClickRelay?.accept(it) }
                }
            } else if (chat.chatType == ChatType.AUDIO) {
                //audio
                chat.audios?.let {
                    val adapter = ChatAudioListAdapter(cxt, owner).apply { submitList(it) }
                    rvAudio.adapter = adapter
                }
            }
//            recording check
//            viewRecorder.chkRecorder.setOnCheckedChangeListener { _, isChecked ->
//                itemRecordingClickRelay.accept(Pair(isChecked, chatInfo))
//            }
            executePendingBindings()
        }

//        private fun initImageView(binding: ItemChatRoomOwnerBinding, imgListMsg: List<ChatImg>) {
//            (binding.rvImg.layoutManager as GridLayoutManager).spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
//                override fun getSpanSize(position: Int): Int {
//                    println("imgListMsg.size:${imgListMsg.size}")
//                    return if (imgListMsg.size % 2 == 1) if (imgListMsg.size - 1 == position) 1 else 2 else 2
//                }
//            }
//
//            binding.rvImg.adapter = ChatImgListAdapter().apply {
//                itemImgClickRelay.subscribe { this@ChatListAdapter.itemImgClickRelay.accept(it) }
////                imgListMsg.forEach {
//                submitList(imgListMsg)
//                notifyItemInserted(imgListMsg.size - 1)
//                binding.rvImg.postDelayed({ binding.rvImg.smoothScrollToPosition(imgListMsg.size - 1) }, 500)
////                }
//            }
//
//        }
    }

    inner class OtherVH(val binding: ItemChatRoomOtherBinding, val context: Context) : VH(binding) {
        override fun bind(chat: ChatInfo) = binding.apply {
            this.chat = chat
            //image
            if (chat.chatType == ChatType.IMAGE) {
                chat.image?.let {
                    val adapter = ChatImageListAdapter()
                    val layoutManager = object : GridLayoutManager(cxt, 2, LinearLayoutManager.VERTICAL, false) {}.apply {
                        spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                            override fun getSpanSize(position: Int): Int = imageArrangementFormat(it, position)
                        }
                    }
                    rvImg.apply {
                        this.layoutManager = layoutManager
                        this.adapter = adapter
                        addOnScrollListener(object : RecyclerView.OnScrollListener() {
                            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                                super.onScrollStateChanged(recyclerView, newState)
                                when (newState) {
                                    RecyclerView.SCROLL_STATE_IDLE -> Glide.with(this@apply).resumeRequests()
                                    RecyclerView.SCROLL_STATE_DRAGGING -> Glide.with(this@apply).pauseRequests()
                                }

                            }
                        })
                    }
                    adapter.submitList(it)
                    adapter.itemClickRelay.subscribeBy { imageClickRelay?.accept(it) }

                }
            } else if (chat.chatType == ChatType.VIDEO) {
                //video
                chat.videos?.let {
                    val adapter = ChatVideoListAdapter(cxt, owner).apply { submitList(it) }
                    rvVideo.adapter = adapter
                    adapter.itemClickRelay.subscribeBy { videoClickRelay?.accept(it) }
                }
            } else if (chat.chatType == ChatType.AUDIO) {
                //audio
                chat.audios?.let {
                    val adapter = ChatAudioListAdapter(cxt, owner).apply { submitList(it) }
                    rvAudio.adapter = adapter
                }
            }

//            ivRecorder.clicks().subscribe {
////                if (viewModel.mediaPlayerState.value!!) {
//////                        chatBean.recorderBytes?.let { viewModel.startPlayer(it) }
////                    viewModel.mMediaPlayerState.postValue(false)
////                    binding.ivRecorder.setImageResource(R.drawable.ic_baseline_play_circle_24_white)
////                } else {
//////                        viewModel.stopPlayer()
////                    viewModel.mMediaPlayerState.postValue(true)
////                    binding.ivRecorder.setImageResource(R.drawable.ic_baseline_stop_circle_24_white)
////
////                }
//            }
//            viewModel.mediaPlayerState.observe(){
//
//            }
            executePendingBindings()
        }

    }

    private fun imageArrangementFormat(it: List<ChatImage>, position: Int) = when {
        it.size % 2 == 0 -> 1
        position == it.size - 1 -> 2
        else -> 1
    }
}