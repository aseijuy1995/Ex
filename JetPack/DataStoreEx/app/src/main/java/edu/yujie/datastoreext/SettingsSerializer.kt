package edu.yujie.datastoreext

import androidx.datastore.CorruptionException
import androidx.datastore.Serializer
import com.example.application.Settings
import com.google.protobuf.InvalidProtocolBufferException
import java.io.InputStream
import java.io.OutputStream

/**
 * @author YuJie on 2020/10/17
 * @describe 說明
 * @param 參數
 */
object SettingsSerializer : Serializer<Settings> {
    override fun readFrom(input: InputStream): Settings {
        try {
            return Settings.parseFrom(input)
        } catch (e: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto.", e)
        }
    }

    override fun writeTo(t: Settings, output: OutputStream) = t.writeTo(output)

}