package edu.yujie.okhttpex

import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route


object TokenHeaderAuthenticator : Authenticator {
    private val authorizationName = "Authorization"

    override fun authenticate(route: Route?, response: Response): Request? {
        val tokenValue = ""//call api
        //save token
        val request = response.request
        return request.newBuilder()
            .addHeader(authorizationName, tokenValue)
            .build()
    }

}