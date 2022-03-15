package com.r3d1r4ph.mobile_lab2_itindr.serverapi.profile

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH

interface ProfileInterface {

    @GET("v1/profile")
    suspend fun getProfile(): Response<ProfileResponse>

    @PATCH("v1/profile")
    suspend fun patchProfile(
        @Body profileParams: ProfileParams
    ): Response<ProfileResponse>
}