package tw.north27.coachingapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.jakewharton.rxrelay3.PublishRelay
import tw.north27.coachingapp.R
import tw.north27.coachingapp.databinding.ItemChatRoomMediaAudioBinding
import tw.north27.coachingapp.databinding.ItemChatRoomMediaImagesBinding
import tw.north27.coachingapp.databinding.ItemChatRoomMediaVideoBinding
import tw.north27.coachingapp.model.Media
import tw.north27.coachingapp.model.MediaAlbumSetting
import tw.north27.coachingapp.model.SendMode

class AskRoomMediaListAdapter(
    private val sendMode: SendMode,
    private val albumSetting: MediaAlbumSetting
) : ListAdapter<Media, AskRoomMediaListAdapter.VH>(

    object : DiffUtil.ItemCallback<Media>() {
        override fun areItemsTheSame(oldItem: Media, newItem: Media): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }

        override fun areContentsTheSame(oldItem: Media, newItem: Media): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }
) {

    val itemClickRelay = PublishRelay.create<Pair<View, Media>>()

    val itemSelectRelay = PublishRelay.create<Triple<View, Boolean, Media>>()

    val toastRelay = PublishRelay.create<String>()

    enum class Mime(val value: Int) {
        AUDIO(0),
        IMAGE(1),
        VIDEO(2)
    }

    override fun getItemViewType(position: Int): Int {
        return when (sendMode) {
            SendMode.ALBUM -> Mime.AUDIO.value
            SendMode.AUDIO -> Mime.IMAGE.value
            SendMode.VIDEO -> Mime.VIDEO.value
            else -> -1
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            Mime.AUDIO.value -> {
                val binding = DataBindingUtil.inflate<ItemChatRoomMediaAudioBinding>(inflater, R.layout.item_ask_room_media_audio, parent, false)
                AudioVH(binding, parent.context)
            }
            Mime.IMAGE.value -> {
                val binding = DataBindingUtil.inflate<ItemChatRoomMediaImagesBinding>(inflater, R.layout.item_ask_room_media_album, parent, false)
                ImagesVH(binding, parent.context)
            }
            else -> {
                val binding = DataBindingUtil.inflate<ItemChatRoomMediaVideoBinding>(inflater, R.layout.item_ask_room_media_video, parent, false)
                VideoVH(binding, parent.context)
            }
        }

    }

    abstract inner class VH(binding: ViewBinding) : RecyclerView.ViewHolder(binding.root) {
        abstract fun bind(media: Media, position: Int): Any
    }

    inner class AudioVH(private val binding: ItemChatRoomMediaAudioBinding, val context: Context) : VH(binding) {
        override fun bind(media: Media, position: Int): Any = binding.apply {
            this.media = media
            this.setting = this@AskRoomMediaListAdapter.albumSetting
            binding.ivImg.setImageResource(
                if (position % 7 == 0) {
                    R.drawable.ic_baseline_audiotrack_24_red
                } else if (position % 7 == 1) {
                    R.drawable.ic_baseline_audiotrack_24_orange
                } else if (position % 7 == 2) {
                    R.drawable.ic_baseline_audiotrack_24_yellow
                } else if (position % 7 == 3) {
                    R.drawable.ic_baseline_audiotrack_24_green
                } else if (position % 7 == 4) {
                    R.drawable.ic_baseline_audiotrack_24_blue
                } else if (position % 7 == 5) {
                    R.drawable.ic_baseline_audiotrack_24_blue2
                } else {
                    R.drawable.ic_baseline_audiotrack_24_purple
                }
            )
            binding.chkSelect.setOnCheckedChangeListener { view, isChoice ->
                val count = currentList.count { it.isChoice }
                if (isChoice && count >= setting?.maxCount ?: 100) {
                    toastRelay.accept(binding.chkSelect.context.getString(R.string.limit_selection))
                    view.isChecked = !isChoice
                } else {
                    media.isChoice = isChoice
                    itemSelectRelay.accept(Triple(view, isChoice, media))
                }
            }
            executePendingBindings()
        }
    }

    inner class ImagesVH(private val binding: ItemChatRoomMediaImagesBinding, val context: Context) : VH(binding) {
        override fun bind(media: Media, position: Int): Any = binding.apply {
            this.media = media
            this.setting = this@AskRoomMediaListAdapter.albumSetting
            binding.chkSelect.setOnCheckedChangeListener { view, isChoice ->
                val count = currentList.count { it.isChoice }
                if (isChoice && count >= setting?.maxCount ?: 100) {
                    toastRelay.accept(binding.chkSelect.context.getString(R.string.limit_selection))
                    view.isChecked = !isChoice
                } else {
                    media.isChoice = isChoice
                    itemSelectRelay.accept(Triple(view, isChoice, media))
                }
            }
            executePendingBindings()
        }
    }

    inner class VideoVH(private val binding: ItemChatRoomMediaVideoBinding, val context: Context) : VH(binding) {
        override fun bind(media: Media, position: Int): Any = binding.apply {
            this.media = media
            this.setting = this@AskRoomMediaListAdapter.albumSetting
            binding.chkSelect.setOnCheckedChangeListener { view, isChoice ->
                val count = currentList.count { it.isChoice }
                if (isChoice && count >= setting?.maxCount ?: 100) {
                    toastRelay.accept(binding.chkSelect.context.getString(R.string.limit_selection))
                    view.isChecked = !isChoice
                } else {
                    media.isChoice = isChoice
                    itemSelectRelay.accept(Triple(view, isChoice, media))
                }
            }
            executePendingBindings()
        }
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val media = getItem(position)
        holder.bind(media, position)
        holder.itemView.setOnClickListener {
            itemClickRelay.accept(it to media)
        }
    }
}