package com.r3d1r4ph.mobile_lab2_itindr.serverapi.auth.logout

import com.r3d1r4ph.mobile_lab2_itindr.serverapi.Network
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

//todo
class LogoutController {
    private val api: LogoutInterface = Network.retrofit.create(LogoutInterface::class.java)

    fun logout(onSuccess: () -> Unit, onFailure: () -> Unit) {
        api.logout()
            .enqueue(object : Callback<Unit> {
                override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                    onSuccess.invoke()
                }

                override fun onFailure(call: Call<Unit>, t: Throwable) {
                    onFailure.invoke()
                }
            })
    }
}