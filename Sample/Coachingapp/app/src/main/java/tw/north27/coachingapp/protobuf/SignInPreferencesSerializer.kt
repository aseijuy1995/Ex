package tw.north27.coachingapp.protobuf

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import androidx.datastore.preferences.protobuf.InvalidProtocolBufferException
import java.io.InputStream
import java.io.OutputStream

const val PREF_SIGN_IN_NAME = "sign_in_prefs.pb"

object SignInPreferencesSerializer : Serializer<SignInPreferences> {
    override val defaultValue: SignInPreferences
        get() = SignInPreferences.getDefaultInstance()

    override fun readFrom(input: InputStream): SignInPreferences {
        try {
            return SignInPreferences.parseFrom(input)
        } catch (e: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto.", e)
        }
    }

    override fun writeTo(t: SignInPreferences, output: OutputStream) {
        t.writeTo(output)
    }
}