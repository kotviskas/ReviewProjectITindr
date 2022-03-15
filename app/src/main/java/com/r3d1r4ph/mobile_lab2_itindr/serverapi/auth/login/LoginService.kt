package com.r3d1r4ph.mobile_lab2_itindr.serverapi.auth.login

import com.r3d1r4ph.mobile_lab2_itindr.serverapi.Network
import com.r3d1r4ph.mobile_lab2_itindr.serverapi.auth.AuthParams
import com.r3d1r4ph.mobile_lab2_itindr.serverapi.auth.TokenResponse
import com.r3d1r4ph.mobile_lab2_itindr.utils.resultwrapper.getWrappedResult
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

class LoginService {

    private val api: LoginInterface = Network.retrofit.create(LoginInterface::class.java)

    suspend fun login(
        authParams: AuthParams
        //todo
       // onSuccess: (params: TokenResponse) -> Unit,
        //onFailure: (response: String) -> Unit
    ) = getWrappedResult { api.login(authParams) }

}