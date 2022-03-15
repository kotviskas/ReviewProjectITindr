package com.r3d1r4ph.mobile_lab2_itindr.serverapi

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.r3d1r4ph.mobile_lab2_itindr.BuildConfig
import com.r3d1r4ph.mobile_lab2_itindr.database.Database
import com.r3d1r4ph.mobile_lab2_itindr.database.dao.AuthDao
import com.r3d1r4ph.mobile_lab2_itindr.repository.auth.AuthDBRepository
import com.r3d1r4ph.mobile_lab2_itindr.utils.SharedPreferencesUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

object Network {

    fun transferAccessToken(): String {
        runBlocking {
            accessToken = authDBRepository.getAccessToken()
        }
        return "Bearer $accessToken"
    }
    lateinit var authDBRepository: AuthDBRepository
    var accessToken: String? = null
    private const val BASE_URL = "http://193.38.50.175/itindr/api/mobile/"
    const val HEADER_TOKEN_ID = "Authorization"

    private val mediaType = "application/json".toMediaType()

    private val client = OkHttpClient.Builder()
        .addInterceptor(StatusCodeInterceptor())
        .addInterceptor(Interceptor { chain ->
            val request = chain.request()
            val requestBuilder = request.newBuilder()

            if (!request.url.toString().contains("v1/auth/login")
                && !request.url.toString().contains("v1/auth/register")
            ) {
                requestBuilder.addHeader("Authorization", transferAccessToken())
            }

            chain.proceed(requestBuilder.build())
        })
        .authenticator(TokenAuthenticator())
        .addInterceptor(HttpLoggingInterceptor().apply {
            setLevel(if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE)
        }) // setLevel(HttpLoggingInterceptor.Level.BODY)
        .readTimeout(30, TimeUnit.SECONDS)
        .connectTimeout(30, TimeUnit.SECONDS)
        .build()

    @ExperimentalSerializationApi
    val retrofit = Retrofit.Builder()
        .client(client)
        .baseUrl(BASE_URL)
        .addConverterFactory(Json.asConverterFactory(mediaType))
        .build()
}