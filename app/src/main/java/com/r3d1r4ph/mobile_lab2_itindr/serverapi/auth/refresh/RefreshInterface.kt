package com.r3d1r4ph.mobile_lab2_itindr.serverapi.auth.refresh

import com.r3d1r4ph.mobile_lab2_itindr.serverapi.Network
import com.r3d1r4ph.mobile_lab2_itindr.serverapi.auth.TokenResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface RefreshInterface {

    @POST("v1/auth/refresh")
    fun refresh(
        @Body refreshParams: RefreshParams
    ): Call<TokenResponse>
}