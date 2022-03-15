package com.r3d1r4ph.mobile_lab2_itindr.serverapi.chat

import com.r3d1r4ph.mobile_lab2_itindr.serverapi.Network
import com.r3d1r4ph.mobile_lab2_itindr.utils.resultwrapper.getWrappedResult
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChatService {
    private val api = Network.retrofit.create(ChatInterface::class.java)
    //todo
    suspend fun getChat(
      //  onSuccess: (params: List<ChatListResponse>) -> Unit,
    //    onFailure: (response: String) -> Unit
    ) = getWrappedResult { api.getChat() }
//            .enqueue(object : Callback<List<ChatListResponse>> {
//            override fun onResponse(
//                call: Call<List<ChatListResponse>>,
//                response: Response<List<ChatListResponse>>
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
//            override fun onFailure(call: Call<List<ChatListResponse>>, t: Throwable) {
//                t.message?.let { onFailure.invoke(it) }
//            }
//
//        })


    suspend fun postChat(chatParams: ChatParams) =
        getWrappedResult { api.postChat(chatParams = chatParams) }
}