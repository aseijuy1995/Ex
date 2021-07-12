package tw.north27.coachingapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.*
import androidx.viewbinding.ViewBinding
import com.yujie.utilmodule.pref.getAccount
import com.yujie.utilmodule.pref.userPref
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import tw.north27.coachingapp.R
import tw.north27.coachingapp.databinding.ItemAskRoomOtherBinding
import tw.north27.coachingapp.databinding.ItemAskRoomSelfBinding
import tw.north27.coachingapp.model.AskRoom
import kotlin.coroutines.CoroutineContext

class AskRoomListAdapter(
    private val cxt: Context
) : ListAdapter<AskRoom, AskRoomListAdapter.VH>(

    object : DiffUtil.ItemCallback<AskRoom>() {
        override fun areItemsTheSame(oldItem: AskRoom, newItem: AskRoom): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: AskRoom, newItem: AskRoom): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }
), CoroutineScope {

//    val imageClickRelay = PublishRelay.create<Pair<View, ChatImage>>()
//
//    val videoClickRelay = PublishRelay.create<Pair<View, ChatVideo>>()
//
//    val itemRecordingClickRelay = PublishRelay.create<Pair<Boolean, ChatInfo>>()

    override fun getItemViewType(position: Int): Int {
        val account = runBlocking { cxt.userPref.getAccount().first() }
        return when (getItem(position).otherClientInfo.account) {
            account -> Type.SELF.code
            else -> Type.OTHER.code
        }
    }

    /**
     * @param SELF >> 自己
     * @param OTHER >> 對方
     * */
    enum class Type(val code: Int) {
        SELF(1),
        OTHER(2)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            Type.SELF.code -> {
                val binding = DataBindingUtil.inflate<ItemAskRoomSelfBinding>(inflater, R.layout.item_ask_room_self, parent, false)
                SelfVH(binding)
            }
            else -> {
                val binding = DataBindingUtil.inflate<ItemAskRoomOtherBinding>(inflater, R.layout.item_ask_room_other, parent, false)
                OtherVH(binding)
            }
        }
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
//        val chat = getItem(position)
//        holder.bind(chat)
    }

    abstract inner class VH(binding: ViewBinding) : RecyclerView.ViewHolder(binding.root) {
        abstract fun bind(askRoom: AskRoom): Any
    }

    override val coroutineContext: CoroutineContext
        get() = Job()

    inner class SelfVH(private val binding: ItemAskRoomSelfBinding) : VH(binding) {
        override fun bind(askRoom: AskRoom) = binding.apply {
//            this.askInfo = askRoom
//            when (askRoom.askType) {
//                AskType.TEXT -> {
//                    tvText.text = askRoom.text
//                }
//                AskType.IMAGE -> {
//                }
//                AskType.AUDIO -> {
//                }
//                AskType.VIDEO -> {
//                }
//            }
//            tvRead.apply {
//                isVisible = (askRoom.unreadNum > 0)
//                text = if (askRoom.unreadNum > 0) context.getString(R.string.un_read) else context.getString(R.string.have_read)
//            }
//            tvTime.text = dateTimeToString(askRoom.sendTime)

//            if (chat.chatType == ChatType.IMAGE) {
//                chat.image?.let {
//                    val adapter = ChatImageListAdapter()
//                    val layoutManager = object : GridLayoutManager(cxt, 2, LinearLayoutManager.VERTICAL, false) {
//                        override fun isLayoutRTL(): Boolean = true
//                    }.apply {
//                        spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
//                            override fun getSpanSize(position: Int): Int = imageArrangementFormat(it, position)
//                        }
//                    }
//                    rvImg.apply {
//                        this.layoutManager = layoutManager
//                        this.adapter = adapter
//                        addOnScrollListener(object : RecyclerView.OnScrollListener() {
//                            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
//                                super.onScrollStateChanged(recyclerView, newState)
//                                when (newState) {
//                                    RecyclerView.SCROLL_STATE_IDLE -> Glide.with(this@apply).resumeRequests()
//                                    RecyclerView.SCROLL_STATE_DRAGGING -> Glide.with(this@apply).pauseRequests()
//                                }
//
//                            }
//                        })
//                    }
//                    adapter.submitList(it)
//                    adapter.itemClickRelay.subscribeBy { imageClickRelay?.accept(it) }
//                }
//            } else if (chat.chatType == ChatType.VIDEO) {
//                //video
//                chat.videos?.let {
//                    val adapter = ChatVideoListAdapter(cxt, owner).apply { submitList(it) }
//                    rvVideo.adapter = adapter
//                    adapter.itemClickRelay.subscribeBy { videoClickRelay?.accept(it) }
//                }
//            } else if (chat.chatType == ChatType.AUDIO) {
//                //audio
//                chat.audios?.let {
//                    val adapter = ChatAudioListAdapter(cxt, owner).apply { submitList(it) }
//                    rvAudio.adapter = adapter
//                }
//            }
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

    inner class OtherVH(val binding: ItemAskRoomOtherBinding) : VH(binding) {
        override fun bind(askRoom: AskRoom) = binding.apply {
            this.askInfo = askRoom
//            when (askRoom.askType) {
//                AskType.TEXT -> {
//                    tvText.text = askRoom.text
//                }
//                AskType.IMAGE -> {
//                }
//                AskType.AUDIO -> {
//                }
//                AskType.VIDEO -> {
//                }
//            }
//            tvRead.apply {
//                isVisible = (askRoom.unreadNum > 0)
//                text = if (askRoom.unreadNum > 0) context.getString(R.string.un_read) else context.getString(R.string.have_read)
//            }
//            tvTime.text = dateTimeToString(askRoom.sendTime)


//            if (chat.chatType == ChatType.IMAGE) {
//                chat.image?.let {
//                    val adapter = ChatImageListAdapter()
//                    val layoutManager = object : GridLayoutManager(cxt, 2, LinearLayoutManager.VERTICAL, false) {}.apply {
//                        spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
//                            override fun getSpanSize(position: Int): Int = imageArrangementFormat(it, position)
//                        }
//                    }
//                    rvImg.apply {
//                        this.layoutManager = layoutManager
//                        this.adapter = adapter
//                        addOnScrollListener(object : RecyclerView.OnScrollListener() {
//                            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
//                                super.onScrollStateChanged(recyclerView, newState)
//                                when (newState) {
//                                    RecyclerView.SCROLL_STATE_IDLE -> Glide.with(this@apply).resumeRequests()
//                                    RecyclerView.SCROLL_STATE_DRAGGING -> Glide.with(this@apply).pauseRequests()
//                                }
//
//                            }
//                        })
//                    }
//                    adapter.submitList(it)
//                    adapter.itemClickRelay.subscribeBy { imageClickRelay?.accept(it) }
//
//                }
//            } else if (chat.chatType == ChatType.VIDEO) {
//                //video
//                chat.videos?.let {
//                    val adapter = ChatVideoListAdapter(cxt, owner).apply { submitList(it) }
//                    rvVideo.adapter = adapter
//                    adapter.itemClickRelay.subscribeBy { videoClickRelay?.accept(it) }
//                }
//            } else if (chat.chatType == ChatType.AUDIO) {
//                //audio
//                chat.audios?.let {
//                    val adapter = ChatAudioListAdapter(cxt, owner).apply { submitList(it) }
//                    rvAudio.adapter = adapter
//                }
//            }

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

//    private fun imageArrangementFormat(it: List<ChatImage>, position: Int) = when {
//        it.size % 2 == 0 -> 1
//        position == it.size - 1 -> 2
//        else -> 1
//    }
}