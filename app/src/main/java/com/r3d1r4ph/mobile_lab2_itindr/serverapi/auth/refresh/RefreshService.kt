package com.r3d1r4ph.mobile_lab2_itindr.serverapi.auth.refresh

import com.r3d1r4ph.mobile_lab2_itindr.serverapi.Network
import com.r3d1r4ph.mobile_lab2_itindr.serverapi.auth.TokenResponse
import com.r3d1r4ph.mobile_lab2_itindr.utils.SharedPreferencesUtils
import org.json.JSONObject
import java.lang.Exception

class RefreshService {

    private val api: RefreshInterface = Network.retrofit.create(RefreshInterface::class.java)

    fun refresh(
        onSuccess: (params: TokenResponse) -> Unit,
        onFailure: (response: String) -> Unit
    ) {
        val response = api.refresh(
            RefreshParams(SharedPreferencesUtils.getRefreshToken())
        )
            .execute()

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
}