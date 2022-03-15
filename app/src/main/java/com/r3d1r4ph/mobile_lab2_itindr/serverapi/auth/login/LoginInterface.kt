package com.r3d1r4ph.mobile_lab2_itindr.serverapi.auth.login

import com.r3d1r4ph.mobile_lab2_itindr.serverapi.auth.AuthParams
import com.r3d1r4ph.mobile_lab2_itindr.serverapi.auth.TokenResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginInterface {

    @POST("v1/auth/login")
    suspend fun login(@Body authParams: AuthParams): Response<TokenResponse>
}