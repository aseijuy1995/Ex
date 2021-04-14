package tw.north27.coachingapp.media.exoplayer

import android.content.Context
import android.net.Uri
import com.google.android.exoplayer2.BasePlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.source.dash.DashMediaSource
import com.google.android.exoplayer2.source.hls.HlsMediaSource
import com.google.android.exoplayer2.source.smoothstreaming.SsMediaSource
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory

data class ExoPlayerConfig(
    val cxt: Context,
    val mediaItem: MediaItem
) {
    companion object {
        /**
         * Default
         * */
        fun createPlayer(cxt: Context, path: String?): BasePlayer {
            val exoPlayer = SimpleExoPlayer.Builder(cxt).build()
            exoPlayer.setMediaItem(MediaItem.fromUri(Uri.parse(path)))
            return exoPlayer
        }

        /**
         * DASH
         * */
        fun createDashPlayer(cxt: Context, dashPath: String?): BasePlayer {
            val dataSourceFactory: DataSource.Factory = DefaultHttpDataSourceFactory()
            val mediaSource = DashMediaSource.Factory(dataSourceFactory).createMediaSource(MediaItem.fromUri(Uri.parse(dashPath)))
            val exoPlayer = SimpleExoPlayer.Builder(cxt).build()
            exoPlayer.setMediaSource(mediaSource)
            return exoPlayer
        }

        /**
         * HLS
         * */
        fun createHlsPlayer(cxt: Context, hlsPath: String?): BasePlayer {
            val dataSourceFactory: DataSource.Factory = DefaultHttpDataSourceFactory()
            val mediaSource = HlsMediaSource.Factory(dataSourceFactory).createMediaSource(MediaItem.fromUri(Uri.parse(hlsPath)))
            val exoPlayer = SimpleExoPlayer.Builder(cxt).build()
            exoPlayer.setMediaSource(mediaSource)
            return exoPlayer
        }

        /**
         * SmoothStreaming
         * */
        fun createSmoothStreamingPlayer(cxt: Context, smoothStreamPath: String?): BasePlayer {
            val dataSourceFactory: DataSource.Factory = DefaultHttpDataSourceFactory()
            val mediaSource = SsMediaSource.Factory(dataSourceFactory).createMediaSource(MediaItem.fromUri(Uri.parse(smoothStreamPath)))
            val exoPlayer = SimpleExoPlayer.Builder(cxt).build()
            exoPlayer.setMediaSource(mediaSource)
            return exoPlayer
        }

        /**
         * Progressive
         * */
        fun createProgressivePlayer(cxt: Context, progressivePath: String?): BasePlayer {
            val dataSourceFactory: DataSource.Factory = DefaultHttpDataSourceFactory()
            val mediaSource = ProgressiveMediaSource.Factory(dataSourceFactory).createMediaSource(MediaItem.fromUri(Uri.parse(progressivePath)))
            val exoPlayer = SimpleExoPlayer.Builder(cxt).build()
            exoPlayer.setMediaSource(mediaSource)
            return exoPlayer
        }
    }
}