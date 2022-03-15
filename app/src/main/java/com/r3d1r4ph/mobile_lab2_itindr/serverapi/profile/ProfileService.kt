package com.r3d1r4ph.mobile_lab2_itindr.serverapi.profile

import com.r3d1r4ph.mobile_lab2_itindr.serverapi.Network
import com.r3d1r4ph.mobile_lab2_itindr.utils.resultwrapper.getWrappedResult
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileService {
    private val api: ProfileInterface = Network.retrofit.create(ProfileInterface::class.java)
    //todo
    suspend fun getProfile(
    //    onSuccess: (params: ProfileResponse) -> Unit,
   //     onFailure: (response: String) -> Unit
    ) = getWrappedResult { api.getProfile() }
//            .enqueue(object : Callback<ProfileResponse> {
//                override fun onResponse(
//                    call: Call<ProfileResponse>,
//                    response: Response<ProfileResponse>
//                ) {
//                    if (response.isSuccessful) {
//                        response.body()?.let(onSuccess)
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
//                override fun onFailure(call: Call<ProfileResponse>, t: Throwable) {
//                    t.message?.let { onFailure.invoke(it) }
//                }
//
//            })

    //todo
    suspend fun patchProfile(
        profileParams: ProfileParams
//        onSuccess: (params: ProfileResponse) -> Unit,
//        onFailure: (response: String) -> Unit
    ) = getWrappedResult { api.patchProfile(profileParams = profileParams) }
//            .enqueue(object : Callback<ProfileResponse> {
//                override fun onResponse(
//                    call: Call<ProfileResponse>,
//                    response: Response<ProfileResponse>
//                ) {
//                    if (response.isSuccessful) {
//                        response.body()?.let(onSuccess)
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
//                override fun onFailure(call: Call<ProfileResponse>, t: Throwable) {
//                    t.message?.let { onFailure.invoke(it) }
//                }
//
//            })

}