package tw.north27.coachingapp.media.mediaCodec

import tw.north27.coachingapp.media.CodecSetting

interface IMediaCodecModule {

    fun setCompress(setting: CodecSetting): IMediaCodecModule

    fun compress()
}