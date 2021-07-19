package tw.north27.coachingapp.adapter.ask

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.*
import androidx.viewbinding.ViewBinding
import com.bumptech.glide.Glide
import com.yujie.core_lib.pref.getId
import com.yujie.core_lib.pref.userPref
import com.yujie.core_lib.util.logD
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import tw.north27.coachingapp.databinding.ItemAskRoomOtherBinding
import tw.north27.coachingapp.databinding.ItemAskRoomSelfBinding
import tw.north27.coachingapp.model.AskImage
import tw.north27.coachingapp.model.AskRoomInfo
import tw.north27.coachingapp.model.AskType

class AskRoomListAdapter(
    private val cxt: Context,
    private val owner: LifecycleOwner
) : ListAdapter<AskRoomInfo, AskRoomListAdapter.VH>(

    object : DiffUtil.ItemCallback<AskRoomInfo>() {
        override fun areItemsTheSame(oldItem: AskRoomInfo, newItem: AskRoomInfo): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: AskRoomInfo, newItem: AskRoomInfo): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }
) {

//    val imageClickRelay = PublishRelay.create<Pair<View, ChatImage>>()
//
//    val videoClickRelay = PublishRelay.create<Pair<View, ChatVideo>>()
//
//    val itemRecordingClickRelay = PublishRelay.create<Pair<Boolean, ChatInfo>>()


    val clientId: String
        get() = runBlocking { cxt.userPref.getId().first() }

    /**
     * @param SELF >> 自己
     * @param OTHER >> 對方
     * */
    enum class AskSideType(val type: Int) {
        SELF(1),
        OTHER(2);

        override fun toString(): String = type.toString()
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position).senderId) {
            clientId -> AskSideType.SELF.type
            else -> AskSideType.OTHER.type
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            AskSideType.SELF.type -> {
                val binding = ItemAskRoomSelfBinding.inflate(inflater, parent, false)
                SelfVH(binding)
            }
            else -> {
                val binding = ItemAskRoomOtherBinding.inflate(inflater, parent, false)
                OtherVH(binding)
            }
        }
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val askRoomInfo = getItem(position)
        holder.bind(askRoomInfo)
    }

    abstract inner class VH(binding: ViewBinding) : RecyclerView.ViewHolder(binding.root) {
        abstract fun bind(askRoomInfo: AskRoomInfo): ViewBinding
    }

    inner class SelfVH(private val binding: ItemAskRoomSelfBinding) : VH(binding) {
        override fun bind(askRoomInfo: AskRoomInfo) = binding.apply {
            this.askRoomInfo = askRoomInfo

            when (askRoomInfo.askType) {
                AskType.IMAGE -> {
                    val adapter = AskRoomImageListAdapter()
                    rvImg.apply {
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
                    adapter.submitList(askRoomInfo.imgList)
//                    adapter.itemClickRelay.subscribeBy { imageClickRelay?.accept(it) }
                }
                AskType.AUDIO -> {
                }
                AskType.VIDEO -> {
                    val adapter = AskRoomVideoListAdapter(cxt = cxt, owner = owner)
                    rvVideo.adapter = adapter
                    logD("askRoomInfo.videoList = ${askRoomInfo.videoList.size}")
                    adapter.submitList(askRoomInfo.videoList)
//                    adapter.itemClickRelay.subscribeBy { videoClickRelay?.accept(it) }
                }
            }


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
        override fun bind(askRoomInfo: AskRoomInfo) = binding.apply {
            this.askRoomInfo = askRoomInfo

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

    override fun submitList(list: List<AskRoomInfo>?) {
        super.submitList(list?.let { ArrayList(it) })
    }
}

fun List<AskImage>.arrangementFormat(position: Int) = when {
    size % 2 == 0 -> 1
    position == size - 1 -> 2
    else -> 1
}