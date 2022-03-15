package com.r3d1r4ph.mobile_lab2_itindr.serverapi.chat.message

import com.r3d1r4ph.mobile_lab2_itindr.serverapi.Network
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

class MessageController {
    private val api = Network.retrofit.create(MessageInterface::class.java)

    fun getMessage(
        chatId: String,
        limit: Int,
        offset: Int,
        onSuccess: (params: List<MessageResponse>) -> Unit,
        onFailure: (response: String) -> Unit
    ) {
        api.getMessage(chatId = chatId, limit = limit, offset = offset)
            .enqueue(object : Callback<List<MessageResponse>> {
                override fun onResponse(
                    call: Call<List<MessageResponse>>,
                    response: Response<List<MessageResponse>>
                ) {
                    if (response.isSuccessful) {
                        response.body()?.let(onSuccess)
                    } else {
                        try {
                            response.errorBody()?.let {
                                val jObjError = JSONObject(it.string())
                                onFailure.invoke(jObjError.getString("message"))
                            }
                        } catch (e: Exception) {
                            e.message?.let { onFailure.invoke(it) }
                        }
                    }
                }

                override fun onFailure(call: Call<List<MessageResponse>>, t: Throwable) {
                    t.message?.let { onFailure.invoke(it) }
                }

            })
    }

    fun postMessage(
        chatId: String,
        messageText: MultipartBody.Part?,
        attachments: MultipartBody.Part?,
        onSuccess: (params: MessageResponse) -> Unit,
        onFailure: (response: String) -> Unit
    ) {
        api.postMessage(chatId = chatId, messageText = messageText, attachments = attachments)
            .enqueue(object : Callback<MessageResponse> {
                override fun onResponse(
                    call: Call<MessageResponse>,
                    response: Response<MessageResponse>
                ) {
                    if (response.isSuccessful) {
                        response.body()?.let(onSuccess)
                    } else {
                        try {
                            response.errorBody()?.let {
                                val jObjError = JSONObject(it.string())
                                onFailure.invoke(jObjError.getString("message"))
                            }
                        } catch (e: Exception) {
                            e.message?.let { onFailure.invoke(it) }
                        }
                    }
                }

                override fun onFailure(call: Call<MessageResponse>, t: Throwable) {
                    t.message?.let { onFailure.invoke(it) }
                }

            })
    }
}