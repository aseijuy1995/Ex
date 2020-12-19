package edu.yujie.okhttpex.interceptor

import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route


object TokenHeaderAuthenticator : Authenticator {
    private val authorizationName = "Authorization"

    override fun authenticate(route: Route?, response: Response): Request {
        /**
         * credentials & tokenValue其一即可
         * */
//        val credentials = Credentials.basic(username = "name", password = "password")
        val tokenValue = "Basic bmFtZTpwYXNzd29yZA=="
        //save token
        val request = response.request
        return request.newBuilder()
//            .addHeader(authorizationName, credentials)
            .addHeader(authorizationName, tokenValue)
            .build()
    }

}