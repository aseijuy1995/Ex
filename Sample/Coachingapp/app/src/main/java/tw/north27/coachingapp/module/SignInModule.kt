package tw.north27.coachingapp.module

import android.content.Context
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import timber.log.Timber
import tw.north27.coachingapp.ext.createDataStoreProto
import tw.north27.coachingapp.ext.getValue
import tw.north27.coachingapp.ext.setValue
import tw.north27.coachingapp.protobuf.PREF_SIGN_IN_NAME
import tw.north27.coachingapp.protobuf.SignInPreferences
import tw.north27.coachingapp.protobuf.SignInPreferencesSerializer
import kotlin.coroutines.CoroutineContext

class SignInModule(val context: Context) : CoroutineScope {

    val signInDataStore = context.createDataStoreProto(PREF_SIGN_IN_NAME, SignInPreferencesSerializer)

    private val job = Job()

    override val coroutineContext: CoroutineContext
        get() = job + CoroutineExceptionHandler { _, throwable ->
            Timber.d(throwable.toString())
        }

    fun cancel() = job.cancel()

    fun setValue(guid: Long, account: String, accessToken: String, refreshToken: String, expiredTime: String, isFirstLogin: Boolean) {
        signInDataStore.setValue(this) {
            it.toBuilder().apply {
                setGuid(guid)
                setAccount(account)
                setAccessToken(accessToken)
                setRefreshToken(refreshToken)
                setExpiredTime(expiredTime)
                setIsFirstLogin(isFirstLogin)
            }.build()
        }
    }

    inline fun <S> getValue(crossinline transform: suspend (it: SignInPreferences) -> S): Flow<S> {
        return signInDataStore.getValue(transform)
    }

}