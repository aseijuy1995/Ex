package tw.north27.coachingapp.chat

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jakewharton.rxrelay3.PublishRelay
import org.koin.core.KoinComponent
import tw.north27.coachingapp.databinding.ItemChatVideoBinding
import tw.north27.coachingapp.media.exoplayer.ExoPlayerConfig
import tw.north27.coachingapp.media.exoplayer.prepare
import tw.north27.coachingapp.model.result.ChatVideo

class ChatVideoListAdapter(
    private val cxt: Context,
    private val owner: LifecycleOwner
) : ListAdapter<ChatVideo, ChatVideoListAdapter.VH>(
    object : DiffUtil.ItemCallback<ChatVideo>() {
        override fun areItemsTheSame(old: ChatVideo, aNew: ChatVideo): Boolean {
            return old.id == aNew.id
        }

        override fun areContentsTheSame(old: ChatVideo, aNew: ChatVideo): Boolean {
            return old.hashCode() == aNew.hashCode()
        }
    }
) {

    val itemClickRelay = PublishRelay.create<Pair<View, ChatVideo>>()

    inner class VH(val binding: ItemChatVideoBinding) : RecyclerView.ViewHolder(binding.root), KoinComponent {

        fun bind(chatVideo: ChatVideo) = binding.apply {
            this.chatVideo = chatVideo
            /**
             * FIXME 尚未撈取影片之協定
             * dash - https://bitmovin-a.akamaihd.net/content/playhouse-vr/mpds/105560.mpd
             * hls - https://bitdash-a.akamaihd.net/content/MI201109210084_1/m3u8s/f08e80da-bf1d-4e3d-8899-f0f6155f6efa.m3u8
             * */
//            val player = ExoPlayerConfig.createDashPlayer(cxt, "https://bitmovin-a.akamaihd.net/content/playhouse-vr/mpds/105560.mpd")
//            val player = ExoPlayerConfig.createPlayer(cxt, chatVideo.url)
//            val player = ExoPlayerConfig.createHlsPlayer(cxt, "https://bitdash-a.akamaihd.net/content/MI201109210084_1/m3u8s/f08e80da-bf1d-4e3d-8899-f0f6155f6efa.m3u8")
//            val player = ExoPlayerConfig.createSmoothStreamingPlayer(cxt, "https://bitdash-a.akamaihd.net/content/MI201109210084_1/m3u8s/f08e80da-bf1d-4e3d-8899-f0f6155f6efa.m3u8")
//            val player = ExoPlayerConfig.createProgressivePlayer(cxt, "https://bitmovin.com/player-content/playhouse-vr/progressive.mp4")
            val player = ExoPlayerConfig.createPlayer(cxt, chatVideo.url)
            playerView.prepare(owner, player)
            itemView.setOnClickListener { itemClickRelay.accept(it to chatVideo) }
            executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemChatVideoBinding.inflate(inflater, parent, false)
        return VH(binding)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(getItem(position))
    }

}