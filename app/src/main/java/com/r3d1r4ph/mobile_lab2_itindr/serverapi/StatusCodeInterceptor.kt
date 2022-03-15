package com.r3d1r4ph.mobile_lab2_itindr.serverapi

import okhttp3.Interceptor
import okhttp3.Response
import java.lang.Exception
import java.net.UnknownHostException

class StatusCodeInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        return try {
            handleResponse(chain.proceed(request))
        } catch (e: UnknownHostException){
            throw Exception("Lol what a host!")
        }
    }

    private fun handleResponse(response: Response): Response{
        when (response.code){
            in 200..300 -> Unit
            401 -> Unit // refresh token
            else -> Unit //throw Exception(response.body?.string())
        }

        return response
    }
}