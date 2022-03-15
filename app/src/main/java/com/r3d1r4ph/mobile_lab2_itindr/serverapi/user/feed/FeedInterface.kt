package com.r3d1r4ph.mobile_lab2_itindr.serverapi.user.feed

import com.r3d1r4ph.mobile_lab2_itindr.serverapi.profile.ProfileResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET

interface FeedInterface {

    @GET("v1/user/feed")
    suspend fun feed(): Response<List<ProfileResponse>>
}