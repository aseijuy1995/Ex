package tw.north27.coachingapp.adapter.ask

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jakewharton.rxrelay3.PublishRelay
import tw.north27.coachingapp.databinding.ItemAskRoomVideoBinding
import tw.north27.coachingapp.model.AskVideo
import tw.north27.coachingapp.model.media.ExoPlayerConfig
import tw.north27.coachingapp.model.media.prepare

class AskRoomVideoListAdapter(
    private val cxt: Context,
    private val owner: LifecycleOwner
) : ListAdapter<AskVideo, AskRoomVideoListAdapter.VH>(
    object : DiffUtil.ItemCallback<AskVideo>() {
        override fun areItemsTheSame(old: AskVideo, aNew: AskVideo): Boolean {
            return old.id == aNew.id
        }

        override fun areContentsTheSame(old: AskVideo, aNew: AskVideo): Boolean {
            return old.hashCode() == aNew.hashCode()
        }
    }
) {

    val itemClickRelay = PublishRelay.create<Pair<View, AskVideo>>()

    inner class VH(val binding: ItemAskRoomVideoBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(askVideo: AskVideo) = binding.apply {
            this.askVideo = askVideo
            /**
             * FIXME 尚未撈取影片之協定
             * dash - https://bitmovin-a.akamaihd.net/content/playhouse-vr/mpds/105560.mpd
             * hls - https://bitdash-a.akamaihd.net/content/MI201109210084_1/m3u8s/f08e80da-bf1d-4e3d-8899-f0f6155f6efa.m3u8
             * */
            val player = ExoPlayerConfig.createProgressivePlayer(cxt, askVideo.url)
            //            val player = ExoPlayerConfig.createPlayer(cxt, chatVideo.url)
//            val player = ExoPlayerConfig.createPlayer(cxt, askVideo.url)
            pvView.prepare(owner, player)
            itemView.setOnClickListener { itemClickRelay.accept(it to askVideo) }
            executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemAskRoomVideoBinding.inflate(inflater, parent, false)
        return VH(binding)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(getItem(position))
    }

}