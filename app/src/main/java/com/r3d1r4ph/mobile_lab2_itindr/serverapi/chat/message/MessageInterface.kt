package com.r3d1r4ph.mobile_lab2_itindr.serverapi.chat.message

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface MessageInterface {

    @GET("v1/chat/{chatId}/message")
    fun getMessage(
        @Path("chatId") chatId: String,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): Call<List<MessageResponse>>

    @Multipart
    @POST("v1/chat/{chatId}/message")
    fun postMessage(
        @Path("chatId") chatId: String,
        @Part messageText: MultipartBody.Part?,
        @Part attachments: MultipartBody.Part?
    ): Call<MessageResponse>
}