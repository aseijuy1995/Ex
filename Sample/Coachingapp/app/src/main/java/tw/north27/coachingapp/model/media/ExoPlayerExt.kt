package tw.north27.coachingapp.model.media

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.google.android.exoplayer2.BasePlayer
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.ui.StyledPlayerView

fun StyledPlayerView.prepare(owner: LifecycleOwner, player: BasePlayer) {
    this.player = player
    player.addObs(owner)
}

fun PlayerView.prepare(owner: LifecycleOwner, player: BasePlayer) {
    this.player = player
    player.addObs(owner)
}

fun BasePlayer.addObs(owner: LifecycleOwner, isAuto: Boolean = false) {
    ExoPlayerLifeObs(owner, this, isAuto)
}

class ExoPlayerLifeObs(
    private val owner: LifecycleOwner,
    private val exoPlayer: BasePlayer,
    private val isAuto: Boolean,
) : DefaultLifecycleObserver {
    init {
        owner.lifecycle.addObserver(this)
    }

    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        exoPlayer.prepare()
    }

    override fun onResume(owner: LifecycleOwner) {
        super.onResume(owner)
        if (isAuto) exoPlayer.play()
    }

    override fun onPause(owner: LifecycleOwner) {
        super.onPause(owner)
        exoPlayer.pause()
    }

    override fun onStop(owner: LifecycleOwner) {
        super.onStop(owner)
        exoPlayer.stop()
    }

    override fun onDestroy(owner: LifecycleOwner) {
        super.onDestroy(owner)
        exoPlayer.release()
    }
}