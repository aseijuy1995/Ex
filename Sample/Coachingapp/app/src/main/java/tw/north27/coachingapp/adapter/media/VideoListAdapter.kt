package tw.north27.coachingapp.adapter.media

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jakewharton.rxrelay3.PublishRelay
import com.yujie.core_lib.adapter.bindImg
import tw.north27.coachingapp.R
import tw.north27.coachingapp.databinding.ItemAskRoomMediaVideoBinding
import tw.north27.coachingapp.model.media.MediaConfig
import tw.north27.coachingapp.model.media.MediaData

class VideoListAdapter : ListAdapter<MediaData, VideoListAdapter.VH>(
    object : DiffUtil.ItemCallback<MediaData>() {
        override fun areItemsTheSame(oldItem: MediaData, newItem: MediaData): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: MediaData, newItem: MediaData): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }
) {

    val itemClickRelay = PublishRelay.create<Pair<View, MediaData>>()

    val itemSelectRelay = PublishRelay.create<Pair<View, MediaData>>()

    val toastRelay = PublishRelay.create<String>()

    var config: MediaConfig? = null

    val selectMediaDataList: List<MediaData>
        get() = currentList.filter(MediaData::isSelect)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemAskRoomMediaVideoBinding.inflate(inflater, parent, false)
        return VH(binding)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val mediaData = getItem(position)
        holder.bind(mediaData)
    }

    inner class VH(private val binding: ItemAskRoomMediaVideoBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(mediaData: MediaData): Any = binding.apply {
            this.mediaData = mediaData
            this.config = this@VideoListAdapter.config
            ivImg.bindImg(url = mediaData.path)
            val time = (mediaData.duration / 1000)
            tvDuration.text = String.format("%02d:%02d", (time % 3600) / 60, (time % 60))
            chkSelect.setOnCheckedChangeListener { view, isChoice ->
                if (isChoice) {
                    val count = currentList.count(MediaData::isSelect)
                    when (config?.isMultipleChoice) {
                        true -> {
                            if (count >= config?.multipleChoiceMaxCount ?: 1) {
                                view.isChecked = !isChoice
                                toastRelay.accept(view.context.getString(R.string.select_limit))
                                return@setOnCheckedChangeListener
                            }
                        }
                        false -> {
                            if (count >= 1) {
                                view.isChecked = !isChoice
                                toastRelay.accept(view.context.getString(R.string.select_limit))
                                return@setOnCheckedChangeListener
                            }
                        }
                    }
                }
                mediaData.isSelect = isChoice
                itemSelectRelay.accept(view to mediaData)
            }
            root.setOnClickListener { itemClickRelay.accept(it to mediaData) }
            executePendingBindings()
        }
    }

}