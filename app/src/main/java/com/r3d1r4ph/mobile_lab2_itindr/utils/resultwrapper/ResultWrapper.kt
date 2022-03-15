package com.r3d1r4ph.mobile_lab2_itindr.utils.resultwrapper

import org.json.JSONObject
import retrofit2.Response

sealed class ResultWrapper<out T> {
    data class Success<out T>(val value: T?) : ResultWrapper<T>()
    data class Error(val error: String?) : ResultWrapper<Nothing>()
}

suspend fun <T> getWrappedResult(apiCall: suspend () -> Response<T>): ResultWrapper<T> {
    try {
        val response = apiCall.invoke()
        if (response.isSuccessful) {
            return ResultWrapper.Success(response.body())
        } else {
            response.errorBody()?.let {
                val jObjError = JSONObject(it.string())
                return ResultWrapper.Error(jObjError.getString("message"))
            }
            throw Exception("Unknown error")
        }
    } catch (e: Exception) {
        return ResultWrapper.Error(e.message)
    }
}
