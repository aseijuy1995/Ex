package edu.yujie.socketex.listener

import android.content.Context
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.jakewharton.rxrelay3.PublishRelay

interface IExoPlayerRepo {

    val playerRelay: PublishRelay<Player>

    fun buildAudioPlayer(setting: ExoPlayerSetting): PublishRelay<Player>

    fun startAudio()

    fun stopAudio()

}

class ExoPlayerRepo : IExoPlayerRepo {

    override val playerRelay = PublishRelay.create<Player>()

    override fun buildAudioPlayer(setting: ExoPlayerSetting): PublishRelay<Player> {
        val player = SimpleExoPlayer.Builder(setting.context).build().apply {
            setMediaItem(setting.mediaItem)
            repeatMode = Player.REPEAT_MODE_ALL
            prepare()
            play()
        }
        playerRelay.accept(player)
        return playerRelay
    }

    override fun startAudio() {
        TODO("Not yet implemented")
    }

    override fun stopAudio() {
        TODO("Not yet implemented")
    }


}

data class ExoPlayerSetting(
    val context: Context,
    val mediaItem: MediaItem
)