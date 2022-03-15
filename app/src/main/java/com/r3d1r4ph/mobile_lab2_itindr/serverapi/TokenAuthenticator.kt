package com.r3d1r4ph.mobile_lab2_itindr.serverapi

import com.r3d1r4ph.mobile_lab2_itindr.serverapi.auth.refresh.RefreshService
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route

class TokenAuthenticator : Authenticator {
    private val refreshController by lazy { RefreshService() }

    @Synchronized
    override fun authenticate(route: Route?, response: Response): Request? {

        if (response.request.url.toString().contains("/v1/auth/refresh")) {
            return null
        }

        var flag = false
        refreshController.refresh(onSuccess = {
            runBlocking {
                Network.authDBRepository.resetTokens(it.toEntity())
            }
        }, onFailure = {
            runBlocking {
                Network.authDBRepository.deleteAll()
            }
            flag = true
        })


        return when (flag) {
            false -> response.request.newBuilder()
                .header(Network.HEADER_TOKEN_ID, Network.transferAccessToken())
                .build()
            true -> null
        }
    }
}