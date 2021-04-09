package tw.north27.coachingapp.media.mediaCodec

import tw.north27.coachingapp.media.DecodeSetting
import tw.north27.coachingapp.media.EncodeSetting
import java.nio.ByteBuffer

interface IMediaCodecModule {

    fun configDecoder(setting: DecodeSetting): IMediaCodecModule

    suspend fun decode(): IMediaCodecModule

    fun configEncoder(setting: EncodeSetting): IMediaCodecModule

    fun startMuxer():IMediaCodecModule

    suspend fun encodeYuvToAvc(yuvByteBuffer: ByteBuffer): IMediaCodecModule
}