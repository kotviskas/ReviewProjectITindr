package com.r3d1r4ph.mobile_lab2_itindr.serverapi.auth.logout

import com.r3d1r4ph.mobile_lab2_itindr.serverapi.Network
import retrofit2.Call
import retrofit2.http.DELETE
import retrofit2.http.Header

interface LogoutInterface {

    @DELETE ("v1/auth/logout")
    fun logout(): Call<Unit>
}