package com.r3d1r4ph.mobile_lab2_itindr.serverapi.profile.avatar

import com.r3d1r4ph.mobile_lab2_itindr.serverapi.Network
import com.r3d1r4ph.mobile_lab2_itindr.utils.resultwrapper.getWrappedResult
import okhttp3.MultipartBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AvatarService {
    private val api: AvatarInterface = Network.retrofit.create(AvatarInterface::class.java)
    //todo
    suspend fun postAvatar(
        multipartBody: MultipartBody.Part
//        onSuccess: () -> Unit,
//        onFailure: (response: String) -> Unit
    ) = api.postAvatar(multipartBody)
//            .enqueue(object : Callback<Unit> {
//                override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
//                    if (response.isSuccessful) {
//                        onSuccess.invoke()
//                    } else {
//                        try {
//                            response.errorBody()?.let {
//                                val jObjError = JSONObject(it.string())
//                                onFailure.invoke(jObjError.getString("message"))
//                            }
//                        } catch (e: Exception) {
//                            e.message?.let { onFailure.invoke(it) }
//                        }
//                    }
//                }
//
//                override fun onFailure(call: Call<Unit>, t: Throwable) {
//                    t.message?.let { onFailure.invoke(it) }
//                }
//
//            })


    suspend fun deleteAvatar(
//        onSuccess: () -> Unit,
//        onFailure: (response: String) -> Unit
    ) = getWrappedResult { api.deleteAvatar() }
//            .enqueue(object : Callback<Unit> {
//            override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
//                if (response.isSuccessful) {
//                    onSuccess.invoke()
//                } else {
//                    try {
//                        response.errorBody()?.let {
//                            val jObjError = JSONObject(it.string())
//                            onFailure.invoke(jObjError.getString("message"))
//                        }
//                    } catch (e: Exception) {
//                        e.message?.let { onFailure.invoke(it) }
//                    }
//                }
//            }
//
//            override fun onFailure(call: Call<Unit>, t: Throwable) {
//                t.message?.let { onFailure.invoke(it) }
//            }
//
//        })

}