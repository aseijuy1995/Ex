package tw.north27.coachingapp.util.http

import android.content.Context
import android.util.Base64
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import tw.north27.coachingapp.module.SignInModule


class BaseTokenAuthenticator(val context: Context) : Authenticator {

    private val signInModule = SignInModule(context)

    override fun authenticate(route: Route?, response: Response): Request {
        val request = response.request

        val signInDataStore = runBlocking { signInModule.getValue { it }.first() }
        val account = signInDataStore.account
        val accessToken = signInDataStore.accessToken

        //account + accessToken
        val token = "$account$accessToken".let {
            "Basic " + Base64.encodeToString(it.toByteArray(), Base64.NO_WRAP)
        }

        //save token
        return request.newBuilder()
            .addHeader("Authorization", token)
            .build()
    }

}