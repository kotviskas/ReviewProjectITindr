package com.r3d1r4ph.mobile_lab2_itindr.serverapi.profile.avatar

import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface AvatarInterface {

    @Multipart
    @POST("v1/profile/avatar")
    suspend fun postAvatar(@Part avatar: MultipartBody.Part): Response<Unit>

    @DELETE("v1/profile/avatar")
    suspend fun deleteAvatar(): Response<Unit>
}