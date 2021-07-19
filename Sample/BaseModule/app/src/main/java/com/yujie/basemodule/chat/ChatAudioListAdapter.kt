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
import org.koin.core.component.KoinComponent
import tw.north27.coachingapp.databinding.ItemChatAudioBinding
import tw.north27.coachingapp.media.exoplayer.ExoPlayerConfig
import tw.north27.coachingapp.model.result.ChatAudio

class ChatAudioListAdapter(
    private val cxt: Context,
    private val owner: LifecycleOwner
) : ListAdapter<ChatAudio, ChatAudioListAdapter.VH>(
    object : DiffUtil.ItemCallback<ChatAudio>() {
        override fun areItemsTheSame(old: ChatAudio, aNew: ChatAudio): Boolean {
            return old.id == aNew.id
        }

        override fun areContentsTheSame(old: ChatAudio, aNew: ChatAudio): Boolean {
            return old.hashCode() == aNew.hashCode()
        }
    }
) {

    val itemClickRelay = PublishRelay.create<Pair<View, ChatAudio>>()

    inner class VH(val binding: ItemChatAudioBinding) : RecyclerView.ViewHolder(binding.root), KoinComponent {

        fun bind(chatAudio: ChatAudio) = binding.apply {
            this.chatAudio = chatAudio
            val player = ExoPlayerConfig.createPlayer(cxt, chatAudio.url)
            playerView.prepare(owner, player)
            itemView.setOnClickListener { itemClickRelay.accept(it to chatAudio) }
            executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemChatAudioBinding.inflate(inflater, parent, false)
        return VH(binding)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(getItem(position))
    }

}