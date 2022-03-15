package com.r3d1r4ph.mobile_lab2_itindr.serverapi.auth.register

import com.r3d1r4ph.mobile_lab2_itindr.serverapi.auth.AuthParams
import com.r3d1r4ph.mobile_lab2_itindr.serverapi.auth.TokenResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface RegisterInterface {

    @POST("v1/auth/register")
    suspend fun register(@Body authParams: AuthParams): Response<TokenResponse>
}