package com.r3d1r4ph.mobile_lab2_itindr.serverapi.chat

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ChatInterface {

    @GET("v1/chat")
    suspend fun getChat(): Response<List<ChatListResponse>>

    @POST("v1/chat")
    suspend fun postChat(@Body chatParams: ChatParams): Response<ChatResponse>
}