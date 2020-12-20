package edu.yujie.okhttpex.proxy

import okhttp3.*


object ProxyAuthenticator : Authenticator {
    private val proxyAuthorization = "Proxy-Authorization"

    override fun authenticate(route: Route?, response: Response): Request? {
        val credentials = Credentials.basic(username = "name", password = "password")
        return response.request.newBuilder()
            .header(proxyAuthorization, credentials)
            .build()
    }
}