package tw.north27.coachingapp.media.mediaCodec

interface IMediaCodecModule {

    fun setCompress(setting: CodecSetting): IMediaCodecModule

    fun compress()
}