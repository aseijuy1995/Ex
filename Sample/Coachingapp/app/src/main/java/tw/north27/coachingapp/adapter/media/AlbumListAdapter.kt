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
import tw.north27.coachingapp.databinding.ItemAskRoomMediaAlbumBinding
import tw.north27.coachingapp.model.media.MediaConfig
import tw.north27.coachingapp.model.media.MediaData

class AlbumListAdapter : ListAdapter<MediaData, AlbumListAdapter.VH>(
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
        val binding = ItemAskRoomMediaAlbumBinding.inflate(inflater, parent, false)
        return VH(binding)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val mediaData = getItem(position)
        holder.bind(mediaData)
    }

    inner class VH(private val binding: ItemAskRoomMediaAlbumBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(mediaData: MediaData): Any = binding.apply {
            this.mediaData = mediaData
            this.config = this@AlbumListAdapter.config
            ivImg.bindImg(url = mediaData.path)
            tvWh.text = String.format("%d x %d", mediaData.width, mediaData.height)
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