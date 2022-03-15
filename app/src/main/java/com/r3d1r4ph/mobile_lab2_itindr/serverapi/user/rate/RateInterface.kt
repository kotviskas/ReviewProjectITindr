package com.r3d1r4ph.mobile_lab2_itindr.serverapi.user.rate

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

interface RateInterface {

    @POST("v1/user/{userId}/like")
    suspend fun like(@Path("userId") userId: String): Response<LikeResponse>

    @POST("v1/user/{userId}/dislike")
    suspend fun dislike(@Path("userId") userId: String): Response<Unit>
}