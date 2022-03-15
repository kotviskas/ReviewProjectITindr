package com.r3d1r4ph.mobile_lab2_itindr.serverapi.topic

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET

interface TopicInterface {

    @GET("v1/topic")
    suspend fun topic(): Response<List<TopicResponse>>
}