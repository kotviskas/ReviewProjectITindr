package com.r3d1r4ph.mobile_lab2_itindr.serverapi.user

import com.r3d1r4ph.mobile_lab2_itindr.serverapi.chat.message.UserResponse
import com.r3d1r4ph.mobile_lab2_itindr.serverapi.profile.ProfileResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface UserInterface {

    @GET("v1/user")
    suspend fun user(@Query("limit") limit: Int, @Query("offset") offset: Int): Response<List<ProfileResponse>>
}