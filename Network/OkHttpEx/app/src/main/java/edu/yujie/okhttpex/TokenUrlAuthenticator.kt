package edu.yujie.okhttpex

import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route


object TokenUrlAuthenticator : Authenticator {
    private val tokenName = "Token"

    override fun authenticate(route: Route?, response: Response): Request? {
        //Add in url
        val tokenValue = ""//call api
        //save token
        val request = response.request
        val url = request.url.newBuilder()
            .setQueryParameter(tokenName, tokenValue)
            .build()
        return response.request.newBuilder().url(url).build()

    }

}