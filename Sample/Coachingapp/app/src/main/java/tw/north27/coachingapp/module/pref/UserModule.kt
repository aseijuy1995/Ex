package tw.north27.coachingapp.module.pref

import android.content.Context
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import timber.log.Timber
import tw.north27.coachingapp.ext.createDataStoreProto
import tw.north27.coachingapp.ext.getValue
import tw.north27.coachingapp.ext.setValue
import tw.north27.coachingapp.protobuf.PREF_USER_NAME
import tw.north27.coachingapp.protobuf.UserPreferences
import tw.north27.coachingapp.protobuf.UserPreferencesSerializer
import kotlin.coroutines.CoroutineContext

class UserModule(val context: Context) : CoroutineScope {

    val userDataStore = context.createDataStoreProto(PREF_USER_NAME, UserPreferencesSerializer)

    private val job = Job()

    override val coroutineContext: CoroutineContext
        get() = job + CoroutineExceptionHandler { _, throwable ->
            Timber.d(throwable.toString())
        }

    fun cancel() = job.cancel()

    fun setValue(
        uuid: Long? = null,
        account: String? = null,
        accessToken: String? = null,
        deviceId: String? = null,
        refreshToken: String? = null,
        fcmToken: String? = null,
        isFirst: Boolean? = null
    ) {
        userDataStore.setValue(this) {
            it.toBuilder().apply {
                uuid?.let { setUuid(it) }
                account?.let { setAccount(it) }
                accessToken?.let { setAccessToken(it) }
                refreshToken?.let { setRefreshToken(it) }
                deviceId?.let { setDeviceId(it) }
                fcmToken?.let { setFcmToken(it) }
                isFirst?.let { setIsFirst(it) }
            }.build()
        }
    }

    inline fun <S> getValue(crossinline transform: suspend (it: UserPreferences) -> S): Flow<S> {
        return userDataStore.getValue(transform)
    }

}