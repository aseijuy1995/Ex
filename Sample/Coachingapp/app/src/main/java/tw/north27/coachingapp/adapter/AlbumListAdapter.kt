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
import tw.north27.coachingapp.databinding.ItemAskRoomMediaAlbumBinding

class AlbumListAdapter : ListAdapter<Media, AlbumListAdapter.VH>(

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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemAskRoomMediaAlbumBinding.inflate(inflater, parent, false)
        return VH(binding)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val media = getItem(position)
        holder.apply {
            bind(media)
            itemView.setOnClickListener {
                itemClickRelay.accept(it to media)
            }
        }
    }

    inner class VH(private val binding: ItemAskRoomMediaAlbumBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(media: Media): Any = binding.apply {
//            this.media = media
//            this.setting = this@AlbumListAdapter.setting
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

}