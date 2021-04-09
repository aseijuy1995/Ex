package tw.north27.coachingapp.media.mediaCodec

import tw.north27.coachingapp.media.DecodeSetting
import tw.north27.coachingapp.media.EncodeSetting

interface IMediaCodecModule {

    fun configDecoder(setting: DecodeSetting): IMediaCodecModule

    suspend fun decode()

    fun configEncoder(setting: EncodeSetting): IMediaCodecModule

    suspend fun encode()
}