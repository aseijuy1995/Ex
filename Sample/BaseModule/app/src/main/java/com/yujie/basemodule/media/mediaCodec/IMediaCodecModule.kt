package tw.north27.coachingapp.media.mediaCodec

interface IMediaCodecModule {

    interface Compress {
        fun setCompress(setting: CodecSetting): IMediaCodecModule
    }

    suspend fun compress()
}