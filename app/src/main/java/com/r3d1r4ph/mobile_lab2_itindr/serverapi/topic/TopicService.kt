package com.r3d1r4ph.mobile_lab2_itindr.serverapi.topic

import com.r3d1r4ph.mobile_lab2_itindr.serverapi.Network
import com.r3d1r4ph.mobile_lab2_itindr.utils.resultwrapper.getWrappedResult
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TopicService {
    private val api: TopicInterface = Network.retrofit.create(TopicInterface::class.java)
    //todo
    suspend fun topic(
//        onSuccess: (params: List<TopicResponse>) -> Unit,
//        onFailure: (response: String) -> Unit
    ) = getWrappedResult { api.topic() }
//            .enqueue(object : Callback<List<TopicResponse>> {
//            override fun onResponse(
//                call: Call<List<TopicResponse>>,
//                response: Response<List<TopicResponse>>
//            ) {
//                if (response.isSuccessful) {
//                    response.body()?.let(onSuccess)
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
//            override fun onFailure(call: Call<List<TopicResponse>>, t: Throwable) {
//                t.message?.let { onFailure.invoke(it) }
//            }
//
//        })

}