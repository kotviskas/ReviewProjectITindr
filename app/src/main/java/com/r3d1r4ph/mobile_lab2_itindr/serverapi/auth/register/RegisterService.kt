package com.r3d1r4ph.mobile_lab2_itindr.serverapi.auth.register

import android.widget.Toast
import com.r3d1r4ph.mobile_lab2_itindr.serverapi.Network
import com.r3d1r4ph.mobile_lab2_itindr.serverapi.auth.AuthParams
import com.r3d1r4ph.mobile_lab2_itindr.serverapi.auth.TokenResponse
import com.r3d1r4ph.mobile_lab2_itindr.utils.resultwrapper.getWrappedResult
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

class RegisterService {

    private val api: RegisterInterface = Network.retrofit.create(RegisterInterface::class.java)

    //todo
    suspend fun register(
        authParams: AuthParams
   //     onSuccess: (params: TokenResponse) -> Unit,
    //    onFailure: (response: String) -> Unit
    ) = getWrappedResult { api.register(authParams) }
//            .enqueue(object : Callback<TokenResponse> {
//            override fun onResponse(
//                call: Call<TokenResponse>,
//                response: Response<TokenResponse>
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
//            override fun onFailure(call: Call<TokenResponse>, t: Throwable) {
//                t.message?.let { onFailure.invoke(it) }
//            }
//        })

}