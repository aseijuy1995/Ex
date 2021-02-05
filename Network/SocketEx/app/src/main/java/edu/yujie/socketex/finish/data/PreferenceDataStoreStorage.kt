package edu.yujie.socketex.finish.data

import android.content.Context
import androidx.datastore.core.CorruptionException
import androidx.datastore.core.DataStore
import androidx.datastore.core.Serializer
import androidx.datastore.createDataStore
import androidx.datastore.preferences.protobuf.InvalidProtocolBufferException
import edu.yujie.socketex.SignInStorage
import java.io.InputStream
import java.io.OutputStream

object SignInStorageSerializer : Serializer<SignInStorage> {

    override val defaultValue: SignInStorage
        get() = SignInStorage.getDefaultInstance()

    override fun readFrom(input: InputStream): SignInStorage =
        try {
            SignInStorage.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto.", exception)
        }

    override fun writeTo(t: SignInStorage, output: OutputStream) = t.writeTo(output)

}

interface IPreferenceDataStoreStorage {
    val signInDataStore: DataStore<SignInStorage>
}

class PreferenceDataStoreStorage(private val context: Context) : IPreferenceDataStoreStorage {

    private val fileName = "ProtoDataStoreStorage.proto"

    private val signInStorageSerializer = SignInStorageSerializer

    override val signInDataStore: DataStore<SignInStorage>
        get() = context.createDataStore(fileName, signInStorageSerializer)
}