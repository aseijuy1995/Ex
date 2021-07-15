package tw.north27.coachingapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jakewharton.rxrelay3.PublishRelay
import com.yujie.core_lib.model.Media
import com.yujie.core_lib.model.MediaSetting
import tw.north27.coachingapp.R
import tw.north27.coachingapp.databinding.*

class AudioListAdapter : ListAdapter<Media, AudioListAdapter.VH>(

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

    val itemSelectRelay = PublishRelay.create<Pair<View, Media>>()

    val toastRelay = PublishRelay.create<String>()

    var setting: MediaSetting? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AudioListAdapter.VH {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemAskRoomMediaAudioBinding.inflate(inflater, parent, false)
        return VH(binding)
    }

    override fun onBindViewHolder(holder: AudioListAdapter.VH, position: Int) {
        val media = getItem(position)
        holder.apply {
            bind(media)
            itemView.setOnClickListener {
                itemClickRelay.accept(it to media)
            }
        }
    }

    inner class VH(private val binding: ItemAskRoomMediaAudioBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(media: Media): Any = binding.apply {
//            this.media = media
//            this.setting = this@AudioListAdapter.setting
//            binding.chkSelect.setOnCheckedChangeListener { view, isChoice ->
//                val isChoice = isChoice
//                if (isChoice) {
//                    val count = currentList.count(Media::isChoice)
//
//                    if (setting.isMultipleChoice) {
//                        if (count > setting.multipleChoiceMaxCount) {
//                            view.isChecked = !isChoice
//                            toastRelay.accept(view.context.getString(R.string.select_limit))
//                            return@setOnCheckedChangeListener
//                        }
//                    } else {
//                        if (count > 1) {
//                            view.isChecked = !isChoice
//                            toastRelay.accept(view.context.getString(R.string.select_limit))
//                            return@setOnCheckedChangeListener
//                        }
//                    }
//                }
//                media.isChoice = isChoice
//                itemSelectRelay.accept(view to media)
//            }
//            executePendingBindings()
        }
    }


//    inner class AudioVH(private val binding: ItemChatRoomMediaAudioBinding, val context: Context) : VH(binding) {
//        override fun bind(media: Media, position: Int): Any = binding.apply {
//            this.media = media
//            this.setting = this@AudioListAdapter.setting
//            binding.ivImg.setImageResource(
//                if (position % 7 == 0) {
//                    R.drawable.ic_baseline_audiotrack_24_red
//                } else if (position % 7 == 1) {
//                    R.drawable.ic_baseline_audiotrack_24_orange
//                } else if (position % 7 == 2) {
//                    R.drawable.ic_baseline_audiotrack_24_yellow
//                } else if (position % 7 == 3) {
//                    R.drawable.ic_baseline_audiotrack_24_green
//                } else if (position % 7 == 4) {
//                    R.drawable.ic_baseline_audiotrack_24_blue
//                } else if (position % 7 == 5) {
//                    R.drawable.ic_baseline_audiotrack_24_blue2
//                } else {
//                    R.drawable.ic_baseline_audiotrack_24_purple
//                }
//            )
//            binding.chkSelect.setOnCheckedChangeListener { view, isChoice ->
//                val count = currentList.count { it.isChoice }
//                if (isChoice && count >= setting?.multipleChoiceMaxCount ?: 100) {
//                    toastRelay.accept(binding.chkSelect.context.getString(R.string.limit_selection))
//                    view.isChecked = !isChoice
//                } else {
//                    media.isChoice = isChoice
//                    itemSelectRelay.accept(Triple(view, isChoice, media))
//                }
//            }
//            executePendingBindings()
//        }
//    }

}